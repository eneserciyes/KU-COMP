package tr.com.teamfaster.domain.models.shields;

import tr.com.teamfaster.domain.models.atoms.Atom;
import tr.com.teamfaster.domain.utils.GameSettings;
import tr.com.teamfaster.domain.utils.ShieldType;

import java.util.List;

public class Lota extends ComponentAtomShield {
    public Lota(Atom atom) {
        super(atom);
    }

    @Override
    public double getEfficiency() {
        return (1 - getAtom().getEfficiency()) * GameSettings.getLotaEfficiencyBoost();
    }

    @Override
    public float getSpeed() {
        return getAtom().getSpeed() * GameSettings.getLotaSpeedRatio();
    }

    @Override
    public List<String> getShields() {
        List<String> shields = getAtom().getShields();
        shields.add(ShieldType.LOTA.name());
        return shields;
    }
}
