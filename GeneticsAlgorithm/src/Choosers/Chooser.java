package Choosers;

import Genetic.Individual;

import java.util.List;

/**
 * Created by sanczo on 2016-03-10.
 */
public interface Chooser {

        public Individual choose(List<Individual> population);

}
