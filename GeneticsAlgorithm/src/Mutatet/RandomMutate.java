package Mutatet;

import Genetic.AlgorithmParameters;
import Genetic.Individual;
import Helpers.RandomHelper;

/**
 * Created by sanczo on 2016-03-12.
 */
public class RandomMutate implements Mutating {
    @Override
    public int mutate(int oldColor) {
        return RandomHelper.giveRandomNumberFromZeroTo_N_Exclusive(AlgorithmParameters.getInstance().getMaxUsedColor()/2) + 1;
    }
}
