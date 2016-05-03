import Choosers.Tournament;
import Crossers.OnePointCross;
import Exceptions.FlatPopulationException;
import Genetic.*;
import Helpers.ProxyHandlerToMeasureTime;
import Helpers.TextFileWriter;
import Initialization.ReversePermutationInitializer;
import Mutatet.RandomMutate;
import MyGraph.GraphMaker;
import MyGraph.IGraphService;
import MyGraph.VertexOrder;

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


        Program programInstance = new Program("GEOM20");

        Population p = new Population(programInstance.graph);

        TextFileWriter textFileWriter = new TextFileWriter("FirstGeneration.col");

        GeneticAlgorithm algorithm = new GeneticAlgorithm(p, textFileWriter);

        AlgorithmParameters.getInstance().setMaxGenValue(Individual.getNumberOfGens()-1);

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
        param.setChanceToMutationCasualGen(0.2 / param.getMaxGenValue());
        param.setChanceToMutationFrostGen(0.5 / param.getMaxGenValue());
        param.setChooser(new Tournament());
        param.setCrosser(new OnePointCross());
        param.setMutating(new RandomMutate());
        param.setInitializer(new ReversePermutationInitializer());
        param.setMaxNumberIndividualWithTheSameFitness(150);
        param.setNumberOfElite(10);
        param.setAverageTournamentSize(5.4);

    }


    public static void greedyResearch(String graphName, int repeat){

        TextFileWriter toFileWriter = new TextFileWriter("greedyResearch/"+graphName+"greedyResearch.txt");

        Program instance = new Program(graphName);
        int greedyResult;
        int[] allResults = new int[repeat];


        instance.orderGraphVertexesBy(VertexOrder.ById);
        greedyResult = instance.takeGreedyOnGraph();
        String info = "Greedy bez zmiany kolejno�ci " + greedyResult;
        System.out.println(info);
        toFileWriter.println(info);


        instance.orderGraphVertexesBy(VertexOrder.ByDegreeAscent);
        greedyResult = instance.takeGreedyOnGraph();
        info = "Greedy, wierzcholki uporzadkowane stopniem rosnaco " + greedyResult;
        System.out.println(info);
        toFileWriter.println(info);

        instance.orderGraphVertexesBy(VertexOrder.ByDegreeDescent);
        greedyResult = instance.takeGreedyOnGraph();
        info = "Greedy, wierzcholki uporzadkowane stopniem malejaco " + greedyResult;
        System.out.println(info);
        toFileWriter.println(info);

        double sum = 0;
        int worst = Integer.MIN_VALUE;
        int best = Integer.MAX_VALUE;
        for(int i =0; i< repeat; i++)
        {
            instance.orderGraphVertexesBy(VertexOrder.ByRandom);
            greedyResult = instance.takeGreedyOnGraph();

            allResults[i] = greedyResult;
            sum+=greedyResult;

            if (greedyResult > worst)
                worst = greedyResult;
            if (greedyResult < best)
                best = greedyResult;

        }

        double average = sum / (double)repeat;

        double  odchylStand;

        double sumPom = 0;

        for(int value : allResults)
        {
            sumPom = Math.pow((value - average), 2);
        }

        odchylStand = Math.sqrt(sum / repeat);


        info = "Dla " + repeat + " powturzen";
        System.out.println(info);
        toFileWriter.println(info);

        info = "Dla losowej kolejno�ci uzsykano srednio " + average + " z dokladnoscia +- " + odchylStand;
        System.out.println(info);
        toFileWriter.println(info);

        info = "Najlepsza wartosc " + best;
        System.out.println();
        toFileWriter.println(info);

        info = "Najgorsza wartosc " + worst;
        System.out.println(info);
        toFileWriter.println(info);

        toFileWriter.EndConnection();
    }

    public void orderGraphVertexesBy(VertexOrder order)
    {
        graph.orderVertexBy(order);
    }

    public int takeGreedyOnGraph()
    {
        return graph.GreedyColoring();
    }


}
