package tr.com.teamfaster.domain.models.movable.strategies;

import tr.com.teamfaster.domain.models.movable.Movable;

/**
 * This Move Strategy describes the movement of the Alpha molecule. It is an alias for MSStraightFall.
 */
public class MSAlphaMolecule implements IMoveStrategy {

    private static MSAlphaMolecule instance = null;

    public static MSAlphaMolecule getInstance() {
        if (instance == null) instance = new MSAlphaMolecule();
        return instance;
    }

    @Override
    public void move(Movable obj) {
        MSStraightFall.getInstance().move(obj);
    }
}
