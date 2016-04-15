package Initialization;

import Genetic.Individual;
import MyGraph.Graph;

/**
 * Created by sanczo on 2016-03-23.
 */
public class GreedyInitializer implements Initializer {

    private Graph graph;
    public GreedyInitializer(Graph graph) {
        this.graph = graph;
    }

    @Override
    public Individual Init() {
        Individual individual = new Individual();
        int[] colloringByGreedy = graph.giveColloringByGreedy();
        for(int i = 0; i< Individual.getNumberOfGens(); i++)
        {
            individual.setColorAtPosition(i, colloringByGreedy[i]);
        }
        return individual;
    }
}
