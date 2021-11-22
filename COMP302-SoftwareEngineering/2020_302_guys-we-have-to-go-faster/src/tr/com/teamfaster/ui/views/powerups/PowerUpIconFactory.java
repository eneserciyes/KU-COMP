package tr.com.teamfaster.ui.views.powerups;

import tr.com.teamfaster.domain.utils.EntityType;
import tr.com.teamfaster.domain.utils.GameSettings;

import javax.swing.*;
import java.awt.*;

/**
 * Creates powerup icons from specified type.
 */
public class PowerUpIconFactory {
    private static PowerUpIconFactory instance;

    private PowerUpIconFactory() {

    }

    public static PowerUpIconFactory getInstance() {
        if (instance == null) instance = new PowerUpIconFactory();
        return instance;
    }

    public ImageIcon createIcon(EntityType entityType) {
        return switch (entityType) {
            case ALPHA -> getIcon("../../resources/powerups/+alpha-b.png");
            case BETA -> getIcon("../../resources/powerups/+beta-b.png");
            case GAMMA -> getIcon("../../resources/powerups/+gamma-b.png");
            case SIGMA -> getIcon("../../resources/powerups/+sigma-b.png");
        };
    }

    private ImageIcon getIcon(String path) {
        return new ImageIcon(new ImageIcon(getClass().getResource(path)).getImage().
                getScaledInstance(GameSettings.getPowerupWidth(), GameSettings.getPowerupWidth(), Image.SCALE_SMOOTH));

    }

}
