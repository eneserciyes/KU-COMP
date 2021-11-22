package background.grids;

import background.Board;
import background.Cell;

import javax.swing.*;
import java.awt.*;

public class VerticalGrid extends JPanel {
    private int x;

    public VerticalGrid(int verGrid) {
        this.x = (verGrid) * Cell.CELL_WIDTH;
        this.setLocation(x, 0);
        this.setSize(1, Board.BOARD_HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.drawLine(0, 0, 0, getHeight());
    }
}
