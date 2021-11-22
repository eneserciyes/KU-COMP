package tr.com.teamfaster.domain.managers;

import tr.com.teamfaster.GameInfo;
import tr.com.teamfaster.domain.models.Molecule;
import tr.com.teamfaster.domain.utils.EntityType;
import tr.com.teamfaster.domain.utils.GameSettings;
import tr.com.teamfaster.domain.utils.Position;

public class MoleculeManager extends FallingEntityManager<Molecule> {
    private static MoleculeManager instance;

    private MoleculeManager() {
        super(GameInfo.getInstance().getMoleculeCount());
    }

    public static MoleculeManager getInstance() {
        if (instance == null) instance = new MoleculeManager();
        return instance;
    }

    /**
     * @param entityPosition
     * @param type
     * @return a Molecule entity in given type.
     * @requires entityPosition and type are not null.
     * @effects creates a new Position pos, which is the same as entityPosition if the
     * entityPosition + molecule_width is smaller than the game frame width. Otherwise,
     * pos becomes (game frame width - molecule width, 0).
     */
    @Override
    public Molecule createSpecificEntity(Position entityPosition, EntityType type) {
        Position pos;
        if (entityPosition.getX() + GameSettings.getMoleculeWidth() > GameSettings.getGameWidth()) {
            pos = new Position(GameSettings.getGameWidth() - GameSettings.getMoleculeWidth(), 0);
        } else pos = entityPosition;
        return new Molecule(pos, type);
    }

    /**
     * @param type
     * @param position
     * @effects publishes an onCreateMolecule message to the viewManager by providing
     * type and position as parameters
     */
    @Override
    protected void publishCreateEntityEvent(EntityType type, Position position) {
        getViewChangeListener().onCreateMolecule(type, position);
    }

    @Override
    public String toString() {
        return "MOLECULE MANAGER";
    }

}
