package Helpers;

/**
 * Created by sanczo on 2016-03-15.
 */
public class ResearchData {

    private int generation;

    private int bestResult;

    private double averageResult;

    private int worstResult;

    public ResearchData(int generation,int bestResult, double averageResult, int worstResult) {
        this.generation = generation;
        this.bestResult = bestResult;
        this.averageResult = averageResult;
        this.worstResult = worstResult;
    }

    public int getWorstResult() {
        return worstResult;
    }

    public double getAverageResult() {
        return averageResult;
    }

    public int getBestResult() {
        return bestResult;
    }

    public int getGeneration() {
        return generation;
    }
}
