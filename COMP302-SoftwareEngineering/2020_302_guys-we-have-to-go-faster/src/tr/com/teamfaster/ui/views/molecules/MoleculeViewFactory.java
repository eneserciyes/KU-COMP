package tr.com.teamfaster.ui.views.molecules;

import tr.com.teamfaster.domain.utils.EntityType;
import tr.com.teamfaster.domain.utils.GameSettings;

import javax.swing.*;
import java.awt.*;

/**
 * Creates molecule views from specified type by molecule icon factory.
 */
public class MoleculeViewFactory {
    private static MoleculeViewFactory instance;

    public boolean isASpinning;
    public boolean isBSpinning;

    private MoleculeViewFactory() {
        this.isASpinning = GameSettings.isAlphaSpinning();
        this.isBSpinning = GameSettings.isBetaSpinning();

    }

    public static MoleculeViewFactory getInstance() {
        if (instance == null) instance = new MoleculeViewFactory();
        return instance;
    }

    /**
     * In case of spinning molecule the view is created from SpinningMoleculeView.
     *
     * @param entityType: type of the molecule vşiew
     * @return: view
     */
    public MoleculeView createMoleculeView(EntityType entityType) {
        switch (entityType) {
            case ALPHA -> {
                if (isASpinning)
                    return new SpinningMoleculeView(MoleculeIconFactory.getInstance().getMoleculeIcon(EntityType.ALPHA));
                else return new MoleculeView(MoleculeIconFactory.getInstance().getMoleculeIcon(EntityType.ALPHA));
            }
            case BETA -> {
                if (isBSpinning)
                    return new SpinningMoleculeView(MoleculeIconFactory.getInstance().getMoleculeIcon(EntityType.BETA));
                else return new MoleculeView(MoleculeIconFactory.getInstance().getMoleculeIcon(EntityType.BETA));
            }
            case GAMMA -> {
                return new MoleculeView(MoleculeIconFactory.getInstance().getMoleculeIcon(EntityType.GAMMA));
            }
            case SIGMA -> {
                return new MoleculeView(MoleculeIconFactory.getInstance().getMoleculeIcon(EntityType.SIGMA));
            }
            default -> throw new IllegalStateException("Unexpected value: " + entityType);
        }
    }

    private ImageIcon getIcon(String path) {
        return new ImageIcon(new ImageIcon(getClass().getResource(path)).getImage().
                getScaledInstance(GameSettings.getMoleculeWidth(), -1, Image.SCALE_SMOOTH));

    }

}
