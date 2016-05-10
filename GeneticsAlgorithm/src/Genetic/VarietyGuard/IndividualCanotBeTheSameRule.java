package Genetic.VarietyGuard;

import Genetic.Individual;

import java.util.List;

/**
 * Created by sanczo on 2016-05-05.
 */
public class IndividualCaNotBeTheSameRule implements VarietyGuard {
    @Override
    public boolean isSatisfiedBy(Individual individual, List<Individual> population) {

        return population.stream().allMatch(individualAlreadyInPopulation
                -> individualAlreadyInPopulation.equals(individual) == false);
    }
}
