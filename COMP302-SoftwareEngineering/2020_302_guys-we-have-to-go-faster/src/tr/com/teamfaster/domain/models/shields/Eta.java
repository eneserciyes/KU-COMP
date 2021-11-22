package tr.com.teamfaster.domain.models.shields;

import tr.com.teamfaster.domain.models.atoms.Atom;
import tr.com.teamfaster.domain.utils.GameSettings;
import tr.com.teamfaster.domain.utils.ShieldType;

import java.util.List;

public class Eta extends ComponentAtomShield {
    public Eta(Atom atom) {
        super(atom);
    }

    @Override
    public double getEfficiency() {
        if (this.getNeutrons() != this.getProtons())
            return (1 - getAtom().getEfficiency()) * Math.abs(this.getNeutrons() - this.getProtons() / this.getProtons());
        else return (1 - this.getEfficiency()) * GameSettings.getEtaEfficiencyBoost();
    }

    @Override
    public List<String> getShields() {
        List<String> shields = getAtom().getShields();
        shields.add(ShieldType.ETA.name());
        return shields;
    }

    @Override
    public float getSpeed() {
        return getAtom().getSpeed() * GameSettings.getEtaSpeedRatio();
    }
}
