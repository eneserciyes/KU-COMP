import data.Actor;
import data.Movie;

import java.util.*;

/**This is the main class of this assignment.
 *
 * When you run the program, movies will be parsed from txt file and you will see an option pane.
 * This option pane explains you what you can do with this application and brings an ease of use.
 * How to use the program is very self-explanatory and simple.
 *
 * I hereby acknowledge that I have neither used nor given any unauthorized help during the completion of this assignment.
 * NAME: Mehmet Enes Erciyes
 *
 * */
public class Main {
    public static void main(String[] args) {
        //MovieParser class gets the file and parses data into a list of Movies.
        List<Movie> movies = MovieParser.parseMovies("src/movies_full.txt");
        Utils utils = new Utils(movies);

        //User Interface is constructed
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        System.out.printf("%d movies have been parsed and ready to search.\n", movies.size());
        System.out.println("Movie Parser and Search Engine: \n-------------------");
        printOptions();

        //According to given option, corresponding Utils method is called
        //All input and error handling is here
        while (option != -1) {
            System.out.print("\nWhat do you want to do?: ");
            try {
                option = Integer.parseInt(scanner.next());
            } catch (NumberFormatException e) {
                e.getMessage();
            }
            scanner = new Scanner(System.in);
            switch (option) {
                case 1:
                    System.out.println("Enter the name and surname of the actors separated by comma (without a space): ");
                        String actorsString = scanner.nextLine();
                    try {
                        Actor[] actors = new Actor[2];
                        String[] actorStrings = new String[2];
                        StringTokenizer tokenizer = new StringTokenizer(actorsString,",");

                        //Input is first tokenized into two separate Strings representing each actor.
                        actorStrings[0] = tokenizer.nextToken();
                        actorStrings[1] = tokenizer.nextToken();
                        //Each string is parsed into an Actor object
                        for (int i= 0; i<actorStrings.length;i++) {
                            tokenizer = new StringTokenizer(actorStrings[i]);
                            String name = tokenizer.nextToken();
                            String surname = tokenizer.hasMoreTokens() ? tokenizer.nextToken():""; //If there is no surname.
                            actors[i] = new Actor(surname,name);
                        }

                        List<Movie> coStarredMovies = utils.hasCoStarred(actors);
                        String movieNumberString;
                        int count = coStarredMovies.size();
                        if (count > 0) {
                            if (count > 1)
                                movieNumberString = "more than one movie";
                            else
                                movieNumberString = "one movie";
                        } else
                            movieNumberString = "no movies";
                        System.out.printf("'%s' and '%s' had co-starred in %s : \n", actors[0], actors[1], movieNumberString);

                       coStarredMovies.stream()
                                .forEach(System.out::println);

                    } catch (ArrayIndexOutOfBoundsException | NoSuchElementException e) {
                        System.out.println("Check your input!!!");
                    }
                    break;
                case 2:
                    System.out.println("Search movies starting with given character, please enter the first character and order type: ");
                    String request = scanner.nextLine();
                    try{
                        String character = request.split(" ")[0].substring(0, 1); //If user enters more than one character, first char is chosen to be searched.
                        String orderType = request.split(" ")[1];
                        if (!orderType.equals("ascending") && !orderType.equals("descending")) {
                            throw new Exception();
                        } else {
                            utils.searchByLetter(character, orderType);
                        }
                    }catch (Exception e){
                        System.out.println("Check your input for order type!!!");
                    }

                    break;
                case 3:
                    System.out.println("Search movies by first name, please enter the actor’s first name: ");
                    String name = scanner.nextLine();
                    utils.searchByFirstName(name);
                    break;
                case 4:
                    System.out.println("Search movies by release date. Please enter the start year and end year of the period " +
                            "you want to search for separated by a space:");
                    try {
                        int beginning = Integer.parseInt(scanner.next());
                        int end = Integer.parseInt(scanner.next());
                        System.out.println(beginning + " " + end);
                        if (end < beginning) {
                            System.out.println("End year of the period cannot be before the start date");
                        } else {
                            utils.searchByYear(beginning, end);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Check your input for years!!!");
                    }
                    break;
                case 5:
                    utils.mostProductiveActor();
                    break;
                case 6:
                    printOptions();
                case -1:
                    break;
                default:
                    System.out.println("You can only enter commands given above!!!");
            }
        }


    }

    private static void printOptions() {
        System.out.println("Choose what you want to do with given movie list: \n" +
                "1-Search for movies where two artists has co-starred\n" +
                "2-Search for movies starting with specified character\n" +
                "3-Search for movies played by an actor with specified first name\n" +
                "4-Search for movies produced within given period\n" +
                "5-Search the most productive actor\n" +
                "6-Print options again\n" +
                "-1 - Terminate search program");
    }
}
