package tr.com.teamfaster.domain.models.movable.strategies;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import tr.com.teamfaster.domain.models.movable.Movable;
import tr.com.teamfaster.domain.models.movable.strategies.IMoveStrategy;
import tr.com.teamfaster.domain.models.movable.strategies.MSAdvanceWallbound;
import tr.com.teamfaster.domain.utils.GameSettings;
import tr.com.teamfaster.domain.utils.Position;

import static org.junit.jupiter.api.Assertions.*;

public class MSAdvWallTest {
    IMoveStrategy ms;
    Movable obj;
    Position initialPosition;
    float initialRotation;
    float initialSpeed;

    @BeforeEach
    public void setUp() {
        ms = MSAdvanceWallbound.getInstance();
        initialPosition = new Position(100, 100);
        initialRotation = -90;
        initialSpeed = 0.2f; // very small because obj moves at each tick.
        obj = new Movable();
        obj.setPosition(initialPosition);
        obj.setRotation(initialRotation);
        obj.setSpeed(initialSpeed);
        obj.setMoveStrategy(ms);
    }

    @AfterEach
    public void tearDown() {}

    @Test
    public void moveNullObj() {
        try {
            ms.move(null);
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
        }
    }

    @Test
    public void moveXLessThanSpeedRotationPositive() {
        obj.setRotation(90);
        obj.setPosition(0.05f, initialPosition.getY());
        ms.move(obj);
        assertEquals(0.15f, obj.getX());
    }

    @Test
    public void moveXLessThanSpeedRotationNegative() {
        obj.setRotation(-90);
        obj.setPosition(0.05f, initialPosition.getY());
        ms.move(obj);
        assertEquals(0.25f, obj.getX());
    }

    @Test
    public void moveXvsGameWidthLessThanSpeedRotationPositive() {
        obj.setRotation(90);
        obj.setPosition(GameSettings.getInstance().getGameWidth() - 0.05f, initialPosition.getY());
        ms.move(obj);
        assertEquals(GameSettings.getInstance().getGameWidth() - 0.05f - initialSpeed, obj.getX());
    }

    @Test
    public void moveXvsGameWidthLessThanSpeedRotationNegative() {
        obj.setRotation(-90);
        obj.setPosition(GameSettings.getInstance().getGameWidth() - 0.05f, initialPosition.getY());
        ms.move(obj);
        assertEquals( GameSettings.getInstance().getGameWidth() - initialSpeed + 0.05f, obj.getX());
    }

    @Test
    public void moveXInBoundsRotationPositive() {
        obj.setRotation(45);
        float gameRotation = (float) Math.toRadians(obj.getRotation() + 90);
        ms.move(obj);
        assertEquals( initialPosition.getX() + (float) Math.cos(gameRotation) * initialSpeed, obj.getY());
    }

    @Test
    public void moveXInBoundsRotationNegative() {
        obj.setRotation(-45);
        float gameRotation = (float) Math.toRadians(obj.getRotation() + 90);
        ms.move(obj);
        assertEquals( initialPosition.getX() - (float) Math.cos(gameRotation) * initialSpeed, obj.getY());
    }

}
