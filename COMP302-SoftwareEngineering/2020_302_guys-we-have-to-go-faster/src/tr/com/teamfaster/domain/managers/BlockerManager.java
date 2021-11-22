package tr.com.teamfaster.domain.managers;

import tr.com.teamfaster.GameInfo;
import tr.com.teamfaster.domain.models.Blocker;
import tr.com.teamfaster.domain.utils.EntityType;
import tr.com.teamfaster.domain.utils.GameSettings;
import tr.com.teamfaster.domain.utils.Position;

public class BlockerManager extends FallingEntityManager<Blocker> {
    private static BlockerManager instance;

    private BlockerManager() {
        super(GameInfo.getInstance().getBlockerCount());
    }

    public static BlockerManager getInstance() {
        if (instance == null) instance = new BlockerManager();
        return instance;
    }

    /**
     * @param entityPosition
     * @param type
     * @return a Blocker entity in given type.
     * @requires entityPosition and type are not null.
     * @effects creates a new Position pos, which is the same as entityPosition if the
     * entityPosition + blocker_width is smaller than the game frame width. Otherwise,
     * pos becomes (game frame width - blocker width, 0).
     */
    @Override
    protected Blocker createSpecificEntity(Position entityPosition, EntityType type) {
        Position pos;
        if (entityPosition.getX() + GameSettings.getBlockerWidth() > GameSettings.getGameWidth()) {
            pos = new Position(GameSettings.getGameWidth() - GameSettings.getBlockerWidth(), 0);
        } else pos = entityPosition;
        return new Blocker(pos, type);

    }

    /**
     * @param type
     * @param position
     * @effects publishes an onCreateBlocker message to the viewManager by providing
     * type and position as parameters
     */
    @Override
    protected void publishCreateEntityEvent(EntityType type, Position position) {
        getViewChangeListener().onCreateBlocker(type, position);
    }

    @Override
    public String toString() {
        return "BLOCKER MANAGER";
    }
}
