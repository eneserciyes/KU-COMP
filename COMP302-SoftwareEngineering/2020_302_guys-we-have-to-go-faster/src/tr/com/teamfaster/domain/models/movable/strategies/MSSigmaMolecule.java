package tr.com.teamfaster.domain.models.movable.strategies;

import tr.com.teamfaster.domain.models.movable.Movable;

/**
 * This Move Strategy describes the movement of the Beta molecule. It is an alias for MSZigzagFall.
 */
public class MSSigmaMolecule implements IMoveStrategy {

    private static MSSigmaMolecule instance = null;

    public static MSSigmaMolecule getInstance() {
        if (instance == null) instance = new MSSigmaMolecule();
        return instance;
    }

    @Override
    public void move(Movable obj) {
        MSZigzagFall.getInstance().move(obj);
    }
}
