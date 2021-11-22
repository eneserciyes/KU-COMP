package objects.edibles;

import background.Board;
import background.Cell;
import objects.Drawable;

import java.awt.*;
import java.util.Random;


/*
* Fruit class
*
* paintComponent for drawing fruit on screen as it grows
* Implements grow and consumed method from Edible interface
* Also has decay and reposition
* void reposition(): changes fruits position when eaten
* */

public class Fruit extends Food implements Drawable, Edible {
    final int FOOD_RADIUS = 20;
    private final int DECAY_AGE = 10;
    Random random = new Random();


    public Fruit(int row, int column) {
        super(row, column);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (age <= 5)
            g.setColor(Color.BLUE);
        else
            g.setColor(Color.GREEN);
        int radius = FOOD_RADIUS + (age - 1);
        g.fillOval((this.getWidth() - radius) / 2, (this.getHeight() - radius) / 2, radius, radius);
    }

    @Override
    public void grow() {
        setAge(age + 1);
        if (age > DECAY_AGE)
            decay();
        draw();
    }

    @Override
    public void consumed() {
        Board.cat.setScore(Board.cat.getScore() + age * 5);
        reposition();
    }

    private void decay() {
        reposition();
    }

    private void reposition() {
        //Sets cell matrix's value to null
        Cell.setCellValue(getRow(), getColumn(), null);
        //Places food to another position
        int column = random.nextInt(Cell.cellColumnNum);
        int row = random.nextInt(Cell.cellRowNum);
        while (Cell.getCellValue(row, column) != null) {
            column = random.nextInt(Cell.cellColumnNum);
            row = random.nextInt(Cell.cellRowNum);
        }
        this.setColumn(column);
        this.setRow(row);
        setAge(1);
        draw();
    }


}
