package Crossers;

import Choosers.ChildSelector;
import Genetic.Colloring;
import Helpers.RandomHelper;
import org.javatuples.Pair;

/**
 * Created by sanczo on 2016-03-12.
 */
public class OnePointCross implements Crosser {


    private ChildSelector childSelector;

    public OnePointCross(ChildSelector childSelector) {
        this.childSelector = childSelector;
    }

    @Override
    public Colloring cross(Colloring firstParent, Colloring secondParent) {


        int pointOfCut = RandomHelper.giveRandomNumberFromZeroTo_N_Exclusive(Colloring.getNumberOfVertex() - 1) + 1;

        Colloring firstChild = new Colloring();

        Colloring secondChild = new Colloring();

        for (int i =0; i < pointOfCut; i++)
        {
            firstChild.assignColor(i, firstParent.getColorOnPosition(i));
            secondChild.assignColor(i, secondParent.getColorOnPosition(i));
        }

        for (int i = pointOfCut; i < Colloring.getNumberOfVertex(); i++)
        {
            firstChild.assignColor(i, secondParent.getColorOnPosition(i));
            secondChild.assignColor(i, firstParent.getColorOnPosition(i));
        }

        return childSelector.selectChild(firstChild, secondChild);
    }
}
