package tr.com.teamfaster.domain.managers;

import tr.com.teamfaster.domain.errors.InvalidBlenderOperation;
import tr.com.teamfaster.domain.listeners.IViewChangeListener;
import tr.com.teamfaster.domain.utils.BlendInfo;
import tr.com.teamfaster.domain.utils.EntityType;

import java.util.Map;

/**
 * A singleton class that handles the Blender operations
 */
public class Blender {
    public static Blender instance;

    private IViewChangeListener viewChangeListener;

    private Blender() {

    }

    public static Blender getInstance() {
        if (instance == null) instance = new Blender();
        return instance;
    }

    /**
     * Blender main method
     *
     * @effects Publishes blend input dialog event, gets rules according to BlendInfo, validates operation and increments/
     * decrements the atoms in AtomShooterManager
     * @requires blendInfo input and output is between 1 and EntityType values number or null&& getRules do not return null &&
     * viewChangeListener is subscribed
     * @modifies AtomShooterManager atomInventory and AtomShooter atomCounts
     */

    public void blendAtom() {
        BlendInfo blendInfo = viewChangeListener.onShowBlendInputDialog();
        if (blendInfo == null)
            return;
        EntityType input = EntityType.values()[blendInfo.getInput() - 1];
        EntityType output = EntityType.values()[blendInfo.getOutput() - 1];

        Rule rule = getRules(input, output);
        try {
            validateOperation(input, rule, AtomShooterManager.getInstance().getAtomCounts());
            AtomShooterManager.getInstance().decreaseAtom(input, rule.inputCount);
            AtomShooterManager.getInstance().incrementAtom(output, rule.outputCount);

            viewChangeListener.onAtomStatisticsUpdate(AtomShooterManager.getInstance().getAtomCounts());
        } catch (InvalidBlenderOperation e) {
        }

    }

    /**
     * Subscribes {@link IViewChangeListener} to this class.
     *
     * @param viewChangeListener: Observer that listens to view changes
     */
    public void subscribeViewManager(IViewChangeListener viewChangeListener) {
        this.viewChangeListener = viewChangeListener;
    }

    /**
     * The method uses inner class {@link Rule} to specify blend/break action. If source atom rank is greater than target atom rank,
     * rule basically indicates a break operation.
     *
     * @param input:  The rank for the source atom to be broken/blended. The rank must be between 1 and number of {@link EntityType} value count.
     *                This invariant is always true as this is validated in the UI side.
     * @param output: The rank for the target atom. The rank must be between 1 and number of {@link EntityType} value count.
     *                This invariant is always true as this is validated in the UI side.
     * @return : The rule that specifies how much atom should be decremented from source type and how much should be incremented in target type.
     */
    private Rule getRules(EntityType input, EntityType output) {
        // Give the required input atom number for the blend operation
        if (input.getRank() < output.getRank()) return new Rule((output.getRank() - input.getRank()) + 1, 1);
        else if (input.getRank() > output.getRank()) return new Rule(1, input.getRank() - output.getRank() + 1);
        else return new Rule(1, 1);
    }

    /**
     * @param input: source type
     * @param rule:  rule that specifies how much atom is needed in source type
     * @param count: current atom inventory counts
     * @throws InvalidBlenderOperation: throws exception if there is not enough atom in the source type
     */
    private void validateOperation(EntityType input, Rule rule, Map<EntityType, Integer> count) throws InvalidBlenderOperation {
        if (count.get(input) < rule.getInputCount()) throw new InvalidBlenderOperation();
    }

    /**
     * A util pair inner class in {@link Blender} that specifies how much source atom is required and how much target
     * atom is generated in blend/break operation.
     */
    private static class Rule {
        private final int inputCount;
        private final int outputCount;

        public Rule(int inputCount, int outputCount) {
            this.inputCount = inputCount;
            this.outputCount = outputCount;
        }

        public int getInputCount() {
            return inputCount;
        }

        public int getOutputCount() {
            return outputCount;
        }
    }
}
