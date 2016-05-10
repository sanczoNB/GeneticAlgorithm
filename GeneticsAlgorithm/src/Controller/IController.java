package Controller;

import Genetic.Population;

/**
 * Created by sanczo on 2016-05-05.
 */
public interface IController {

    void nextGeneration(Population population);

    void callForNextGeneration();

    void callForZeroGeneration();
}
