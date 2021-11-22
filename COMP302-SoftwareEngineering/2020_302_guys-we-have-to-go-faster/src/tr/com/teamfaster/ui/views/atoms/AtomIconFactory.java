package tr.com.teamfaster.ui.views.atoms;

import tr.com.teamfaster.domain.utils.EntityType;
import tr.com.teamfaster.domain.utils.GameSettings;

import javax.swing.*;
import java.awt.*;
import java.util.InputMismatchException;

/**
 * Creates atom icons from specified type.
 */
public class AtomIconFactory {
    private static AtomIconFactory instance;

    private AtomIconFactory() {

    }

    public static AtomIconFactory getInstance() {
        if (instance == null) instance = new AtomIconFactory();
        return instance;
    }

    public ImageIcon createIcon(EntityType entityType) {
        return switch (entityType) {
            case ALPHA -> getIcon("../../resources/atoms/alpha.png");
            case BETA -> getIcon("../../resources/atoms/beta.png");
            case GAMMA -> getIcon("../../resources/atoms/gamma.png");
            case SIGMA -> getIcon("../../resources/atoms/sigma.png");
        };
    }

    private ImageIcon getIcon(String path) {
        return new ImageIcon(new ImageIcon(getClass().getResource(path)).getImage().
                getScaledInstance(GameSettings.getAtomDiameter(), GameSettings.getAtomDiameter(), Image.SCALE_SMOOTH));

    }
}
