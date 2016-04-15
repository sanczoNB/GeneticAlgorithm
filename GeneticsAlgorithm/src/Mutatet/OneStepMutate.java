package Mutatet;

import Genetic.AlgorithmParameters;
import Genetic.Individual;
import Helpers.RandomHelper;

/**
 * Created by sanczo on 2016-03-12.
 */
public class OneStepMutate implements Mutating {

   /* Je¿eli jesteœmy na krawedzi magicznie przeskakujemy na drug¹ stronê
    @Override
    public int mutate(int oldColor) {
        int newColor;

        int step;

        int addOrSub = Helpers.RandomHelper.giveRandomNumberFromZeroTo_N_Exclusive(2);

        if(addOrSub == 0)
            step = 1;
        else step = -1;

        newColor = (Genetic.Genetic.Individual.colorsNumber + oldColor + step) % Genetic.Genetic.Individual.colorsNumber;

        return newColor;
    }
    */

    @Override
    public int mutate(int oldColor) {
        int newColor;

        int step;

        int addOrSub = RandomHelper.giveRandomNumberFromZeroTo_N_Exclusive(2);

        if(addOrSub == 0)
            step = 1;
        else step = -1;

        newColor = oldColor + step;

        if (newColor == 0)
            newColor = oldColor;

        if (newColor == AlgorithmParameters.getInstance().getMaxUsedColor())
            newColor = oldColor;


        return newColor;
    }

}
