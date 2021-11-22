package objects.actors;

import java.util.Random;


/* Direction enum provides the directions for cat and ghosts.
It also includes a randomDirection method which Casper uses.
*/
public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    static Random random = new Random();

    public static Direction randomDirection() {
        return values()[random.nextInt(4)];
    }
}
