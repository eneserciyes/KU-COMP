package tr.com.teamfaster;

import tr.com.teamfaster.domain.utils.EntityType;
import tr.com.teamfaster.domain.utils.GameSettings;
import tr.com.teamfaster.domain.utils.ShieldType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * BuildMenu is the configuration menu used in build mode. It allows customizing the parameters of the game,
 * as well as setting a username for the player. It passes values for parameters to a GameInfo object, which
 * is then used to communicate these to the domain layer.
 */
public class BuildMenu extends JFrame implements ActionListener {
    private GameInfo info;

    private JTextField username;

    private JTextField alphaAC;
    private JTextField betaAC;
    private JTextField gammaAC;
    private JTextField sigmaAC;

    private JTextField alphaMC;
    private JTextField betaMC;
    private JTextField gammaMC;
    private JTextField sigmaMC;

    private JTextField alphaBC;
    private JTextField betaBC;
    private JTextField gammaBC;
    private JTextField sigmaBC;

    private JTextField alphaPC;
    private JTextField betaPC;
    private JTextField gammaPC;
    private JTextField sigmaPC;

    private JTextField etaSC;
    private JTextField lotaSC;
    private JTextField thetaSC;
    private JTextField zetaSC;

    private JTextField LValue;
    private JRadioButton alphaFolded;
    private JRadioButton alphaLinear;
    private JCheckBox alphaSpin;
    private JRadioButton betaFolded;
    private JRadioButton betaLinear;
    private JCheckBox betaSpin;
    private JRadioButton diffEasy;
    private JRadioButton diffMedium;
    private JRadioButton diffHard;
    private JButton startButton;

    public BuildMenu(GameInfo info) {
        setLayout(new GridBagLayout());
        setVisible(true);
        setSize(GameSettings.getGameFrameWidth(), GameSettings.getGameFrameHeight() + getInsets().top);

        this.info = info;

        this.username = new JTextField("Player 1", 7);
        this.alphaAC = new JTextField("100", 3);
        this.betaAC = new JTextField("100", 3);
        this.gammaAC = new JTextField("100", 3);
        this.sigmaAC = new JTextField("100", 3);
        this.alphaMC = new JTextField("100", 3);
        this.betaMC = new JTextField("100", 3);
        this.gammaMC = new JTextField("100", 3);
        this.sigmaMC = new JTextField("100", 3);
        this.alphaBC = new JTextField("10", 3);
        this.betaBC = new JTextField("10", 3);
        this.gammaBC = new JTextField("10", 3);
        this.sigmaBC = new JTextField("10", 3);
        this.alphaPC = new JTextField("20", 3);
        this.betaPC = new JTextField("20", 3);
        this.gammaPC = new JTextField("20", 3);
        this.sigmaPC = new JTextField("20", 3);
        this.etaSC = new JTextField("10", 3);
        this.lotaSC = new JTextField("10", 3);
        this.thetaSC = new JTextField("10", 3);
        this.zetaSC = new JTextField("10", 3);
        this.LValue = new JTextField(String.valueOf(GameSettings.getL()), 3);

        ButtonGroup alphaMStructure = new ButtonGroup();
        alphaFolded = new JRadioButton("Folded", true);
        alphaFolded.addActionListener(this);
        alphaLinear = new JRadioButton("Linear");
        alphaLinear.addActionListener(this);
        alphaMStructure.add(alphaFolded);
        alphaMStructure.add(alphaLinear);
        alphaSpin = new JCheckBox("Alpha Molecules Spin");
        alphaSpin.setEnabled(false);

        ButtonGroup betaMStructure = new ButtonGroup();
        betaFolded = new JRadioButton("Folded", true);
        betaFolded.addActionListener(this);
        betaLinear = new JRadioButton("Linear");
        betaLinear.addActionListener(this);
        betaMStructure.add(betaFolded);
        betaMStructure.add(betaLinear);
        this.betaSpin = new JCheckBox("Beta Molecules Spin");
        betaSpin.setEnabled(false);

        ButtonGroup difficulty = new ButtonGroup();
        diffEasy = new JRadioButton("Easy");
        diffMedium = new JRadioButton("Medium", true);
        diffHard = new JRadioButton("Hard");
        difficulty.add(diffEasy);
        difficulty.add(diffMedium);
        difficulty.add(diffHard);

        startButton = new JButton("Start");
        startButton.setFont(new Font(startButton.getFont().getName(), Font.PLAIN, 24));
        startButton.addActionListener(this);

        // Atom Counts
        add(new JLabel("Alpha Atom Count:"), constraintProducer(1, 1, 1, 1));
        add(alphaAC, constraintProducer(2, 1, 1, 1));

        add(new JLabel("Beta Atom Count:"), constraintProducer(1, 2, 1, 1));
        add(betaAC, constraintProducer(2, 2, 1, 1));

        add(new JLabel("Sigma Atom Count:"), constraintProducer(1, 3, 1, 1));
        add(sigmaAC, constraintProducer(2, 3, 1, 1));

        add(new JLabel("Gamma Atom Count:"), constraintProducer(1, 4, 1, 1));
        add(gammaAC, constraintProducer(2, 4, 1, 1));
        //add(new JLabel("     "), constraintProducer(3, 1, 1, 1));

        // Molecule  Counts
        add(new JLabel("Alpha Molecule Count:"), constraintProducer(4, 1, 1, 1));
        add(alphaMC, constraintProducer(5, 1, 1, 1));

        add(new JLabel("Beta Molecule Count:"), constraintProducer(4, 2, 1, 1));
        add(betaMC, constraintProducer(5, 2, 1, 1));

        add(new JLabel("Sigma Molecule Count:"), constraintProducer(4, 3, 1, 1));
        add(sigmaMC, constraintProducer(5, 3, 1, 1));

        add(new JLabel("Gamma Molecule Count:"), constraintProducer(4, 4, 1, 1));
        add(gammaMC, constraintProducer(5, 4, 1, 1));

        add(new JLabel("     "), constraintProducer(1, 5, 1, 1));

        // Power-up Counts
        add(new JLabel("Alpha Power-up Count:"), constraintProducer(1, 6, 1, 1));
        add(alphaPC, constraintProducer(2, 6, 1, 1));

        add(new JLabel("Beta Power-up Count:"), constraintProducer(1, 7, 1, 1));
        add(betaPC, constraintProducer(2, 7, 1, 1));

        add(new JLabel("Sigma Power-up Count:"), constraintProducer(1, 8, 1, 1));
        add(sigmaPC, constraintProducer(2, 8, 1, 1));

        add(new JLabel("Gamma Power-up Count:"), constraintProducer(1, 9, 1, 1));
        add(gammaPC, constraintProducer(2, 9, 1, 1));

        // Blocker  Counts
        add(new JLabel("Alpha Blocker Count:"), constraintProducer(4, 6, 1, 1));
        add(alphaBC, constraintProducer(5, 6, 1, 1));

        add(new JLabel("Beta Blocker Count:"), constraintProducer(4, 7, 1, 1));
        add(betaBC, constraintProducer(5, 7, 1, 1));

        add(new JLabel("Sigma Blocker Count:"), constraintProducer(4, 8, 1, 1));
        add(sigmaBC, constraintProducer(5, 8, 1, 1));

        add(new JLabel("Gamma Blocker Count:"), constraintProducer(4, 9, 1, 1));
        add(gammaBC, constraintProducer(5, 9, 1, 1));

        add(new JLabel("     "), constraintProducer(6, 1, 1, 1));
        add(new JLabel("     "), constraintProducer(5, 5, 1, 1));

        // Shield  Counts
        add(new JLabel("Eta Shield Count:"), constraintProducer(7, 1, 1, 1));
        add(etaSC, constraintProducer(8, 1, 1, 1));

        add(new JLabel("Lota Shield Count:"), constraintProducer(7, 2, 1, 1));
        add(lotaSC, constraintProducer(8, 2, 1, 1));

        add(new JLabel("Theta Shield Count:"), constraintProducer(7, 3, 1, 1));
        add(thetaSC, constraintProducer(8, 3, 1, 1));

        add(new JLabel("Zeta Shield Count:"), constraintProducer(7, 4, 1, 1));
        add(zetaSC, constraintProducer(8, 4, 1, 1));

        // Alpha & Beta molecule structures, spin
        add(new JLabel("     "), constraintProducer(1, 10, 1, 1));
        add(new JLabel("     "), constraintProducer(1, 11, 1, 1));
        add(new JLabel("     "), constraintProducer(1, 16, 1, 1));

        add(new JLabel("Alpha Molecule Structure:"), constraintProducer(1, 12, 1, 1));
        add(alphaFolded, constraintProducer(1, 13, 1, 1));
        add(alphaLinear, constraintProducer(1, 14, 1, 1));
        add(alphaSpin, constraintProducer(1, 17, 1, 1));

        add(new JLabel("Beta Molecule Structure:"), constraintProducer(3, 12, 1, 1));
        add(betaFolded, constraintProducer(3, 13, 1, 1));
        add(betaLinear, constraintProducer(3, 14, 1, 1));
        add(betaSpin, constraintProducer(3, 17, 1, 1));

        // Difficulty
        add(new JLabel("Difficulty:"), constraintProducer(4, 12, 1, 1));
        add(diffEasy, constraintProducer(4, 13, 1, 1));
        add(diffMedium, constraintProducer(4, 14, 1, 1));
        add(diffHard, constraintProducer(4, 15, 1, 1));

        add(new JLabel("     "), constraintProducer(5, 12, 1, 1));

        // Value of L
        add(new JLabel("L:"), constraintProducer(6, 12, 1, 1));
        add(LValue, constraintProducer(7, 12, 1, 1));

        // username
        add(new JLabel("Username:"), constraintProducer(6, 14, 1, 1));
        add(username, constraintProducer(7, 14, 1, 1));

        // Start button
        add(new JLabel("     "), constraintProducer(1, 18, 1, 1));
        add(startButton, constraintProducer(7, 19, 1, 1));

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == alphaLinear) {
            alphaSpin.setEnabled(true);
        } else if (e.getSource() == alphaFolded) {
            alphaSpin.setSelected(false);
            alphaSpin.setEnabled(false);
        } else if (e.getSource() == betaLinear) {
            betaSpin.setEnabled(true);
        } else if (e.getSource() == betaFolded) {
            betaSpin.setSelected(false);
            betaSpin.setEnabled(false);
        } else if (e.getSource() == diffEasy) {
            diffEasy.setSelected(true);
            diffEasy.setEnabled(false);
        } else if (e.getSource() == diffMedium) {
            diffMedium.setSelected(true);
            diffMedium.setEnabled(false);
        } else if (e.getSource() == diffHard) {
            diffHard.setSelected(true);
            diffHard.setEnabled(false);
        } else if (e.getSource() == startButton) {
            int i = 0;

            if (Integer.parseInt(alphaAC.getText()) >= 0) {
                info.setAtomCount(EntityType.ALPHA, Integer.parseInt(alphaAC.getText()));
                i++;
            }
            if (Integer.parseInt(betaAC.getText()) >= 0) {
                info.setAtomCount(EntityType.BETA, Integer.parseInt(betaAC.getText()));
                i++;
            }
            if (Integer.parseInt(gammaAC.getText()) >= 0) {
                info.setAtomCount(EntityType.GAMMA, Integer.parseInt(gammaAC.getText()));
                i++;
            }
            if (Integer.parseInt(sigmaAC.getText()) >= 0) {
                info.setAtomCount(EntityType.SIGMA, Integer.parseInt(sigmaAC.getText()));
                i++;
            }
            if (Integer.parseInt(alphaMC.getText()) >= 0) {
                info.setMoleculeCount(EntityType.ALPHA, Integer.parseInt(alphaMC.getText()));
                i++;
            }
            if (Integer.parseInt(betaMC.getText()) >= 0) {
                info.setMoleculeCount(EntityType.BETA, Integer.parseInt(betaMC.getText()));
                i++;
            }
            if (Integer.parseInt(gammaMC.getText()) >= 0) {
                info.setMoleculeCount(EntityType.GAMMA, Integer.parseInt(gammaMC.getText()));
                i++;
            }
            if (Integer.parseInt(sigmaMC.getText()) >= 0) {
                info.setMoleculeCount(EntityType.SIGMA, Integer.parseInt(sigmaMC.getText()));
                i++;
            }
            if (Integer.parseInt(alphaBC.getText()) >= 0) {
                info.setBlockerCount(EntityType.ALPHA, Integer.parseInt(alphaMC.getText()));
                i++;
            }
            if (Integer.parseInt(betaBC.getText()) > 0) {
                info.setBlockerCount(EntityType.BETA, Integer.parseInt(betaMC.getText()));
                i++;
            }
            if (Integer.parseInt(gammaBC.getText()) > 0) {
                info.setBlockerCount(EntityType.GAMMA, Integer.parseInt(gammaMC.getText()));
                i++;
            }
            if (Integer.parseInt(sigmaBC.getText()) > 0) {
                info.setBlockerCount(EntityType.SIGMA, Integer.parseInt(sigmaMC.getText()));
                i++;
            }
            if (Integer.parseInt(alphaPC.getText()) > 0) {
                info.setBlockerCount(EntityType.ALPHA, Integer.parseInt(alphaPC.getText()));
                i++;
            }
            if (Integer.parseInt(betaPC.getText()) > 0) {
                info.setBlockerCount(EntityType.BETA, Integer.parseInt(betaPC.getText()));
                i++;
            }
            if (Integer.parseInt(gammaPC.getText()) > 0) {
                info.setBlockerCount(EntityType.GAMMA, Integer.parseInt(gammaPC.getText()));
                i++;
            }
            if (Integer.parseInt(sigmaPC.getText()) > 0) {
                info.setBlockerCount(EntityType.SIGMA, Integer.parseInt(sigmaPC.getText()));
                i++;
            }
            if (Integer.parseInt(etaSC.getText()) > 0) {
                info.setShieldCount(ShieldType.ETA, Integer.parseInt(etaSC.getText()));
                i++;
            }
            if (Integer.parseInt(lotaSC.getText()) > 0) {
                info.setShieldCount(ShieldType.LOTA, Integer.parseInt(lotaSC.getText()));
                i++;
            }
            if (Integer.parseInt(thetaSC.getText()) > 0) {
                info.setShieldCount(ShieldType.THETA, Integer.parseInt(thetaSC.getText()));
                i++;
            }
            if (Integer.parseInt(zetaSC.getText()) > 0) {
                info.setShieldCount(ShieldType.ZETA, Integer.parseInt(zetaSC.getText()));
                i++;
            }
            if (Integer.parseInt(LValue.getText()) > 0) {
                info.setL(Integer.parseInt(LValue.getText()));
                i++;
            }
            if (username.getText().length() > 0) {
                info.setUsername(username.getText());
                i++;
            }

            info.setAlphaLinear(alphaLinear.isSelected());
            info.setAlphaSpinning(alphaSpin.isSelected());
            info.setBetaLinear(betaLinear.isSelected());
            info.setBetaSpinning(betaSpin.isSelected());
            if (diffEasy.isSelected()) info.setdLevel(Level.EASY);
            else if (diffMedium.isSelected()) info.setdLevel(Level.MEDIUM);
            else info.setdLevel(Level.HARD);

            if (i == 22) info.setReady(true);
        }
    }

    private GridBagConstraints constraintProducer(int gridx, int gridy, int widthx, int heigthy) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = gridx;
        c.gridy = gridy;
        c.gridwidth = widthx;
        c.gridheight = heigthy;
        return c;
    }

    public boolean isReady() {
        return info.isReady();
    }
}
