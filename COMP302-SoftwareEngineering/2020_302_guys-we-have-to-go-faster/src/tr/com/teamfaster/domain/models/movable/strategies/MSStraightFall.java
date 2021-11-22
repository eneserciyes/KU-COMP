package tr.com.teamfaster.domain.models.movable.strategies;

import tr.com.teamfaster.domain.models.movable.Movable;


/**
 * This Move Strategy describes a simple downwards fall.
 * It does not take into account the object's rotation.
 */
public class MSStraightFall implements IMoveStrategy {

    private static MSStraightFall instance = null;

    public static MSStraightFall getInstance() {
        if (instance == null) instance = new MSStraightFall();
        return instance;
    }

    public void move(Movable obj) {
        float dy = obj.getSpeed();
        obj.setPosition(obj.getX(), obj.getY() + dy);
    }
}
