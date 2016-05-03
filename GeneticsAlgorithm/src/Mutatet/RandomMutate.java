package Mutatet;

import Genetic.AlgorithmParameters;
import Genetic.Individual;
import Helpers.RandomHelper;

/**
 * Created by sanczo on 2016-03-12.
 */
public class RandomMutate implements Mutating {

    private int maxValue;

    private int minValue;

    @Override
    public int mutate(int oldColor) {
        return RandomHelper.giveRandomIntegerFromGivenInterval(minValue, maxValue);
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }
}
