package tr.com.teamfaster.domain.models.movable.strategies;

import tr.com.teamfaster.domain.models.movable.Movable;
import tr.com.teamfaster.domain.utils.GameSettings;

/**
 * This Move Strategy describes the movement of the Beta molecule. It switches between
 * MSZigzagFall and MSStraightFall depending on the molecule's height.
 */
public class MSBetaMolecule implements IMoveStrategy {

    private static MSBetaMolecule instance = null;

    public static MSBetaMolecule getInstance() {
        if (instance == null) instance = new MSBetaMolecule();
        return instance;
    }

    @Override
    public void move(Movable obj) {

        if (obj.getY() < (float) GameSettings.getGameHeight() / 4) {
            MSZigzagFall.getInstance().move(obj);
        } else {
            MSStraightFall.getInstance().move(obj);
        }
    }
}
