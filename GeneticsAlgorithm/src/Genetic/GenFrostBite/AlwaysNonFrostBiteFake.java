package Genetic.GenFrostBite;

import Genetic.Individual;
import Genetic.Population;

import java.util.List;

/**
 * Created by sanczo on 2016-05-06.
 */
public class AlwaysNonFrostBiteFake implements IGenFrostBite {
    @Override
    public boolean[] assignGenFrostBite(List<Individual> population) {

        boolean[] fakeGenFrostBite = new boolean[Individual.getNumberOfGens()];

        for (int i = 0; i < fakeGenFrostBite.length; i++)
        {
            fakeGenFrostBite[i] = false;
        }

        return fakeGenFrostBite;
    }
}
