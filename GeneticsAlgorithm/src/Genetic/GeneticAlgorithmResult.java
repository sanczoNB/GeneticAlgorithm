package Genetic;

import Helpers.MyTime;

/**
 * Created by sanczo on 2016-03-21.
 */
public class GeneticAlgorithmResult {

    private Result result;

    private int iterations;

    private int take;

    private Individual bestIndividual;


    private MyTime durationTime;

    public GeneticAlgorithmResult(Result result, Individual bestIndividual,int iterations) {
        this.result = result;
        this.bestIndividual = bestIndividual;
        this.iterations = iterations;
    }

    public int getTake() {
        return take;
    }

    public void setTake(int take) {
        this.take = take;
    }

    public MyTime getDurationTime() {
        return durationTime;
    }

    public int getIterations() {
        return iterations;
    }

    public Result getResult() {
        return result;
    }

    public void setDurationTime(MyTime durationTime) {
        this.durationTime = durationTime;
    }


    public Individual getBestIndividual() {
        return bestIndividual;
    }

    public void setBestIndividual(Individual bestIndividual) {
        this.bestIndividual = bestIndividual;
    }
}
