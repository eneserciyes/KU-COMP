package objects.actors.ghosts;

import background.Cell;
import objects.Drawable;
import objects.actors.Movable;

import javax.swing.*;

/* Ghost is the abstract superclass of all ghost types.
It implement draw method and handles the icon of all ghost types.
*/
public abstract class Ghost extends JLabel implements Drawable, Movable {

    final int GHOST_SIZE = Cell.CELL_WIDTH;
    private ImageIcon icon;

    void setImageIcon(ImageIcon icon) {
        this.icon = icon;
    }

    @Override
    public void draw() {
        this.setIcon(icon);
        this.setSize(getPreferredSize());
    }
}
