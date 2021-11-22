package control;

import background.Board;
import background.Cell;
import objects.Drawable;
import objects.actors.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Animator {
    public static final int TIMER_RATE = 1000 / Cell.CELL_WIDTH;
    private Timer timer;
    private ArrayList<Drawable> drawables;
    private Board board;
    private CollisionListener collisionListener;
    private KeyboardListener keyboardListener;

    public Animator(ArrayList<Drawable> drawables, Board board) {
        this.drawables = drawables;
        this.board = board;

        keyboardListener = new KeyboardListener();
        keyboardListener.addListener(board);

        collisionListener = new CollisionListener(board.getCat(), board.getGhosts());
    }

    public void startAnimation() {
        timer = new Timer(TIMER_RATE, e -> {
            //Checks if there is a change in cat's direction
            checkDirection();
            //Iterates over all drawable objects on the screen and calls doAction()
            for (Drawable drawable : drawables) {
                drawable.doAction();
            }
            //If ghost and cat collides,ends the game.
            if (collisionListener.checkGhostCollision()) {
                gameOver();
            }
            //Checks food collision and ends the game if the score is less than zero.
            collisionListener.checkFoodCollision();
            if (Board.cat.getScore() < 0)
                gameOver();
        });
        timer.start();
    }

    private void checkDirection() {
        //To make KocCat able to move only in grids, it is only allowed to change direction when KocCat's center is at a grid's center
        //or when tried to turn back in a grid
        int distanceFromCenterX = (int) ((Board.cat.getCenter().getX() - Cell.CELL_WIDTH * 0.5) % Cell.CELL_WIDTH);
        int distanceFromCenterY = (int) ((Board.cat.getCenter().getY() - Cell.CELL_HEIGHT * 0.5) % Cell.CELL_HEIGHT);
        if (distanceFromCenterX == 0 && distanceFromCenterY == 0
                || Board.cat.getDirection() == Direction.UP && keyboardListener.getKeyCode() == KeyEvent.VK_DOWN
                || Board.cat.getDirection() == Direction.DOWN && keyboardListener.getKeyCode() == KeyEvent.VK_UP
                || Board.cat.getDirection() == Direction.LEFT && keyboardListener.getKeyCode() == KeyEvent.VK_RIGHT
                || Board.cat.getDirection() == Direction.RIGHT && keyboardListener.getKeyCode() == KeyEvent.VK_LEFT) {
            keyboardListener.doAction();
        }
    }

    private void gameOver() {
        //Stops the animation, removes all elements on the frame and adds game over label.
        timer.stop();
        board.getContentPane().removeAll();
        board.getContentPane().setBackground(Color.BLACK);
        GameOverLabel gameOverLabel = new GameOverLabel();
        board.add(gameOverLabel);
        board.repaint();
    }


    private class GameOverLabel extends JLabel {
        GameOverLabel() {
            this.setText("GAME OVER");
            setForeground(Color.WHITE);
            setFont(new Font("SansSerif", Font.BOLD, 36));
            setSize(getPreferredSize());
            setLocation((board.getWidth()-getWidth())/2,(board.getHeight()-getHeight())/2);
        }
    }
}
