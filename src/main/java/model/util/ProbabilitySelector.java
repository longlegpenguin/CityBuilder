package model.util;

import java.util.Random;

/**
 * This is a utility to make a decision: YES or NO, according to the given probability (0-1)
 */
public class ProbabilitySelector {
    private static Random random = new Random();

    /**
     * Makes a decision: YES or NO, according to the given probability (0-1)
     * @param prob probability of YES.
     * @return True if decision is yes, otherwise false.
     */
    public static boolean decision(double prob) {
        if (prob > 1 || prob < 0) {
            throw new IllegalArgumentException("Probability out of bounds");
        }
        int luck =  random.nextInt(100);
        return luck < prob * 100;
    }
}
