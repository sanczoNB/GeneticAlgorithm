package Comperators;

import Genetic.Colloring;
import java.util.Comparator;

/**
 * Created by sanczo on 2016-03-12.
 */
public class SortByFitnessComparator implements Comparator<Colloring> {
    @Override
    public int compare(Colloring o1, Colloring o2) {
        return Integer.compare(o1.getFitness(), o2.getFitness());
    }
}
