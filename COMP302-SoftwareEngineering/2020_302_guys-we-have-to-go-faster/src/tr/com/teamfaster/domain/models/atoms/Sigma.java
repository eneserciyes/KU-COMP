package tr.com.teamfaster.domain.models.atoms;

import tr.com.teamfaster.domain.utils.EntityType;
import tr.com.teamfaster.domain.utils.GameSettings;
import tr.com.teamfaster.domain.utils.Position;
import tr.com.teamfaster.domain.utils.RandomUtils;

public class Sigma extends Atom {

    public Sigma(Position position, float rotation) {
        super(position, rotation, EntityType.SIGMA);
        chooseNeutronNumber();
        this.setProtons(GameSettings.getSigmaProtons());
    }

    @Override
    public void chooseNeutronNumber() {
        int[] neutrons = GameSettings.getSigmaNeutrons();
        this.setNeutrons(RandomUtils.getRandomIndex(neutrons.length));
    }

    @Override
    public double getEfficiency() {
        return (1 + GameSettings.getSigmaStabilityConstant()) / 2 + -(Math.abs(this.getNeutrons() - this.getProtons())) / this.getProtons();
    }
}
