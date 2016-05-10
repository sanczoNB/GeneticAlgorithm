package Genetic.Observer;

import Genetic.Population;

/**
 * Created by sanczo on 2016-05-09.
 */
public interface NextGenerationOccurredObserver {

    void nextGeneration(Population population);

}
