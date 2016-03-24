package Mutatet;

import Genetic.Colloring;
import Helpers.RandomHelper;
import Mutatet.Mutating;

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

        newColor = (Genetic.Genetic.Colloring.colorsNumber + oldColor + step) % Genetic.Genetic.Colloring.colorsNumber;

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

        if (newColor == -1)
            newColor = oldColor;

        if (newColor == Colloring.colorsNumber)
            newColor = oldColor;


        return newColor;
    }

}
