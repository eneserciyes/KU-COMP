package tr.com.teamfaster.ui.views.statistics;

import tr.com.teamfaster.domain.utils.GameSettings;
import tr.com.teamfaster.ui.managers.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * Creates labels for health and time, updates these values over the game.
 */
public class HealthTimeStatisticsPanel extends JPanel {

    private final JLabel timeLabel;
    private final JLabel healthLabel;
    private final JLabel scoreLabel;

    public HealthTimeStatisticsPanel() {
        this.setLayout(new GridBagLayout());
        timeLabel = new JLabel();
        healthLabel = new JLabel();
        scoreLabel = new JLabel();

        int height = (int) (GameSettings.getStatisticsTopWindowRatio() * GameSettings.getStatisticsWindowHeight());
        this.setBounds(GameSettings.getGameWidth(), 0, GameSettings.getStatisticsWindowWidth(), height);
        this.setBackground(GameSettings.getStatisticsWindowColor());
        this.setBorder(BorderFactory.createBevelBorder(0));

        updateTime(0);
        updateLabels(100, 0);

        GridBagConstraints constraintTime = constraintProducer(0, 0, 1, 1);
        this.add(timeLabel, constraintTime);

        GridBagConstraints constraintHealth = constraintProducer(0, 1, 1, 1);
        this.add(healthLabel, constraintHealth);

        GridBagConstraints constraintScore = constraintProducer(0, 2, 1, 1);
        this.add(scoreLabel, constraintScore);

    }

    public void updateLabels(int health, double score) {

        healthLabel.setText("Health: " + health);
        scoreLabel.setText("Score: " + String.format("%.2f", score));
    }

    public void updateTime(long time) {
        String timeText = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(time), TimeUnit.MILLISECONDS.toMinutes(time) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time)), TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)));
        timeLabel.setText("Time: " + timeText);
        GameFrame.getInstance().repaint();
    }

    private GridBagConstraints constraintProducer(int gridx, int gridy, int widthx, int heigthy) {
        GridBagConstraints cc = new GridBagConstraints();
        cc.gridx = gridx;
        cc.gridy = gridy;
        cc.gridwidth = widthx;
        cc.gridheight = heigthy;
        return cc;
    }

}

