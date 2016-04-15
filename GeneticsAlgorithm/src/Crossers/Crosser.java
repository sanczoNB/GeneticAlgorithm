package Crossers;

import Genetic.Individual;
import org.javatuples.Pair;

/**
 * Created by sanczo on 2016-03-12.
 */
public interface Crosser {

    Pair<Individual, Individual> cross(Individual firstParent, Individual secondParent);
}
