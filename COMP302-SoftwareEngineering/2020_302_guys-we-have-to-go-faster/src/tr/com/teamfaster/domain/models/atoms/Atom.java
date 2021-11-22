package tr.com.teamfaster.domain.models.atoms;

import tr.com.teamfaster.domain.models.movable.TypedMovable;
import tr.com.teamfaster.domain.models.movable.strategies.MSAdvanceWallbound;
import tr.com.teamfaster.domain.utils.EntityType;
import tr.com.teamfaster.domain.utils.GameSettings;
import tr.com.teamfaster.domain.utils.Position;

import java.util.ArrayList;
import java.util.List;

public abstract class Atom extends TypedMovable {

    private int protons;
    private int electrons;
    private int neutrons;

    /**
     * Creates a new Atom with the params
     *
     * @param position
     * @param rotation
     * @param entityType
     */
    public Atom(Position position, float rotation, EntityType entityType) {
        super(position, rotation, entityType);
        this.setWidth(GameSettings.getAtomDiameter());
        this.setHeight(GameSettings.getAtomDiameter());
        setSpeed(GameSettings.getAtomSpeed());
        setMoveStrategy(new MSAdvanceWallbound());
    }

    public int getProtons() {
        return protons;
    }

    public void setProtons(int protons) {
        this.protons = protons;
    }

    public int getNeutrons() {
        return neutrons;
    }

    public void setNeutrons(int neutrons) {
        this.neutrons = neutrons;
    }

    public abstract void chooseNeutronNumber();

    public abstract double getEfficiency();

    public List<String> getShields() {
        return new ArrayList<>();
    }

}
