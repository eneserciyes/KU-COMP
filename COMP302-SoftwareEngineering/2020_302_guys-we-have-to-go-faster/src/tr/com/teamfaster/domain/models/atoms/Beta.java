package tr.com.teamfaster.domain.models.atoms;

import tr.com.teamfaster.domain.utils.EntityType;
import tr.com.teamfaster.domain.utils.GameSettings;
import tr.com.teamfaster.domain.utils.Position;
import tr.com.teamfaster.domain.utils.RandomUtils;

public class Beta extends Atom {

    public Beta(Position position, float rotation) {
        super(position, rotation, EntityType.BETA);
        chooseNeutronNumber();
        this.setProtons(GameSettings.getBetaProtons());
    }

    @Override
    public void chooseNeutronNumber() {
        int[] neutrons = GameSettings.getBetaNeutrons();
        this.setNeutrons(RandomUtils.getRandomIndex(neutrons.length));
    }

    @Override
    public double getEfficiency() {
        return GameSettings.getBetaStabilityConstant() - 0.5 * (Math.abs(this.getNeutrons() - this.getProtons())) / this.getProtons();
    }
}
