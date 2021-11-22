package tr.com.teamfaster.domain.models.movable.strategies;

import tr.com.teamfaster.domain.models.movable.Movable;
import tr.com.teamfaster.domain.utils.GameSettings;

/**
 * This Move Strategy describes a zigzag fall at a fixed angle, for the specified distance towards either side.
 */
public class MSZigzagFall implements IMoveStrategy {

    private static MSZigzagFall instance = null;

    public static MSZigzagFall getInstance() {
        if (instance == null) instance = new MSZigzagFall();
        return instance;
    }

    public void move(Movable obj) {
        float dx = (float) (GameSettings.getInstance().getMoleculeSpeed() / Math.sqrt(2));
        float dy = dx;

        if ((int) (obj.getY() / (GameSettings.getInstance().getL() / Math.sqrt(2))) % 2 == 0) dx = -dx;

        obj.setPosition(obj.getX() + dx, obj.getY() + dy);
    }

}
