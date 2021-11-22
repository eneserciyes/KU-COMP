package tr.com.teamfaster.domain.managers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tr.com.teamfaster.domain.listeners.IViewChangeListener;
import tr.com.teamfaster.domain.utils.BlendInfo;
import tr.com.teamfaster.domain.utils.EntityType;
import tr.com.teamfaster.domain.utils.Position;
import tr.com.teamfaster.domain.utils.ShieldType;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BlenderTest {

    IViewChangeListener mockViewChangeListener;
    AtomShooterManager atomShooterManager;
    Blender blender;
    BlendInfo blendInfo;

    @BeforeEach
    void setUp() {
        mockViewChangeListener = new IViewChangeListener() {
            @Override
            public void onCreateAtom(EntityType type, Position position) {

            }

            @Override
            public void onCreateBlocker(EntityType type, Position position) {

            }

            @Override
            public void onCreateMolecule(EntityType type, Position position) {

            }

            @Override
            public void onCreatePowerup(EntityType type, Position position, boolean status) {

            }

            @Override
            public void onCreateAtomShooter() {

            }

            @Override
            public void onChangeShooterBarrelView(EntityType entityType) {

            }

            @Override
            public void onCreateInitialObjects() {

            }

            @Override
            public void onCreateAtomStatisticsPanel() {

            }

            @Override
            public void onCreatePowerupsPanel() {

            }

            @Override
            public void onAtomStatisticsUpdate(Map<EntityType, Integer> numbers) {

            }

            @Override
            public void onShieldNumberUpdate(Map<ShieldType, Integer> numbers) {

            }

            @Override
            public void onTimeUpdate(long time) {

            }

            @Override
            public void onHealthScoreUpdate(int health, double score) {

            }

            @Override
            public BlendInfo onShowBlendInputDialog() {
                return blendInfo;
            }

            @Override
            public void onPowerupCounts(EntityType type, int i) {

            }

            @Override
            public void onChangeShooterBarrelViewPowerup(EntityType value) {

            }
        };
        atomShooterManager = AtomShooterManager.getInstance();
        blender = Blender.getInstance();
        blender.subscribeViewManager(mockViewChangeListener);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void blendAtomNullBlendInfo() {
        /*
        * BLACKBOX
        * Null blend info test
        * */
        blendInfo = null;
        Map<EntityType, Integer> previousAtomCounts = atomShooterManager.getAtomCounts();
        blender.blendAtom();
        assertEquals(previousAtomCounts, atomShooterManager.getAtomCounts());
    }

    @Test
    void blendAtomInputRankSmallerThanOutputRank() {
        /*
        * BLACKBOX
        * Input rank smaller than output blending test
        * */
        blendInfo = new BlendInfo(1,3);
        int inputAtomCount = atomShooterManager.getAtomCounts().get(EntityType.ALPHA);
        int outputAtomCount = atomShooterManager.getAtomCounts().get(EntityType.GAMMA);

        blender.blendAtom();
        int inputAtomCountAfterOperation = atomShooterManager.getAtomCounts().get(EntityType.ALPHA);
        int outputAtomCountAfterOperation = atomShooterManager.getAtomCounts().get(EntityType.GAMMA);
        assertEquals(inputAtomCountAfterOperation, inputAtomCount-3);
        assertEquals(outputAtomCountAfterOperation,outputAtomCount+1);

    }

    @Test
    void blendAtomInputRankHigherThanOutputRank() {
        /*
         * BLACKBOX
         * Input rank greater than output breaking test
         * */
        blendInfo = new BlendInfo(3,1);
        int inputAtomCount = atomShooterManager.getAtomCounts().get(EntityType.GAMMA);
        int outputAtomCount = atomShooterManager.getAtomCounts().get(EntityType.ALPHA);

        blender.blendAtom();
        int inputAtomCountAfterOperation = atomShooterManager.getAtomCounts().get(EntityType.GAMMA);
        int outputAtomCountAfterOperation = atomShooterManager.getAtomCounts().get(EntityType.ALPHA);
        assertEquals(inputAtomCountAfterOperation, inputAtomCount-1);
        assertEquals(outputAtomCountAfterOperation,outputAtomCount+3);
    }

    @Test
    void blendAtomInputRankEqualToOutputRank() {
        /*
         * BLACKBOX
         * Input rank equal to output no change test
         * */
        blendInfo = new BlendInfo(1,1);
        int inputAtomCount = atomShooterManager.getAtomCounts().get(EntityType.ALPHA);
        int outputAtomCount = atomShooterManager.getAtomCounts().get(EntityType.ALPHA);

        blender.blendAtom();
        int inputAtomCountAfterOperation = atomShooterManager.getAtomCounts().get(EntityType.ALPHA);
        int outputAtomCountAfterOperation = atomShooterManager.getAtomCounts().get(EntityType.ALPHA);
        assertEquals(inputAtomCountAfterOperation, inputAtomCount);
        assertEquals(outputAtomCountAfterOperation,outputAtomCount);
    }

    @Test
    void blendAtomInvalidBlendOperation() {
        /*
        * BLACKBOX
        * Not enough atom to blend, no change test
        * */
        blendInfo = new BlendInfo(1,3);
        atomShooterManager.getAtomCounts().put(EntityType.ALPHA, 1);
        Map<EntityType, Integer> previousAtomCounts = atomShooterManager.getAtomCounts();

        blender.blendAtom();
        assertEquals(previousAtomCounts, atomShooterManager.getAtomCounts());
    }
}