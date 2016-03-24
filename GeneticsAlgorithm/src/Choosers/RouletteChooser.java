package Choosers;

import Genetic.Colloring;
import java.util.List;

/**
 * Created by sanczo on 2016-03-10.
 */
public class RouletteChooser implements Chooser {
    @Override
    public Colloring choose(List<Colloring> population) {

        double rouleteChosee = Math.random();

        int i = 0;
        while (rouleteChosee > population.get(i).getRouletteRating())
            i++;
        return population.get(i);
    }
}
