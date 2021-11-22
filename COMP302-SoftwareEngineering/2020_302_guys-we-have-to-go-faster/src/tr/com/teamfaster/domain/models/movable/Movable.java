package tr.com.teamfaster.domain.models.movable;

import tr.com.teamfaster.domain.models.Entity;
import tr.com.teamfaster.domain.models.movable.strategies.IMoveStrategy;

/**
 * Movable objects are Entity objects that can be moved in various ways according to the
 * game specifications. They all have a Move Strategy, rotation, and speed. In their move() methods,
 * Movable objects call their moveStrategy's move method on themselves to execute the correct move behaviour.
 */
public class Movable extends Entity {
    IMoveStrategy moveStrategy;
    private float rotation;
    private float speed;

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float newRotation) {
        rotation = newRotation;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float newSpeed) {
        speed = newSpeed;
    }

    public IMoveStrategy getMoveStrategy() {
        return moveStrategy;
    }

    public void setMoveStrategy(IMoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

    public void move() {
        moveStrategy.move(this);
    }

}
