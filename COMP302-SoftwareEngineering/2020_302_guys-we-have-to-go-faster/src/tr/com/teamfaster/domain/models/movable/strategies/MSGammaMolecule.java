package tr.com.teamfaster.domain.models.movable.strategies;

import tr.com.teamfaster.domain.models.movable.Movable;
import tr.com.teamfaster.domain.utils.GameSettings;

/**
 * This Move Strategy describes the movement of the Gamma molecule. It switches between
 * MSZigzagFall and MSStraightFall depending on the molecule's height.
 */
public class MSGammaMolecule implements IMoveStrategy {

    private static MSGammaMolecule instance = null;

    public static MSGammaMolecule getInstance() {
        if (instance == null) instance = new MSGammaMolecule();
        return instance;
    }

    @Override
    public void move(Movable obj) {

        if (obj.getY() < (float) GameSettings.getGameHeight() / 2) {
            MSZigzagFall.getInstance().move(obj);
        } else {
            MSStraightFall.getInstance().move(obj);
        }
    }
}
