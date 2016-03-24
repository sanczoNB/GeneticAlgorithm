package Choosers;

import Comperators.SortByFitnessComparator;
import Genetic.Colloring;
import Helpers.RandomHelper;

import java.util.List;
import java.util.Arrays;

/**
 * Created by sanczo on 2016-03-12.
 */
public class Tournament implements Chooser {

    private int participantNumber;


    public Tournament(int participantNumber)
    {
        this.participantNumber = participantNumber;
    }


    @Override
    public Colloring choose(List<Colloring> population) {
        Colloring[] participants = new Colloring[participantNumber];

        for(int i = 0; i < participants.length; i++)
            participants[i] = population.get(RandomHelper.giveRandomNumberFromZeroTo_N_Exclusive(population.size()));
        Arrays.sort(participants,new SortByFitnessComparator());

        return participants[0];
    }
}
