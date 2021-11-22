package tr.com.teamfaster.domain.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoundsTest {

    @org.junit.jupiter.api.Test
    void isOverlapping() {
        /* *** BLACK BOX *** */
        // Same objects test
        Bounds bounds = new Bounds(new Position(1, 2), new Position(3, 5));
        assertTrue(bounds.isOverlapping(bounds));

        // Overlapping bounds
        Bounds bounds2 = new Bounds(new Position(2, 2), new Position(5, 5));
        assertTrue(bounds.isOverlapping(bounds2));

        // Non overlapping bounds
        Bounds bounds3 = new Bounds(new Position(3, 3), new Position(8, 8));
        assertFalse(bounds.isOverlapping(bounds3));

        /* *** GLASS BOX *** */
        // only the first if holds
        Bounds bounds4 = new Bounds(new Position(1, 3), new Position(3, 8));
        assertFalse(bounds3.isOverlapping(bounds4));

        // only the second if holds
        Bounds bounds5 = new Bounds(new Position(3, 9), new Position(8, 2));
        assertFalse(bounds3.isOverlapping(bounds5));

    }

}