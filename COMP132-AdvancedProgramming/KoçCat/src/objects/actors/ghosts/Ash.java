package objects.actors.ghosts;

import background.Board;

import javax.swing.*;
import java.awt.*;


public class Ash extends Ghost {
    public Ash() {
        //Imports the Ash icon and sets this icon using superclass method 
        ImageIcon icon = new ImageIcon(new ImageIcon(getClass().getResource("/objects/icons/ghost/ash.png")).getImage().getScaledInstance(this.GHOST_SIZE, this.GHOST_SIZE, Image.SCALE_SMOOTH));
        this.setImageIcon(icon);
        draw();
    }

    //Does the action described for Ash.
    @Override
    public void doAction() {
        if (getX() == 0 && getY() != 0)
            moveUp();
        else if (getY() == 0 && getX() != Board.BOARD_WIDTH - this.getWidth())
            moveRight();
        else if (getX() == Board.BOARD_WIDTH - this.getWidth() && getY() != Board.BOARD_HEIGHT - this.getHeight())
            moveDown();
        else if (getY() == Board.BOARD_HEIGHT - this.getHeight() && getX() != 0)
            moveLeft();
        else
            moveLeft();
    }

}
