package Choosers;

import Genetic.Colloring;

/**
 * Created by sanczo on 2016-03-15.
 */
public interface ChildSelector{

    Colloring selectChild(Colloring firstChild,Colloring secondChild);
}
