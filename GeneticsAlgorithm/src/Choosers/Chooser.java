package Choosers;

import Genetic.Colloring;
import java.util.List;

/**
 * Created by sanczo on 2016-03-10.
 */
public interface Chooser {

        public Colloring choose(List<Colloring> population);

}
