package tr.com.teamfaster.ui.views;

import tr.com.teamfaster.domain.listeners.IPositionListener;
import tr.com.teamfaster.domain.utils.GameSettings;
import tr.com.teamfaster.domain.utils.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Manage the atom shooter and barrel view.
 */
public class AtomShooterView extends JLabel implements IPositionListener {
    private JLabel atomOnTop;
    private ImageIcon shooterImage;
    private double rotation = 0;

    public AtomShooterView(Position position) {
        atomOnTop = new JLabel();
        shooterImage = new ImageIcon(new ImageIcon(getClass().getResource("../resources/shooter.png")).getImage().
                getScaledInstance(-1, GameSettings.getAtomShooterHeight(), Image.SCALE_SMOOTH));
        this.setBounds(0, 0, shooterImage.getIconWidth(), shooterImage.getIconHeight());
        this.setLocation((int) position.getX(), (int) position.getY());
        this.setPreferredSize(new Dimension(250, 100));
    }

    @Override
    public void onPositionChanged(Position position) {
        setLocation((int) position.getX(), (int) position.getY());
        setEntityOnTopOfShooter();
        repaint();
    }

    @Override
    public void onVisibilityChange() {

    }

    /**
     * The atomShooter view rotated bu rotation degree.
     *
     * @param rotation: degree of rotation
     */
    public void rotate(float rotation) {
        BufferedImage newImage = new BufferedImage(shooterImage.getIconWidth(), shooterImage.getIconHeight(), 2);
        this.rotation = -Math.toRadians(rotation);
        setEntityOnTopOfShooter();
        this.paintComponent(this.getGraphics());
    }

    /**
     * The atomShooter view rotated and painted to frame.
     *
     * @param g: graphics of the atomShooter view
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.rotate(rotation, shooterImage.getImage().getHeight(null) + shooterImage.getImage().getWidth(null) / 2, shooterImage.getImage().getHeight(null));
        g2.drawImage(shooterImage.getImage(), shooterImage.getImage().getHeight(null), 0, null);
        this.setSize(shooterImage.getImage().getHeight(null) * 2, shooterImage.getImage().getHeight(null));
        this.repaint();
    }

    /**
     * Sets the barrel of the atomShooter view with the icon.
     *
     * @param icon: atom or powerup
     * @return: the barrel view
     */
    public JLabel setBarrelIcon(ImageIcon icon) {
        atomOnTop.setIcon(icon);
        atomOnTop.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
        setEntityOnTopOfShooter();
        return atomOnTop;
    }

    /**
     * Calculates the position of the barrel of the atomShooter.
     */
    private void setEntityOnTopOfShooter() {
        int x = (int) ((int) this.getLocation().getX() + shooterImage.getIconHeight() + shooterImage.getIconWidth() / 2 + shooterImage.getIconHeight() * Math.sin(rotation));
        int y = (int) (((int) this.getLocation().getY() + shooterImage.getIconHeight() - (atomOnTop.getWidth() / 2 + shooterImage.getIconHeight()) * Math.cos(rotation)));

        atomOnTop.setLocation(x - atomOnTop.getWidth() / 2, y - atomOnTop.getHeight() / 2);
        atomOnTop.repaint();
    }

    /**
     * Gives the position of the top of the atomShooter
     *
     * @return: position of the barrel
     */
    public Position getAtomOnTopPosition() {
        return new Position(atomOnTop.getX(), atomOnTop.getY());
    }

}
