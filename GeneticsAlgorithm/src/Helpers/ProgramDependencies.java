package Helpers;

import Genetic.AlgorithmParameters;
import Genetic.GeneticAlgorithmManager;
import Genetic.Population;
import Genetic.PopulationManager;
import Model.GraphicRepresentationMaker;

import java.util.HashMap;

/**
 * Created by sanczo on 2016-05-09.
 */
public class ProgramDependencies {

    private static HashMap<String, Object> dependencies = new HashMap<>();

    private static AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance();

    public static void setUpDependencies()
    {
        dependencies.put("Population", new Population(algorithmParameters.GraphService()));
        dependencies.put("PopulationManager", new PopulationManager());
        dependencies.put("GraphicRepresentationMaker", new GraphicRepresentationMaker());
        dependencies.put("GeneticAlgorithmManager", new GeneticAlgorithmManager());
    }

    public static Object getDependence(String name)
    {
        return dependencies.get(name);
    }

}
