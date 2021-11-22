package tr.com.teamfaster.domain.services.storage;

import tr.com.teamfaster.domain.managers.*;
import tr.com.teamfaster.domain.utils.GameSettings;

/**
 * SaveLoadService is where the information from managers are saved into a new SaveInfo
 */
public class SaveLoadService {

    private static SaveLoadService instance;

    private final AtomShooterManager atomShooterManager;
    private final AtomManager atomManager;
    private final MoleculeManager moleculeManager;
    private final PowerupManager powerupManager;
    private final BlockerManager blockerManager;

    private final IStorageAdapter storageAdapter;

    /**
     * @return SaveLoadService
     */
    public static SaveLoadService getInstance() {
        if (instance == null) return new SaveLoadService();
        else return instance;
    }

    /**
     * Initializes a SaveLoadService with instances of managers
     */
    private SaveLoadService() {
        atomShooterManager = AtomShooterManager.getInstance();
        atomManager = AtomManager.getInstance();
        moleculeManager = MoleculeManager.getInstance();
        powerupManager = PowerupManager.getInstance();
        blockerManager = BlockerManager.getInstance();
        storageAdapter = getStorageAdapter();
    }

    /**
     * Creates a new SaveInfo and saves to storageAdapter
     */
    public void save() {
        SaveInfo saveInfo = new SaveInfo();
        saveInfo.setUsername(GameSettings.getUsername());
        saveInfo.setShooterInfo(atomShooterManager.getInfo());
        saveInfo.setAtomInfo(atomManager.getInfo());
        saveInfo.setMoleculeInfo(moleculeManager.getInfo());
        saveInfo.setPowerupInfo(powerupManager.getInfo());
        saveInfo.setBlockerInfo(blockerManager.getInfo());
        saveInfo.setGameSettings(GameSettings.getInstance().getInfo());

        storageAdapter.save(saveInfo);
    }

    /**
     * Loads saved game info
     */
    public void load() {
        SaveInfo saveInfo = storageAdapter.getSave();

        if (saveInfo == null) {
            System.out.println("No save found for: " + GameSettings.getUsername());
            return;
        }

        atomManager.loadInfo(saveInfo.getAtomInfo());
        moleculeManager.loadInfo(saveInfo.getMoleculeInfo());
        powerupManager.loadInfo(saveInfo.getPowerupInfo());
        blockerManager.loadInfo(saveInfo.getBlockerInfo());
        atomShooterManager.loadInfo(saveInfo.getShooterInfo());
        GameSettings.getInstance().loadInfo(saveInfo.getGameSettings());
    }

    /**
     * Chooses which save-load method to use with environment variable "storage"
     *
     * @return IStorageAdapter
     */
    private IStorageAdapter getStorageAdapter() {
        if (System.getenv("storage") != null) {
            switch (System.getenv("storage")) {
                case "database", "db", "d" -> {
                    return DatabaseAdapter.getInstance();
                }
                case "file", "f" -> {
                    return FileStorageAdapter.getInstance();
                }
                default -> {
                    return FileStorageAdapter.getInstance();
                }
            }
        } else return FileStorageAdapter.getInstance();
    }
}
