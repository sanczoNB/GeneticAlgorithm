package Initialization;

import Genetic.AlgorithmParameters;
import Genetic.Individual;
import Helpers.RandomHelper;

/**
 * Created by sanczo on 2016-03-23.
 */
public class RandomInitializer implements Initializer {

    @Override
    public Individual Init() {

        Individual individual = new Individual();
        for(int i =0; i < individual.getNumberOfGens(); i++)
        {
            int gen = RandomHelper.giveRandomNumberFromZeroTo_N_Exclusive(AlgorithmParameters.getInstance().getMaxUsedColor())+1;
            individual.setColorAtPosition(i, gen);
        }

        return individual;
    }
}
