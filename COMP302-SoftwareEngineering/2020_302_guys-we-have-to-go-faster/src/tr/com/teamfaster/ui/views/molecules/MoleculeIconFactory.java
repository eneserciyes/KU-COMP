package tr.com.teamfaster.ui.views.molecules;

import tr.com.teamfaster.domain.utils.EntityType;
import tr.com.teamfaster.domain.utils.GameSettings;

import javax.swing.*;
import java.awt.*;

/**
 * Creates molecule icons from specified type.
 */
public class MoleculeIconFactory {
    private static MoleculeIconFactory instance;
    public boolean isALinear;
    public boolean isBLinear;

    private MoleculeIconFactory() {
        this.isALinear = GameSettings.isAlphaLinear();
        this.isBLinear = GameSettings.isBetaLinear();

    }

    public static MoleculeIconFactory getInstance() {
        if (instance == null) instance = new MoleculeIconFactory();
        return instance;
    }

    /**
     * In case of linear type of the alpha or beta molecule the resources change.
     *
     * @param type: type of the molecule icon
     * @return: icon of molecule
     */
    public ImageIcon getMoleculeIcon(EntityType type) {
        switch (type) {
            case ALPHA -> {
                if (this.isALinear) return getIcon("../../resources/molecules/alpha-2.png");
                else return getIcon("../../resources/molecules/alpha-1.png");
            }
            case BETA -> {
                if (this.isBLinear) return getIcon("../../resources/molecules/beta-2.png");
                else return getIcon("../../resources/molecules/beta-1.png");
            }
            case GAMMA -> {
                return getIcon("../../resources/molecules/gamma-.png");
            }
            case SIGMA -> {
                return getIcon("../../resources/molecules/sigma-.png");
            }
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }

    }

    private ImageIcon getIcon(String path) {
        return new ImageIcon(new ImageIcon(getClass().getResource(path)).getImage().
                getScaledInstance(GameSettings.getMoleculeWidth(), -1, Image.SCALE_SMOOTH));

    }

}
