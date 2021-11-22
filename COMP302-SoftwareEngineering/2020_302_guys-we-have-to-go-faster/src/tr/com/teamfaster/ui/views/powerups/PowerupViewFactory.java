package tr.com.teamfaster.ui.views.powerups;

import tr.com.teamfaster.domain.utils.EntityType;

/**
 * Creates powerup views from specified type by powerup icon factory.
 */
public class PowerupViewFactory {
    private static PowerupViewFactory instance;

    private PowerupViewFactory() {

    }

    public static PowerupViewFactory getInstance() {
        if (instance == null) instance = new PowerupViewFactory();
        return instance;
    }

    public PowerupView createPowerupView(EntityType entityType) {
        return switch (entityType) {
            case ALPHA -> new PowerupView(PowerUpIconFactory.getInstance().createIcon(EntityType.ALPHA));
            case BETA -> new PowerupView(PowerUpIconFactory.getInstance().createIcon(EntityType.BETA));
            case GAMMA -> new PowerupView(PowerUpIconFactory.getInstance().createIcon(EntityType.GAMMA));
            case SIGMA -> new PowerupView(PowerUpIconFactory.getInstance().createIcon(EntityType.SIGMA));
        };

    }

}
