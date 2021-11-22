package tr.com.teamfaster.ui.views.powerups;

import tr.com.teamfaster.domain.listeners.IPositionListener;
import tr.com.teamfaster.domain.utils.Position;
import tr.com.teamfaster.ui.managers.GameFrame;

import javax.swing.*;

public class PowerupView extends JLabel implements IPositionListener {
    ImageIcon icon;
    private float degrees = 10;
    private Position pos;

    public PowerupView(ImageIcon icon) {
        this.icon = icon;
        this.pos = new Position(0, 0);
        this.setIcon(icon);
        this.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());

    }

    public PowerupView(ImageIcon icon, Position initialPosition) {
        int x = (int) initialPosition.getX();
        int y = (int) initialPosition.getY();

        this.setIcon(icon);
        this.setBounds(x, y, icon.getIconWidth(), icon.getIconHeight());
    }

    @Override
    public void onPositionChanged(Position position) {

        this.setLocation((int) position.getX(), (int) position.getY());
        repaint();
    }

    @Override
    public void onVisibilityChange() {
        this.setVisible(false);
        GameFrame.getInstance().remove(this);
        revalidate();
        repaint();
    }

}
