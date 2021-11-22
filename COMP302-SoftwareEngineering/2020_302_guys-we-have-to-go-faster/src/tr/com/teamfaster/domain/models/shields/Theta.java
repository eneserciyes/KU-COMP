package tr.com.teamfaster.domain.models.shields;

import tr.com.teamfaster.domain.models.atoms.Atom;
import tr.com.teamfaster.domain.utils.GameSettings;
import tr.com.teamfaster.domain.utils.ShieldType;

import java.util.List;
import java.util.Random;

public class Theta extends ComponentAtomShield {
    public Theta(Atom atom) {
        super(atom);
    }

    @Override
    public float getSpeed() {
        return getAtom().getSpeed() * GameSettings.getThetaSpeedRatio();
    }

    @Override
    public double getEfficiency() {
        Random rand = new Random();
        double boost = GameSettings.getThetaEfficiencyBoostMin() + ((GameSettings.getThetaEfficiencyBoostMax() - GameSettings.getThetaEfficiencyBoostMin()) * rand.nextDouble());
        return (1 - getAtom().getEfficiency()) * boost;
    }

    @Override
    public List<String> getShields() {
        List<String> shields = getAtom().getShields();
        shields.add(ShieldType.THETA.name());
        return shields;
    }
}
