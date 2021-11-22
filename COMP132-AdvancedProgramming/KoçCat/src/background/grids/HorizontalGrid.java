package background.grids;

import background.Board;
import background.Cell;

import javax.swing.*;
import java.awt.*;

public class HorizontalGrid extends JPanel {
    private int y;

    public HorizontalGrid(int horGrid) {
        this.y = (horGrid) * Cell.CELL_HEIGHT;
        this.setLocation(0, y);
        this.setSize(Board.BOARD_WIDTH, 1);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.drawLine(0, 0, Board.BOARD_WIDTH, 0);
    }
}
