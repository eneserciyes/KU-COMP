package tr.com.teamfaster.domain.managers;

import tr.com.teamfaster.GameInfo;
import tr.com.teamfaster.domain.listeners.IPositionListener;
import tr.com.teamfaster.domain.listeners.IViewChangeListener;
import tr.com.teamfaster.domain.models.AtomShooter;
import tr.com.teamfaster.domain.models.atoms.Atom;
import tr.com.teamfaster.domain.models.atoms.AtomFactory;
import tr.com.teamfaster.domain.models.shields.ShieldFactory;
import tr.com.teamfaster.domain.services.storage.ISaveLoader;
import tr.com.teamfaster.domain.utils.EntityType;
import tr.com.teamfaster.domain.utils.Position;
import tr.com.teamfaster.domain.utils.RandomUtils;
import tr.com.teamfaster.domain.utils.ShieldType;
import tr.com.teamfaster.ui.managers.ViewChangeListener;
import tr.com.teamfaster.ui.views.AtomShooterView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AtomShooterManager implements ISaveLoader {
    private static AtomShooterManager instance;
    private final AtomShooter atomShooter;
    IViewChangeListener viewChangeListener;
    private AtomShooterView shooterView;
    private List<Atom> atomInventory;
    private int currentAtomIndex;
    private EntityType toShoot;
    private boolean powerupShootStatus;

    /**
     * @effects sets toShoot to EntityType.Alpha. Initializes atom shooter and atomInventorys.
     * sets currentAtomIndex to 0.
     */
    private AtomShooterManager() {
        powerupShootStatus = false;
        toShoot = EntityType.ALPHA;
        this.atomShooter = AtomShooter.getInstance();
        atomInventory = new ArrayList<>();
        currentAtomIndex = 0;
        initializeInventory();
    }

    public static AtomShooterManager getInstance() {
        if (instance == null) instance = new AtomShooterManager();
        return instance;
    }

    /**
     * @effect adds atoms with dummy positions and rotations as many as the atom counts
     * for each atom type
     */
    private void initializeInventory() {
        Map<EntityType, Integer> atomCounts = GameInfo.getInstance().getAtomCount();
        for (EntityType e : atomCounts.keySet()) {
            for (int i = 0; i < atomCounts.get(e); i++)
                atomInventory.add(AtomFactory.getInstance().getAtom(new Position(0, 0), 0, e));
        }
    }

    /**
     * @effect if powerupShootStatus of this is false, it sets the rotation and the position
     * of the atom at currentAtomIndex in the inventory to the rotation and the position of the
     * shooter. It adds this atom to the moving entities collection of the atomManager and removes
     * it from the atomInventory. A message to the viewManager for updating the atom number statistics
     * is published.
     * @return
     */
    public boolean shoot() {
        if (!powerupShootStatus) {
            EntityType nextAtomType = atomShooter.getCurrentAtomType();
            if (nextAtomType != null) {
                float rotation = atomShooter.getRotation();
                Position position = shooterView.getAtomOnTopPosition();

                Atom currentAtom = atomInventory.get(currentAtomIndex);
                currentAtom.setPosition(new Position(position.getX(), position.getY()));
                currentAtom.setRotation(rotation);

                AtomManager.getInstance().createEntity(currentAtom);
                atomInventory.remove(currentAtom);
                atomShooter.decreaseAmount(nextAtomType, 1);
                viewChangeListener.onAtomStatisticsUpdate(atomShooter.getAtomCounts());

                changeAtomType();
                return true;
            }
        } else {
            float rotation = atomShooter.getRotation();
            Position position = shooterView.getAtomOnTopPosition();
            Position newPos = new Position(position.getX(), position.getY());
            PowerupManager.getInstance().createEntity(position, rotation, toShoot);
            PowerupManager.getInstance().updatePowerupCount(toShoot);
            changeAtomType();
            setPowerupShootStatus(false);
            return true;
        }
        return false;
    }

    /**
     * @requires shieldType is not null.
     * @effects if the count of the shield with the given type is not zero,
     * the atom in the currentAtomIndex is wrapped with the corresponding shield.
     * The shield count of the given shieldType is decreased by one.
     * @param shieldType
     */
    public void addShield(ShieldType shieldType) {
        if (getShieldCounts().get(shieldType) > 0) {
            Atom currentAtom = atomInventory.get(currentAtomIndex);
            atomInventory.set(currentAtomIndex, ShieldFactory.getInstance().getShieldedAtom(currentAtom, shieldType));
            decreaseShield(shieldType, 1);
        }
    }

    public Position getShooterPosition() {
        return atomShooter.getPosition();
    }

    public EntityType getCurrentAtomType() {
        return atomShooter.getCurrentAtomType();
    }

    public void setCurrentAtomType(EntityType type) {
        atomShooter.setCurrentAtomType(type);
    }

    public List<Atom> getAtomInventory() {
        return atomInventory;
    }

    public void setAtomInventory(List<Atom> newAtomInventory) {
        atomInventory = newAtomInventory;
    }

    public void changeAtomType() {
        if (atomInventory.size() != 0) {
            int randomIndex = RandomUtils.getRandomIndex(atomInventory.size());
            atomShooter.changeAtomType(atomInventory.get(randomIndex).getType());
            currentAtomIndex = randomIndex;
        } else atomShooter.changeAtomType(null);

        EntityType newEntityType = atomShooter.getCurrentAtomType();
        if (newEntityType != null) viewChangeListener.onChangeShooterBarrelView(newEntityType);
    }

    public void decreaseAtom(EntityType type, int amount) {
        List<Atom> atomsToRemove = new ArrayList<>();
        for (Atom atom : atomInventory) {
            if (amount > 0 && atom.getType() == type) {
                atomsToRemove.add(atom);
            }
        }
        for (Atom atom : atomsToRemove) {
            atomInventory.remove(atom);
        }
        atomShooter.decreaseAmount(type, amount);
    }

    public void decreaseShield(ShieldType type, int amount) {
        atomShooter.decreaseShieldAmount(type, amount);
        viewChangeListener.onShieldNumberUpdate(atomShooter.getShieldCounts());
    }

    public void incrementAtom(EntityType type, int amount) {
        for (int i = 0; i < amount; i++) {
            atomInventory.add(AtomFactory.getInstance().getAtom(new Position(0, 0), 0, type));
        }
        atomShooter.incrementAmount(type, amount);
    }

    public Map<EntityType, Integer> getAtomCounts() {
        return atomShooter.getAtomCounts();
    }

    public void setAtomCounts(Map<EntityType, Integer> newcounts) {
        atomShooter.setAtomCounts(newcounts);
    }

    public void moveShooter() {
        atomShooter.move();
        if (shooterView != null) {
            shooterView.onPositionChanged(atomShooter.getPosition());
            shooterView.rotate((int) atomShooter.getRotation());
        }
    }

    public Map<ShieldType, Integer> getShieldCounts() {
        return atomShooter.getShieldCounts();
    }

    public void setShooterMoveDirection(int direction) {
        atomShooter.setMoveDirection(direction);
    }

    public void setShooterRotateDirection(int direction) {
        atomShooter.setRotateDirection(direction);
    }

    public AtomShooter getShooter() {
        return atomShooter;
    }

    public double getScore() {
        return atomShooter.getScore();
    }

    public void incrementScore(double score) {
        atomShooter.setScore(atomShooter.getScore() + score);
        viewChangeListener.onHealthScoreUpdate(atomShooter.getHealth(), getScore());
    }

    public void subscribeToAtomShooter(IPositionListener shooterView) {
        this.shooterView = (AtomShooterView) shooterView;
    }

    public void subscribeViewManager(ViewChangeListener viewChangeListener) {
        this.viewChangeListener = viewChangeListener;
    }

    public void receiveDamage(int i) {
        int health = atomShooter.getHealth();
        atomShooter.setHealth(health - i);
        viewChangeListener.onHealthScoreUpdate(atomShooter.getHealth(), getScore());

    }

    @Override
    public ArrayList<ArrayList<String>> getInfo() {
        ArrayList<ArrayList<String>> shooterInfo = new ArrayList<>();

        ArrayList<String> shooter = new ArrayList<String>();
        shooter.add(String.valueOf(atomShooter.getPosition().getX()));
        shooter.add(String.valueOf(atomShooter.getPosition().getY()));
        shooter.add(String.valueOf(atomShooter.getRotation()));
        shooter.add(String.valueOf(atomShooter.getCurrentAtomType().name()));
        shooter.add(String.valueOf(atomShooter.getHealth()));
        shooter.add(String.valueOf(atomShooter.getScore()));

        // atom counts
        shooter.add(String.valueOf(atomShooter.getAtomCounts().get(EntityType.ALPHA)));
        shooter.add(String.valueOf(atomShooter.getAtomCounts().get(EntityType.BETA)));
        shooter.add(String.valueOf(atomShooter.getAtomCounts().get(EntityType.GAMMA)));
        shooter.add(String.valueOf(atomShooter.getAtomCounts().get(EntityType.SIGMA)));

        // shield inventory
        shooter.add(String.valueOf(atomShooter.getShieldCounts().get(ShieldType.ETA)));
        shooter.add(String.valueOf(atomShooter.getShieldCounts().get(ShieldType.LOTA)));
        shooter.add(String.valueOf(atomShooter.getShieldCounts().get(ShieldType.THETA)));
        shooter.add(String.valueOf(atomShooter.getShieldCounts().get(ShieldType.ZETA)));

        shooter.add(String.valueOf(currentAtomIndex));

        shooterInfo.add(shooter);

        // atom inventory
        ArrayList<String> inventoryInfo = new ArrayList<>();

        for (Atom atom : atomInventory) {
            ArrayList<String> current = new ArrayList<>(3);
            current.add(atom.getType().name());
            current.addAll(atom.getShields());
            shooterInfo.add(current);
        }
        System.out.println(atomInventory);
        return shooterInfo;
    }

    @Override
    public void loadInfo(ArrayList<ArrayList<String>> info) {
        ArrayList<String> shooterInfo = info.get(0);
        atomShooter.setPosition(Float.parseFloat(shooterInfo.get(0)), Float.parseFloat(shooterInfo.get(1)));
        atomShooter.setRotation(Float.parseFloat(shooterInfo.get(2)));
        atomShooter.setCurrentAtomType(EntityType.valueOf(shooterInfo.get(3)));
        viewChangeListener.onChangeShooterBarrelView(EntityType.valueOf((shooterInfo.get(3))));
        atomShooter.setHealth(Integer.parseInt(shooterInfo.get(4)));
        atomShooter.setScore(Double.parseDouble(shooterInfo.get(5)));
        atomShooter.setAtomCount(EntityType.ALPHA, Integer.parseInt(shooterInfo.get(6)));
        atomShooter.setAtomCount(EntityType.BETA, Integer.parseInt(shooterInfo.get(7)));
        atomShooter.setAtomCount(EntityType.GAMMA, Integer.parseInt(shooterInfo.get(8)));
        atomShooter.setAtomCount(EntityType.SIGMA, Integer.parseInt(shooterInfo.get(9)));

        atomShooter.setShieldCount(ShieldType.ETA, Integer.parseInt(shooterInfo.get(10)));
        atomShooter.setShieldCount(ShieldType.LOTA, Integer.parseInt(shooterInfo.get(11)));
        atomShooter.setShieldCount(ShieldType.THETA, Integer.parseInt(shooterInfo.get(12)));
        atomShooter.setShieldCount(ShieldType.ZETA, Integer.parseInt(shooterInfo.get(13)));

        viewChangeListener.onAtomStatisticsUpdate(atomShooter.getAtomCounts());
        viewChangeListener.onHealthScoreUpdate(atomShooter.getHealth(), atomShooter.getScore());
        viewChangeListener.onShieldNumberUpdate(atomShooter.getShieldCounts());

        atomInventory.clear();
        currentAtomIndex = 0;

        for (ArrayList<String> atomInInventoryInfo : info.subList(1, info.size() - 1)) {
            Atom currentAtom = AtomFactory.getInstance().getAtom(new Position(0, 0), 0, EntityType.valueOf(atomInInventoryInfo.get(0)));
            atomInventory.add(currentAtom);
            if (atomInInventoryInfo.size() > 1)
                for (String shield : atomInInventoryInfo.subList(1, atomInInventoryInfo.size() - 1))
                    addShield(ShieldType.valueOf(shield));
            currentAtomIndex++;
        }
        currentAtomIndex = Integer.parseInt(shooterInfo.get(14));
    }

    public void setPowerupShootStatus(boolean bool) {
        powerupShootStatus = bool;
    }

    public void setBarrelView(EntityType value) {
        viewChangeListener.onChangeShooterBarrelViewPowerup(value);
        toShoot = value;
    }
}
