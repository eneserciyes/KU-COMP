package tr.com.teamfaster.domain.services.storage;

import java.util.ArrayList;

/**
 * SaveInfo is where game information is stored.
 * It has a username field, and information from managers.
 * The format for manager information is: list of ["type", "info1", ... , "infoN"]
 */

public class SaveInfo {

    private String username;
    private ArrayList<ArrayList<String>> atomInfo;
    private ArrayList<ArrayList<String>> moleculeInfo;
    private ArrayList<ArrayList<String>> powerupInfo;
    private ArrayList<ArrayList<String>> blockerInfo;
    private ArrayList<ArrayList<String>> shooterInfo;
    private ArrayList<ArrayList<String>> gameSettings;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<ArrayList<String>> getAtomInfo() {
        return atomInfo;
    }

    public void setAtomInfo(ArrayList<ArrayList<String>> atomInfo) {
        this.atomInfo = atomInfo;
    }

    public ArrayList<ArrayList<String>> getMoleculeInfo() {
        return moleculeInfo;
    }

    public void setMoleculeInfo(ArrayList<ArrayList<String>> moleculeInfo) {
        this.moleculeInfo = moleculeInfo;
    }

    public ArrayList<ArrayList<String>> getPowerupInfo() {
        return powerupInfo;
    }

    public void setPowerupInfo(ArrayList<ArrayList<String>> powerupInfo) {
        this.powerupInfo = powerupInfo;
    }

    public ArrayList<ArrayList<String>> getBlockerInfo() {
        return blockerInfo;
    }

    public void setBlockerInfo(ArrayList<ArrayList<String>> blockerInfo) {
        this.blockerInfo = blockerInfo;
    }

    public ArrayList<ArrayList<String>> getShooterInfo() {
        return shooterInfo;
    }

    public void setShooterInfo(ArrayList<ArrayList<String>> shooterInfo) {
        this.shooterInfo = shooterInfo;
    }

    public ArrayList<ArrayList<String>> getGameSettings() {
        return gameSettings;
    }

    public void setGameSettings(ArrayList<ArrayList<String>> gameSettings) {
        this.gameSettings = gameSettings;
    }

}
