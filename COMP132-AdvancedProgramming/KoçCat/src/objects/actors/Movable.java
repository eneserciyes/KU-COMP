package objects.actors;

import java.awt.*;

/* KocCat and ghosts implement this interface
It provides methods to move in the screen.
Also, it contains a getCenter method which gives the location of center pixel of a movable item.
*/

public interface Movable {
    //Speed of KocCat can be adjusted using these values
    //MAKE SURE THAT THESE VALUES ARE FACTORS OF Cell.CELL_WIDTH
    int dX = 2;
    int dY = 2;

    default void moveLeft() {
        ((Component) this).setLocation(((Component) this).getX() - dX, ((Component) this).getY());
    }

    default void moveRight() {
        ((Component) this).setLocation(((Component) this).getX() + dX, ((Component) this).getY());
    }

    default void moveUp() {
        ((Component) this).setLocation(((Component) this).getX(), ((Component) this).getY() - dY);
    }

    default void moveDown() {
        ((Component) this).setLocation(((Component) this).getX(), ((Component) this).getY() + dY);
    }

    default Point getCenter() {
        return new Point(((Component) this).getX() + (((Component) this).getWidth() / 2),
                ((Component) this).getY() + (((Component) this).getHeight() / 2));
    }
}
