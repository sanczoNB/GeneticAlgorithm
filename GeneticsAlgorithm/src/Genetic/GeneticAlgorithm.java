package Genetic;

import Exceptions.FlatPopulationException;
import Helpers.PopulationPrinter;
import Helpers.ResearchData;
import Helpers.TextFileWriter;

/**
 * Created by sanczo on 2016-03-10.
 */
public class GeneticAlgorithm {

    private Population population;

    private PopulationPrinter populationPrinter;



    int iterationWithoutProgress = 1;

    private TextFileWriter textFileWriter;

    AlgorithmParameters param;


    public static final String pathToFolder = "Individuals";

    public GeneticAlgorithm(Population population, TextFileWriter textFileWriter)
    {
        this.population = population;
        this.textFileWriter = textFileWriter;
        param = AlgorithmParameters.getInstance();
        populationPrinter = new PopulationPrinter("Population logs");
    }

    public GeneticAlgorithmResult SearchSolutionGraphColoringProblemByGeneticAlgorithm ()
    {

        try {
            setFirstGeneration();
            textFileWriter.writeHeader();
            populationPrinter.printPopulation(population);
            while (checkStopConditions() == false)
            {
                population.selectNewPopulation();
                population.CountPopulationParameters();
                iterationWithoutProgress++;
                population.incrematePopulationNumber();

                if (population.isProgress())
                {
                    iterationWithoutProgress = 0;
                    population.setProgress(false);
                    System.out.println("Make progress ");
                }
                populationPrinter.printPopulation(population);
                textFileWriter.write(new ResearchData(iterationWithoutProgress, population.getBestIndividual().getFitness(), population.getFitnessAverage(), population.getWorstIndividual().getFitness()));

            }
            Result result = ReturnProperAlgorithmResult(population.getBestIndividual());
            return new GeneticAlgorithmResult(result,population.getBestIndividual(), iterationWithoutProgress);
        } catch (FlatPopulationException e) {
            e.printStackTrace();
            return new GeneticAlgorithmResult(Result.TotalyFlatGenerations,population.getBestIndividual() , iterationWithoutProgress);
        }
    }

    public void nextGeneration() {
        population.selectNewPopulation();
    }

    public void setFirstGeneration() throws FlatPopulationException {
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
