import background.Board;
import control.UserPrompt;

/**
 * THIS CODE IS MY OWN WORK. I DID NOT CONSULT TO ANY  PROGRAM WRITTEN BY OTHER STUDENTS.
 * I READ AND FOLLOWED THE GUIDELINE GIVEN IN THE PROGRAMMING ASSIGNMENT. NAME: MEHMET ENES ERCIYES
 * */


/*
* Main class for the game KocCat.
*
* TASKS:
* Initializes board variable.
* Asks user input for fruit,poison and ghost numbers.
* Calls initGame from board
* */

public class KocCat {

    public static void main(String[] args) {
        Board gameBoard = new Board();
        gameBoard.setVisible(true);

        int fruitNum = UserPrompt.askFruitNumber();
        int poisonNum = UserPrompt.askPoisonNumber();
        int ghostNum = UserPrompt.askGhostNumber();

        gameBoard.initGame(fruitNum, poisonNum, ghostNum);
    }
}
