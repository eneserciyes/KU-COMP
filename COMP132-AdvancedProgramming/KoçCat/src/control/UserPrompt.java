package control;

import javax.swing.*;

/*
* User Prompt Class
* Static methods to ask for user input.
* */

public class UserPrompt {
    public static int askFruitNumber() {
        int fruitNum = 0;
        boolean isInvalid = true;
        while(isInvalid){
            try{
                String fruit = JOptionPane.showInputDialog("Enter the number of fruit in the game.");
                fruitNum =  Integer.parseInt(fruit);
                isInvalid =false;
            }catch (NumberFormatException e){
                isInvalid = true;
            }
        }
        return fruitNum;
    }

    public static int askPoisonNumber() {
        int poisonNum = 0;
        boolean isInvalid = true;
        while(isInvalid){
            try {
                String poison = JOptionPane.showInputDialog("Enter the number of poison in the game");
                poisonNum = Integer.parseInt(poison);
                isInvalid = false;
            }catch (NumberFormatException e){
                isInvalid = true;
            }
        }
        return poisonNum;
    }

    public static int askGhostNumber() {
        int ghostNum = 0;
        boolean isInvalid = true;
        while(isInvalid){
            try {
                String ghost = JOptionPane.showInputDialog("Enter the number of ghosts in the game.");
                ghostNum = Integer.parseInt(ghost);
                isInvalid = false;
            } catch (NumberFormatException e) {
                isInvalid = true;
            }
        }
        return ghostNum;
    }

}
