package objects.actors.ghosts;

import background.Board;
import objects.actors.Direction;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/*Casper includes a random direction change different from other ghost types.
It starts the game by going down. When it comes to a boundary or the random condition is met, it changes direction
*/
public class Casper extends Ghost {
    //This constant determines how often will Casper change direction.
    private final int RANDOMNESS_CONSTANT = 50;
    private Direction direction;
    private Random random = new Random();

    public Casper() {
        ImageIcon icon = new ImageIcon(new ImageIcon(getClass().getResource("/objects/icons/ghost/casper.png")).getImage().getScaledInstance(this.GHOST_SIZE, this.GHOST_SIZE, Image.SCALE_SMOOTH));
        this.setImageIcon(icon);

        direction = Direction.DOWN;
        draw();
    }

    @Override
    public void doAction() {
        while (getX() == 0 && direction == Direction.LEFT
                || getX() == Board.BOARD_WIDTH - this.getWidth() && direction == Direction.RIGHT
                || getY() == 0 && direction == Direction.UP
                || getY() == Board.BOARD_HEIGHT - this.getHeight() && direction == Direction.DOWN
                || random.nextInt(RANDOMNESS_CONSTANT) == 0) {
            changeDirection();
        }
        switch (direction) {
            case UP:
                moveUp();
                break;
            case DOWN:
                moveDown();
                break;
            case LEFT:
                moveLeft();
                break;
            case RIGHT:
                moveRight();
        }
    }

    private void changeDirection() {
        direction = Direction.randomDirection();
    }
}

