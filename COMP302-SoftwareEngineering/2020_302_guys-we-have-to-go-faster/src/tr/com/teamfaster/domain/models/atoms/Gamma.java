package tr.com.teamfaster.domain.models.atoms;

import tr.com.teamfaster.domain.utils.EntityType;
import tr.com.teamfaster.domain.utils.GameSettings;
import tr.com.teamfaster.domain.utils.Position;
import tr.com.teamfaster.domain.utils.RandomUtils;

public class Gamma extends Atom {

    public Gamma(Position position, float rotation) {
        super(position, rotation, EntityType.GAMMA);
        chooseNeutronNumber();
        this.setProtons(GameSettings.getGammaProtons());
    }

    @Override
    public void chooseNeutronNumber() {
        int[] neutrons = GameSettings.getGammaNeutrons();
        this.setNeutrons(RandomUtils.getRandomIndex(neutrons.length));
    }

    @Override
    public double getEfficiency() {
        return GameSettings.getGammaStabilityConstant() + (Math.abs(this.getNeutrons() - this.getProtons())) / (2 * this.getProtons());
    }
}
