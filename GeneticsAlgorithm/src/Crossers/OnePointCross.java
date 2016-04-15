package Crossers;

import Choosers.ChildSelector;
import Genetic.Individual;
import Helpers.RandomHelper;
import org.javatuples.Pair;

/**
 * Created by sanczo on 2016-03-12.
 */
public class OnePointCross implements Crosser {


    private ChildSelector childSelector;

    public OnePointCross() {
    }

    @Override
    public Pair<Individual, Individual> cross(Individual firstParent, Individual secondParent) {


        int pointOfCut = RandomHelper.giveRandomNumberFromZeroTo_N_Exclusive(Individual.getNumberOfGens() - 1) + 1;

        Individual firstChild = new Individual();

        Individual secondChild = new Individual();

        for (int i =0; i < pointOfCut; i++)
        {
            firstChild.assignColor(i, firstParent.getColorOnPosition(i));
            secondChild.assignColor(i, secondParent.getColorOnPosition(i));
        }

        for (int i = pointOfCut; i < Individual.getNumberOfGens(); i++)
        {
            firstChild.assignColor(i, secondParent.getColorOnPosition(i));
            secondChild.assignColor(i, firstParent.getColorOnPosition(i));
        }


        return new Pair<>(firstChild, secondChild);
    }
}
