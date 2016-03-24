package Crossers;

import Genetic.Colloring;

/**
 * Created by sanczo on 2016-03-12.
 */
public interface Crosser {

    Colloring cross(Colloring firstParent, Colloring secondParent);
}
