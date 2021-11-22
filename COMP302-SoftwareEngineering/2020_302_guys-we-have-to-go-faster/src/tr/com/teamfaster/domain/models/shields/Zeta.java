package tr.com.teamfaster.domain.models.shields;

import tr.com.teamfaster.domain.models.atoms.Atom;
import tr.com.teamfaster.domain.utils.GameSettings;
import tr.com.teamfaster.domain.utils.ShieldType;

import java.util.List;

public class Zeta extends ComponentAtomShield {
    public Zeta(Atom atom) {
        super(atom);
    }

    @Override
    public float getSpeed() {
        return getAtom().getSpeed() * GameSettings.getZetaSpeedRatio();
    }

    @Override
    public double getEfficiency() {
        if (this.getProtons() == this.getNeutrons()) {
            return (1 - getAtom().getEfficiency()) * GameSettings.getZetaEfficiencyBoost();
        }
        return getAtom().getEfficiency();
    }

    @Override
    public List<String> getShields() {
        List<String> shields = getAtom().getShields();
        shields.add(ShieldType.ZETA.name());
        return shields;
    }
}
