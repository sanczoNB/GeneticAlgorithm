package Genetic.FitnessCalculator;

import Genetic.AlgorithmParameters;
import Genetic.Individual;
import Helpers.PermutationConverter;
import MyGraph.Graph;
import MyGraph.IGraphService;
import MyGraph.VertexOrder;

/**
 * Created by sanczo on 2016-05-05.
 */
public class LeastNumberOfUsedColorFitnessRPR implements IFitnessCalculator {

    private IGraphService graphService;

    public LeastNumberOfUsedColorFitnessRPR(IGraphService graphService) {
        this.graphService = graphService;
    }

    @Override
    public double calculateFitness(Individual individual) {

        int[] permutation = PermutationConverter.getInstance().covertToPerm(individual.gens);
        graphService.establishVertexPriority(permutation);
        graphService.orderVertexBy(VertexOrder.ByEarlierEstablishedPriority);

        return graphService.GreedyColoring();
    }
}
