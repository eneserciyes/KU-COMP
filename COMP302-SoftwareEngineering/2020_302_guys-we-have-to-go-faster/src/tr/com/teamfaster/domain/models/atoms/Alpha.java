package tr.com.teamfaster.domain.models.atoms;

import tr.com.teamfaster.domain.utils.EntityType;
import tr.com.teamfaster.domain.utils.GameSettings;
import tr.com.teamfaster.domain.utils.Position;
import tr.com.teamfaster.domain.utils.RandomUtils;

public class Alpha extends Atom {

    public Alpha(Position position, float rotation) {
        super(position, rotation, EntityType.ALPHA);
        chooseNeutronNumber();
        this.setProtons(GameSettings.getAlphaProtons());
    }

    @Override
    public void chooseNeutronNumber() {
        int[] neutrons = GameSettings.getAlphaNeutrons();
        this.setNeutrons(RandomUtils.getRandomIndex(neutrons.length));
    }

    @Override
    public double getEfficiency() {
        return (1 - Math.abs(this.getNeutrons() - this.getProtons())) / this.getProtons() * GameSettings.getAlphaStabilityConstant();
    }
}
