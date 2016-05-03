package Mutatet;

import Genetic.AlgorithmParameters;
import Genetic.Individual;
import Helpers.RandomHelper;

/**
 * Created by sanczo on 2016-03-12.
 */
public class OneStepMutate implements Mutating {

    private int maxValue = -1;

    private int minValue = -1;



    @Override
    public int mutate(int orginalGen) {
        if (maxValue == -1)
            throw new IllegalArgumentException("Not set maxValue for OneStepMutate");
        if (minValue == -1)
            throw new IllegalArgumentException("Not set minValue for OneStepMutate");

        int genAfterMutations;

        int step;

        int addOrSub = RandomHelper.giveRandomNumberFromZeroTo_N_Exclusive(2);

        if(addOrSub == 0)
            step = 1;
        else step = -1;

        genAfterMutations = orginalGen + step;

        if (genAfterMutations < minValue)
            genAfterMutations = minValue;

        if (genAfterMutations > maxValue)
            genAfterMutations = maxValue;


        return genAfterMutations;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }
}
