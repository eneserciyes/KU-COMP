package tr.com.teamfaster.domain.models.movable.strategies;

import tr.com.teamfaster.domain.models.AtomShooter;
import tr.com.teamfaster.domain.models.movable.Movable;
import tr.com.teamfaster.domain.utils.GameSettings;

/**
 * This Move Strategy implements the movement of the atom shooter. It uses the current direction and
 * speed of the shooter and moves it accordingly. The direction depends on keyboard input from the user,
 * and is passed to the shooter. This MS will prevent the shooter from leaving the game boundaries.
 */
public class MSAtomShooter implements IMoveStrategy {

    private static MSAtomShooter instance = null;

    public static MSAtomShooter getInstance() {
        if (instance == null) instance = new MSAtomShooter();
        return instance;
    }

    public void move(Movable obj) {
        try {
            move((AtomShooter) obj);
        } catch (Exception e) {
        }
    }

    /**
     * @param shooter AtomShooter class' instance for playing game.
     * @requires that shooter is not null. The values from GameSettings such as gameWidth, shooter speed,
     * shooter height are not null and the values are true.
     * @modifies the rotation and position of shooter
     * @effects the rotation and position changed according to old position, its rotation and move speed and
     * constrains of GameFrame.
     */
    public void move(AtomShooter shooter) {
        float dx = shooter.getSpeed();
        float newX = shooter.getX() + dx;

        if (newX + GameSettings.getAtomShooterHeight() <= 0) {
            dx = 0;
        } else if (newX + GameSettings.getAtomShooterHeight() >= GameSettings.getGameWidth()) {
            dx = 0;
        }

        shooter.setPosition((shooter.getX() + dx), shooter.getY());

        float newRotation = shooter.getRotation() + shooter.getRotationSpeed();

        if (newRotation < -80) newRotation = -80;
        else if (newRotation > 80) newRotation = 80;

        shooter.setRotation(newRotation);

    }

}
