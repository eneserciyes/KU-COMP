package tr.com.teamfaster.ui.managers;

import tr.com.teamfaster.domain.controller.GameController;
import tr.com.teamfaster.domain.listeners.IViewChangeListener;
import tr.com.teamfaster.domain.managers.*;
import tr.com.teamfaster.domain.utils.*;
import tr.com.teamfaster.ui.views.AtomShooterView;
import tr.com.teamfaster.ui.views.atoms.AtomIconFactory;
import tr.com.teamfaster.ui.views.atoms.AtomView;
import tr.com.teamfaster.ui.views.blockers.BlockerView;
import tr.com.teamfaster.ui.views.blockers.BlockerViewFactory;
import tr.com.teamfaster.ui.views.molecules.MoleculeView;
import tr.com.teamfaster.ui.views.molecules.MoleculeViewFactory;
import tr.com.teamfaster.ui.views.powerups.PowerUpIconFactory;
import tr.com.teamfaster.ui.views.powerups.PowerupView;
import tr.com.teamfaster.ui.views.powerups.PowerupViewFactory;
import tr.com.teamfaster.ui.views.statistics.AtomStatisticsPanel;
import tr.com.teamfaster.ui.views.statistics.HealthTimeStatisticsPanel;
import tr.com.teamfaster.ui.views.statistics.PowerUpsPanel;

import javax.swing.*;
import java.util.Map;

public class ViewChangeListener implements IViewChangeListener {

    private final GameController controller;
    private final GameFrame frame;

    private AtomShooterView shooterView;
    private AtomStatisticsPanel atomStatistics;
    private HealthTimeStatisticsPanel healthTimeStatisticsPanel;
    private PowerUpsPanel powerUpsPanel;

    public ViewChangeListener() {

        this.controller = GameController.getInstance();
        this.frame = GameFrame.getInstance();
        initialize();
    }

    private void initialize() {
        controller.setCreator(this);
        AtomManager.getInstance().subscribeViewManager(this);
        AtomShooterManager.getInstance().subscribeViewManager(this);
        MoleculeManager.getInstance().subscribeViewManager(this);
        PowerupManager.getInstance().subscribeViewManager(this);
        BlockerManager.getInstance().subscribeViewManager(this);
        Blender.getInstance().subscribeViewManager(this);
    }

    @Override
    public void onCreateAtom(EntityType type, Position position) {
        AtomView atomView = new AtomView(AtomIconFactory.getInstance().createIcon(type), position);
        AtomManager.getInstance().subscribe(atomView);
        frame.add(atomView);
        frame.repaint();
    }

    @Override
    public void onCreateBlocker(EntityType type, Position position) {
        BlockerView blockerView = BlockerViewFactory.getInstance().createBlockerView(type);
        BlockerManager.getInstance().subscribe(blockerView);
        frame.add(blockerView);
    }

    @Override
    public void onCreateMolecule(EntityType type, Position position) {
        MoleculeView moleculeView = MoleculeViewFactory.getInstance().createMoleculeView(type);
        MoleculeManager.getInstance().subscribe(moleculeView);
        frame.add(moleculeView);
    }

    @Override
    public void onCreatePowerup(EntityType type, Position position, boolean status) {
        PowerupView powerupView;
        if (status) powerupView = PowerupViewFactory.getInstance().createPowerupView(type);
        else powerupView = new PowerupView(PowerUpIconFactory.getInstance().createIcon(type), position);
        PowerupManager.getInstance().subscribe(powerupView);
        frame.add(powerupView);
        frame.repaint();
    }

    @Override
    public void onChangeShooterBarrelViewPowerup(EntityType value) {
        JLabel powerupIcon = shooterView.setBarrelIcon(PowerUpIconFactory.getInstance().createIcon(value));
        frame.add(powerupIcon);
        frame.repaint();

    }

    @Override
    public void onCreateAtomShooter() {
        shooterView = new AtomShooterView(AtomShooterManager.getInstance().getShooterPosition());
        onChangeShooterBarrelView(AtomShooterManager.getInstance().getCurrentAtomType());
        AtomShooterManager.getInstance().subscribeToAtomShooter(shooterView);

        frame.add(shooterView);
        frame.repaint();
    }

    public void onChangeShooterBarrelView(EntityType entityType) {
        JLabel atomIcon = shooterView.setBarrelIcon(AtomIconFactory.getInstance().createIcon(entityType));
        frame.add(atomIcon);
        frame.repaint();
    }

    public void onCreateAtomStatisticsPanel() {
        atomStatistics = new AtomStatisticsPanel();
        atomStatistics.updateAtomAmounts(AtomShooterManager.getInstance().getAtomCounts());
        GameFrame.getInstance().add(atomStatistics);
        GameFrame.getInstance().repaint();
        GameFrame.getInstance().setVisible(true);
    }

    @Override
    public void onCreatePowerupsPanel() {
        powerUpsPanel.updateShieldNumbers(AtomShooterManager.getInstance().getShieldCounts());
        GameFrame.getInstance().add(powerUpsPanel);
        GameFrame.getInstance().repaint();
        GameFrame.getInstance().setVisible(true);
    }

    public void createPowerUpsPanel() {
        powerUpsPanel = new PowerUpsPanel();
        // powerUpsPanel.updateAtomAmounts(controller.getPowerupCounts());
        GameFrame.getInstance().add(powerUpsPanel);
        onCreatePowerupsPanel();
        GameFrame.getInstance().repaint();
        GameFrame.getInstance().setVisible(true);
    }

    public void createHealthTimeStatisticsPanel() {
        healthTimeStatisticsPanel = new HealthTimeStatisticsPanel();
        GameFrame.getInstance().add(healthTimeStatisticsPanel);
        GameFrame.getInstance().repaint();
        GameFrame.getInstance().setVisible(true);
    }

    @Override
    public void onAtomStatisticsUpdate(Map<EntityType, Integer> numbers) {
        atomStatistics.updateAtomAmounts(numbers);
        GameFrame.getInstance().repaint();
    }

    @Override
    public void onShieldNumberUpdate(Map<ShieldType, Integer> numbers) {
        powerUpsPanel.updateShieldNumbers(numbers);
        GameFrame.getInstance().repaint();
    }

    @Override
    public void onTimeUpdate(long time) {
        healthTimeStatisticsPanel.updateTime(time);
    }

    @Override
    public void onHealthScoreUpdate(int health, double score) {
        healthTimeStatisticsPanel.updateLabels(health, score);
        GameFrame.getInstance().repaint();
    }

    @Override
    public void onPowerupCounts(EntityType type, int i) {
        powerUpsPanel.setPowerupNumbers(type, i);
    }

    public void onShieldCounts(ShieldType type, int i) {
        powerUpsPanel.setShieldNumbers(type, i);
    }

    @Override
    public BlendInfo onShowBlendInputDialog() {
        try {
            String input = (String) JOptionPane.showInputDialog(frame, "Select the atom type to blend: ", "Blender", JOptionPane.PLAIN_MESSAGE, null, null, "");
            if (input != null) {
                validate(input);
                String output = (String) JOptionPane.showInputDialog(frame, "Select the atom type to generate: ", "Blender", JOptionPane.PLAIN_MESSAGE, null, null, "");
                if (output != null) {
                    validate(output);
                    return new BlendInfo(Integer.parseInt(input), Integer.parseInt(output));
                }
            }
        } catch (Exception e) {
            return onShowBlendInputDialog();
        }
        return null;
    }

    private void validate(String s) throws Exception {
        ValidationStatus status = BlendInputValidator.validate(s);
        if (status == ValidationStatus.INVALID_INPUT) {
            JOptionPane.showMessageDialog(frame, String.format("Your input should be between 1 and %d", EntityType.values().length - 1), "Blender", JOptionPane.ERROR_MESSAGE);
            throw new Exception();
        } else if (status == ValidationStatus.INVALID_FORMAT) {
            JOptionPane.showMessageDialog(frame, "You can use atom ranks to specify atom types.", "Blender", JOptionPane.ERROR_MESSAGE);
            throw new Exception();
        }
    }

    @Override
    public void onCreateInitialObjects() {
        onCreateAtomShooter();
        onCreateAtomStatisticsPanel();
        createPowerUpsPanel();
        createHealthTimeStatisticsPanel();

    }
}
