package tr.com.teamfaster.ui.views.molecules;

import tr.com.teamfaster.domain.listeners.IPositionListener;
import tr.com.teamfaster.domain.utils.Position;
import tr.com.teamfaster.ui.managers.GameFrame;

import javax.swing.*;

public class MoleculeView extends JLabel implements IPositionListener {
    ImageIcon icon;
    private Position pos;

    public MoleculeView(ImageIcon icon) {
        this.pos = new Position(0, 0);
        this.setIcon(icon);
        this.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());

    }

    public MoleculeView() {
        this.pos = new Position(0, 0);
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
