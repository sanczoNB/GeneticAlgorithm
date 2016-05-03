package Initialization;

import Genetic.Individual;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sanczo on 2016-04-26.
 */
public class PermutationInitializer implements Initializer {

    @Override
    public Individual Init() {
        Individual individual = new Individual();

        List<Integer> permutation = new LinkedList<>();
        for (int i = 0; i<Individual.getNumberOfGens(); i++ )
        {
            permutation.add(i+1);
        }

        Collections.shuffle(permutation);

        for (int i = 0; i < Individual.getNumberOfGens(); i++)
        {
            individual.setGenAtPosition(i, permutation.get(i));
        }

        return null;
    }
}
