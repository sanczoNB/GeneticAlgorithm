package Genetic;

import Exceptions.FlatPopulationException;
import Helpers.ResearchData;
import Helpers.TextFileWriter;

/**
 * Created by sanczo on 2016-03-10.
 */
public class GeneticAlgorithm {

    private Population population;



    int iterationWithoutProgress = 1;

    private TextFileWriter textFileWriter;

    AlgorithmParameters param;


    public static final String pathToFolder = "Individuals";

    public GeneticAlgorithm(Population population, TextFileWriter textFileWriter)
    {
        this.population = population;
        this.textFileWriter = textFileWriter;
        param = AlgorithmParameters.getInstance();
    }

    public GeneticAlgorithmResult SearchSolutionGraphColoringProblemByGeneticAlgorithm ()
    {

        try {
            setFirstGeneration();
            textFileWriter.writeHeader();
            while (checkStopConditions() == false)
            {
                population.selectNewPopulation();
                population.CountPopulationParameters();
                iterationWithoutProgress++;
                if (population.isProgress())
                {
                    iterationWithoutProgress = 0;
                    population.setProgress(false);
                    System.out.println("Make progress ");
                }

                textFileWriter.write(new ResearchData(iterationWithoutProgress, population.getBestIndividual().getFitness(), population.getFitnessAverage(), population.getWorstIndividual().getFitness()));

            }
            Result result = ReturnProperAlgorithmResult(population.getBestIndividual());
            return new GeneticAlgorithmResult(result,population.getBestIndividual(), iterationWithoutProgress);
        } catch (FlatPopulationException e) {
            e.printStackTrace();
            return new GeneticAlgorithmResult(Result.TotalyFlatGenerations,population.getBestIndividual() , iterationWithoutProgress);
        }
    }

    public void setFirstGeneration() throws FlatPopulationException {
     //   population.fillPopulation();
        population.initialPopulation();
        population.CountPopulationParameters();
    }

    private Result ReturnProperAlgorithmResult(Individual bestIndividual) {
         if(bestIndividual.getFitness() == 0)
             return Result.Success;
        else
             return Result.ToManyIterations;
    }

    private boolean checkStopConditions() {
        return population.getBestIndividual().getFitness() == 0 || iterationWithoutProgress > AlgorithmParameters.getInstance().getMaxNumberOfIterationsWithoutProgress();
    }
}
