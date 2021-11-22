package tr.com.teamfaster.domain.utils;

public enum ShieldType {
    ETA(0), LOTA(1), THETA(2), ZETA(3);

    private final int type;

    ShieldType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

}
