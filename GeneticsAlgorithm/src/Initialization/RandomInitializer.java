package Initialization;

import Genetic.Colloring;
import Genetic.Population;
import Helpers.RandomHelper;
import MyGraph.ColorChoose;

import java.awt.*;

/**
 * Created by sanczo on 2016-03-23.
 */
public class RandomInitializer implements Initializer {

    @Override
    public void Init(Colloring individual) {
        for(int i =0; i < individual.getNumberOfVertex(); i++)
        {
            int color = RandomHelper.giveRandomNumberFromZeroTo_N_Exclusive(Colloring.colorsNumber);
            individual.setColorAtPosition(i, color);
        }
    }
}
