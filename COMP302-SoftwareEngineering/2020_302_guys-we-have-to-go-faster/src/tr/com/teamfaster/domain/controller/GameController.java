package tr.com.teamfaster.domain.controller;

import tr.com.teamfaster.domain.listeners.IViewChangeListener;
import tr.com.teamfaster.domain.managers.*;
import tr.com.teamfaster.domain.models.movable.TypedMovable;
import tr.com.teamfaster.domain.services.CheckCollisionService;
import tr.com.teamfaster.domain.services.storage.SaveLoadService;
import tr.com.teamfaster.domain.utils.GameSettings;
import tr.com.teamfaster.domain.utils.ShieldType;

import java.util.Timer;
import java.util.TimerTask;

public class GameController {
    private static GameController gameController;

    private final AtomShooterManager atomShooterManager;
    private final AtomManager atomManager;
    private final MoleculeManager moleculeManager;
    private final PowerupManager powerupManager;
    private final BlockerManager blockerManager;
    private final CheckCollisionService checkCollisionService;
    private final Blender blender;
    private final SaveLoadService saveLoadService;

    private IViewChangeListener viewManager;

    private Timer domainTimer;
    private boolean timerStatus;
    private long time;

    /**
     * @effects initializes managers, services and blender
     */
    private GameController() {
        atomShooterManager = AtomShooterManager.getInstance();
        atomManager = AtomManager.getInstance();
        moleculeManager = MoleculeManager.getInstance();
        powerupManager = PowerupManager.getInstance();
        blockerManager = BlockerManager.getInstance();
        checkCollisionService = new CheckCollisionService();
        saveLoadService = SaveLoadService.getInstance();
        blender = Blender.getInstance();
    }

    public static GameController getInstance() {
        if (gameController == null) gameController = new GameController();
        return gameController;
    }

    public void initGame() {
        initializeTimer();
    }

    public void setShooterMoveDirection(int direction) {
        if (timerStatus) {
            atomShooterManager.setShooterMoveDirection(direction);
        }
    }

    public void setShooterRotateDirection(int direction) {
        if (timerStatus) {
            atomShooterManager.setShooterRotateDirection(direction);
        }
    }

    public void shoot() {
        if (timerStatus) {
            boolean shootSuccess = atomShooterManager.shoot();
            if (!shootSuccess) stopGame();
        }
    }

    public void changeAtom() {
        atomShooterManager.changeAtomType();
    }

    /**
     * @effects sets timerStatus to false and cancels timer tasks
     */
    public void stopGame() {
        timerStatus = false;
        domainTimer.cancel();
    }

    /**
     * @effects if the timer status is false, (so the timer tasks
     * are cancelled) the timer is reinitialized.
     */
    public void resumeGame() {
        if (!timerStatus) initializeTimer();
    }

    public void blendAtom() {
        stopGame();
        blender.blendAtom();
        resumeGame();
    }

    public void setCreator(IViewChangeListener viewManager) {
        this.viewManager = viewManager;
    }

    public void save() {
        if (!timerStatus) {
            saveLoadService.save();
        }
    }

    public void load() {
        if (!timerStatus) {
            saveLoadService.load();
            initializeTimer();
        }
    }

    /**
     * @effects sets timer status to true and creates a new timer
     * with following tasks:
     * -> movingEntities in atomManager, moleculeManager,powerupManager,
     * blockerManager, atomShooterManager in each tick duration
     * -> checking collisions in each tick duration after movingEntities
     * -> creating falling entities: blockers, powerups and molecules
     */
    private void initializeTimer() {
        timerStatus = true;
        domainTimer = new Timer();
        domainTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                atomManager.moveEntities();
                moleculeManager.moveEntities();
                powerupManager.moveEntities();
                blockerManager.moveEntities();
                atomShooterManager.moveShooter();
                checkCollisionService.checkCollisions();
            }
        }, 0, GameSettings.getTickDuration());

        domainTimer.scheduleAtFixedRate((new TimerTask() {
            @Override
            public void run() {
                createFallingEntity(moleculeManager);
            }
        }), 0, 1000);
        domainTimer.scheduleAtFixedRate((new TimerTask() {
            @Override
            public void run() {
                time += 1000;
                updateTime();
            }
        }), 0, 1000);

        domainTimer.scheduleAtFixedRate((new TimerTask() {
            @Override
            public void run() {
                createFallingEntity(powerupManager);
            }
        }), 0, 2000);

        domainTimer.scheduleAtFixedRate((new TimerTask() {
            @Override
            public void run() {
                createFallingEntity(blockerManager);
            }
        }), 0, 4000);

    }

    private <T extends TypedMovable> void createFallingEntity(FallingEntityManager<T> manager) {
        boolean creationSuccess = manager.createFallingEntity();
        if (!creationSuccess) stopGame();
    }

    private void updateTime() {
        viewManager.onTimeUpdate(time);
    }

    public void addShield(ShieldType shieldType) {
        AtomShooterManager.getInstance().addShield(shieldType);
    }
}
