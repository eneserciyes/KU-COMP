package background;

import objects.edibles.Food;

/* Cell class provides static constants such as cell width, height and column,row numbers.
Also it has cellMatrix which is quite important for cat-food collision and determining what lies in which cell.
* */

public class Cell {
    public static final int CELL_WIDTH = 50;
    public static final int CELL_HEIGHT = 50;
    public static final int cellColumnNum = Board.BOARD_WIDTH / Cell.CELL_WIDTH;
    public static final int cellRowNum = Board.BOARD_HEIGHT / Cell.CELL_HEIGHT;

    private static Food[][] cellMatrix = new Food[Board.BOARD_WIDTH / CELL_WIDTH][Board.BOARD_HEIGHT / CELL_HEIGHT];

    public static void setCellValue(int row, int column, Food food) {
        cellMatrix[row][column] = food;
    }

    public static Food getCellValue(int row, int column) {
        return cellMatrix[row][column];
    }
}
