package tr.com.teamfaster.domain.services;

import tr.com.teamfaster.domain.managers.*;
import tr.com.teamfaster.domain.models.*;
import tr.com.teamfaster.domain.models.atoms.Atom;
import tr.com.teamfaster.domain.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class CheckCollisionService {

    private final CollisionService collisionService;

    public CheckCollisionService() {
        collisionService = CollisionService.getInstance();
    }

    /**
     * By taking the list of every moving entities create 6 type of pair list which a collision detected
     * and for every collided pair calls the collide method of CollisionService.
     */
    public void checkCollisions() {
        List<Atom> atoms = List.copyOf(AtomManager.getInstance().getMovingEntities());
        List<Molecule> molecules = List.copyOf(MoleculeManager.getInstance().getMovingEntities());
        List<Blocker> blockers = BlockerManager.getInstance().getMovingEntities();
        List<Powerup> powerups = PowerupManager.getInstance().getMovingEntities();

        AtomShooter shooter = AtomShooterManager.getInstance().getShooter();
        List<AtomShooter> shooterList = new ArrayList<>();
        shooterList.add(shooter);

        List<Pair<Atom, Molecule>> pairList1 = checkCollisionFor(atoms, molecules);
        for (Pair<Atom, Molecule> pair : pairList1) {
            collisionService.collide(pair.getFirst(), pair.getSecond());
        }

        List<Pair<Blocker, AtomShooter>> pairList2 = checkCollisionFor(blockers, shooterList);
        for (Pair<Blocker, AtomShooter> pair : pairList2) {
            collisionService.collide(pair.getFirst(), pair.getSecond());
        }

        List<Pair<Blocker, Atom>> pairList3 = checkCollisionFor(blockers, atoms);
        for (Pair<Blocker, Atom> pair : pairList3) {
            collisionService.collide(pair.getFirst(), pair.getSecond());
        }

        List<Pair<Blocker, Molecule>> pairList4 = checkCollisionFor(blockers, molecules);
        for (Pair<Blocker, Molecule> pair : pairList4) {
            collisionService.collide(pair.getFirst(), pair.getSecond());
        }
        List<Pair<Blocker, Powerup>> pairList5 = checkCollisionFor(blockers, powerups);
        for (Pair<Blocker, Powerup> pair : pairList5) {
            collisionService.collide(pair.getFirst(), pair.getSecond());
        }

        List<Pair<Powerup, AtomShooter>> pairList6 = checkCollisionFor(powerups, shooterList);
        for (Pair<Powerup, AtomShooter> pair : pairList6) {
            collisionService.collide(pair.getFirst(), pair.getSecond());
        }

    }

    /**
     * Check pairwise collision.
     *
     * @param list1: list of moving entities of domain object T.
     * @param list2: list of moving entities of domain object U.
     * @param <T>:   Entity which can be atomShooter, atom, molecule, powerup or blocker.
     * @param <U>:   Entity which can be atomShooter, atom, molecule, powerup or blocker.
     * @return: list off pairs which a collision detected.
     */
    public <T extends Entity, U extends Entity> List<Pair<T, U>> checkCollisionFor(List<T> list1, List<U> list2) {
        List<Pair<T, U>> collidingPairs = new ArrayList<>();
        for (int i = 0; i < list1.size(); i++) {
            T entity1 = list1.get(i);
            for (int j = 0; j < list2.size(); j++) {
                U entity2 = list2.get(j);
                boolean res = entity1.isColliding(entity2);
                if (res) {
                    collidingPairs.add(new Pair<>(entity1, entity2));
                }

            }
        }
        return collidingPairs;
    }
}
