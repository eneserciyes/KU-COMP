package tr.com.teamfaster.domain.models.movable.strategies;

import tr.com.teamfaster.domain.models.movable.Movable;

/**
 * All Movable objects implement move strategies. A Move Strategy (MS) dictates
 * the type of movement the object has.
 */
public interface IMoveStrategy {
    /**
     * @param obj the Movable object to be moved. obj calls this method itself.
     * @requires obj being passed should be initialized and should not be null.
     * @modifies obj position and rotation
     * @effects obj’s new position and rotation is set.
     */
    void move(Movable obj);
}