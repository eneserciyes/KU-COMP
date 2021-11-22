package tr.com.teamfaster.ui.views.molecules;

import tr.com.teamfaster.domain.utils.Position;

import javax.swing.*;
import java.awt.*;

public class SpinningMoleculeView extends MoleculeView {
    private float degrees = 10;

    public SpinningMoleculeView(ImageIcon icon) {
        super();
        this.icon = icon;
        this.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight() * 5 / 2);

    }

    @Override
    public void onPositionChanged(Position position) {
        this.setLocation((int) position.getX(), (int) position.getY());
    }

    /**
     * Spinning molecule view is painted.
     *
     * @param g: graphics of the spinning molecule view.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(this.getWidth() / 2, this.getHeight() / 2);
        g2d.rotate(Math.toRadians(degrees));
        g2d.translate(-icon.getIconWidth() / 2, -icon.getIconHeight() / 2);
        g2d.drawImage(icon.getImage(), 0, 0, null);
        repaint();
        degrees++;

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(icon.getIconWidth(), icon.getIconHeight());
    }

}
