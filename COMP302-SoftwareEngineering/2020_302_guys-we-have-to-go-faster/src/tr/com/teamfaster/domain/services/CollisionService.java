package tr.com.teamfaster.domain.services;

import tr.com.teamfaster.domain.managers.*;
import tr.com.teamfaster.domain.models.AtomShooter;
import tr.com.teamfaster.domain.models.Blocker;
import tr.com.teamfaster.domain.models.Molecule;
import tr.com.teamfaster.domain.models.Powerup;
import tr.com.teamfaster.domain.models.atoms.Atom;
import tr.com.teamfaster.domain.utils.GameSettings;

/**
 * @author menes
 */

public class CollisionService {

    private static CollisionService instance;
    private final AtomManager atomManager;
    private final MoleculeManager moleculeManager;
    private final AtomShooterManager atomShooterManager;
    private final BlockerManager blockerManager;
    private final PowerupManager powerupManager;

    private CollisionService() {
        atomManager = AtomManager.getInstance();
        moleculeManager = MoleculeManager.getInstance();
        atomShooterManager = AtomShooterManager.getInstance();
        blockerManager = BlockerManager.getInstance();
        powerupManager = PowerupManager.getInstance();
    }

    public static CollisionService getInstance() {
        if (instance == null) instance = new CollisionService();
        return instance;
    }

    /**
     * Removes the two entities from their manager. Increments the score and updates the labels
     *
     * @param atom     : collided atom
     * @param molecule : collided molecule
     */
    public void collide(Atom atom, Molecule molecule) {
        if (atom.getType() == molecule.getType()) {
            atomManager.removeEntity(atom);
            moleculeManager.removeEntity(molecule);
            atomShooterManager.incrementScore(atom.getEfficiency());
        }

    }

    /**
     * Removes the blocker from  blocker manager. Decrements the health point of atomShooter
     * and updates the labels.
     *
     * @param blocker:collided blocker
     * @param atomShooter:     atomShooter
     */
    public void collide(Blocker blocker, AtomShooter atomShooter) {
        if (blocker.getY() >= GameSettings.getGameHeight() - 50) {
            System.out.println("COLLISION HAPPENED");
            atomShooterManager.receiveDamage(getBlockerDamage(atomShooter, blocker));
            blockerManager.removeEntity(blocker);
        }
    }

    /**
     * Removes the atom from atom manager.
     *
     * @param blocker : collided blocker
     * @param atom    : collided atom
     */
    public void collide(Blocker blocker, Atom atom) {
        if (atom.getType() == blocker.getType()) {
            atomManager.removeEntity(atom);
        }
    }

    /**
     * Rempves the molecule from molecule manager.
     *
     * @param blocker:  collided blocker
     * @param molecule: collided molecule
     */
    public void collide(Blocker blocker, Molecule molecule) {
        if (blocker.getType() == molecule.getType()) {
            System.out.println("BLOCKER DESTROYED MOLECULE");
            moleculeManager.removeEntity(molecule);
        }
    }

    /**
     * Removes the two entities from their manager.
     *
     * @param blocker: collided blocker
     * @param powerup: collided powerup
     */
    public void collide(Blocker blocker, Powerup powerup) {
        if (blocker.getType() == powerup.getType()) {
            System.out.println("POWERUP DESTROYED BLOCKER");
            blockerManager.removeEntity(blocker);
            powerupManager.removeEntity(powerup);
        }
    }

    /**
     * Incremenets the count of collected powerup in powerup manager.
     *
     * @param powerup:     collected powerup
     * @param atomShooter: atomShooter
     */
    public void collide(Powerup powerup, AtomShooter atomShooter) {
        if (powerup.getFallingStatus()) {
            powerupManager.collectPowerup(powerup);
            System.out.println("POWERUP COLLECTED");
        }
    }

    /**
     * The damage of the blocker to the atom shooter  inversely proportional to distance between them.
     *
     * @param atomShooter: atomShooter
     * @param blocker:     collided blocker
     * @return: the health point reduction received by atomShooter
     */
    private int getBlockerDamage(AtomShooter atomShooter, Blocker blocker) {
        double distance = atomShooter.getCenter().getEuclideanDistance(blocker.getCenter());
        System.out.println(atomShooter.getCenter());

        distance = Math.max(10, distance);
        int damage = (int) (GameSettings.getGameWidth() / distance);
        System.out.println("Damage: " + damage);
        return damage;
    }
}
