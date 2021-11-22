package tr.com.teamfaster.domain.utils;

/**
 * Bounds objects represent rectangular boundaries of Entity objects. Bounds are used to check if
 * two Entity objects are colliding. The isOverlapping method checks whether two Bounds' intersect.
 */
public class Bounds {

    private Position topLeftCorner;
    private Position bottomRightCorner;

    public Bounds(Position topLeftCorner, Position bottomRightCorner) {
        this.topLeftCorner = topLeftCorner;
        this.bottomRightCorner = bottomRightCorner;

    }

    public Position getTopLeftCorner() {
        return topLeftCorner;
    }

    public void setTopLeftCorner(Position topLeftCorner) {
        this.topLeftCorner = topLeftCorner;
    }

    public Position getBottomRightCorner() {
        return bottomRightCorner;
    }

    public void setBottomRightCorner(Position bottomRightCorner) {
        this.bottomRightCorner = bottomRightCorner;
    }

    /**
     * @param bounds
     * @requires bounds.getTopLeftCorner().getY() < bounds.getBottomRightCorner().getY()
     * @effects gets a bounds object in swing coordinate system representing the top left
     * and bottom right corners of a rectangle. Returns true, if the bounds overlaps with
     * this object. Otherwise, returns false.
     * * @return boolean
     */
    public boolean isOverlapping(Bounds bounds) {
        if (this.getTopLeftCorner().getX() >= bounds.getBottomRightCorner().getX() || bounds.getTopLeftCorner().getX() >= this.getBottomRightCorner().getX())
            return false;
        if (this.getTopLeftCorner().getY() >= bounds.getBottomRightCorner().getY() || bounds.getTopLeftCorner().getY() >= this.getBottomRightCorner().getY())
            return false;

        return true;
    }
}

