package Comperators;

import Genetic.Individual;
import java.util.Comparator;

/**
 * Created by sanczo on 2016-03-12.
 */
public class SortByFitnessComparator implements Comparator<Individual> {
    @Override
    public int compare(Individual o1, Individual o2) {
        return Double.compare(o1.getFitness(), o2.getFitness());
    }
}
