package Choosers;

import Comperators.SortByFitnessComparator;
import Genetic.AlgorithmParameters;
import Genetic.Individual;
import Helpers.RandomHelper;

import java.util.List;
import java.util.Arrays;

/**
 * Created by sanczo on 2016-03-12.
 */
public class Tournament implements Chooser {


    public Tournament()
    {

    }


    @Override
    public Individual choose(List<Individual> population) {
        Individual[] participants = new Individual[AlgorithmParameters.getInstance().getTournamentSize()];
        for(int i = 0; i < participants.length; i++)
            participants[i] = population.get(RandomHelper.giveRandomNumberFromZeroTo_N_Exclusive(population.size()));
        Arrays.sort(participants,new SortByFitnessComparator());

        return participants[0];
    }
}
