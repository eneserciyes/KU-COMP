package tr.com.teamfaster.domain.models;

import tr.com.teamfaster.GameInfo;
import tr.com.teamfaster.domain.models.movable.Movable;
import tr.com.teamfaster.domain.models.movable.strategies.MSAtomShooter;
import tr.com.teamfaster.domain.utils.EntityType;
import tr.com.teamfaster.domain.utils.GameSettings;
import tr.com.teamfaster.domain.utils.Position;
import tr.com.teamfaster.domain.utils.ShieldType;

import java.awt.*;
import java.util.Map;

/**
 * AtomShooter singleton class implements the functionalities related to shooting and moving, rotating itself. It also acts as the
 * domain representation of the player by holding fields like shieldCounts, health and score. It extends {@link Movable}.
 */
public class AtomShooter extends Movable {
    private static AtomShooter atomShooter;
    private Map<EntityType, Integer> atomCounts;
    private final Map<ShieldType, Integer> shieldCounts;
    private EntityType currentAtomType;
    private float rotationSpeed;
    private int health = GameSettings.getStarterHealth();
    private double score = 0;

    /**
     * Different from other movable constructors, {@link AtomShooter} constructor sets the speed and rotation to 0. It also
     * calls changeAtomType to set the first atom on barrel. It sets the width and height from {@link GameSettings} and also sets it
     * position.
     */
    private AtomShooter() {
        atomCounts = GameInfo.getInstance().getAtomCount();
        shieldCounts = GameInfo.getInstance().getShieldCount();
        setMoveStrategy(MSAtomShooter.getInstance());
        setSpeed(0);
        setRotationSpeed(0);

        changeAtomType(EntityType.ALPHA);
        setWidth(GameSettings.getAtomShooterWidth());
        setHeight(GameSettings.getAtomShooterHeight());
        setPosition(new Position((GameSettings.getGameWidth() - GameSettings.getAtomShooterWidth()) / 2f, GameSettings.getGameHeight() - GameSettings.getAtomShooterHeight()));
    }

    public static AtomShooter getInstance() {
        if (atomShooter == null) atomShooter = new AtomShooter();
        return atomShooter;
    }

    /**
     * AtomShooter movement is implemented much like other entities to provide smooth transitions. Instead of moving the shooter
     * everytime the move button is pressed, we modify its speed and move it accordingly in its {@link MSAtomShooter}.
     * @param direction: a parameter value that represents going left or right.
     */
    public void setMoveDirection(int direction) {
        if (direction == GameSettings.getLEFT()) setSpeed(-GameSettings.getHorizontalShooterSpeed());
        else if (direction == GameSettings.getRIGHT()) setSpeed(GameSettings.getHorizontalShooterSpeed());
        else if (direction == GameSettings.getSTOP()) setSpeed(0);
    }

    /**
     * @see AtomShooter#setMoveDirection(int)
     * @param direction: rotation direction parameter value
     */
    public void setRotateDirection(int direction) {
        if (direction == GameSettings.getLEFT()) setRotationSpeed(GameSettings.getShooterRotationSpeed());
        else if (direction == GameSettings.getRIGHT()) setRotationSpeed(-GameSettings.getShooterRotationSpeed());
        else if (direction == GameSettings.getSTOP()) setRotationSpeed(0);
    }

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public void decreaseAmount(EntityType type, int amount) {
        atomCounts.put(type, atomCounts.get(type) - amount);
    }

    public void incrementAmount(EntityType type, int amount) {
        atomCounts.put(type, atomCounts.get(type) + amount);
    }

    public Map<EntityType, Integer> getAtomCounts() {
        return atomCounts;
    }

    public void setAtomCount(EntityType type, int count) {
        GameInfo.getInstance().setAtomCount(type, count);
    }

    public void setShieldCount(ShieldType type, int count) {
        GameInfo.getInstance().setShieldCount(type, count);
    }

    public Map<ShieldType, Integer> getShieldCounts() {
        return shieldCounts;
    }

    public void decreaseShieldAmount(ShieldType type, int amount) {
        if (shieldCounts.get(type) > 0) {
            shieldCounts.put(type, shieldCounts.get(type) - amount);
        }
    }

    public EntityType getCurrentAtomType() {
        return currentAtomType;
    }

    public void setCurrentAtomType(EntityType entityType) {
        currentAtomType = entityType;
    }

    public void changeAtomType(EntityType newAtomType) {
        setCurrentAtomType(newAtomType);
    }

    public void setAtomCounts(Map<EntityType, Integer> counts) {
        this.atomCounts = counts;
    }

    /**
     * Overridden due to the {@link tr.com.teamfaster.ui.views.AtomShooterView}'s different paintComponent implementation.
     * This is done to allow the rotation of atom shooter in UI.
     * @return the center of the atom shooter
     */
    @Override
    public Position getCenter() {
        return new Position(this.getX() + getHeight() + 13, this.getY() + getHeight() / 2f);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
