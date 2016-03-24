package Genetic;

import MyGraph.Graph;

/**
 * Created by sanczo on 2016-03-15.
 */
public class GeneticParameters {

    private int populationSize;
    private int colorNumber;
    private int iterationNumber;
    private double chanceToMutate;
    private double chanceToCross;
    private Graph graph;

    public GeneticParameters(Graph graph,int populationSize, int colorNumber,int iterationNumber ,double chanceToMutate, double chanceToCross) {
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

    public Graph getGraph() {
        return graph;
    }

    public void setColorNumber(int colorNumber) {
        this.colorNumber = colorNumber;
    }
}
