package tr.com.teamfaster.domain.utils;

/**
 * The Position class is used to keep positions of Entity objects.
 */
public class Position {
    private float x;
    private float y;

    public Position(float x, float y) {
        this.x = x;
        this.y = y;

    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setXY(float x, float y) {
        setX(x);
        setY(y);
    }

    /**
     * @param pos: a Position object
     * @return the euclidean this between this and the parameter
     */

    public double getEuclideanDistance(Position pos) {
        return Math.sqrt(Math.pow((this.x - pos.getX()), 2) + Math.pow((this.y - pos.getY()), 2));
    }

    @Override
    public String toString() {
        return "X: " + x + ", Y:  " + y;
    }
}
