package tr.com.teamfaster.domain.utils;

import tr.com.teamfaster.GameInfo;
import tr.com.teamfaster.Level;
import tr.com.teamfaster.domain.services.storage.ISaveLoader;

import java.awt.*;
import java.util.ArrayList;

public class GameSettings implements ISaveLoader {
    private static final int GAME_FRAME_HEIGHT = 516;
    private static final int GAME_FRAME_WIDTH = 900;
    private static final int GAME_WIDTH = 700;
    private static final int GAME_HEIGHT = GAME_FRAME_HEIGHT;
    private static final int STATISTICS_WINDOW_WIDTH = 200;
    private static final int STATISTICS_WINDOW_HEIGHT = GAME_FRAME_HEIGHT;
    private static final int TICK_DURATION = 10;
    private static final int LEFT = -1;
    private static final int STOP = 0;
    private static final int RIGHT = 1;
    private static final float SHOOTER_ROTATION_SPEED = ((float) 90 * TICK_DURATION) / 1000;
    private static final int HEALTH = 100;
    private static final Color STATISTICS_WINDOW_COLOR = new Color(0, 0, 0, 123);
    private static final double STATISTICS_TOP_WINDOW_RATIO = 0.2;
    private static final double STATISTICS_MIDDLE_WINDOW_RATIO = 0.4;
    private static final double STATISTICS_BOTTOM_WINDOW_RATIO = 0.4;
    private static final int BLENDER_SIZE_UNIT = 2;
    private static final double LABEL_PADDING = 0.05;

    private static GameSettings instance;

    private static String USERNAME = "Player 1";

    private static int L = GAME_FRAME_HEIGHT / 10;
    private static float HORIZONTAL_SHOOTER_SPEED = ((float) (L * TICK_DURATION)) / 1000;
    private static int k = 1;

    private static float ATOM_SPEED = ((float) (L * TICK_DURATION)) / 1000;
    private static float MOLECULE_SPEED = ATOM_SPEED;
    private static float POWERUP_SPEED = ATOM_SPEED;
    private static float BLOCKER_SPEED = ATOM_SPEED;

    private static int ATOM_DIAMETER = L / 4;
    private static int ATOM_SHOOTER_WIDTH = (int) (0.5 * L);
    private static int MOLECULE_WIDTH = L;
    private static int POWERUP_WIDTH = 2 * L / 3;
    private static int BLOCKER_WIDTH = L / 2;
    private static int ATOM_SHOOTER_HEIGHT = L;

    private static boolean ALPHA_LINEAR = false;
    private static boolean BETA_LINEAR = false;
    private static boolean ALPHA_SPINNING = false;
    private static boolean BETA_SPINNING = false;

    private static int[] ALPHA_NEUTRONS = {7, 8, 9};
    private static int[] BETA_NEUTRONS = {15, 16, 17, 18, 21};
    private static int[] GAMMA_NEUTRONS = {29, 32, 33};
    private static int[] SIGMA_NEUTRONS = {63, 64, 67};

    private static int ALPHA_PROTONS = 8;
    private static int BETA_PROTONS = 16;
    private static int GAMMA_PROTONS = 32;
    private static int SIGMA_PROTONS = 64;

    private static final double ALPHA_STABILITY_CONSTANT = 0.85;
    private static final double BETA_STABILITY_CONSTANT = 0.9;
    private static final double GAMMA_STABILITY_CONSTANT = 0.8;
    private static final double SIGMA_STABILITY_CONSTANT = 0.7;

    private static final double ETA_EFFICIENCY_BOOST = 1.05;
    private static final double LOTA_EFFICIENCY_BOOST = 1.01;
    private static final double THETA_EFFICIENCY_BOOST_MAX = 1.15;
    private static final double THETA_EFFICIENCY_BOOST_MIN = 1.05;
    private static final double ZETA_EFFICIENCY_BOOST = 1.2;

    private static final float ETA_SPEED_RATIO = ((float) 95) / 100;
    private static final float LOTA_SPEED_RATIO = ((float) 93) / 100;
    private static final float THETA_SPEED_RATIO = ((float) 91) / 100;
    private static final float ZETA_SPEED_RATIO = ((float) 89) / 100;

    public GameSettings() {
        GameInfo info = GameInfo.getInstance();
        USERNAME = info.getUsername();

        Level gLevel = info.getdLevel();
        switch (gLevel) {
            case EASY -> k = 1;
            case MEDIUM -> k = 2;
            case HARD -> k = 4;
        }

        if (info.getL() != getL()) {
            setL(info.getL());
            setHorizontalShooterSpeed(getL());
            setAtomSpeed(((float) (getL() * getTickDuration())) / 1000);
            setMoleculeSpeed(getAtomSpeed());
            setPowerupSpeed(getAtomSpeed());
            setBlockerSpeed(getAtomSpeed());
            setAtomDiameter(getL() / 4);
            setAtomShooterWidth((int) (0.5 * getL()));
            setHorizontalShooterSpeed(((float) (getL() * getTickDuration())) / 1000);
        }

        setAtomSpeed(ATOM_SPEED * k);
        setMoleculeSpeed(MOLECULE_SPEED * k);
        setPowerupSpeed(POWERUP_SPEED * k);
        setBlockerSpeed(BLOCKER_SPEED * k);
        setHorizontalShooterSpeed(HORIZONTAL_SHOOTER_SPEED * k);

        setAlphaLinear(info.isAlphaLinear());
        setBetaLinear(info.isBetaLinear());
        setAlphaSpinning(info.isAlphaSpinning());
        setBetaSpinning(info.isBetaSpinning());

    }

    public static GameSettings getInstance() {
        if (instance == null) instance = new GameSettings();
        return instance;
    }

    public static String getUsername() {
        return USERNAME;
    }

    public static void setUsername(String username) {
        USERNAME = username;
    }

    public static long getTickDuration() {
        return TICK_DURATION;
    }

    public static int getStarterHealth() {
        return HEALTH;
    }

    public static int getLEFT() {
        return LEFT;
    }

    public static int getSTOP() {
        return STOP;
    }

    public static int getRIGHT() {
        return RIGHT;
    }

    public static double getLabelPadding() {
        return LABEL_PADDING;
    }

    public static int getBlenderSizeUnit() {
        return BLENDER_SIZE_UNIT;
    }

    public static double getStatisticsBottomWindowRatio() {
        return STATISTICS_BOTTOM_WINDOW_RATIO;
    }

    public static double getStatisticsMiddleWindowRatio() {
        return STATISTICS_MIDDLE_WINDOW_RATIO;
    }

    public static double getStatisticsTopWindowRatio() {
        return STATISTICS_TOP_WINDOW_RATIO;
    }

    public static Color getStatisticsWindowColor() {
        return STATISTICS_WINDOW_COLOR;
    }

    public static int getStatisticsWindowHeight() {
        return STATISTICS_WINDOW_HEIGHT;
    }

    public static int getStatisticsWindowWidth() {
        return STATISTICS_WINDOW_WIDTH;
    }

    public static int getGameHeight() {
        return GAME_HEIGHT;
    }

    public static int getGameWidth() {
        return GAME_WIDTH;
    }

    public static int getGameFrameWidth() {
        return GAME_FRAME_WIDTH;
    }

    public static int getGameFrameHeight() {
        return GAME_FRAME_HEIGHT;
    }

    public static int getAtomShooterHeight() {
        return ATOM_SHOOTER_HEIGHT;
    }

    private void setAtomShooterHeight(int i) {
        ATOM_SHOOTER_HEIGHT = i;
    }

    public static int getAtomShooterWidth() {
        return ATOM_SHOOTER_WIDTH;
    }

    private void setAtomShooterWidth(int i) {
        ATOM_SHOOTER_WIDTH = i;
    }

    public static int getAtomDiameter() {
        return ATOM_DIAMETER;
    }

    private void setAtomDiameter(int i) {
        ATOM_DIAMETER = i;
    }

    public static float getMoleculeSpeed() {
        return MOLECULE_SPEED;
    }

    private static void setMoleculeSpeed(float moleculeSpeed) {
        MOLECULE_SPEED = moleculeSpeed;
    }

    public static float getPowerupSpeed() {
        return POWERUP_SPEED;
    }

    private static void setPowerupSpeed(float powerupSpeed) {
        POWERUP_SPEED = powerupSpeed;
    }

    public static float getBlockerSpeed() {
        return BLOCKER_SPEED;
    }

    private static void setBlockerSpeed(float blockerSpeed) {
        BLOCKER_SPEED = blockerSpeed;
    }

    public static float getAtomSpeed() {
        return ATOM_SPEED;
    }

    private static void setAtomSpeed(float atomSpeed) {
        ATOM_SPEED = atomSpeed;
    }

    public static int getMoleculeWidth() {
        return MOLECULE_WIDTH;
    }

    private static void setMoleculeWidth(int moleculeWidth) {
        MOLECULE_WIDTH = moleculeWidth;
    }

    public static int getPowerupWidth() {
        return POWERUP_WIDTH;
    }

    private static void setPowerupWidth(int powerupWidth) {
        POWERUP_WIDTH = powerupWidth;
    }

    public static int getBlockerWidth() {
        return BLOCKER_WIDTH;
    }

    private static void setBlockerWidth(int blockerWidth) {
        BLOCKER_WIDTH = blockerWidth;
    }

    public static float getHorizontalShooterSpeed() {
        return HORIZONTAL_SHOOTER_SPEED;
    }

    private static void setHorizontalShooterSpeed(float horizontalShooterSpeed) {
        HORIZONTAL_SHOOTER_SPEED = horizontalShooterSpeed;
    }

    public static float getShooterRotationSpeed() {
        return SHOOTER_ROTATION_SPEED;
    }

    public static int getL() {
        return L;
    }

    private static void setL(int l) {
        L = l;
    }

    public static int getK() {
        return k;
    }

    private void setK(int k) {
        this.k = k;
    }

    public static boolean isAlphaLinear() {
        return ALPHA_LINEAR;
    }

    private static void setAlphaLinear(boolean alphaLinear) {
        ALPHA_LINEAR = alphaLinear;
    }

    public static boolean isBetaLinear() {
        return BETA_LINEAR;
    }

    private static void setBetaLinear(boolean betaLinear) {
        BETA_LINEAR = betaLinear;
    }

    public static boolean isAlphaSpinning() {
        return ALPHA_SPINNING;
    }

    private static void setAlphaSpinning(boolean alphaSpinning) {
        ALPHA_SPINNING = alphaSpinning;
    }

    public static boolean isBetaSpinning() {
        return BETA_SPINNING;
    }

    private static void setBetaSpinning(boolean betaSpinning) {
        BETA_SPINNING = betaSpinning;
    }

    public static int[] getAlphaNeutrons() {
        return ALPHA_NEUTRONS;
    }

    public static int[] getSigmaNeutrons() {
        return SIGMA_NEUTRONS;
    }

    public static void setSigmaNeutrons(int[] sigmaNeutrons) {
        SIGMA_NEUTRONS = sigmaNeutrons;
    }

    public static int[] getGammaNeutrons() {
        return GAMMA_NEUTRONS;
    }

    public static void setGammaNeutrons(int[] gammaNeutrons) {
        GAMMA_NEUTRONS = gammaNeutrons;
    }

    public static int[] getBetaNeutrons() {
        return BETA_NEUTRONS;
    }

    public static void setBetaNeutrons(int[] betaNeutrons) {
        BETA_NEUTRONS = betaNeutrons;
    }

    public static void setAlphaNeutrons(int[] alphaNeutrons) {
        ALPHA_NEUTRONS = alphaNeutrons;
    }

    public static int getAlphaProtons() {
        return ALPHA_PROTONS;
    }

    public static int getBetaProtons() {
        return BETA_PROTONS;
    }

    public static int getGammaProtons() {
        return GAMMA_PROTONS;
    }

    public static int getSigmaProtons() {
        return SIGMA_PROTONS;
    }

    public static double getAlphaStabilityConstant() {
        return ALPHA_STABILITY_CONSTANT;
    }

    public static double getBetaStabilityConstant() {
        return BETA_STABILITY_CONSTANT;
    }

    public static double getGammaStabilityConstant() {
        return GAMMA_STABILITY_CONSTANT;
    }

    public static double getSigmaStabilityConstant() {
        return SIGMA_STABILITY_CONSTANT;
    }

    public static double getLotaEfficiencyBoost() {
        return LOTA_EFFICIENCY_BOOST;
    }

    public static double getEtaEfficiencyBoost() {
        return ETA_EFFICIENCY_BOOST;
    }

    public static double getZetaEfficiencyBoost() {
        return ZETA_EFFICIENCY_BOOST;
    }

    public static double getThetaEfficiencyBoostMax() {
        return THETA_EFFICIENCY_BOOST_MAX;
    }

    public static double getThetaEfficiencyBoostMin() {
        return THETA_EFFICIENCY_BOOST_MIN;
    }

    public static float getEtaSpeedRatio() {
        return ETA_SPEED_RATIO;
    }

    public static float getLotaSpeedRatio() {
        return LOTA_SPEED_RATIO;
    }

    public static float getThetaSpeedRatio() {
        return THETA_SPEED_RATIO;
    }

    public static float getZetaSpeedRatio() {
        return ZETA_SPEED_RATIO;
    }

    @Override
    public ArrayList<ArrayList<String>> getInfo() {
        ArrayList<ArrayList<String>> settingsInfo = new ArrayList<>();
        ArrayList<String> info = new ArrayList<String>(17);

        info.add(String.valueOf(L));
        info.add(String.valueOf(HORIZONTAL_SHOOTER_SPEED));
        info.add(String.valueOf(k));

        info.add(String.valueOf(ATOM_SPEED));
        info.add(String.valueOf(MOLECULE_SPEED));
        info.add(String.valueOf(POWERUP_SPEED));
        info.add(String.valueOf(BLOCKER_SPEED));

        info.add(String.valueOf(ATOM_DIAMETER));
        info.add(String.valueOf(ATOM_SHOOTER_WIDTH));
        info.add(String.valueOf(MOLECULE_WIDTH));
        info.add(String.valueOf(POWERUP_WIDTH));
        info.add(String.valueOf(BLOCKER_WIDTH));
        info.add(String.valueOf(ATOM_SHOOTER_HEIGHT));

        info.add(String.valueOf(ALPHA_LINEAR));
        info.add(String.valueOf(BETA_LINEAR));
        info.add(String.valueOf(ALPHA_SPINNING));
        info.add(String.valueOf(BETA_SPINNING));

        settingsInfo.add(info);

        return settingsInfo;
    }

    @Override
    public void loadInfo(ArrayList<ArrayList<String>> info) {
        ArrayList<String> settingsInfo = info.get(0);

        setL(Integer.parseInt(settingsInfo.get(0)));
        setHorizontalShooterSpeed(Float.parseFloat(settingsInfo.get(1)));
        setK(Integer.parseInt(settingsInfo.get(2)));

        setAtomSpeed(Float.parseFloat(settingsInfo.get(3)));
        setMoleculeSpeed(Float.parseFloat(settingsInfo.get(4)));
        setPowerupSpeed(Float.parseFloat(settingsInfo.get(5)));
        setBlockerSpeed(Float.parseFloat(settingsInfo.get(6)));

        setAtomDiameter(Integer.parseInt(settingsInfo.get(7)));
        setAtomShooterWidth(Integer.parseInt(settingsInfo.get(8)));
        setMoleculeWidth(Integer.parseInt(settingsInfo.get(9)));
        setPowerupWidth(Integer.parseInt(settingsInfo.get(10)));
        setBlockerWidth(Integer.parseInt(settingsInfo.get(11)));
        setAtomShooterHeight(Integer.parseInt(settingsInfo.get(12)));

        setAlphaLinear(Boolean.parseBoolean(settingsInfo.get(13)));
        setBetaLinear(Boolean.parseBoolean(settingsInfo.get(14)));
        setAlphaSpinning(Boolean.parseBoolean(settingsInfo.get(15)));
        setBetaSpinning(Boolean.parseBoolean(settingsInfo.get(16)));

    }
}
