package objects.edibles;

import background.Cell;
import control.Animator;
import objects.Drawable;

import javax.swing.*;

/*Food class is the super class of fruit and poison
It handles the growing of both types of food, it sets the location and draws them on the JFrame */

public abstract class Food extends JLabel implements Edible, Drawable {
    //GROW_TICK is the number of Timer action in 3 seconds.
    //How fast foods should grow can be adjusted with this constant.
    private final int GROW_TICK = 3000 / Animator.TIMER_RATE;
    int age = 1;
    private int row, column;
    private int timerTick = 0;

    Food(int row, int column) {
        this.row = row;
        this.column = column;
        this.setSize(Cell.CELL_WIDTH, Cell.CELL_HEIGHT);
        draw();
    }


    @Override
    public void doAction() {
        timerTick++;
        if (timerTick == GROW_TICK) {
            grow();
            timerTick = 0;
        }
    }

    @Override
    public void draw() {
        //Sets the food's location and places it on cell matrix.
        this.setLocation(column * Cell.CELL_WIDTH, row * Cell.CELL_HEIGHT);
        Cell.setCellValue(row, column, this);
        repaint();
    }

    @Override
    public String toString() {
        return "FOOD";
    }

    void setAge(int age) {
        this.age = age;
    }

    int getRow() {
        return row;
    }

    void setRow(int row) {
        this.row = row;
    }

    int getColumn() {
        return column;
    }

    void setColumn(int column) {
        this.column = column;
    }
}
