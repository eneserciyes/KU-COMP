package objects.actors;

import background.Board;
import background.Cell;
import objects.Drawable;

import javax.swing.*;
import java.awt.*;


/*Cat class extends JLabel to ease the implementation of icons. 
It handles the direction, movement and score of the cat character.*/

public class Cat extends JLabel implements Drawable, Movable {
    //Cat's size is equal to cell width, making game more easily adjustable
    private final static int CAT_SIZE = Cell.CELL_WIDTH;
    private int score = 0;
    private Direction direction;

    //Icons are imported and scaled to fit in screen
    private ImageIcon up = new ImageIcon(new ImageIcon(getClass().getResource("/objects/icons/cat/image_up.png")).getImage().getScaledInstance(CAT_SIZE, CAT_SIZE, Image.SCALE_SMOOTH));
    private ImageIcon down = new ImageIcon(new ImageIcon(getClass().getResource("/objects/icons/cat/image_down.png")).getImage().getScaledInstance(CAT_SIZE, CAT_SIZE, Image.SCALE_SMOOTH));
    private ImageIcon left = new ImageIcon(new ImageIcon(getClass().getResource("/objects/icons/cat/image_right.png")).getImage().getScaledInstance(CAT_SIZE, CAT_SIZE, Image.SCALE_SMOOTH));
    private ImageIcon right = new ImageIcon(new ImageIcon(getClass().getResource("/objects/icons/cat/image_left.png")).getImage().getScaledInstance(CAT_SIZE, CAT_SIZE, Image.SCALE_SMOOTH));

    public Cat() {
        //Cat is placed int the middle cell, set to move right and drawn.
        this.setLocation((((Cell.cellColumnNum + 1) / 2) - 1) * Cell.CELL_WIDTH, (((Cell.cellRowNum + 1) / 2) - 1) * Cell.CELL_HEIGHT);
        direction = Direction.RIGHT;
        draw();
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
        draw();
    }

    @Override
    public void doAction() {
        //According to the direction cat moves in, polymorphic method doAction from Drawable interface handles the movement of KocCat
        //moveUp, moveDown etc. is inherited from Movable interface 
        switch (direction) {
            case UP:
                if (this.getY() != 0)
                    moveUp();
                break;
            case DOWN:
                if (this.getY() != (Board.BOARD_HEIGHT - this.getHeight()))
                    moveDown();
                break;
            case LEFT:
                if (this.getX() != 0)
                    moveLeft();
                break;
            case RIGHT:
                if (this.getX() != Board.BOARD_WIDTH - this.getWidth())
                    moveRight();
        }

    }

    @Override
    public void draw() {
        //Polymorphic draw method handles the icon to show according to the direction of cat and sets the size.
        if (direction == Direction.UP) {
            this.setIcon(up);
        } else if (direction == Direction.DOWN) {
            this.setIcon(down);
        } else if (direction == Direction.LEFT) {
            this.setIcon(left);
        } else {
            this.setIcon(right);
        }
        this.setSize(this.getPreferredSize());
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
