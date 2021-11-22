package tr.com.teamfaster.domain.managers;

import tr.com.teamfaster.domain.listeners.IPositionListener;
import tr.com.teamfaster.domain.listeners.IViewChangeListener;
import tr.com.teamfaster.domain.models.movable.TypedMovable;
import tr.com.teamfaster.domain.services.storage.ISaveLoader;
import tr.com.teamfaster.domain.utils.EntityType;
import tr.com.teamfaster.domain.utils.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link TypedMovableEntityManager} is the base class for other manager services that handles domain operations of {@link TypedMovable}
 * entities.
 * These operations range from moving the objects, removing them and applying internal changes in the model to persistence.
 * This base class implements the common operations across these managers and specifies three abstract methods.
 *
 * @param <T> : A TypedMovable object that the concrete classes implementing {@link TypedMovableEntityManager} will manage.
 */

public abstract class TypedMovableEntityManager<T extends TypedMovable> implements ISaveLoader {
    final List<T> movingEntities;
    final List<IPositionListener> entityViews;
    ConcurrentHashMap<T, IPositionListener> entities;
    IViewChangeListener viewChangeListener;

    public TypedMovableEntityManager() {
        this.movingEntities = new ArrayList<>();
        this.entityViews = new ArrayList<>();
        entities = new ConcurrentHashMap<>();
    }

    /**
     * This method takes an {@link IPositionListener} from the buffer entityViews and adds it to the entities {@link ConcurrentHashMap}
     *
     * @param entityView: The observer that notifies the corresponding UI View
     */
    public void subscribe(IPositionListener entityView) {
        List<Integer> toBeRemoved = new ArrayList<>();
        entityViews.add(entityView);
        for (int i = 0; i < entityViews.size(); i++) {
            entities.put(movingEntities.get(i), entityViews.get(i));
            movingEntities.remove(i);
            toBeRemoved.add(i);
        }
        for (int index : toBeRemoved) {
            entityViews.remove(index);
        }
    }

    /**
     * Subscribes view change observer to the manager class.
     *
     * @param manager: The observer that listens to view changes like creating {@link TypedMovable} entities.
     */
    public void subscribeViewManager(IViewChangeListener manager) {
        this.viewChangeListener = manager;
    }

    /**
     * This method handles the movement of all entities in entities hash map. At each call, a move call is made to the entity,
     * their observers are notified on the position change and objects that should be removed are removed from the hash map
     * and their view observer are notified of the removal.
     */
    public void moveEntities() {
        List<T> objectsToRemove = new ArrayList<>();
        for (Map.Entry<T, IPositionListener> entity : entities.entrySet()) {
            if (checkMovingEntityRemoval(entity.getKey())) {
                objectsToRemove.add(entity.getKey());
            }
            entity.getKey().move();
            Position position = entity.getKey().getPosition();
            entity.getValue().onPositionChanged(position);
        }
        for (T willRemoved : objectsToRemove) {
            try {
                entities.get(willRemoved).onVisibilityChange();
                entities.remove(willRemoved);
            } catch (Exception e) {

            }

        }
    }

    /**
     * The specified entity is removed from the hash map and its observer is notified
     *
     * @param entity: Entity to remove
     */
    public void removeEntity(T entity) {
        try {
            entities.get(entity).onVisibilityChange();
            entities.remove(entity);
        } catch (Exception e) {

        }

    }

    /**
     * @return : Returns how many entities are currently in moving state.
     */
    public int getMovingEntitiesSize() {
        return entities.size();
    }

    /**
     * @return : The moving entities buffer
     */
    public List<T> getMovingEntities() {
        List<T> movingEntities = new ArrayList<>();
        for (T entity : entities.keySet()) {
            movingEntities.add(entity);
        }
        return movingEntities;
    }

    /**
     * @param entity: Entity to be added to movingEntities buffer
     */
    public void addEntity(T entity) {
        movingEntities.add(entity);
    }

    public IViewChangeListener getViewChangeListener() {
        return viewChangeListener;
    }

    @Override
    public ArrayList<ArrayList<String>> getInfo() {
        ArrayList<ArrayList<String>> atomInfo = new ArrayList<>();

        for (T entity : entities.keySet()) {
            ArrayList<String> current = new ArrayList<>(4);
            current.add(String.valueOf(entity.getX()));
            current.add(String.valueOf(entity.getY()));
            current.add(String.valueOf(entity.getRotation()));
            current.add(entity.getType().name());
            atomInfo.add(current);
        }
        return atomInfo;
    }

    @Override
    public void loadInfo(ArrayList<ArrayList<String>> info) {
        entities.values().forEach(IPositionListener::onVisibilityChange);
        movingEntities.clear();
        entityViews.clear();
        entities.clear();

        for (ArrayList<String> atomInfo : info) {
            Position position = new Position(Float.parseFloat(atomInfo.get(0)), Float.parseFloat(atomInfo.get(1)));
            float rotation = Float.parseFloat(atomInfo.get(2));
            EntityType atomType = EntityType.valueOf(atomInfo.get(3));
            createEntity(position, rotation, atomType);
        }
    }

    /**
     * Takes the parameters and creates the entity. Should be overriden when this class is extended.
     *
     * @param position: position of the entity to be created
     * @param rotation: rotation of the entity to be created
     * @param type:     {@link EntityType} of the entity
     */
    public abstract void createEntity(Position position, float rotation, EntityType type);

    /**
     * Checks if the entity should be removed from moving hash map
     *
     * @param object: Entity to be checked for removal
     * @return : Whether the entity should be removed
     */
    protected abstract boolean checkMovingEntityRemoval(T object);

    /**
     * Publishes the create entity event to {@link IViewChangeListener}
     *
     * @param type:     {@link EntityType}
     * @param position: : position of the view object to be created
     */
    protected abstract void publishCreateEntityEvent(EntityType type, Position position);

}
