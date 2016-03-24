package Initialization;

import Genetic.Colloring;
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
    public void Init(Colloring individual) {
        int[] colloringByGreedy = graph.giveColloringByGreedy();
        for(int i = 0; i< Colloring.getNumberOfVertex(); i++)
        {
            individual.setColorAtPosition(i, colloringByGreedy[i]);
        }
    }
}
