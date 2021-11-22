package tr.com.teamfaster.domain.utils;

import java.util.Random;

public class RandomUtils {
    private static final Random random = new Random();

    public static int getRandomIndex(int size) {
        return random.nextInt(size);
    }
}
