package tr.com.teamfaster.domain.listeners;

import tr.com.teamfaster.domain.utils.Position;

public interface IPositionListener {
    /**
     * The new location of the view is set to position and view is repainted.
     *
     * @param position: new position of the view
     */
    void onPositionChanged(Position position);

    /**
     * When the object is removed the view is removed from the frame.
     */
    void onVisibilityChange();
}
