#include<stdio.h>
#include<stdlib.h>
#include<string.h>

#define SIZE_STRING 20
#define SIZE_GRADES 5

//DISCLAIMER//

/*
THIS CODE IS MY OWN WORK. I DID NOT CONSULT TO ANY PROGRAM WRITTEN BY OTHER STUDENTS. 
I READ AND FOLLOWED THE GUIDELINE GIVEN IN THE PROGRAMMING ASSIGNMENT 3. 
NAME: MEHMET ENES ERCİYES
*/

/*TYPE DEFINITIONS*/


typedef struct{
	char examName[SIZE_STRING];
	int points;
}Grade;

struct Student{
	int id;
	char name[SIZE_STRING];
	char surname[SIZE_STRING];
	char letterGrade;
	Grade grades[SIZE_GRADES];
	int numberOfExamGraded;
	struct Student* next;
};

typedef struct Student Student;


/* FUNCTION PROTOTYPES */
void addNewStudent(Student** headPtr, int id, char name[],char surname[]);
int checkStudentInList(Student* head, int id);
Student* removeStudent(Student ** headPtr, int id);
void addOrChangeGrade(Student *head, int id, char exam[],int points);
double getAverageForExam(Student* head, char exam[]);
void printCourseReport(Student* head, int id);
void printReportForAll(Student* head);

/* HELPER METHODS */
void printOptions(){
	printf("Option 1- Enter 1 in order to add a new student into the course then press Enter\n");
	printf("Option 2- Enter 2 in order to check if the student is in the course list then press Enter\n");
	printf("Option 3- Enter 3 in order to delete a student in the course then press Enter\n");
	printf("Option 4- Enter 4 in order to add or change the grade of the student then press Enter\n");
	printf("Option 5- Enter 5 in order to calculate the class point average then press Enter\n");
	printf("Option 6- Enter 6 in order to print the course report for one student then press Enter\n");
	printf("Option 7- Enter 7 in order to print the course report for all students then press Enter\n");
	printf("Option 8- Enter 8 in order to exit then press Enter\n");
}

void printStudent(Student* student) {
    printf("%s %s\t id:%d\t", student->name, student->surname, student->id);
    printf("Graded exams: ");

    for (int i = 0; i <5; i++) {	
       printf("%s :%d\t", student->grades[i].examName, student->grades[i].points);
    }
    printf("\nLetter : %c\n", student->letterGrade); 
}

void printAllStudents(Student* head){
	Student* current = head;
	while(current!=NULL){
		printStudent(current);
		current = current->next;
	}


}

char letterGradeConversion(Student* student){
	double sum = 0;
	for(int i=0;i<student->numberOfExamGraded;i++){
		sum += student->grades[i].points;
	}

	double average = sum/student->numberOfExamGraded;
	char letter;
	if (average <= 100 && average >= 90) 
		letter = 'A';
	else if(average >=80)
		letter = 'B';
	else if(average>=70)
		letter = 'C';
	else if(average>=60)
		letter = 'D';
	else 
		letter = 'F';

	return letter;
}

Student* findStudentbyID(Student* head, int id){
	Student* currentPtr = head;

	while(currentPtr != NULL){
		if(currentPtr->id == id){
			return currentPtr;
		}
		currentPtr = currentPtr->next;
	}
	return NULL;//Student not found

}


/* MAIN METHOD AND TASKS*/

int main(){
	
	printOptions();
	int option;
	Student *head = NULL;
	do{
		printf("\nChoose one option: ");
		scanf("%d",&option);
		
		int id; 
		char name[SIZE_STRING],surname[SIZE_STRING],exam[SIZE_STRING];
		int points;
		
		switch(option){
			case 1:
				printf("Enter student name, surname and id with one space between them: \n");
				scanf("%19s %19s %d",name,surname,&id);
				addNewStudent(&head,id,name,surname);
				break;
			case 2:
				printf("Enter student id to look for: \n");
				scanf("%d",&id);
				if(checkStudentInList(head,id)){
					printf("The student with id %d is in this course.\n",id);
				}else{
					printf("The student you looked for cannot be found in this course.\n");
				}
				break;
			case 3:
				printf("Enter student id to remove: \n");
				scanf("%d",&id);
				removeStudent(&head,id);
				break;
			case 4:
				printf("Enter student id,exam and points with one space between them: \n");
				scanf("%d %19s %d",&id,exam,&points);
				addOrChangeGrade(head,id,exam,points);
				break;
			case 5:
				printf("Enter the exam name to find average: \n");
				scanf("%19s",exam);
				printf("The exam average is: %.2f\n",getAverageForExam(head,exam));
				break;
			case 6:
				printf("Enter the id of the student to get his/her report: \n");
				scanf("%d",&id);
				printCourseReport(head,id);
				break;
			case 7:
				printf("\n *** COURSE REPORT *** \n");
				printReportForAll(head);
				break;
			case 8:
				printf("\nBye bye!!\n");
		}
	}while(option != 8);
	
}


void addNewStudent(Student** headPtr, int id, char name[],char surname[]){
	// INITIALIZE NEW STUDENT WITH GIVEN VALUES //
	Student* newStudent = malloc(sizeof(Student));
	newStudent->id = id;
	strcpy((*newStudent).name,name);
	strcpy((*newStudent).surname,surname);


	Student* previousPtr = NULL;
	Student* currentPtr = *headPtr;

	// Loop moves the pointer until it is end of the list or new id is less than current element
	while(currentPtr != NULL && (currentPtr->id)<(newStudent->id)){
		previousPtr = currentPtr;
		currentPtr = currentPtr->next;
	}

	if(previousPtr==NULL){
		*headPtr = newStudent;
		newStudent->next = currentPtr;
	}else{
		previousPtr->next = newStudent;
		newStudent->next = currentPtr;
	}
}



int checkStudentInList(Student* head, int id){
	return (findStudentbyID(head,id)!= NULL);
}

Student* removeStudent(Student ** headPtr, int id){
	Student *currentPtr = *headPtr;
	Student *previousPtr = NULL;
	
	while(currentPtr != NULL){
		if(currentPtr->id == id){
			if(previousPtr==NULL){
				*headPtr = currentPtr->next;
				free(currentPtr);
				return *headPtr;
			}
			previousPtr->next = currentPtr->next;
			free(currentPtr);
			return previousPtr->next;
		}
		previousPtr = currentPtr;
		currentPtr = currentPtr->next; 
	}
	return NULL; //Student cannot be found
}

void addOrChangeGrade(Student *head, int id, char exam[],int points){
	Student *currentPtr = head;
	if(!checkStudentInList(head, id)){
		printf("There is no student with given ID.\n");
		return;
	}
	while(currentPtr != NULL){
		if(currentPtr->id == id){
			int i;
			// Loop moves until it finds the given exam
			for(i=0; i<currentPtr->numberOfExamGraded;i++){
				if(strcmp((currentPtr->grades[i]).examName,exam)==0){
					currentPtr->grades[i].points = points;
					break;
				}
			}
			//If it does not it adds new gtade at the end of grades array
			if(i==currentPtr->numberOfExamGraded){
				if(currentPtr->numberOfExamGraded==5){
					printf("You cannot enter more grades\n");
					return;
				}
				Grade grade;
				strcpy(grade.examName,exam);
				grade.points = points;

				(currentPtr->grades)[i] = grade;
				(currentPtr->numberOfExamGraded)++;
			}
			break;
		}
		currentPtr = currentPtr->next;
	}
}

double getAverageForExam(Student* head, char exam[]){
	double sumOfGrades = 0;
	int numOfStudents = 0;

	Student *currentPtr = head;

	while(currentPtr != NULL){
		for(int i=0;i<currentPtr->numberOfExamGraded;i++){
			if(strcmp(currentPtr->grades[i].examName,exam)==0){
				sumOfGrades+=currentPtr->grades[i].points;
				numOfStudents++;
				break;
			}

		}
		currentPtr = currentPtr->next;
	}

	return sumOfGrades/numOfStudents;
}

void printCourseReport(Student* head, int id){
	if(!checkStudentInList(head,id)){
		printf("There is no student with given ID.\n");
	}
	Student *currentPtr = head;
	while(currentPtr!=NULL){
		if(currentPtr->id == id){
			currentPtr->letterGrade = letterGradeConversion(currentPtr);
			printStudent(currentPtr);
			break;
		}
		currentPtr = currentPtr->next;
	}
}

void printReportForAll(Student* head){
	Student *currentPtr = head;
	while(currentPtr != NULL){
		currentPtr->letterGrade = letterGradeConversion(currentPtr);
		printStudent(currentPtr);
		currentPtr = currentPtr->next;
	}
	printf("\n");
}

