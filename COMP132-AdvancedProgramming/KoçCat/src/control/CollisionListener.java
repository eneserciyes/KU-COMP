package control;

import background.Board;
import background.Cell;
import objects.actors.Cat;
import objects.actors.ghosts.Ghost;

import java.awt.*;

class CollisionListener {
    private final int GHOST_COLLISION_DISTANCE = 15;
    private final int FOOD_COLLISION_DISTANCE = 0;
    private Cat cat;
    private Ghost[] ghosts;

    CollisionListener(Cat cat, Ghost[] ghosts) {
        this.cat = cat;
        this.ghosts = ghosts;
    }

    boolean checkGhostCollision() {
        //For every ghost, checks if cat is close enough to end the game.
        for (Ghost ghost : ghosts) {
            if (getDistance(cat.getCenter(), ghost.getCenter()) <= GHOST_COLLISION_DISTANCE)
                return true;
        }
        return false;
    }

    void checkFoodCollision() {
        //This method finds the closest row and column to the cat. Then checks if the cell's center is colliding with the center of
        //cat. Then it updates cat's score and the score label on screen.
        Point center = cat.getCenter();
        int closestRow = (int) Math.round(center.getY() / Cell.CELL_HEIGHT) - 1;
        int closestColumn = (int) Math.round(center.getX() / Cell.CELL_WIDTH) - 1;
        Point cellPoint = new Point((int) ((closestColumn + 0.5) * Cell.CELL_WIDTH), (int) ((closestRow + 0.5) * Cell.CELL_HEIGHT));
        if (getDistance(center, cellPoint) <= FOOD_COLLISION_DISTANCE) {
            if (Cell.getCellValue(closestRow, closestColumn) != null) {
                Cell.getCellValue(closestRow, closestColumn).consumed();
                Board.setScoreLabel(Board.cat.getScore());

            }
        }

    }


    private double getDistance(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2)
                + Math.pow(p1.getY() - p2.getY(), 2));
    }
}
