package tr.com.teamfaster.ui.managers;

import tr.com.teamfaster.domain.controller.GameController;
import tr.com.teamfaster.domain.utils.GameSettings;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

public class GameFrame extends JFrame {
    private static GameFrame instance;
    private final GameController gameController;

    /**
     * @effects initializes gameController, sets the background and size of the game frame
     */
    private GameFrame() {

        this.gameController = GameController.getInstance();

        this.setVisible(true);
        setSize(new Dimension(GameSettings.getGameFrameWidth(), GameSettings.getGameFrameHeight() + getInsets().top));

        ImageIcon backgroundImage = new ImageIcon((new ImageIcon(getClass().getResource("../resources/kuvid_bc.png")).getImage().getScaledInstance(GameSettings.getGameFrameWidth(), -1, Image.SCALE_SMOOTH)));
        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setIcon(backgroundImage);

        setContentPane(backgroundLabel);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        setTitle("KUVID");
        setBackgroundImage();
    }

    public static GameFrame getInstance() {
        if (instance == null) instance = new GameFrame();
        return instance;
    }

    /**
     * @effects sets background image of the jframe
     */
    private void setBackgroundImage() {
        ImageIcon backgroundImage = new ImageIcon((new ImageIcon(getClass().getResource("../resources/kuvid_bc.png")).getImage().getScaledInstance(-1, GameSettings.getGameFrameHeight(), Image.SCALE_SMOOTH)));
        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setIcon(backgroundImage);
        this.setContentPane(backgroundLabel);
    }

    /**
     * @effects initializes key listeners for the game
     */
    public void initGame() {
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_RIGHT -> gameController.setShooterMoveDirection(GameSettings.getRIGHT());
                    case KeyEvent.VK_LEFT -> gameController.setShooterMoveDirection(GameSettings.getLEFT());
                    case KeyEvent.VK_D -> gameController.setShooterRotateDirection(GameSettings.getRIGHT());
                    case KeyEvent.VK_A -> gameController.setShooterRotateDirection(GameSettings.getLEFT());
                    case KeyEvent.VK_UP -> gameController.shoot();
                    case KeyEvent.VK_C -> gameController.changeAtom();
                    case KeyEvent.VK_P -> gameController.stopGame();
                    case KeyEvent.VK_R -> gameController.resumeGame();
                    case KeyEvent.VK_B -> gameController.blendAtom();
                    case KeyEvent.VK_S -> gameController.save();
                    case KeyEvent.VK_L -> gameController.load();

                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT -> gameController.setShooterMoveDirection(GameSettings.getSTOP());
                    case KeyEvent.VK_D, KeyEvent.VK_A -> gameController.setShooterRotateDirection(GameSettings.getSTOP());
                }
            }
        });

    }
}
