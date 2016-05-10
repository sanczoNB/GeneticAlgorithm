package Genetic.VarietyGuard;

import Genetic.Individual;

import java.util.List;

/**
 * Created by sanczo on 2016-05-05.
 */
public interface VarietyGuard {

   boolean isSatisfiedBy(Individual individual, List<Individual> population);

}
