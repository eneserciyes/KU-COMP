package tr.com.teamfaster.domain.managers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tr.com.teamfaster.GameInfo;
import tr.com.teamfaster.domain.models.atoms.Atom;
import tr.com.teamfaster.domain.utils.*;
import tr.com.teamfaster.ui.managers.ViewChangeListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class changeAtomTypeTest {

    AtomShooterManager shooterManager;
    EntityType atomType;
    ViewChangeListener mockViewChangeListener;
    GameInfo info;

    @BeforeEach
    void setUp() {
        mockViewChangeListener = new ViewChangeListener() {
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
                return null;
            }
        };
        shooterManager = AtomShooterManager.getInstance();
        atomType = EntityType.ALPHA;
        shooterManager.subscribeViewManager(mockViewChangeListener);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void shooterIsNull() {
        /*
         * BLACKBOX
         * Null shooter manager test
         * */
        assertThrows(NullPointerException.class,
                ()->{
                    shooterManager=null;
                    changeAtomType();
               });
    }

    @Test
    public void atomInventoryIsNull() {
        /*
         * BLACKBOX
         * Null atom inventory test
         * */
        assertThrows(NullPointerException.class,
                ()->{
                    shooterManager = AtomShooterManager.getInstance();
                    shooterManager.setAtomInventory(null);
                    changeAtomType();
                });
    }

    @Test
    public void atomInventorySizeIsZero() {
        /*
         * BLACKBOX
         * Size=0 atom inventory test
         * */
        shooterManager.getAtomInventory().clear();
        changeAtomType();
        assertNull(shooterManager.getCurrentAtomType());
    }

    @Test
    public void changeAtomType() {

        shooterManager.changeAtomType();
      //  int randomIndex = RandomUtils.getRandomIndex(shooterManager.getAtomInventory().size());
     //   Atom newAtom = shooterManager.getAtomInventory().get(randomIndex);
        //   EntityType newType = newAtom.getType();
        EntityType newType = shooterManager.getCurrentAtomType();
        assertNotEquals(atomType,newType);
    }

// tek atom type olsun farkli bir sey geliyor mu
//   --> gamesettingsden
    @Test
    public void oneTypeChangeAtomType(){
        Map<EntityType,Integer> counts  = new HashMap<>();
        counts.put(EntityType.ALPHA,5);
        counts.put(EntityType.BETA,0);
        counts.put(EntityType.GAMMA,0);
        counts.put(EntityType.SIGMA,0);
        List<Atom> atoms = new ArrayList<>();

     //   shooterManager = AtomShooterManager.getInstance();
        shooterManager.setAtomCounts(counts);

        shooterManager.setCurrentAtomType(EntityType.ALPHA);
        System.out.println(shooterManager.getAtomCounts());

        int alphaCount = 0;
        System.out.println(shooterManager.getCurrentAtomType());
        for(int i=0 ; i<100 ; i++){
            shooterManager.changeAtomType();
            EntityType type = shooterManager.getCurrentAtomType();
            System.out.println(type);
            if(type == EntityType.ALPHA) alphaCount ++;
         //   System.out.println(alphaCount);
        //    System.out.println(alphaCount);
         //   alphaCount = 100;
        }
        assertEquals(100,alphaCount);
    }
}
