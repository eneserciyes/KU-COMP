package tr.com.teamfaster;

import tr.com.teamfaster.domain.controller.GameController;
import tr.com.teamfaster.domain.utils.GameSettings;
import tr.com.teamfaster.ui.managers.GameFrame;
import tr.com.teamfaster.ui.managers.ViewChangeListener;

import javax.swing.*;

public class KuvidGame {
    /**
     * @param args
     * @effect waits until user completes the configuration and presses the button. If the
     * inputs are valid, initializes GameFrame, GameController and ViewManager. If the inputs
     * are not valid, it continues to display build menu.
     */
    public static void main(String[] args) {
        GameInfo info = GameInfo.getInstance();
        BuildMenu menu = new BuildMenu(info);
        menu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        menu.setTitle("KUVID");

        while (!info.isReady()) {
            System.out.print("");
        }

        if (info.isReady()) {
            menu.setVisible(false);
            GameSettings.getInstance();

            GameController gameController = GameController.getInstance();
            GameFrame gameFrame = GameFrame.getInstance();

            ViewChangeListener viewManager = new ViewChangeListener();
            viewManager.onCreateInitialObjects();

            gameController.initGame();
            gameFrame.initGame();

        }
    }
}
