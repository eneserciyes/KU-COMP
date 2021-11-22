package tr.com.teamfaster.domain.managers;

import tr.com.teamfaster.GameInfo;
import tr.com.teamfaster.domain.models.Powerup;
import tr.com.teamfaster.domain.utils.EntityType;
import tr.com.teamfaster.domain.utils.GameSettings;
import tr.com.teamfaster.domain.utils.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PowerupManager extends FallingEntityManager<Powerup> {
    private static PowerupManager instance;
    private Map<EntityType, Integer> collectedPowerups = new HashMap<>();
    private boolean fallingStatus;

    /**
     * @effects fallingStatus is set to true at the beginning for creating the
     * powerups with fallingStatus = true.
     */
    private PowerupManager() {
        super(GameInfo.getInstance().getPowerupCount());
        fallingStatus = true;
    }

    public static PowerupManager getInstance() {
        if (instance == null) instance = new PowerupManager();
        return instance;
    }

    public boolean isFallingStatus() {
        return fallingStatus;
    }

    public void setFallingStatus(boolean fallingStatus) {
        this.fallingStatus = fallingStatus;
    }

    /**
     * @param powerup
     * @requires powerup is not null
     * @effects given powerup is removed from the entities collection of the manager. It is added
     * to the collectedPowerups collection. A message for updating the collected power up counts
     * of the player is published to the viewManager.
     */
    public void collectPowerup(Powerup powerup) {
        removeEntity(powerup);
        collectedPowerups.put(powerup.getType(), collectedPowerups.getOrDefault(powerup.getType(), 0) + 1);
        viewChangeListener.onPowerupCounts(powerup.getType(), collectedPowerups.get(powerup.getType()));
    }

    /**
     * @param toShoot
     * @requires toShoot is not null.
     * @effects decreases the amount of the powerup with the given toShoot type by 1
     * in collectedPowerups of the player. Publishes a message to the viewManager for
     * updating the collected power up counts of the player.
     */

    public void updatePowerupCount(EntityType toShoot) {
        collectedPowerups.put(toShoot, collectedPowerups.get(toShoot) - 1);
        viewChangeListener.onPowerupCounts(toShoot, collectedPowerups.get(toShoot));
    }

    /**
     * @param type
     * @param position
     * @requires type and position is not null.
     * @effects publishes an onCreatePowerup message to viewManager with the type
     * and position
     */
    @Override
    protected void publishCreateEntityEvent(EntityType type, Position position) {
        getViewChangeListener().onCreatePowerup(type, position, fallingStatus);
    }

    /**
     * @param entityPosition
     * @param type
     * @return a Powerup entity in given type.
     * @requires entityPosition and type are not null.
     * @effects creates a new Position pos, which is the same as entityPosition if the
     * entityPosition + powerup_width is smaller than the game frame width. Otherwise,
     * pos becomes (game frame width - powerup width, 0).
     */
    @Override
    protected Powerup createSpecificEntity(Position entityPosition, EntityType type) {
        Position pos;
        if (entityPosition.getX() + GameSettings.getPowerupWidth() > GameSettings.getGameWidth()) {
            pos = new Position(GameSettings.getGameWidth() - GameSettings.getPowerupWidth(), 0);
        } else pos = entityPosition;
        Powerup newPowerup = new Powerup(pos, type);
        this.setFallingStatus(newPowerup.getFallingStatus());
        return newPowerup;
    }

    /**
     * @param position
     * @param rotation
     * @param entityType
     * @requires position, rotation, entityType is not null.
     * @effects creates a powerup with given position, rotation and entityType. New powerup
     * is added to the entity collection of this. The falling status of this is set to the
     * falling status of the newly created powerup, which is true. A create message is published
     * with given entityType and position.
     */
    @Override
    public void createEntity(Position position, float rotation, EntityType entityType) {
        Position posCopy = new Position(position.getX(), position.getY());
        Powerup newPowerup = new Powerup(posCopy, rotation, entityType);
        addEntity(newPowerup);
        this.setFallingStatus(newPowerup.getFallingStatus());
        publishCreateEntityEvent(entityType, position);
    }

    /**
     * @param powerup
     * @return true if the falling status of the powerup is true and its Y coordinate is
     * greater than game frame height, returns false otherwise.
     */
    @Override
    protected boolean checkMovingEntityRemoval(Powerup powerup) {

        if (powerup.getFallingStatus()) return powerup.getY() > GameSettings.getGameFrameHeight();
        else return powerup.getPosition().getY() < 0;
    }


    @Override
    public String toString() {
        return "POWERUP MANAGER";
    }

    @Override
    public ArrayList<ArrayList<String>> getInfo() {
        ArrayList<ArrayList<String>> info = super.getInfo();
        for (ArrayList<String> list : info) {
            list.add(String.valueOf(fallingStatus));
        }
        info.get(0).add(String.valueOf(collectedPowerups.getOrDefault(EntityType.ALPHA, 0)));
        info.get(0).add(String.valueOf(collectedPowerups.getOrDefault(EntityType.BETA, 0)));
        info.get(0).add(String.valueOf(collectedPowerups.getOrDefault(EntityType.GAMMA, 0)));
        info.get(0).add(String.valueOf(collectedPowerups.getOrDefault(EntityType.SIGMA, 0)));

        return info;
    }


    @Override
    public void loadInfo(ArrayList<ArrayList<String>> info) {
        super.loadInfo(info);
        for (ArrayList<String> list : info) {
            setFallingStatus(Boolean.parseBoolean(list.get(3)));
        }
        HashMap<EntityType, Integer> savedCounts = new HashMap<>();
        savedCounts.put(EntityType.ALPHA, Integer.parseInt(info.get(0).get(4)));
        savedCounts.put(EntityType.BETA, Integer.parseInt(info.get(0).get(5)));
        savedCounts.put(EntityType.GAMMA, Integer.parseInt(info.get(0).get(6)));
        savedCounts.put(EntityType.SIGMA, Integer.parseInt(info.get(0).get(7)));

        setCollectedPowerups(savedCounts);

        viewChangeListener.onPowerupCounts(EntityType.ALPHA, collectedPowerups.get(EntityType.ALPHA));
        viewChangeListener.onPowerupCounts(EntityType.BETA, collectedPowerups.get(EntityType.BETA));
        viewChangeListener.onPowerupCounts(EntityType.GAMMA, collectedPowerups.get(EntityType.GAMMA));
        viewChangeListener.onPowerupCounts(EntityType.SIGMA, collectedPowerups.get(EntityType.SIGMA));
    }

    private void setCollectedPowerups(HashMap<EntityType, Integer> collectedPowerups) {
        this.collectedPowerups = collectedPowerups;
    }

}
