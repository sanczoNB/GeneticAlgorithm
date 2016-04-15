import Choosers.Tournament;
import Crossers.OnePointCross;
import Exceptions.FlatPopulationException;
import Genetic.*;
import Helpers.ProxyHandlerToMeasureTime;
import Helpers.TextFileWriter;
import Initialization.RandomInitializer;
import Mutatet.RandomMutate;
import MyGraph.GraphMaker;
import MyGraph.IGraphService;

import java.lang.reflect.Proxy;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * Created by sanczo on 2016-03-07.
 */
public class Program {


    private IGraphService graph;

    private ProxyHandlerToMeasureTime timeKepper;

    public Program(String fileName)
    {
        GraphMaker factory = new GraphMaker(fileName + ".col");


        //Wersja z proxy mierzacym czas
        this.graph = this.createDynamicProxy(factory);

        //Bez proxy
        //this.graph = factory.getGraph();


    }

    private IGraphService createDynamicProxy(GraphMaker factory) {
        timeKepper = new ProxyHandlerToMeasureTime(factory.getGraph());
        IGraphService graphSrevice =
                (IGraphService) Proxy.newProxyInstance(
                        IGraphService.class.getClassLoader(),
                        new Class[]{IGraphService.class},
                        timeKepper);
        return graphSrevice;
    }

    public static void main(String[] args) throws FlatPopulationException {

        Program simpleInstance = new Program("GEOM4");

        int maxUsedColorForSimpleGraph = simpleInstance.graph.GreedyColoring();
        System.out.println("Dla prostego, cztero wezwolego grafu gredu znjaduje " + maxUsedColorForSimpleGraph);

        int[] exampleIndividual = {4, 3, 3, 2, 1, 3, 1, 1};

        AlgorithmParameters.getInstance().setMaxUsedColor(12);

        int simpleGraphExampleSolution = simpleInstance.graph.ColorGraph(exampleIndividual);

        System.out.println("Dla prostego, cztero-wezwolego grafu przykladowe zakodowane rozwiazanie daje " + simpleGraphExampleSolution);




        Program programInstance = new Program("GEOM20");

        Population p = new Population(programInstance.graph);

        TextFileWriter textFileWriter = new TextFileWriter("FirstGeneration.col");

        GeneticAlgorithm algorithm = new GeneticAlgorithm(p, textFileWriter);

        int maxUsedColor = programInstance.graph.GreedyColoring();

        AlgorithmParameters.getInstance().setMaxUsedColor(maxUsedColor);

        programInstance.setParam();

        long startTime = System.nanoTime();

        algorithm.SearchSolutionGraphColoringProblemByGeneticAlgorithm();

        long durationTime = System.nanoTime() - startTime;

        Duration algorithmDuration = Duration.of(durationTime, ChronoUnit.NANOS);

        programInstance.disconectTextFileWriter(textFileWriter);

        System.out.println("Calkowity czas trwania algorytmu " + algorithmDuration.toString());

        System.out.println("Czas wykonywana kolorowania grafu " + programInstance.timeKepper.getTotalTimeInNanoSeconds());

        System.out.println("Sredni czas dzialaniu funkcji kolorujacej graf " + programInstance.timeKepper.getAveregeRunTime());

        System.out.println("Funkcja kolorowania zostala wywolana az " + programInstance.timeKepper.getCounter());

        System.out.print("Koniec");




    }



    private void disconectTextFileWriter(TextFileWriter textFileWriter) {
        textFileWriter.FlushData();
        textFileWriter.EndConnection();
    }
    public void setParam()
    {
        AlgorithmParameters param = AlgorithmParameters.getInstance();
        param.setMaxNumberOfIterationsWithoutProgress(200);
        param.setSizeOfPopulation(150);
        param.setChanceToCross(0.85);
        param.setChanceToMutationCasualGen(0.2 / param.getMaxUsedColor());
        param.setChanceToMutationFrostGen(0.5 / param.getMaxUsedColor());
        param.setChooser(new Tournament());
        param.setCrosser(new OnePointCross());
        param.setMutating(new RandomMutate());
        param.setInitializer(new RandomInitializer());
        param.setMaxNumberIndividualWithTheSameFitness(40);
        param.setNumberOfElite(100);
        param.setAverageTournamentSize(5.4);

    }
}
