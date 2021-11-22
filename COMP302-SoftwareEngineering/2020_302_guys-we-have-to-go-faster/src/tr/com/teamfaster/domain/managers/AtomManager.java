package tr.com.teamfaster.domain.managers;

import tr.com.teamfaster.domain.models.atoms.Atom;
import tr.com.teamfaster.domain.models.atoms.AtomFactory;
import tr.com.teamfaster.domain.utils.EntityType;
import tr.com.teamfaster.domain.utils.Position;

/**
 * The singleton AtomManager class that extends {@link TypedMovableEntityManager} with generic type {@link Atom}
 */
public class AtomManager extends TypedMovableEntityManager<Atom> {
    private static AtomManager instance;

    private AtomManager() {
        super();
    }

    public static AtomManager getInstance() {
        if (instance == null) instance = new AtomManager();
        return instance;
    }

    @Override
    public void createEntity(Position position, float rotation, EntityType entityType) {
        Position posCopy = new Position(position.getX(), position.getY());
        Atom newAtom = AtomFactory.getInstance().getAtom(posCopy, rotation, entityType);
        addEntity(newAtom);

        publishCreateEntityEvent(entityType, position);
    }

    /**
     * The overridden createEntity atom to be used with AtomInventory in {@link AtomShooterManager}
     *
     * @param atom: atom to add to the moving hash map
     */
    public void createEntity(Atom atom) {
        addEntity(atom);
        publishCreateEntityEvent(atom.getType(), atom.getPosition());
    }

    @Override
    protected boolean checkMovingEntityRemoval(Atom atom) {
        return atom.getPosition().getY() <= 0;
    }

    @Override
    protected void publishCreateEntityEvent(EntityType type, Position position) {
        getViewChangeListener().onCreateAtom(type, position);
    }

    @Override
    public String toString() {
        return "ATOM MANAGER";
    }

}


