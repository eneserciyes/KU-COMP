package tr.com.teamfaster.domain.models.shields;

import tr.com.teamfaster.domain.models.atoms.Atom;
import tr.com.teamfaster.domain.models.movable.strategies.IMoveStrategy;
import tr.com.teamfaster.domain.utils.Bounds;
import tr.com.teamfaster.domain.utils.EntityType;
import tr.com.teamfaster.domain.utils.Position;

/**
 * ComponentAtomShield is the abstract class to handle decorating atoms with shields.
 * It has the state field to know if an Atom has a Shield or not.
 */
public abstract class ComponentAtomShield extends Atom {
    private Atom atom;
    private boolean state = false;

    /**
     * Initializes a shielded atom with the given atom and sets the state true
     *
     * @param atom
     */
    public ComponentAtomShield(Atom atom) {
        super(atom.getPosition(), atom.getRotation(), atom.getType());
        this.atom = atom;
        state = true;
    }

    @Override
    public void chooseNeutronNumber() {

    }

    @Override
    public EntityType getType() {
        return atom.getType();
    }

    @Override
    public Position getPosition() {
        return atom.getPosition();
    }

    /**
     * @param position
     * @effects sets the position to the given param if the
     */
    @Override
    public void setPosition(Position position) {
        if (state)
            atom.setPosition(position);
    }

    @Override
    public float getX() {
        return atom.getX();
    }

    @Override
    public float getY() {
        return atom.getY();
    }

    @Override
    public void setPosition(float x, float y) {
        if (state)
            atom.setPosition(new Position(x, y));
    }

    /**
     * @return a new Bound with the objects
     */
    @Override
    public Bounds getBounds() {
        return new Bounds(atom.getPosition(), new Position(this.getX() + atom.getWidth(), this.getY() + atom.getHeight()));
    }

    @Override
    public float getRotation() {
        return atom.getRotation();
    }

    @Override
    public void setRotation(float newRotation) {
        if (state)
            atom.setRotation(newRotation);
    }

    @Override
    public int getProtons() {
        return atom.getProtons();
    }

    @Override
    public void setProtons(int protons) {
        if (state)
            atom.setProtons(protons);
    }

    @Override
    public int getNeutrons() {
        return atom.getNeutrons();
    }

    @Override
    public void setNeutrons(int neutrons) {
        if (state)
            atom.setNeutrons(neutrons);
    }

    @Override
    public void setSpeed(float newSpeed) {
        if (state)
            atom.setSpeed(newSpeed);
    }

    @Override
    public IMoveStrategy getMoveStrategy() {
        return atom.getMoveStrategy();
    }

    @Override
    public void setMoveStrategy(IMoveStrategy moveStrategy) {
        if (state)
            atom.setMoveStrategy(moveStrategy);
    }

    @Override
    public void move() {
        atom.getMoveStrategy().move(this);
    }

    public int getWidth() {
        return atom.getWidth();
    }

    public void setWidth(int width) {
        if (state)
            atom.setWidth(width);
    }

    public int getHeight() {
        return atom.getHeight();
    }

    public void setHeight(int height) {
        if (state)
            atom.setHeight(height);
    }

    public Atom getAtom() {
        return atom;
    }

    /**
     * @return speed
     * @effects calculated in typed Shield classes with their specific formulas
     */
    @Override
    public abstract float getSpeed();

    /**
     * @return speed
     * @effects calculated in typed Shield classes with their specific formulas
     */
    @Override
    public abstract double getEfficiency();

}
