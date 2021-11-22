package objects.actors.ghosts;

import background.Board;
import objects.actors.Direction;

import javax.swing.*;
import java.awt.*;

public class Dolley extends Ghost {
    private Direction direction;

    public Dolley() {
        ImageIcon icon = new ImageIcon(new ImageIcon(getClass().getResource("/objects/icons/ghost/dolley.jpg")).getImage().getScaledInstance(this.GHOST_SIZE, this.GHOST_SIZE, Image.SCALE_SMOOTH));
        direction = Direction.DOWN;
        this.setImageIcon(icon);
        draw();
    }

    @Override
    public void doAction() {
        if (getY() == 0)
            direction = Direction.DOWN;
        else if (getY() == Board.BOARD_HEIGHT - this.getHeight())
            direction = Direction.UP;

        switch (direction) {
            case UP:
                moveUp();
                break;
            case DOWN:
                moveDown();
        }
    }
}
