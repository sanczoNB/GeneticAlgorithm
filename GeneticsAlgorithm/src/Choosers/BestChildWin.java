package Choosers;

import Genetic.Colloring;
import MyGraph.Graph;


/**
 * Created by sanczo on 2016-03-15.
 */
public class BestChildWin implements ChildSelector {

    private Graph graph;

    public BestChildWin(Graph graph) {
        this.graph = graph;
    }


    @Override
    public Colloring selectChild(Colloring firstChild, Colloring secondChild) {
        int fitnessOfFirstChild = graph.getFitness(firstChild);
        int fitnessOfSeconfChild = graph.getFitness(secondChild);
        if(fitnessOfFirstChild < fitnessOfSeconfChild)
            return firstChild;
        else
            return secondChild;
    }
}
