package Choosers;

import Genetic.Individual;

/**
 * Created by sanczo on 2016-03-15.
 */
public interface ChildSelector{

    Individual selectChild(Individual firstChild, Individual secondChild);
}
