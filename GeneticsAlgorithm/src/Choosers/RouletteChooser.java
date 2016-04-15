package Choosers;

import Genetic.Individual;

import java.util.List;

/**
 * Created by sanczo on 2016-03-10.
 */
public class RouletteChooser implements Chooser {
    @Override
    public Individual choose(List<Individual> population) {

        double rouleteChosee = Math.random();

        int i = 0;
        while (rouleteChosee > population.get(i).getRouletteRating())
            i++;
        return population.get(i);
    }
}
