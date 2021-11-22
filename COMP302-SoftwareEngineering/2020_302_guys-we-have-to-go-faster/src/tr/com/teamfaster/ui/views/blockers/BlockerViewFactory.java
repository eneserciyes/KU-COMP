package tr.com.teamfaster.ui.views.blockers;

import tr.com.teamfaster.domain.utils.EntityType;
import tr.com.teamfaster.domain.utils.GameSettings;

import javax.swing.*;
import java.awt.*;

/**
 * Creates blocker views from specified type by blocker icon factory.
 */
public class BlockerViewFactory {
    private static BlockerViewFactory instance;

    private BlockerViewFactory() {

    }

    public static BlockerViewFactory getInstance() {
        if (instance == null) instance = new BlockerViewFactory();
        return instance;
    }

    public BlockerView createBlockerView(EntityType entityType) {
        return switch (entityType) {
            case ALPHA -> new BlockerView(BlockerIconFactory.getInstance().getBlockerIcon(EntityType.ALPHA));
            case BETA -> new BlockerView(BlockerIconFactory.getInstance().getBlockerIcon(EntityType.BETA));
            case GAMMA -> new BlockerView(BlockerIconFactory.getInstance().getBlockerIcon(EntityType.GAMMA));
            case SIGMA -> new BlockerView(BlockerIconFactory.getInstance().getBlockerIcon(EntityType.SIGMA));
        };
    }

    private ImageIcon getIcon(String path) {
        return new ImageIcon(new ImageIcon(getClass().getResource(path)).getImage().
                getScaledInstance(GameSettings.getMoleculeWidth(), -1, Image.SCALE_SMOOTH));

    }

}
