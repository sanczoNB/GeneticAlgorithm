package Genetic;

import MyGraph.Graph;
import MyGraph.IGraphService;

/**
 * Created by sanczo on 2016-03-15.
 */
public class GeneticParameters {

    private int populationSize;
    private int colorNumber;
    private int iterationNumber;
    private double chanceToMutate;
    private double chanceToCross;
    private IGraphService graph;

    public GeneticParameters(IGraphService graph,int populationSize, int colorNumber,int iterationNumber ,double chanceToMutate, double chanceToCross) {
        this.graph = graph;
        this.populationSize = populationSize;
        this.colorNumber = colorNumber;
        this.iterationNumber = iterationNumber;
        this.chanceToMutate = chanceToMutate;
        this.chanceToCross = chanceToCross;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public int getColorNumber() {
        return colorNumber;
    }

    public int getIterationNumber() {
        return iterationNumber;
    }

    public double getChanceToMutate() {
        return chanceToMutate;
    }

    public double getChanceToCross() {
        return chanceToCross;
    }

    public IGraphService getGraph() {
        return graph;
    }

    public void setColorNumber(int colorNumber) {
        this.colorNumber = colorNumber;
    }
}
