package tr.com.teamfaster.domain.models.movable;

import tr.com.teamfaster.domain.utils.EntityType;
import tr.com.teamfaster.domain.utils.Position;

/**
 * TypedMovable objects are Movable objects that additionally have an EntityType.
 */
public class TypedMovable extends Movable {
    private EntityType type;

    public TypedMovable(Position position, EntityType type) {
        setPosition(position);
        setType(type);
        setRotation(type == EntityType.ALPHA ? 180 : 135); // effectively dummy default value, MS handles rotation itself
    }

    public TypedMovable(Position position, float rotation, EntityType type) {
        this(position, type);
        setRotation(rotation);
    }

    public EntityType getType() {
        return type;
    }

    public void setType(EntityType type) {
        this.type = type;
    }
}
