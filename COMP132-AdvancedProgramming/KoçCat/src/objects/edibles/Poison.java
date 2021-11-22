package objects.edibles;

import background.Board;
import objects.Drawable;

import java.awt.*;

public class Poison extends Food implements Drawable, Edible {
    private final int POISON_HEIGHT = 10;
    private final int POISON_WIDTH = 10;
    private final int POISON_GROW_LIMIT = 15;

    public Poison(int row, int column) {
        super(row, column);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (age <= 10)
            g.setColor(Color.YELLOW);
        else
            g.setColor(Color.RED);
        int poisonWidth = (age < POISON_GROW_LIMIT) ? (POISON_WIDTH + (age - 1) * 2) : POISON_WIDTH + (POISON_GROW_LIMIT - 1) * 2;
        int poisonHeight = (age < POISON_GROW_LIMIT) ? (POISON_HEIGHT + (age - 1) * 2) : POISON_HEIGHT + (POISON_GROW_LIMIT - 1) * 2;
        g.fillRect((this.getWidth() - poisonWidth) / 2, (this.getHeight() - poisonHeight) / 2, poisonWidth, poisonHeight);
        g.setColor(Color.BLACK);
    }

    @Override
    public void grow() {
        setAge(age + 1);
        draw();
    }

    @Override
    public void consumed() {
        Board.cat.setScore(Board.cat.getScore() - age * 10);
    }
}
