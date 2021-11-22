package tr.com.teamfaster.domain.models.shields;

import tr.com.teamfaster.domain.models.atoms.Atom;
import tr.com.teamfaster.domain.utils.ShieldType;

public class ShieldFactory {
    private static ShieldFactory factory;

    private ShieldFactory() {

    }

    public static ShieldFactory getInstance() {
        if (factory == null)
            factory = new ShieldFactory();
        return factory;
    }

    public static Atom getShieldedAtom(Atom atom, ShieldType shieldType) {
        return switch (shieldType) {
            case ETA -> new Eta(atom);
            case LOTA -> new Lota(atom);
            case ZETA -> new Zeta(atom);
            case THETA -> new Theta(atom);
            default -> null;
        };
    }
}
