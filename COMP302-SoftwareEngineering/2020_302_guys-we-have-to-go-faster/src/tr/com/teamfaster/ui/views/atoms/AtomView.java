package tr.com.teamfaster.ui.views.atoms;

import tr.com.teamfaster.domain.listeners.IPositionListener;
import tr.com.teamfaster.domain.utils.Position;
import tr.com.teamfaster.ui.managers.GameFrame;

import javax.swing.*;

public class AtomView extends JLabel implements IPositionListener {

    /**
     * Forms an atom view from the icon at the initial position.
     *
     * @param icon:            view of the atom
     * @param initialPosition: position which the view is created
     */
    public AtomView(ImageIcon icon, Position initialPosition) {
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
