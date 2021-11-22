package tr.com.teamfaster.domain.listeners;

import tr.com.teamfaster.domain.utils.BlendInfo;
import tr.com.teamfaster.domain.utils.EntityType;
import tr.com.teamfaster.domain.utils.Position;
import tr.com.teamfaster.domain.utils.ShieldType;

import java.util.Map;

public interface IViewChangeListener {

    /**
     * The view of the atom which is just created added to frame.
     *
     * @param type:     type of the atom view
     * @param position: position of the atom view
     */
    void onCreateAtom(EntityType type, Position position);

    /**
     * The view of the blocker which is just created added to frame.
     *
     * @param type:     type of the blocker view
     * @param position: position of the atom view
     */
    void onCreateBlocker(EntityType type, Position position);

    /**
     * The view of the molecule which is just created added to frame.
     *
     * @param type:     type of the molecule view
     * @param position: position of the molecule view
     */
    void onCreateMolecule(EntityType type, Position position);

    /**
     * The view of the powerup which is just created added to frame.
     *
     * @param type:     type of the powerup view
     * @param position: position of the powerup view
     */
    void onCreatePowerup(EntityType type, Position position, boolean status);

    /**
     * While atomShooter initialized the view of it added to GameFrame.
     */
    void onCreateAtomShooter();

    /**
     * The barrel view of the atomShooter is set to atom.
     *
     * @param entityType: type of the atom
     */
    void onChangeShooterBarrelView(EntityType entityType);

    /**
     * When the game started the panel views and atomShooter view added to GameFrame.
     */
    void onCreateInitialObjects();

    /**
     * The panel is added to frame to show remaining atom counts.
     */
    void onCreateAtomStatisticsPanel();

    /**
     * The panel is added to frame to show collected atom counts.
     */
    void onCreatePowerupsPanel();

    /**
     * When atom is shot or blended the statistics updated.
     *
     * @param numbers: new counts of the atoms
     */
    void onAtomStatisticsUpdate(Map<EntityType, Integer> numbers);

    /**
     * When shields are used remaining shields are updated.
     *
     * @param numbers: new counts of the shields
     */
    void onShieldNumberUpdate(Map<ShieldType, Integer> numbers);

    /**
     * Update the time label.
     *
     * @param time: passed time while playing the game
     */
    void onTimeUpdate(long time);

    /**
     * Because of a collision the health and score labels are updated.
     *
     * @param health: new health point
     * @param score:  new score
     */
    void onHealthScoreUpdate(int health, double score);

    /**
     * When player wants to blend an atom, player specifies the rank of the atom.
     *
     * @return: BlendInfo which includes the source and target atom ranks.
     */
    BlendInfo onShowBlendInputDialog();

    /**
     * When a powerup collected pr shot the bumber of that type updated.
     *
     * @param type: type of the shot or collected powerup
     * @param i:    new count of the powerup
     */
    void onPowerupCounts(EntityType type, int i);

    /**
     * When player selected the powerup icon, the powerup is set as barrel.
     *
     * @param value: the type of the chosen powerup
     */
    void onChangeShooterBarrelViewPowerup(EntityType value);
}
