package tr.com.teamfaster.ui.views.statistics;

import tr.com.teamfaster.domain.utils.EntityType;
import tr.com.teamfaster.domain.utils.GameSettings;
import tr.com.teamfaster.ui.views.atoms.AtomIconFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creates the labels for atom counts and updates them for shooting and blending.
 */
public class AtomStatisticsPanel extends JPanel {
    private Map<EntityType, Integer> atomNumbers;

    private JLabel blender;
    private List<JLabel> labels;
    private List<JLabel> texts;

    public AtomStatisticsPanel() {
        atomNumbers = new HashMap<>();
        atomNumbers.put(EntityType.ALPHA, 0);
        atomNumbers.put(EntityType.BETA, 0);
        atomNumbers.put(EntityType.SIGMA, 0);
        atomNumbers.put(EntityType.GAMMA, 0);

        this.setLayout(new GridBagLayout());
        int height = (int) (GameSettings.getStatisticsBottomWindowRatio() * GameSettings.getStatisticsWindowHeight());
        this.setBounds(GameSettings.getGameWidth(), GameSettings.getStatisticsWindowHeight() - height, GameSettings.getStatisticsWindowWidth(), height);
        this.setBackground(GameSettings.getStatisticsWindowColor());
        this.setBorder(BorderFactory.createBevelBorder(0));

        ImageIcon blenderIcon = new ImageIcon(new ImageIcon(getClass().getResource("../../resources/mixer.png")).getImage().
                getScaledInstance(100, -1, Image.SCALE_SMOOTH));

        JLabel blenderLabel = new JLabel();
        blenderLabel.setIcon(blenderIcon);
        GridBagConstraints blenderConstraints = constraintProducer(0, 0, 2, 2);
        this.add(blenderLabel, blenderConstraints);

        labels = new ArrayList<>();
        texts = new ArrayList<>();

        for (int i = 0; i < EntityType.values().length; i++) {
            EntityType type = EntityType.values()[i];
            JLabel label = new JLabel();
            label.setIcon(AtomIconFactory.getInstance().createIcon(type));
            JLabel text = new JLabel();
            GridBagConstraints labelConstraints = constraintProducer(0, i + 2, 1, 1);
            this.add(label, labelConstraints);
            GridBagConstraints textConstraints = constraintProducer(1, i + 2, 1, 1);
            this.add(text, textConstraints);

            labels.add(label);
            texts.add(text);

        }

        setTextContents();
    }

    private GridBagConstraints constraintProducer(int gridx, int gridy, int widthx, int heigthy) {
        GridBagConstraints cc = new GridBagConstraints();
        cc.gridx = gridx;
        cc.gridy = gridy;
        cc.gridwidth = widthx;
        cc.gridheight = heigthy;
        return cc;
    }

    private void setTextContents() {
        for (int i = 0; i < texts.size(); i++) {
            texts.get(i).setText(Integer.toString(atomNumbers.get(EntityType.values()[i])));
        }
    }

    /**
     * Increments or decrements the counts od the atoms
     *
     * @param atomNumbers: new counts of the atoms
     */
    public void updateAtomAmounts(Map<EntityType, Integer> atomNumbers) {
        this.atomNumbers = atomNumbers;
        setTextContents();
    }
}
