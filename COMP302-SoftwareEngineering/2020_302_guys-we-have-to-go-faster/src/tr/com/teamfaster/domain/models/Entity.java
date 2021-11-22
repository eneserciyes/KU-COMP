package tr.com.teamfaster.domain.models;

import tr.com.teamfaster.domain.utils.Bounds;
import tr.com.teamfaster.domain.utils.Position;

/**
 * All objects in the game are entities. {@link tr.com.teamfaster.domain.models.movable.Movable} and {@link tr.com.teamfaster.domain.models.movable.TypedMovable}
 * extends from Entity. Holds a {@link Position} and the width and height for entities.
 */
public abstract class Entity {
    private Position position;
    private int width;
    private int height;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public float getX() {
        return position.getX();
    }

    public float getY() {
        return position.getY();
    }

    public void setPosition(float x, float y) {
        this.position = new Position(x, y);
    }

    /**
     * @return The rectangle bounds of the entity specified by two points equally distant from the center that is computed with
     * getCenter() method.
     */
    public Bounds getBounds() {
        return new Bounds(new Position(getCenter().getX() - width / 2f, getCenter().getY() - height / 2f),
                new Position(getCenter().getX() + width / 2f, getCenter().getY() + height / 2f));
    }

    public Position getCenter() {
        return new Position(this.getX() + width / 2f, this.getY() + height / 2f);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @param entity : entity to check collision with
     * @return whether the entities are colliding
     */
    public boolean isColliding(Entity entity) {
        return getBounds().isOverlapping(entity.getBounds());
    }

}
