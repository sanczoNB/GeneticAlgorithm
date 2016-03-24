import Choosers.BestChildWin;
import Choosers.RouletteChooser;
import Crossers.OnePointCross;
import Genetic.*;
import Helpers.MyTime;
import Helpers.StringHelper;
import Helpers.TextFileWriter;
import Initialization.GreedyInitializer;
import Initialization.RandomInitializer;
import MyGraph.Graph;
import Mutatet.OneStepMutate;
import MyGraph.GraphFactory;

import java.io.File;

/**
 * Created by sanczo on 2016-03-07.
 */
public class Program {


    private int success;

    private int failure;

    private int sum;

    private Graph graph;

    private StringHelper stringHelper;
    private TextFileWriter timeLogger;

    private static int colorNumber;

    private String fileName;

    private String directoryPath;

    private static int populationSize;

    private static double chanceToMutate;

    private static double chanceToCross;

    private String pathToAllResults ;

    public Program(String fileName)
    {
        stringHelper = new StringHelper();
        GraphFactory factory = new GraphFactory(fileName + ".col");
        this.graph = factory.getGraph();
        this.fileName = fileName;
    }

    public static void main(String[] args)
    {
        Program programInstance = new Program("GEOM120");




        System.out.println(programInstance.graph.GreedyColoring(25));
        System.out.println("Dopasowanie grafu "+programInstance.graph.getFitness());
        System.out.println();

        colorNumber = 64;

        populationSize = 1000;
        chanceToMutate = 0.8;
        chanceToCross = 0.8;

        programInstance.researchForOneSelectColor(10);

        //programInstance.SearchSmallestColorNumber(programInstance);
    }

    public  void SearchSmallestColorNumber(Program programInstance) {
        GeneticAlgorithmResult geneticAlgorithmResult=new GeneticAlgorithmResult(Result.Success,null,0);
        programInstance.pathToAllResults = "";

        GeneticParameters parameters = new GeneticParameters(graph,populationSize, colorNumber,1,chanceToMutate, chanceToCross);
        while (geneticAlgorithmResult.getResult() == Result.Success) {
            System.out.println("Szukam rozwiazania dla zbioru kolorów o licznoœci "+ colorNumber);
            parameters.setColorNumber(colorNumber);
            geneticAlgorithmResult = programInstance.DoGeneticExperimentForSpeceficNumberOfColors(parameters);
            System.out.println("Dla " + colorNumber + " kolorow poszukiwanie zakoñczy³o siê " + geneticAlgorithmResult.getResult());
            colorNumber --;
        }
    }

    public  void researchForOneSelectColor(int repeat) {

        directoryPath = stringHelper.makeTitleForDirectoryWithResults(fileName, colorNumber);
        pathToAllResults = directoryPath+"/all results/";
        makeDirector(directoryPath);
        makeDirectorForAllResults();

        createLogger("Raport"+colorNumber);



        boolean worldKnow = false;

        for(int i =0; i < repeat; i++) {
            GeneticParameters parameters = new GeneticParameters(graph,populationSize, colorNumber,i,chanceToMutate, chanceToCross);
            System.out.println("Szukam rozwiazania dla zbioru kolorów o licznoœci " + colorNumber);
            System.out.println("Próba nr " + i);
            GeneticAlgorithmResult geneticAlgorithmResult= DoGeneticExperimentForSpeceficNumberOfColors(parameters);
            geneticAlgorithmResult.setTake(i+1);

            if(worldKnow == false)
            {

                printBestIndividual(geneticAlgorithmResult);
                worldKnow = true;

            }

            incremateFailureOrSuccess(geneticAlgorithmResult);
            incremateTime(geneticAlgorithmResult);
            timeLogger.write(geneticAlgorithmResult);

        }

        long avargeTime = -1;
        if(success != 0)
            avargeTime = sum / success;
        timeLogger.logSuccessAndFailure(success, failure,avargeTime);
        disconectTextFileWriter(timeLogger);
    }

    private void makeDirectorForAllResults() {
        File directory = new File(directoryPath+"/"+"all results");
        directory.mkdir();
    }

    private void makeDirector(String directoryName) {
        File file = new File(directoryName);
        String[] names = directoryName.split("/");
        StringBuilder bob = new StringBuilder(names[0]);
        File directory = new File(bob.toString());
        directory.mkdir();
        for (int i = 1; i < names.length; i++ )
        {
            bob.append("/"+names[i]);
            directory = new File(bob.toString());
            directory.mkdir();
            boolean isHeCreate = file.mkdir();
            System.out.print(isHeCreate);
        }


    }

    public void printBestIndividual(GeneticAlgorithmResult geneticAlgorithmResult) {
        graph.setWriter(new TextFileWriter(directoryPath+"/"+this.fileName+"BestSolutionWith"+colorNumber+".txt"));
        graph.SetColoring(geneticAlgorithmResult.getBestIndividual());
        graph.print();
    }

    private void incremateTime(GeneticAlgorithmResult geneticAlgorithmResult) {
        if(geneticAlgorithmResult.getResult() == Result.Success)
            sum+=geneticAlgorithmResult.getDurationTime().getMilliseconds();
    }

    private  void createLogger(String name) {
        timeLogger = new TextFileWriter(directoryPath+"/"+name+".txt");
        timeLogger.CreatePrintWriter();
    }

    private GeneticAlgorithmResult DoGeneticExperimentForSpeceficNumberOfColors(GeneticParameters parameters) {

        TextFileWriter textFileWriter = new TextFileWriter(pathToAllResults+parameters.getIterationNumber()+".csv");

        Population p = new Population(parameters.getColorNumber(),
                parameters.getPopulationSize(),
                parameters.getChanceToCross(),
                parameters.getChanceToMutate(),
                parameters.getGraph(),
                new RouletteChooser(),
                new OnePointCross(new BestChildWin(parameters.getGraph())),
                new OneStepMutate(),
                new GreedyInitializer(graph));


        GeneticAlgorithm alg = new GeneticAlgorithm(p, textFileWriter);


        long beforeExperiment = System.currentTimeMillis();

        GeneticAlgorithmResult result = alg.SearchSolutionGraphColoringProblemByGeneticAlgorithm();
        long afterExperiment = System.currentTimeMillis();

        long dif =(afterExperiment - beforeExperiment);



        MyTime myDif = new MyTime(dif);

        result.setDurationTime(myDif);


        disconectTextFileWriter(textFileWriter);
        return result;
    }

    private void disconectTextFileWriter(TextFileWriter textFileWriter) {
        textFileWriter.FlushData();
        textFileWriter.EndConnection();
    }

    private void incremateFailureOrSuccess(GeneticAlgorithmResult result){
        if(result.getResult() == Result.Success)
            success++;
        else
            failure++;

    }


}
