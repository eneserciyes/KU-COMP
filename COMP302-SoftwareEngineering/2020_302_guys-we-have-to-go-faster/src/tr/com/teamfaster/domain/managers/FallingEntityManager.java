package tr.com.teamfaster.domain.managers;

import tr.com.teamfaster.domain.listeners.IPositionListener;
import tr.com.teamfaster.domain.models.movable.TypedMovable;
import tr.com.teamfaster.domain.utils.EntityType;
import tr.com.teamfaster.domain.utils.GameSettings;
import tr.com.teamfaster.domain.utils.Position;
import tr.com.teamfaster.domain.utils.RandomUtils;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class FallingEntityManager<T extends TypedMovable> extends TypedMovableEntityManager<T> {
    private HashMap<EntityType, Integer> entityCount;

    public FallingEntityManager(HashMap<EntityType, Integer> entityCount) {
        super();
        this.entityCount = entityCount;
    }

    /**
     * @effects randomly chooses and returns an entity type from the remaining
     * entities in entityCounts. If entityCounts has no elements, returns null.
     */
    public EntityType nextFallingEntityType() {
        if (entityCount.size() == 0) return null;
        int randomIndex = RandomUtils.getRandomIndex(entityCount.size());
        EntityType[] types = entityCount.keySet().toArray(new EntityType[0]);

        EntityType type = types[randomIndex];
        entityCount.replace(type, entityCount.get(type) - 1);
        if (entityCount.get(type) == 0) {
            entityCount.remove(type);
        }
        return type;
    }

    /**
     * @param type
     * @return a position with the randomly choosing x and y = 0.
     * @requires : type should not be null.
     * @effects : if the given type is sigma, it returns a random x between 0 and game frame width
     * otherwise, as the falling entities with other types fall by zigzaging, it returns a random
     * x between L / sqrt(2) and ((game frame with - 2 * L) / sqrt(2)).
     */
    public Position nextFallingEntityPosition(EntityType type) {
        int randomX;
        if (type == EntityType.SIGMA) {
            randomX = RandomUtils.getRandomIndex(GameSettings.getGameWidth());
        } else
            randomX = RandomUtils.getRandomIndex((int) (GameSettings.getGameWidth() - 2 * GameSettings.getL() / Math.sqrt(2))) + (int) (GameSettings.getL() / Math.sqrt(2));

        return new Position(randomX, 0);
    }

    /**
     * @param movable
     * @return true if the Y coordinate of the movable is greater than game frame height.
     * false otherwise.
     * @requires movable is not null.
     */
    @Override
    protected boolean checkMovingEntityRemoval(T movable) {
        return movable.getY() > GameSettings.getGameFrameHeight();
    }

    public HashMap<EntityType, Integer> getEntityCount() {
        return entityCount;
    }

    public void setEntityCount(HashMap<EntityType, Integer> entityCount) {
        this.entityCount = entityCount;
    }

    /**
     * @effects if the next entity type is null, returns false indicating that all
     * entities were produced. Otherwise, creates a falling entity with randomly chosen
     * type and position.
     */
    public boolean createFallingEntity() {
        EntityType newType = this.nextFallingEntityType();
        if (newType != null) {
            Position newPosition = this.nextFallingEntityPosition(newType);
            createFallingEntity(newPosition, newType);
            return true;
        } else {
            return !(this.getMovingEntitiesSize() == 0);
        }
    }

    /**
     * @param position
     * @param type
     * @requires position and type are not null.
     * @effects creates a falling entity with given position and type. Adds it to the
     * moving entities of the manager and publishes a message to the viewManager about
     * the creation of the falling entity.
     */
    public void createFallingEntity(Position position, EntityType type) {
        T newFallingEntity = createSpecificEntity(position, type);
        movingEntities.add(newFallingEntity);

        publishCreateEntityEvent(type, position);
    }

    /**
     * @return an arrayList whose entries are array list of strings consisting of the
     * x, y coordinates and the type of the entities that the manager has.
     */
    @Override
    public ArrayList<ArrayList<String>> getInfo() {
        ArrayList<ArrayList<String>> info = new ArrayList<>();

        for (T entity : entities.keySet()) {
            ArrayList<String> current = new ArrayList<>(10);
            current.add(String.valueOf(entity.getX()));
            current.add(String.valueOf(entity.getY()));
            current.add(entity.getType().name());
            info.add(current);
        }
        return info;
    }

    /**
     * @param info
     * @requires info is not null.
     * @effects clears movingEntities, entityViews and entities collections of the manager.
     * By using the array list of strings in the given of info, it creates falling entities
     * with the position and type taken from the info element.
     */
    @Override
    public void loadInfo(ArrayList<ArrayList<String>> info) {
        entities.values().forEach(IPositionListener::onVisibilityChange);
        movingEntities.clear();
        entityViews.clear();
        entities.clear();

        for (ArrayList<String> atomInfo : info) {
            Position position = new Position(Float.parseFloat(atomInfo.get(0)), Float.parseFloat(atomInfo.get(1)));
            EntityType atomType = EntityType.valueOf(atomInfo.get(2));
            createFallingEntity(position, atomType);
        }
    }

    @Override
    public void createEntity(Position position, float rotation, EntityType type) {

    }

    protected abstract T createSpecificEntity(Position entityPosition, EntityType type);
}
