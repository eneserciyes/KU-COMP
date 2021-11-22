#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>
#include <stdlib.h>
#include <termios.h>            //termios, TCSANOW, ECHO, ICANON
#include <string.h>
#include <stdbool.h>
#include <errno.h>
#include <sys/stat.h>
#include <ctype.h>

#include "include/hashmap.h"

const char *sysname = "seashell";
const char *shortdir_file = "/tmp/.shortdir"; // Shortdir file that keeps associations
const char *shortdir_delimiter = ",*"; // Delimiter used in shortdir file to separate alias and path.

// A struct to represent shortdir associations
struct shortdir {
    char *alias;
    char *path;
};

// A function to compare hashmap keys
int shortdir_compare(const void *a, const void *b, void *udata) {
    const struct shortdir *sa = a;
    const struct shortdir *sb = b;
    return strcmp(sa->alias, sb->alias);
}

// A hash function for our hashmap
uint64_t shortdir_hash(const void *item, uint64_t seed0, uint64_t seed1) {
    const struct shortdir *shortdir = item;
    return hashmap_sip(shortdir->alias, strlen(shortdir->alias), seed0, seed1);
}


FILE *f; // Global FILE variable that points to the shortdir file

struct hashmap *shortdir_map; // Global hashmap pointer that holds shortdir associations in memory.


// ALL SHORTDIR RELATED FUNCTIONS

// Function to add a shortdir to hashmap
void add_shortdir_alias(char *alias, char *path) {
    // First copy alias and path to new pointers to avoid unwanted mutations.
    char *alias_set = malloc(strlen(alias) + 1);
    char *path_set = malloc(strlen(path) + 1);
    strcpy(alias_set, alias);
    strcpy(path_set, path);

    // Add function of the hashmap implementation we used. First element in the struct is considered the key.
    hashmap_set(shortdir_map, &(struct shortdir) {.alias=alias_set, .path=path_set});
}

// Function to delete a shortdir from hashmap
void delete_shortdir_alias(char *alias) {
    hashmap_delete(shortdir_map, &(struct shortdir) {.alias=alias});
}

// Function to delete all shortdirs
void clear_shortdir_aliases() {
    hashmap_clear(shortdir_map, true);
}

char *get_shortdir_path(char *alias) {
    // Implementation returns the whole struct, only return the path..
    struct shortdir *shortdir = hashmap_get(shortdir_map, &(struct shortdir) {.alias=alias});
    return shortdir->path;
}

bool shortdir_list_iter(const void *item, void *udata) {
    // Prints the associations
    const struct shortdir *shortdir = item;
    printf("%s: %s\n", shortdir->alias, shortdir->path);
    return true;
}

void list_shortdir_aliases() {
    // Hashmap implementation iteration function. shortdir_list_iter is called everytime for each struct in the hashmap.
    hashmap_scan(shortdir_map, shortdir_list_iter, NULL);
}

// Shortdir function to save each association to the shortdir file
bool shortdir_save_iter(const void *item, void *udata) {
    const struct shortdir *shortdir = item;
    char *line = malloc(512);
    memset(line, 0, strlen(line)); // set all bytes to 0

    strcat(line, shortdir->alias);
    strcat(line, shortdir_delimiter);
    strcat(line, shortdir->path);
    strcat(line, "\n");

    return fputs(line, f);
}

// Scans the shortdir_map to save each association
void save_shortdir_aliases() {
    f = fopen(shortdir_file, "w+");
    hashmap_scan(shortdir_map, shortdir_save_iter, NULL);
    fclose(f);
};

void read_shortdir_aliases() {
    // Open the shortdir file
    f = fopen(shortdir_file, "r+");
    if (f == NULL) {
        // Create if it does not exist
        f = fopen(shortdir_file, "w+");
    } else {
        // Create line array
        char line[512];
        // Read each line
        while (fgets(line, sizeof(line), f)) {
            // Omit the newline character
            if (line[strlen(line) - 1] == '\n')
                line[strlen(line) - 1] = '\0';
            // Get alias and path using the shortdir delimiter.
            char *alias = strtok(line, shortdir_delimiter);
            char *path = strtok(NULL, shortdir_delimiter);
            // Add to hashmap
            add_shortdir_alias(alias, path);
        }
    }
    fclose(f);
}

enum return_codes {
    SUCCESS = 0,
    EXIT = 1,
    UNKNOWN = 2,
};
struct command_t {
    char *name;
    bool background;
    bool auto_complete;
    int arg_count;
    char **args;
    char *redirects[3]; // in/out redirection
    struct command_t *next; // for piping
};

// Generic read into buffer function.
char *read_files(long size, FILE *hfile) {
    char *buffer = (char *) malloc(sizeof(char) * size + 1);
    memset(buffer, 0, sizeof(char) * size + 1);
    if (size != fread(buffer, sizeof(char), size, hfile)) {
        fputs("Reading error!\n", stderr);
        exit(3);
    }
    return buffer;
}
// PART 5: LINE BY LINE COMPARISON FUNCTION
int compare_txt_files(char *filename1, char *filename2) {
    // Get the filename extensions
    char *file1_ext = &(filename1[strlen(filename1) - 3]);
    char *file2_ext = &(filename2[strlen(filename2) - 3]);
    // Return error if they are not .txt
    if (strcmp(file1_ext, "txt") != 0 || strcmp(file2_ext, "txt") != 0) {
        printf("You need to pass in files with .txt extensions!!\n");
        return UNKNOWN;
    }
    // Open files
    FILE *file1 = fopen(filename1, "r");
    FILE *file2 = fopen(filename2, "r");
    if (file1 == NULL || file2 == NULL) {
        printf("Cannot find the files you give!!\n");
        return UNKNOWN;
    }
    // Create line buffers to hold each line
    char line1[1024];
    char line2[1024];

    // Two counter: one for counting different lines, one for line numbers.
    int count = 0;
    int line_no = 0;
    while (1) {
        line_no++;
        // Read lines and store if one has ended.
        bool is_line_read_1 = fgets(line1, sizeof(line1), file1);
        bool is_line_read_2 = fgets(line2, sizeof(line2), file2);
        // Omit end of line chars
        if (line1[strlen(line1) - 1] == '\n')
            line1[strlen(line1) - 1] = '\0';

        if (line2[strlen(line2) - 1] == '\n')
            line2[strlen(line2) - 1] = '\0';
        // If both files have ended, break the loop
        if (!is_line_read_1 && !is_line_read_2) {
            break;
        }
        // If not, check the lines and print if they are different.
        if (strcmp(line1, line2) != 0) {
            count++;
            // Print __none__, if a file has ended.
            printf("%s:Line %d: %s\n", filename1, line_no, is_line_read_1 ? line1 : "__none__");
            printf("%s:Line %d: %s\n", filename2, line_no, is_line_read_2 ? line2 : "__none__");
        }
    }
    if (count > 0)
        printf("%d different lines found\n", count);
    else
        printf("Two files are identical.\n");
    return SUCCESS;
}

// PART 5: Binary comparison function
int compare_bin_files(char *filename1, char *filename2) {
    // Open files
    FILE *file1 = fopen(filename1, "rb");
    FILE *file2 = fopen(filename2, "rb");
    if (file1 == NULL || file2 == NULL) {
        // Error if files cannot be found
        printf("Cannot find the file you give: %s\n", filename1);
        return UNKNOWN;
    }
    // Get the file sizes using fseek
    fseek(file1, 0, SEEK_END);
    fseek(file2, 0, SEEK_END);

    long size1 = ftell(file1);
    long size2 = ftell(file2);

    // Take the pointer to the start of the file
    rewind(file1);
    rewind(file2);

    // Read files to buffers by allocating just enough memory
    char *buffer1 = read_files(size1, file1);
    char *buffer2 = read_files(size2, file2);

    // Get the max and min size, used for comparison below
    size_t max_size = (size1 > size2 ? size1 : size2);
    size_t min_size = (size1 > size2 ? size2 : size1);

    int count = 0; // Initialize counter to count different bytes
    // Go to the max size
    for (int i = 0; i < max_size; i++) {
        // If one file ended, just add to the count
        if (i > min_size)
            count++;
        else {
            // If not, check if two bytes are the same
            if (buffer1[i] != buffer2[i])
                count++;
        }
    }
    // Print the result.
    if (count == 0)
        printf("Two files are identical.\n");
    else
        printf("%d bytes are different in two files\n", count);

    return SUCCESS;
}
// Custom written case insensitive string comparator
bool strcmp_ncase(char *s1, char *s2) {
    char *s1_copy = strdup(s1);
    char *s2_copy = strdup(s2);
    while (true) {
        if (tolower(*s1_copy) != tolower(*s2_copy))
            return false;
        if (*s1_copy == '\0' && *s2_copy == '\0')
            return true;
        s1_copy++;
        s2_copy++;
    }
}

// PART 3: Highlighting function
int highlight(char *word, char *color, char *filename) {
    // Set the color code according to the input.
    int color_code = 0;
    if (strcmp(color, "r") == 0)
        color_code = 31;
    else if (strcmp(color, "g") == 0)
        color_code = 32;
    else if (strcmp(color, "b") == 0)
        color_code = 34;
    else {
        printf("Unknown color!\n");
        return UNKNOWN;
    }

    FILE *file1 = fopen(filename, "rb");
    if (file1 == NULL) {
        printf("Cannot find the file you give: %s\n", filename);
        return UNKNOWN;
    }
    char line[512];
    const char *delim = " ";
    // Read each line
    while (fgets(line, sizeof(line), file1)) {
        // Omit the newline character
        if (line[strlen(line) - 1] == '\n')
            line[strlen(line) - 1] = '\0';

        char *lineCopy = strdup(line); // Copy line for the second pass
        char *token = strtok(line, delim);

        bool found_flag = false;
        // First pass over the line to see if the word exists.
        while (token != NULL) {
            if (strcmp_ncase(token, word)) {
                found_flag = true;
                break;
            }
            token = strtok(NULL, delim);
        }
        if (found_flag) {
            // If found, second pass to print the lines.
            token = strtok(lineCopy, delim);
            while (token != NULL) {
                // Using a custom written function to compare strings case insensitive.
                if (strcmp_ncase(token, word)) {
                    printf("\033[;%dm%s \033[0m", color_code, token);
                } else {
                    printf("%s ", token);
                }
                token = strtok(NULL, delim);
            }
            printf("\n");
        }
    }
    return SUCCESS;
}

/**
 * Prints a command struct
 * @param struct command_t *
 */
void print_command(struct command_t *command) {
    int i = 0;
    printf("Command: <%s>\n", command->name);
    printf("\tIs Background: %s\n", command->background ? "yes" : "no");
    printf("\tNeeds Auto-complete: %s\n", command->auto_complete ? "yes" : "no");
    printf("\tRedirects:\n");
    for (i = 0; i < 3; i++)
        printf("\t\t%d: %s\n", i, command->redirects[i] ? command->redirects[i] : "N/A");
    printf("\tArguments (%d):\n", command->arg_count);
    for (i = 0; i < command->arg_count; ++i)
        printf("\t\tArg %d: %s\n", i, command->args[i]);
    if (command->next) {
        printf("\tPiped to:\n");
        print_command(command->next);
    }
}

/**
 * Release allocated memory of a command
 * @param  command [description]
 * @return         [description]
 */
int free_command(struct command_t *command) {
    if (command->arg_count) {
        for (int i = 0; i < command->arg_count; ++i)
            free(command->args[i]);
        free(command->args);
    }
    for (int i = 0; i < 3; ++i)
        if (command->redirects[i])
            free(command->redirects[i]);
    if (command->next) {
        free_command(command->next);
        command->next = NULL;
    }
    free(command->name);
    free(command);
    return 0;
}

/**
 * Show the command prompt
 * @return [description]
 */
int show_prompt() {
    char cwd[1024], hostname[1024];
    gethostname(hostname, sizeof(hostname));
    getcwd(cwd, sizeof(cwd));
    printf("%s@%s:%s %s$ ", getenv("USER"), hostname, cwd, sysname);
    return 0;
}

/**
 * Parse a command string into a command struct
 * @param  buf     [description]
 * @param  command [description]
 * @return         0
 */
int parse_command(char *buf, struct command_t *command) {
    const char *splitters = " \t"; // split at whitespace
    int index, len;
    len = strlen(buf);
    while (len > 0 && strchr(splitters, buf[0]) != NULL) // trim left whitespace
    {
        buf++;
        len--;
    }
    while (len > 0 && strchr(splitters, buf[len - 1]) != NULL)
        buf[--len] = 0; // trim right whitespace

    if (len > 0 && buf[len - 1] == '?') // auto-complete
        command->auto_complete = true;
    if (len > 0 && buf[len - 1] == '&') // background
        command->background = true;

    char *pch = strtok(buf, splitters);
    command->name = (char *) malloc(strlen(pch) + 1);
    if (pch == NULL)
        command->name[0] = 0;
    else
        strcpy(command->name, pch);

    command->args = (char **) malloc(sizeof(char *));

    int redirect_index;
    int arg_index = 0;
    char temp_buf[1024], *arg;
    while (1) {
        // tokenize input on splitters
        pch = strtok(NULL, splitters);
        if (!pch) break;
        arg = temp_buf;
        strcpy(arg, pch);
        len = strlen(arg);

        if (len == 0) continue; // empty arg, go for next
        while (len > 0 && strchr(splitters, arg[0]) != NULL) // trim left whitespace
        {
            arg++;
            len--;
        }
        while (len > 0 && strchr(splitters, arg[len - 1]) != NULL) arg[--len] = 0; // trim right whitespace
        if (len == 0) continue; // empty arg, go for next

        // piping to another command
        if (strcmp(arg, "|") == 0) {
            struct command_t *c = malloc(sizeof(struct command_t));
            int l = strlen(pch);
            pch[l] = splitters[0]; // restore strtok termination
            index = 1;
            while (pch[index] == ' ' || pch[index] == '\t') index++; // skip whitespaces

            parse_command(pch + index, c);
            pch[l] = 0; // put back strtok termination
            command->next = c;
            continue;
        }

        // background process
        if (strcmp(arg, "&") == 0)
            continue; // handled before

        // handle input redirection
        redirect_index = -1;
        if (arg[0] == '<')
            redirect_index = 0;
        if (arg[0] == '>') {
            if (len > 1 && arg[1] == '>') {
                redirect_index = 2;
                arg++;
                len--;
            } else redirect_index = 1;
        }
        if (redirect_index != -1) {
            command->redirects[redirect_index] = malloc(len);
            strcpy(command->redirects[redirect_index], arg + 1);
            continue;
        }

        // normal arguments
        if (len > 2 && ((arg[0] == '"' && arg[len - 1] == '"')
                        || (arg[0] == '\'' && arg[len - 1] == '\''))) // quote wrapped arg
        {
            arg[--len] = 0;
            arg++;
        }
        command->args = (char **) realloc(command->args, sizeof(char *) * (arg_index + 1));
        command->args[arg_index] = (char *) malloc(len + 1);
        strcpy(command->args[arg_index++], arg);
    }
    command->arg_count = arg_index;
    return 0;
}

void prompt_backspace() {
    putchar(8); // go back 1
    putchar(' '); // write empty over
    putchar(8); // go back 1 again
}

/**
 * Prompt a command from the user
 * @param  buf      [description]
 * @param  buf_size [description]
 * @return          [description]
 */
int prompt(struct command_t *command) {
    int index = 0;
    char c;
    char buf[4096];
    static char oldbuf[4096];

    // tcgetattr gets the parameters of the current terminal
    // STDIN_FILENO will tell tcgetattr that it should write the settings
    // of stdin to oldt
    static struct termios backup_termios, new_termios;
    tcgetattr(STDIN_FILENO, &backup_termios);
    new_termios = backup_termios;
    // ICANON normally takes care that one line at a time will be processed
    // that means it will return if it sees a "\n" or an EOF or an EOL
    new_termios.c_lflag &= ~(ICANON | ECHO); // Also disable automatic echo. We manually echo each char.
    // Those new settings will be set to STDIN
    // TCSANOW tells tcsetattr to change attributes immediately.
    tcsetattr(STDIN_FILENO, TCSANOW, &new_termios);


    //FIXME: backspace is applied before printing chars
    show_prompt();
    int multicode_state = 0;
    buf[0] = 0;
    while (1) {
        c = getchar();
        // printf("Keycode: %u\n", c); // DEBUG: uncomment for debugging

        if (c == 9) // handle tab
        {
            buf[index++] = '?'; // autocomplete
            break;
        }

        if (c == 127) // handle backspace
        {
            if (index > 0) {
                prompt_backspace();
                index--;
            }
            continue;
        }
        if (c == 27 && multicode_state == 0) // handle multi-code keys
        {
            multicode_state = 1;
            continue;
        }
        if (c == 91 && multicode_state == 1) {
            multicode_state = 2;
            continue;
        }
        if (c == 65 && multicode_state == 2) // up arrow
        {
            int i;
            while (index > 0) {
                prompt_backspace();
                index--;
            }
            for (i = 0; oldbuf[i]; ++i) {
                putchar(oldbuf[i]);
                buf[i] = oldbuf[i];
            }
            index = i;
            continue;
        } else
            multicode_state = 0;

        putchar(c); // echo the character
        buf[index++] = c;
        if (index >= sizeof(buf) - 1) break;
        if (c == '\n') // enter key
            break;
        if (c == 4) // Ctrl+D
            return EXIT;
    }
    if (index > 0 && buf[index - 1] == '\n') // trim newline from the end
        index--;
    buf[index++] = 0; // null terminate string

    strcpy(oldbuf, buf);

    parse_command(buf, command);

    // print_command(command); // DEBUG: uncomment for debugging

    // restore the old settings
    tcsetattr(STDIN_FILENO, TCSANOW, &backup_termios);
    return SUCCESS;
}

int process_command(struct command_t *command);

int main() {
    // Initialize the shortdir hashmap used for storing associations in the memory.
    shortdir_map = hashmap_new(sizeof(struct shortdir), 0, 0, 0,
                               shortdir_hash, shortdir_compare, NULL);
    // Read from the file at the start.
    read_shortdir_aliases();

    while (1) {
        struct command_t *command = malloc(sizeof(struct command_t));
        memset(command, 0, sizeof(struct command_t)); // set all bytes to 0

        int code;
        code = prompt(command);
        if (code == EXIT) break;

        code = process_command(command);
        if (code == EXIT) break;

        free_command(command);
    }
    // Save shortdirs to file if exited
    save_shortdir_aliases();

    printf("\n");
    return 0;
}

int process_command(struct command_t *command) {
    int r;
    if (strcmp(command->name, "") == 0) return SUCCESS;

    if (strcmp(command->name, "exit") == 0)
        return EXIT;

    if (strcmp(command->name, "cd") == 0) {
        if (command->arg_count > 0) {
            r = chdir(command->args[0]);
            if (r == -1)
                printf("-%s: %s: %s\n", sysname, command->name, strerror(errno));
            return SUCCESS;
        }
    }

    // PART 2
    // Shortdir is implemented using an existing open source hashmap implementation.
    // Reference: https://github.com/tidwall/hashmap.c
    if (strcmp(command->name, "shortdir") == 0) {
        if (command->arg_count < 1) {
            printf("Usage: shortdir [clear|list|set|jump|del] [alias_name]\n");
            return UNKNOWN;
        }

        if (command->arg_count == 1) {
            if (strcmp(command->args[0], "clear") == 0) {
                clear_shortdir_aliases();
                save_shortdir_aliases();
                printf("shortdir cleared\n");
                return SUCCESS;
            } else if (strcmp(command->args[0], "list") == 0) {
                list_shortdir_aliases();
                return SUCCESS;
            }
        } else if (command->arg_count == 2) {
            char *alias = command->args[1];
            if (strcmp(command->args[0], "set") == 0) {
                // Get the current directory and save it to hashmap
                char cwd[1024];
                getcwd(cwd, sizeof(cwd));
                add_shortdir_alias(alias, cwd);
                // Associations are saved to file each time you add a new shortdir.
                save_shortdir_aliases();

                printf("'%s' path set as shortdir with alias '%s' \n", get_shortdir_path(alias), alias);
                return SUCCESS;
            } else if (strcmp(command->args[0], "jump") == 0) {
                char *path = get_shortdir_path(alias);
                r = chdir(path);
                // Directory not found error handling
                if (r == -1) {
                    printf("-%s: %s: %s\n", sysname, "shortdir jump", strerror(errno));
                    return EXIT;
                }
                return SUCCESS;

            } else if (strcmp(command->args[0], "del") == 0) {
                delete_shortdir_alias(alias);
                save_shortdir_aliases(); // File is updated when something is deleted.

                printf("'%s' shortdir deleted!\n", alias);
                return SUCCESS;
            }
        }
        printf("Usage: shortdir [clear|list|set|jump|del] [alias_name]\n");
        return UNKNOWN;
    }

    // PART 3: Highlight is implemented using Linux terminal color codes.
    if (strcmp(command->name, "highlight") == 0) {
        if (command->arg_count != 3){
            printf("Usage: highlight {word} [r|g|b : color] {filepath}\n");
            return UNKNOWN;
        }

        return highlight(command->args[0], command->args[1], command->args[2]);
    }

    // PART 5: kdiff implementation is done in the functions above.
    if (strcmp(command->name, "kdiff") == 0) {
        if (command->arg_count == 3) {
            if (strcmp(command->args[0], "-b") == 0) {
                return compare_bin_files(command->args[1], command->args[2]);
            } else if (strcmp(command->args[0], "-a") == 0) {
                return compare_txt_files(command->args[1], command->args[2]);
            }
        } else if (command->arg_count == 2) {
            return compare_txt_files(command->args[0], command->args[1]);
        }
        // Usage printed if argument counts are not correct.
        printf("Usage: kdiff [-a : txt files |-b : binary files | -a assumed if omitted] {filename1} {filename2}\n");
        return UNKNOWN;
    }

    // PART 6: CUSTOM COMMAND: Collatz Sequence Playing
    if (strcmp(command->name, "playCollatz") == 0) {
        if (command->arg_count != 2) {
            return UNKNOWN;
        }
        // create two variables to keep numbers given as arguments
        int number1, number2;
        number1 = atoi(command->args[0]);
        number2 = atoi(command->args[1]);

        // create two variables to keep number of steps needed to reach number 1
        int counter1 = 0, counter2 = 0;

        // Collatz sequence implementation for player 1
        while (number1 != 1) {
            printf("%d, ", number1);
            if (number1 % 2 == 0) {
                number1 = number1 / 2;
            } else {
                number1 = number1 * 3 + 1;
            }
            counter1 += 1;
        }
        printf("%d ", number1);
        printf("Player one reached %d, at %d steps", number1, counter1);
        printf("\n");

        // Collatz sequence implementation for player 2
        while (number2 != 1) {
            printf("%d, ", number2);
            if (number2 % 2 == 0) {
                number2 = number2 / 2;
            } else {
                number2 = number2 * 3 + 1;
            }
            counter2 += 1;
        }
        printf("%d ", number2);
        printf("Player two reached %d, at %d steps", number2, counter2);
        printf("\n");

        // check who the winner is
        if (counter1 < counter2) {
            printf("**********Player 1 wins**********\n");
            printf("********CONGRATULATIONS********");

        } else if (counter2 < counter1) {
            printf("**********Player 2 wins**********\n");
            printf("********CONGRATULATIONS********");
        } else {
            printf("**********EVEN STEVENS**********\n");
        }

        printf("\n");
        return SUCCESS;
    }

    pid_t pid = fork();
    if (pid == 0) // child
    {

        // PART 4

        // goodMorning implementation using CRONTAB and RHYTHM BOX
        if (strcmp(command->name, "goodMorning") == 0) {

            if (command->arg_count != 2) {
                return UNKNOWN;
            }

            // seperating hour and minute information taken from args[0]
            char *hh = strtok(command->args[0], ".");
            char *mm = strtok(NULL, ".");

            if (hh == NULL || mm == NULL) {
                return UNKNOWN;
            }
            //keep the music file information in the alarm variable
            char *alarm = command->args[1];

            // create crontab file and write the path and scheduled time into that file.
            // to play an mp3 file rhythmbox is used.
            // since crontab does not set environment variables automatically,
            // we add XDG_RUNTIME_DIR=/run/user/$(id -u)

            FILE *crontab_file;
            crontab_file = fopen("crontab_file", "w");
            fprintf(crontab_file, "SHELL=/bin/bash\n");
            fprintf(crontab_file, "PATH=%s\n", getenv("PATH"));
            fprintf(crontab_file, "%s %s * * * XDG_RUNTIME_DIR=/run/user/$(id -u) rhythmbox-client --play %s\n", mm,
                    hh, alarm);
            fclose(crontab_file);

            // to run the crontab file we created args array that we keep crontab information and
            // then call execvp with args to execute the process
            char *args[] = {"crontab", "crontab_file", NULL};
            execvp("/usr/bin/crontab", args);
            return SUCCESS;

        }

        // increase args size by 2
        command->args = (char **) realloc(
                command->args, sizeof(char *) * (command->arg_count += 2));

        // shift everything forward by 1
        for (int i = command->arg_count - 2; i > 0; --i)
            command->args[i] = command->args[i - 1];

        // set args[0] as a copy of name
        command->args[0] = strdup(command->name);
        // set args[arg_count-1] (last) to NULL
        command->args[command->arg_count - 1] = NULL;

        // PART 1

        // Execv implementation is done here by first getting the PATH environment variable and looping
        // over every directory separated by colons to find the command.
        char PATH[256];
        strcpy(PATH, getenv("PATH"));

        char *token = strtok(PATH, ":");
        while (token != NULL) {

            struct stat *file_prop = malloc(sizeof(struct stat));
            memset(file_prop, 0, sizeof(struct stat));

            char *command_path = malloc(256);
            memset(command_path, 0, strlen(command_path));

            strcat(command_path, token);
            strcat(command_path, "/");
            strcat(command_path, command->name);

            // Check if file is found and also executable using stat() function!!
            stat(command_path, file_prop);

            if (file_prop->st_mode & X_OK) {
                // Call execv if command is found and executable
                execv(command_path, command->args);
                break;
            }
            token = strtok(NULL, ":");
        }

    } else {
        if (!command->background)
            wait(0); // wait for child process to finish
        return SUCCESS;
    }

    printf("-%s: %s: command not found\n", sysname, command->name);
    return UNKNOWN;
}
