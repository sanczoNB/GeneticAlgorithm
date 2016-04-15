package Choosers;

import Genetic.Individual;
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
    public Individual selectChild(Individual firstChild, Individual secondChild) {
        int fitnessOfFirstChild = graph.getFitness(firstChild);
        int fitnessOfSeconfChild = graph.getFitness(secondChild);
        if(fitnessOfFirstChild < fitnessOfSeconfChild)
            return firstChild;
        else
            return secondChild;
    }
}
