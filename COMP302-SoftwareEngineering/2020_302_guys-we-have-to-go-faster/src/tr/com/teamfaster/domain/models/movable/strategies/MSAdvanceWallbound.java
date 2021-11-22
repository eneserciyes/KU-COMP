package tr.com.teamfaster.domain.models.movable.strategies;

import tr.com.teamfaster.domain.models.movable.Movable;
import tr.com.teamfaster.domain.utils.GameSettings;

/**
 * This Move Strategy moves the object according to its current velocity (speed + rotation).
 * If the object hits the left or right side of the game boundaries, it will bounce off with the same speed.
 */
public class MSAdvanceWallbound implements IMoveStrategy {

    private static MSAdvanceWallbound instance = null;

    public static MSAdvanceWallbound getInstance() {

        if (instance == null) instance = new MSAdvanceWallbound();
        return instance;
    }

    public void move(Movable obj) {

        double rotation = Math.toRadians(obj.getRotation() + 90);
        //System.out.println(obj.getClass() + " speed: " + obj.getSpeed() + " rotation: " + obj.getRotation());
        float dy = (float) -(Math.sin(rotation) * obj.getSpeed());
        float dx = (float) (Math.cos(rotation) * obj.getSpeed());

        float newX = obj.getX() + dx;
        float newY = obj.getY() + dy;

        if (newX <= 0) {
            obj.setRotation(-obj.getRotation());
            obj.setPosition(-newX, newY);
        } else if (newX + obj.getWidth() >= GameSettings.getGameWidth()) {
            newX = (2 * GameSettings.getGameWidth()) - (2 * obj.getWidth()) - newX;
            obj.setRotation(-obj.getRotation());
            obj.setPosition(newX, newY);
        } else {
            obj.setPosition(newX, newY);
        }
    }
}