package tr.com.teamfaster.domain.models;

import tr.com.teamfaster.domain.models.movable.TypedMovable;
import tr.com.teamfaster.domain.models.movable.strategies.MSAdvanceWallbound;
import tr.com.teamfaster.domain.models.movable.strategies.MSStraightFall;
import tr.com.teamfaster.domain.utils.EntityType;
import tr.com.teamfaster.domain.utils.GameSettings;
import tr.com.teamfaster.domain.utils.Position;

/**
 * Extends from {@link TypedMovable}. Has two constructors that depends on whether the powerup is falling or shot from
 * the {@link AtomShooter}. The fallingStatus, rotation and the moveStrategy is different in two constructors.
 */
public class Powerup extends TypedMovable {
    private boolean fallingStatus;

    public Powerup(Position position, EntityType type) {
        super(position, type);

        this.setWidth(GameSettings.getPowerupWidth());
        this.setHeight(GameSettings.getPowerupWidth());
        setSpeed(GameSettings.getPowerupSpeed());
        this.fallingStatus = true;

        setMoveStrategy(MSStraightFall.getInstance());

    }

    public Powerup(Position position, float rotation, EntityType entityType) {
        super(position, rotation, entityType);

        this.setWidth(GameSettings.getPowerupWidth());
        this.setHeight(GameSettings.getPowerupWidth());
        setSpeed(GameSettings.getPowerupSpeed());
        this.fallingStatus = false;

        setMoveStrategy(MSAdvanceWallbound.getInstance());
    }

    public boolean getFallingStatus() {
        return fallingStatus;
    }

    public void setStatus(boolean fallingStatus) {
        this.fallingStatus = fallingStatus;
    }
}


