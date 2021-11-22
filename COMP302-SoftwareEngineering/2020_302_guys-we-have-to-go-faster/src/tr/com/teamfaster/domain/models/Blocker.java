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
 * Blocker extends {@link TypedMovable} and sets the speed and corresponding move strategies in its constructor. Apart from this,
 * it also overrides a different isColliding method from {@link TypedMovable}.
 */
public class Blocker extends TypedMovable {

    public Blocker(Position position, EntityType type) {
        super(position, type);

        this.setWidth(GameSettings.getBlockerWidth());
        this.setHeight(GameSettings.getBlockerWidth());
        setSpeed(GameSettings.getBlockerSpeed());

        switch (type) {
            case ALPHA -> setMoveStrategy(MSAlphaMolecule.getInstance());
            case BETA -> setMoveStrategy(MSBetaMolecule.getInstance());
            case GAMMA -> setMoveStrategy(MSGammaMolecule.getInstance());
            case SIGMA -> setMoveStrategy(MSSigmaMolecule.getInstance());
        }
    }

    /**
     * @param entity : entity to check collision with
     * @return whether the euclidean distance between this and parameter is smaller than 2L
     */
    @Override
    public boolean isColliding(Entity entity) {
        return getPosition().getEuclideanDistance(entity.getPosition()) < (GameSettings.getL() * 2);
    }
}


