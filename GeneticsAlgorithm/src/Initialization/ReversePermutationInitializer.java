package Initialization;

import Genetic.Individual;
import Helpers.RandomHelper;

/**
 * Created by sanczo on 2016-04-26.
 */
public class ReversePermutationInitializer implements Initializer {
    @Override
    public Individual Init() {

        Individual individual = new Individual();

        for (int i = 0; i < Individual.getNumberOfGens(); i++){

            int gen = RandomHelper.giveRandomIntegerFromGivenInterval(0,Individual.getNumberOfGens() - (i+1));
            individual.setGenAtPosition(i, gen);
        }


        return individual;
    }
}
