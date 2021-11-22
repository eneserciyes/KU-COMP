package tr.com.teamfaster.domain.models;

import tr.com.teamfaster.domain.models.movable.TypedMovable;
import tr.com.teamfaster.domain.models.movable.strategies.MSAlphaMolecule;
import tr.com.teamfaster.domain.models.movable.strategies.MSBetaMolecule;
import tr.com.teamfaster.domain.models.movable.strategies.MSGammaMolecule;
import tr.com.teamfaster.domain.models.movable.strategies.MSSigmaMolecule;
import tr.com.teamfaster.domain.utils.EntityType;
import tr.com.teamfaster.domain.utils.GameSettings;
import tr.com.teamfaster.domain.utils.Position;

/**
 * Molecule class that extends from {@link TypedMovable}. It only holds a constructor that
 * sets the speed of the molecule equal to molecule speed from {@link GameSettings} and the corresponding
 * {@link tr.com.teamfaster.domain.models.movable.strategies.IMoveStrategy}
 */
public class Molecule extends TypedMovable {

    public Molecule(Position position, EntityType type) {
        super(position, type);

        this.setWidth(GameSettings.getMoleculeWidth());
        this.setHeight(GameSettings.getMoleculeWidth());
        setSpeed(GameSettings.getMoleculeSpeed());

        switch (type) {
            case ALPHA -> setMoveStrategy(MSAlphaMolecule.getInstance());
            case BETA -> setMoveStrategy(MSBetaMolecule.getInstance());
            case GAMMA -> setMoveStrategy(MSGammaMolecule.getInstance());
            case SIGMA -> setMoveStrategy(MSSigmaMolecule.getInstance());
        }
    }
}


