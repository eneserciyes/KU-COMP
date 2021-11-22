package tr.com.teamfaster.domain.utils;

public enum EntityType {
    ALPHA(1), BETA(2), GAMMA(3), SIGMA(4);

    private final int rank;

    EntityType(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

}
