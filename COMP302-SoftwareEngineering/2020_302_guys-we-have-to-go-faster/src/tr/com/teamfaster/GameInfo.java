package tr.com.teamfaster;

import tr.com.teamfaster.domain.utils.EntityType;
import tr.com.teamfaster.domain.utils.GameSettings;
import tr.com.teamfaster.domain.utils.ShieldType;

import java.util.HashMap;

/**
 * GameInfo holds the customizable parameters of the game, receiving updates from BuildMenu at the
 * building stage of the game. It is then used to pass the values of the parameters to the domain layer.
 */
public class GameInfo {
    private static GameInfo info;

    private String username;

    private HashMap<EntityType, Integer> atomCount;
    private HashMap<EntityType, Integer> moleculeCount;
    private HashMap<EntityType, Integer> powerupCount;
    private HashMap<EntityType, Integer> blockerCount;
    private HashMap<ShieldType, Integer> shieldCount;

    private boolean isAlphaLinear;
    private boolean isBetaLinear;
    private boolean isAlphaSpinning;
    private boolean isBetaSpinning;
    private boolean isReady;

    private int L;
    private Level dLevel;

    public GameInfo() {
        username = "Player 1";
        atomCount = new HashMap<>();
        moleculeCount = new HashMap<>();
        powerupCount = new HashMap<>();
        blockerCount = new HashMap<>();
        shieldCount = new HashMap<>();
        for (EntityType type : EntityType.values()) {
            atomCount.put(type, 100);
            moleculeCount.put(type, 100);
            powerupCount.put(type, 20);
            blockerCount.put(type, 10);
        }
        for (ShieldType type : ShieldType.values()) {
            shieldCount.put(type, 10);
            shieldCount.put(type, 10);
            shieldCount.put(type, 10);
            shieldCount.put(type, 10);
        }
        // How L will be equal to game frame width/10 by default?
        L = GameSettings.getL();
        isReady = false;
        isAlphaLinear = false;
        isBetaLinear = false;
        isAlphaSpinning = false;
        isBetaSpinning = false;
        dLevel = Level.EASY;

    }

    public static GameInfo getInstance() {
        if (info == null) info = new GameInfo();
        return info;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setAtomCount(EntityType type, int count) {
        atomCount.replace(type, count);
    }

    public HashMap<EntityType, Integer> getAtomCount() {
        return atomCount;
    }

    public void setMoleculeCount(EntityType type, int count) {
        moleculeCount.replace(type, count);
    }

    public HashMap<EntityType, Integer> getMoleculeCount() {
        return moleculeCount;
    }

    public void setPowerupCount(EntityType type, int count) {
        powerupCount.replace(type, count);
    }

    public HashMap<EntityType, Integer> getPowerupCount() {
        return powerupCount;
    }

    public void setBlockerCount(EntityType type, int count) {
        blockerCount.replace(type, count);
    }

    public HashMap<EntityType, Integer> getBlockerCount() {
        return blockerCount;
    }

    public void setShieldCount(ShieldType type, int count) {
        shieldCount.replace(type, count);
    }

    public HashMap<ShieldType, Integer> getShieldCount() {
        return shieldCount;
    }

    public int getL() {
        return L;
    }

    public void setL(int l) {
        L = l;
    }

    public Level getdLevel() {
        return dLevel;
    }

    public void setdLevel(Level dLevel) {

        this.dLevel = dLevel;
    }

    public boolean isBetaSpinning() {
        return isBetaSpinning;
    }

    public void setBetaSpinning(boolean betaSpinning) {
        isBetaSpinning = betaSpinning;
    }

    public boolean isAlphaSpinning() {
        return isAlphaSpinning;
    }

    public void setAlphaSpinning(boolean alphaSpinning) {
        isAlphaSpinning = alphaSpinning;
    }

    public boolean isBetaLinear() {
        return isBetaLinear;
    }

    public void setBetaLinear(boolean betaLinear) {
        isBetaLinear = betaLinear;
    }

    public boolean isAlphaLinear() {
        return isAlphaLinear;
    }

    public void setAlphaLinear(boolean alphaLinear) {
        isAlphaLinear = alphaLinear;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

}

