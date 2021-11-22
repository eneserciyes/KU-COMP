package tr.com.teamfaster.ui.views.statistics;

import tr.com.teamfaster.domain.controller.GameController;
import tr.com.teamfaster.domain.managers.AtomShooterManager;
import tr.com.teamfaster.domain.utils.EntityType;
import tr.com.teamfaster.domain.utils.GameSettings;
import tr.com.teamfaster.domain.utils.ShieldType;
import tr.com.teamfaster.ui.views.powerups.PowerUpIconFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creates the powerups and shields panels on frame.
 * When there happens a collection or shooting of powerup updates the powerup counts.
 * In case of usage of shield updates the number of the shield.
 */
public class PowerUpsPanel extends JPanel {
    private Map<EntityType, Integer> powerupNumbers;
    private Map<ShieldType, Integer> shieldNumbers;
    private List<JLabel> labels;
    private List<JLabel> texts;
    private List<JButton> buttons;
    private List<String> buttonTexts;

    public PowerUpsPanel() {
        texts = new ArrayList<>();
        labels = new ArrayList<>();
        buttons = new ArrayList<>();
        buttonTexts = new ArrayList<>();

        buttonTexts.add("Eta");
        buttonTexts.add("Lota");
        buttonTexts.add("Zeta");
        buttonTexts.add("Theta");

        shieldNumbers = new HashMap<>();
        shieldNumbers.put(ShieldType.ETA, 0);
        shieldNumbers.put(ShieldType.LOTA, 0);
        shieldNumbers.put(ShieldType.ZETA, 0);
        shieldNumbers.put(ShieldType.THETA, 0);

        powerupNumbers = new HashMap<>();
        powerupNumbers.put(EntityType.ALPHA, 0);
        powerupNumbers.put(EntityType.BETA, 0);
        powerupNumbers.put(EntityType.SIGMA, 0);
        powerupNumbers.put(EntityType.GAMMA, 0);

        this.setLayout(new GridBagLayout());

        int height = (int) (GameSettings.getStatisticsMiddleWindowRatio() * GameSettings.getStatisticsWindowHeight());
        this.setBounds(GameSettings.getGameWidth(), (int) (GameSettings.getStatisticsWindowHeight() * GameSettings.getStatisticsTopWindowRatio()), GameSettings.getStatisticsWindowWidth(), height);
        this.setBackground(GameSettings.getStatisticsWindowColor());
        this.setBorder(BorderFactory.createBevelBorder(0));

        for (int i = 0; i < EntityType.values().length; i++) {
            EntityType type = EntityType.values()[i];
            JLabel label = new JLabel();
            ImageIcon scaledIcond = PowerUpIconFactory.getInstance().createIcon(type);
            scaledIcond = new ImageIcon(scaledIcond.getImage().getScaledInstance(2 * scaledIcond.getIconWidth() / 3, -1, Image.SCALE_SMOOTH));
            label.setIcon(scaledIcond);
            JLabel text = new JLabel();
            GridBagConstraints labelConstraints = constraintProducer(0, i + 2, 1, 1);
            this.add(label, labelConstraints);
            label.addMouseListener(createMouseAdapter());
            GridBagConstraints textConstraints = constraintProducer(1, i + 2, 1, 1);
            this.add(text, textConstraints);

            labels.add(label);
            texts.add(text);

        }

        JButton etaButton = new JButton();
        etaButton.setFocusable(false);
        etaButton.setText(buttonTexts.get(0));
        GridBagConstraints constraintEta = constraintProducer(0, 6, 1, 1);
        this.add(etaButton, constraintEta);
        buttons.add(etaButton);
        etaButton.addActionListener(e -> GameController.getInstance().addShield(ShieldType.ETA));

        JButton lotaButton = new JButton();
        lotaButton.setFocusable(false);
        lotaButton.setText(buttonTexts.get(1));
        GridBagConstraints constraintLota = constraintProducer(0, 7, 1, 1);
        this.add(lotaButton, constraintLota);
        buttons.add(lotaButton);
        lotaButton.addActionListener(e -> GameController.getInstance().addShield(ShieldType.LOTA));

        JButton thetaButton = new JButton();
        thetaButton.setFocusable(false);
        thetaButton.setText(buttonTexts.get(3));
        GridBagConstraints constraintTheta = constraintProducer(1, 6, 1, 1);
        this.add(thetaButton, constraintTheta);
        buttons.add(thetaButton);
        thetaButton.addActionListener(e -> GameController.getInstance().addShield(ShieldType.THETA));

        JButton zetaButton = new JButton();
        zetaButton.setFocusable(false);
        zetaButton.setText(buttonTexts.get(2));
        GridBagConstraints constraintZeta = constraintProducer(1, 7, 1, 1);
        this.add(zetaButton, constraintZeta);
        buttons.add(zetaButton);
        zetaButton.addActionListener(e -> GameController.getInstance().addShield(ShieldType.ZETA));

        setTextContents();
    }

    /**
     * delegate message to atomShooter to shoot a powerup.
     *
     * @return: click on a powerup label
     */
    private MouseAdapter createMouseAdapter() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for (int i = 0; i < EntityType.values().length; i++) {
                    if (e.getSource() == labels.get(i) && Integer.parseInt(texts.get(i).getText()) > 0) {
                        AtomShooterManager.getInstance().setBarrelView(EntityType.values()[i]);
                        AtomShooterManager.getInstance().setPowerupShootStatus(true);
                    }
                }
            }
        };
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
            texts.get(i).setText(Integer.toString(powerupNumbers.get(EntityType.values()[i])));
        }
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setText(buttonTexts.get(i) + " " + shieldNumbers.get(ShieldType.values()[i]));
        }

    }

    /**
     * Set the powerup count to the new value.
     *
     * @param type: type of the updated powerup
     * @param i:    new value of the count of the powerup
     */
    public void setPowerupNumbers(EntityType type, int i) {
        this.powerupNumbers.put(type, i);
        setTextContents();
    }

    /**
     * Increments or decrements the shield counts.
     *
     * @param shieldNumbers: new shield counts
     */
    public void updateShieldNumbers(Map<ShieldType, Integer> shieldNumbers) {
        this.shieldNumbers = shieldNumbers;
        setTextContents();
    }


    public void setShieldNumbers(ShieldType type, int i) {
        this.shieldNumbers.put(type, i);
        setTextContents();
    }

}
