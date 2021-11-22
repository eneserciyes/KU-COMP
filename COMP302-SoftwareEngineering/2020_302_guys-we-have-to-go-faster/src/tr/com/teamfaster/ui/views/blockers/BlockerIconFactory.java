package tr.com.teamfaster.ui.views.blockers;

import tr.com.teamfaster.domain.utils.EntityType;
import tr.com.teamfaster.domain.utils.GameSettings;

import javax.swing.*;
import java.awt.*;

/**
 * Creates blocker icons from specified type.
 */
public class BlockerIconFactory {
    private static BlockerIconFactory instance;

    private BlockerIconFactory() {

    }

    public static BlockerIconFactory getInstance() {
        if (instance == null) instance = new BlockerIconFactory();
        return instance;
    }

    public ImageIcon getBlockerIcon(EntityType entityType) {
        return switch (entityType) {
            case ALPHA -> getIcon("../../resources/blockers/alpha-b.png");
            case BETA -> getIcon("../../resources/blockers/beta-b.png");
            case GAMMA -> getIcon("../../resources/blockers/gamma-b.png");
            case SIGMA -> getIcon("../../resources/blockers/sigma-b.png");
        };

    }

    private ImageIcon getIcon(String path) {
        return new ImageIcon(new ImageIcon(getClass().getResource(path)).getImage().
                getScaledInstance(GameSettings.getBlockerWidth(), -1, Image.SCALE_SMOOTH));

    }

}
