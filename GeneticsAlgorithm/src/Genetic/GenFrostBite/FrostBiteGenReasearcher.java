package Genetic.GenFrostBite;

import Genetic.Individual;
import Genetic.Population;

import java.util.List;

/**
 * Created by sanczo on 2016-05-06.
 */
public class FrostBiteGenReasearcher implements IGenFrostBite {
    @Override
    public boolean[] assignGenFrostBite(List<Individual> population) {

        boolean[] frostbite = new boolean[Individual.getNumberOfGens()];
        if(population != null) {


            for (int i = 0; i < frostbite.length; i++) {
                boolean onlyOneValueOnThisPosition = true;
                int genOfFirstIndividualONiPosition = population.get(0).getGenAtPosition(i);
                int j = 1;
                while (j < population.size() && onlyOneValueOnThisPosition) {
                    onlyOneValueOnThisPosition = genOfFirstIndividualONiPosition
                            == population.get(j).getGenAtPosition(i);
                    j++;
                }
                frostbite[i] = onlyOneValueOnThisPosition;
            }
        }else {
            for (int i = 0; i < frostbite.length; i++){
                frostbite[i] = false;
            }
        }

        return frostbite;

    }
}
