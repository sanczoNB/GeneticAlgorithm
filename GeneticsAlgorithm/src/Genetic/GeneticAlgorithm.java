package Genetic;

import Exceptions.FlatPopulationException;
import Helpers.ResearchData;
import Helpers.TextFileWriter;

/**
 * Created by sanczo on 2016-03-10.
 */
public class GeneticAlgorithm {

    private Population population;

    public static final int LimitOfIterations = 1000;

    int iteration = 0;

    private TextFileWriter textFileWriter;

    public GeneticAlgorithm(Population population, TextFileWriter textFileWriter)
    {
        this.population = population;
        this.textFileWriter = textFileWriter;
        textFileWriter.CreatePrintWriter();
    }

    public GeneticAlgorithmResult SearchSolutionGraphColoringProblemByGeneticAlgorithm ()
    {

        try {
            population.fillPopulation();
            population.initialPopulation();
            population.CountPopulationParameters();
            textFileWriter.writeHeader();
            while (checkStopConditions() == false)
            {
                population.selectNewPopulation();
                population.CountPopulationParameters();
                iteration++;
                textFileWriter.write(new ResearchData(iteration, population.getBestIndividual().getFitness(), population.getFitnessAverage(), population.getWorstIndividual().getFitness()));
            }

            Result result = ReturnProperAlgorithmResult(population.getBestIndividual());
            return new GeneticAlgorithmResult(result,population.getBestIndividual(), iteration);
        } catch (FlatPopulationException e) {
            e.printStackTrace();
            return new GeneticAlgorithmResult(Result.TotalyFlatGenerations,population.getBestIndividual() ,iteration);
        }
    }

    private Result ReturnProperAlgorithmResult(Colloring bestIndividual) {
         if(bestIndividual.getFitness() == 0)
             return Result.Success;
        else
             return Result.ToManyIterations;
    }

    private boolean checkStopConditions() {
        return population.getBestIndividual().getFitness() == 0 || iteration > LimitOfIterations;
    }
}
