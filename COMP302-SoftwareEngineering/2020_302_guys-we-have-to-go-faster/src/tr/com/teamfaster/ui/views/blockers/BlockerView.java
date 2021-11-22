package tr.com.teamfaster.ui.views.blockers;

import tr.com.teamfaster.domain.listeners.IPositionListener;
import tr.com.teamfaster.domain.utils.Position;
import tr.com.teamfaster.ui.managers.GameFrame;

import javax.swing.*;
import java.awt.*;

public class BlockerView extends JLabel implements IPositionListener {
    ImageIcon icon;
    private float degrees = 10;
    private Position pos;

    public BlockerView(ImageIcon icon) {
        this.icon = icon;
        this.pos = new Position(0, 0);
        this.setIcon(icon);
        this.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight() * 5 / 2);

    }

    @Override
    public void onPositionChanged(Position position) {
        this.setLocation((int) position.getX(), (int) position.getY());
        repaint();
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(icon.getIconWidth(), icon.getIconHeight());
    }

    @Override
    public void onVisibilityChange() {
        this.setVisible(false);
        GameFrame.getInstance().remove(this);
        revalidate();
        repaint();
    }

}
