package Genetic;

import Exceptions.FlatPopulationException;
import Genetic.Observer.NextGenerationOccurredObserver;
import Helpers.ProgramDependencies;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by sanczo on 2016-05-06.
 */
public class GeneticAlgorithmManager {

    private int iterationWithoutProgress = 1;

    private int iterationNumber = 0;

    private PopulationManager populationManager;

    private List<NextGenerationOccurredObserver> nextGenerationOccurredObservers;

    public GeneticAlgorithmManager() {
        nextGenerationOccurredObservers = new LinkedList<>();
        populationManager = (PopulationManager) ProgramDependencies.getDependence("PopulationManager");
    }

   public void start() throws FlatPopulationException {

       populationManager.setFirstGeneration();
       notifyNextGenerationOccurredObservers();

   }

    public void nextGeneration(){

        populationManager.selectNextGeneration();
        iterationNumber++;
        notifyNextGenerationOccurredObservers();
    }

    public void addNextGenerationOccurredObserver(NextGenerationOccurredObserver observer)
    {
        nextGenerationOccurredObservers.add(observer);
    }

    public void removeNextGenerationOccurredObserver(NextGenerationOccurredObserver observer)
    {
        nextGenerationOccurredObservers.remove(observer);
    }

    private void notifyNextGenerationOccurredObservers(){
        nextGenerationOccurredObservers.stream().forEach(o -> o.nextGeneration(populationManager.getPopulation()));
    }


    public int getIterationNumber() {
        return iterationNumber;
    }
}
