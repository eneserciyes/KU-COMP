package tr.com.teamfaster.domain.models.atoms;

import tr.com.teamfaster.domain.utils.EntityType;
import tr.com.teamfaster.domain.utils.Position;

public class AtomFactory {

    private static AtomFactory atomFactory;

    private AtomFactory() {

    }

    public static AtomFactory getInstance() {
        if (atomFactory == null)
            atomFactory = new AtomFactory();
        return atomFactory;
    }

    public Atom getAtom(Position pos, float rotation, EntityType type) {
        return switch (type) {
            case ALPHA -> new Alpha(pos, rotation);
            case BETA -> new Beta(pos, rotation);
            case GAMMA -> new Gamma(pos, rotation);
            case SIGMA -> new Sigma(pos, rotation);
            default -> null;
        };
    }
}
