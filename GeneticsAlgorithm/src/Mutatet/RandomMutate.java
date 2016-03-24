package Mutatet;

import Genetic.Colloring;
import Helpers.RandomHelper;

/**
 * Created by sanczo on 2016-03-12.
 */
public class RandomMutate implements Mutating {
    @Override
    public int mutate(int oldColor) {
        return RandomHelper.giveRandomNumberFromZeroTo_N_Exclusive(Colloring.colorsNumber);
    }
}
