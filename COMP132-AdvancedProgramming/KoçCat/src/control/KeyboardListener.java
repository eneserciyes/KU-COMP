package control;

import background.Board;
import objects.actors.Direction;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class KeyboardListener {
    private int keyCode;
    void addListener(Board board) {
        board.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                keyCode = e.getKeyCode();
            }
        });

    }

    int getKeyCode() {
        return keyCode;
    }

    void doAction() {
        if (keyCode == KeyEvent.VK_UP) {
            Board.cat.setDirection(Direction.UP);
        } else if (keyCode == KeyEvent.VK_DOWN) {
            Board.cat.setDirection(Direction.DOWN);
        } else if (keyCode == KeyEvent.VK_LEFT) {
            Board.cat.setDirection(Direction.LEFT);
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            Board.cat.setDirection(Direction.RIGHT);
        }
    }
}
