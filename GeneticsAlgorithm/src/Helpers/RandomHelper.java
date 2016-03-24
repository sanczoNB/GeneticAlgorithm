package Helpers;

import java.util.Random;

/**
 * Created by sanczo on 2016-03-10.
 */
public class RandomHelper {

    private static Random random = new Random();

    public static int giveRandomNumberFromZeroTo_N_Exclusive(int n)
    {
        return random.nextInt(n);
    }

    public static boolean IfItHappenWithAskedProbability(double probability)
    {
        return Math.random() < probability;

    }
}
