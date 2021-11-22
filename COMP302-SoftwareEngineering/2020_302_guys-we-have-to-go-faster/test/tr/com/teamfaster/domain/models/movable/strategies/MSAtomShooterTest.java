package tr.com.teamfaster.domain.models.movable.strategies;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tr.com.teamfaster.domain.models.AtomShooter;
import tr.com.teamfaster.domain.utils.GameSettings;
import tr.com.teamfaster.domain.utils.Position;

import static org.junit.jupiter.api.Assertions.*;

class MSAtomShooterTest {
    AtomShooter shooter;
    float x;

    @BeforeEach
    void setUp() {
        shooter = AtomShooter.getInstance();
        shooter.setPosition(100,200);
        shooter.setRotation(20);
        x=GameSettings.getHorizontalShooterSpeed();
    }

    @AfterEach
    void tearDown() {
    }
    @Test
    public void shooterIsNull() {
        /*
         * BLACKBOX
         * Null shooter test
         * */
        assertThrows(NullPointerException.class,
                ()->{
                   shooter=null;
                   move();
                });
    }

    @Test
    void move() {
        /*
         * GLASSBOX
         * Calculated newX is between 0 and GameWith the rotation is between -80<=newRotation<=80
         *
         * */

        //move to right, rotate to right
        shooter.setMoveDirection(1);
        shooter.setRotationSpeed(10);
        shooter.move();
        assertEquals((float)100+x,shooter.getX());
        assertEquals((float)30,shooter.getRotation());

        //move to right, rotate to left
        setUp();
        shooter.setRotationSpeed(-10);
        shooter.move();
        assertEquals((float)100+x,shooter.getX());
        assertEquals((float)10,shooter.getRotation());

        //move to left, rotate to left
        setUp();
        shooter.setMoveDirection(-1);
        shooter.setRotationSpeed(-40);
        shooter.move();
        assertEquals((float)100-x,shooter.getX());
        assertEquals((float)-20,shooter.getRotation());

        //move to left, rotate to right
        setUp();
        shooter.setMoveDirection(-1);
        shooter.setRotationSpeed(40);
        shooter.move();
        assertEquals((float)100-x,shooter.getX());
        assertEquals((float)60,shooter.getRotation());

    }
    @Test
    void moveWithRightBoundRightRotation(){
        /*
         * GLASSBOX
         * Calculated newX is greater than GameWith the rotation is between 0<=rotationRate
         *
         * */
        //newX greater than GameWidth try to move right, rotate to right
        setUp();
        shooter.setPosition(GameSettings.getAtomShooterHeight()+GameSettings.getGameWidth(),200);
        shooter.setMoveDirection(1);
        shooter.setRotationSpeed(40);
        shooter.move();
        assertEquals((float)GameSettings.getAtomShooterHeight()+GameSettings.getGameWidth(),shooter.getX());
        assertEquals((float)60,shooter.getRotation());

        //newX greater than GameWidth try to move right, rotate to value that greater than 80
        shooter.setPosition(GameSettings.getAtomShooterHeight()+GameSettings.getGameWidth(),200);
        shooter.setRotationSpeed(90);
        shooter.move();
        assertEquals((float)GameSettings.getAtomShooterHeight()+GameSettings.getGameWidth(),shooter.getX());
        assertEquals((float)80,shooter.getRotation());

    }
    @Test
    void moveWithRightBoundLeftRotation(){
        /*
         * GLASSBOX
         * Calculated newX is greater than GameWith the rotation is between rotationRate<=0
         *
         * */
        //newX greater than GameWidth try to move right, rotate to left
        shooter.setPosition(GameSettings.getAtomShooterHeight()+GameSettings.getGameWidth(),200);
        shooter.setRotation(20);
        shooter.setMoveDirection(1);
        shooter.setRotationSpeed(-40);
        shooter.move();
        assertEquals((float)GameSettings.getAtomShooterHeight()+GameSettings.getGameWidth(),shooter.getX());
        assertEquals((float)-20,shooter.getRotation());

        //newX greater than GameWidth try to move right, rotate to value that smaller than -80
        shooter.setPosition(GameSettings.getAtomShooterHeight()+GameSettings.getGameWidth(),200);
        shooter.setRotationSpeed(-90);
        shooter.move();
        assertEquals((float)GameSettings.getAtomShooterHeight()+GameSettings.getGameWidth(),shooter.getX());
        assertEquals((float)-80,shooter.getRotation());

    }
    @Test
    void moveWithLeftBoundRightRotation(){
        /*
         * GLASSBOX
         * Calculated newX is smaller than 0, the rotation is between rotationRate>=0
         *
         * */
        //newX smaller than 0 try to move left, rotate to right
        shooter.setPosition(-GameSettings.getAtomShooterHeight(),200);
        shooter.setRotation(0);
        shooter.setMoveDirection(-1);
        shooter.setRotateDirection(1);
        shooter.setRotationSpeed(40);
        shooter.move();
        assertEquals(-GameSettings.getAtomShooterHeight(),shooter.getX());
        assertEquals((float)40,shooter.getRotation());

        //newX smaller than 0 try to move left, rotate to value that greater than 80
        shooter.setPosition(-GameSettings.getAtomShooterHeight(),200);
        shooter.setRotationSpeed(90);
        shooter.move();
        assertEquals(-GameSettings.getAtomShooterHeight(),shooter.getX());
        assertEquals((float)80,shooter.getRotation());

    }
    @Test
    void moveWithLeftBoundLeftRotation(){
        /*
         * GLASSBOX
         * Calculated newX is smaller than 0, the rotation is between rotationRate<=0
         *
         * */
        //newX smaller than 0 try to move left, rotate to left
        shooter.setPosition(-GameSettings.getAtomShooterHeight(),200);
        shooter.setRotation(0);
        shooter.setMoveDirection(-1);
        shooter.setRotationSpeed(-40);
        shooter.move();
        assertEquals(-GameSettings.getAtomShooterHeight(),shooter.getX());
        assertEquals((float)-40,shooter.getRotation());

        //newX greater than GameWidth try to move left, rotate to value that smaller than -80
        shooter.setPosition(-GameSettings.getAtomShooterHeight(),200);
        shooter.setRotationSpeed(-90);
        shooter.move();
        assertEquals(-GameSettings.getAtomShooterHeight(),shooter.getX());
        assertEquals((float)-80,shooter.getRotation());

    }
}