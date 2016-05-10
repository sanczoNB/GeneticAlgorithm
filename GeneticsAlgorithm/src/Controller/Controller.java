package Controller;

import Exceptions.FlatPopulationException;
import Genetic.GeneticAlgorithmManager;
import Genetic.Observer.NextGenerationOccurredObserver;
import Genetic.Population;
import Helpers.ProgramDependencies;
import Model.GraphicPopulationRepresentation;
import Model.GraphicRepresentationMaker;
import MyGraph.Graph;
import View.PopulationView;


/**
 * Created by sanczo on 2016-05-05.
 */
public class Controller implements IController, NextGenerationOccurredObserver {

    private GeneticAlgorithmManager geneticAlgorithmManager;

    private GraphicRepresentationMaker maker;

    private PopulationView populationView;

    public Controller(PopulationView populationView) {

        this.populationView = populationView;

        maker = (GraphicRepresentationMaker) ProgramDependencies.getDependence("GraphicRepresentationMaker");

        geneticAlgorithmManager = (GeneticAlgorithmManager) ProgramDependencies.getDependence("GeneticAlgorithmManager");

        geneticAlgorithmManager.addNextGenerationOccurredObserver(this);
    }

    public void callForZeroGeneration()
    {
        try {
            geneticAlgorithmManager.start();
        } catch (FlatPopulationException e) {
            e.printStackTrace();
        }
    }

    private void startGeneration(Population population) {

        populationView.createViewItems();

        populationView
                .setSizeForCanvasForGraphicalRepresentationOfPopulation(
                        maker.calculateSizeOfCanvas(population));
    }

    @Override
    public void nextGeneration(Population population) {

        if (geneticAlgorithmManager.getIterationNumber() == 0) {
            startGeneration(population);
        }

        populationView.setTitle(geneticAlgorithmManager.getIterationNumber());

        GraphicPopulationRepresentation representation = maker.transform(population);

        populationView.drawPopulationRepresentation(representation);
    }

    @Override
    public void callForNextGeneration() {
        geneticAlgorithmManager.nextGeneration();
    }
}
