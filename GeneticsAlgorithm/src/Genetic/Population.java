package Genetic;

import Comperators.SortByFitnessComparator;
import Exceptions.FlatPopulationException;
import Helpers.RandomHelper;
import MyGraph.Graph;
import MyGraph.IGraphService;
import org.javatuples.Pair;


import java.util.*;

/**
 * Created by sanczo on 2016-03-10.
 */
public class Population {

    public List<Individual> population;

    private IGraphService graph;

    private Individual bestIndividual;

    private Individual worstIndividual;

    private int fitnessSum;

    private double fitnessAverage;

    private Set<Integer> bannedFitness;

    private Map<Integer, Integer> fitnessToBan;

    private AlgorithmParameters parameters;

    private boolean progress;

    private boolean[] frostbite;

    private List<Map<Integer, Integer>> mapsWhichShowWhatGenAppearOnCertainPosition;

    //private TextFileWriter textFileWriter;



    public Population(IGraphService graph) {
        this.graph = graph;
        parameters = AlgorithmParameters.getInstance();
        population = new ArrayList<>(parameters.getSizeOfPopulation());
        bannedFitness = new HashSet<>();
        fitnessToBan = new HashMap<>();
        frostbite = new boolean[Individual.getNumberOfGens()];

        mapsWhichShowWhatGenAppearOnCertainPosition = new LinkedList<>();
        for (int i = 0; i < Individual.getNumberOfGens(); i++)
        {
            mapsWhichShowWhatGenAppearOnCertainPosition.add(new HashMap<>());
        }
    }


    public Individual getBestIndividual() {
        return bestIndividual;
    }


    public Individual getWorstIndividual() {
        return worstIndividual;
    }

    public void initialPopulation() {



        for (int i = 0; i < parameters.getSizeOfPopulation(); i++) {
            Individual individual;
            do {
                individual = parameters.getInitializer().Init();
                individual.setFitness(graph.ColorGraph(individual.colors));
            }while (individual.getFitness() == parameters.getMaxUsedColor()+1 || bannedFitness.contains(individual.getFitness()));
            countFitnessFault(individual.getFitness());
            population.add(individual);
        }


    }

    public void CountPopulationParameters() throws FlatPopulationException {
        //resetTheBestIndividual();
        //resetTheWorstIndividual();
        resetFitnessSum();

        for (Individual individual : population) {
           /* int fitness = graph.colorGraph(individual.colors);
            individual.setFitness(fitness);*/

            if (checkIsItTheBestIndividual(individual)) {
                this.bestIndividual = individual;
                progress = true;
            }
            if (checkIsItTheWorstIndividual(individual))
                this.worstIndividual = individual;
            addToFitnessSum(individual);
        }

       // countNormalizedFitness();
      //  setRouletteRating();

        CountAverage();

    }

    private void CountAverage() {
        fitnessAverage = ((double) fitnessSum) / ((double) parameters.getSizeOfPopulation());
    }

    private void addToFitnessSum(Individual individual) {
        fitnessSum += individual.getFitness();
    }

    private boolean checkIsItTheWorstIndividual(Individual individual) {
        if (worstIndividual == null)
            return true;
        else
            return individual.getFitness() > worstIndividual.getFitness();
    }

    private boolean checkIsItTheBestIndividual(Individual individual) {
        if (bestIndividual == null)
            return true;
        else
            return individual.getFitness() < bestIndividual.getFitness();
    }

    private void resetFitnessSum() {
        fitnessSum = 0;
    }

    private void resetTheWorstIndividual() {
        worstIndividual = null;
    }

    private void resetTheBestIndividual() {
        bestIndividual = null;
    }


    public void countNormalizedFitness() throws FlatPopulationException {
        //Krok 1 Obliczamy wartosc fitness dla najgorzej przystosowanego osobnika
        int worstFitness = worstIndividual.getFitness();

        double sum = 0;
        //Krok 2 O zwyk�ego fitness odejmujemy najgorszy fitness tak aby funkcja dopasowania byla rosnoca
        //Otrzymana wartosc sumujemy
        for (Individual individual : population) {
            double moveFitness = (worstFitness - individual.getFitness());
            sum += moveFitness;
            individual.setNormalizedFitness(moveFitness);

        }



        for (int i = 0; i < population.size(); i++) {
            Individual individual = population.get(i);
            double moveFitness = individual.getNormalizedFitness();
            individual.setNormalizedFitness(moveFitness / sum);

        }


        ThrowExceptionIfPopulationsIsTotalyFlat(fitnessSum);

    }

    private void ThrowExceptionIfPopulationsIsTotalyFlat(double fitnessSum) throws FlatPopulationException {
        if (fitnessSum == 0)
            throw new FlatPopulationException();
    }

    public void setRouletteRating() {
        double sum = 0;
        for (Individual individual : population) {
            sum += individual.getNormalizedFitness();
            individual.setRouletteRating(sum);
        }
    }


    public void selectNewPopulation() {

        resetBan();

        Collections.sort(population, new SortByFitnessComparator());

        List<Individual> nextPopulation = takeEliteToNextGeneration();

        List<Individual> candidateForParents = chooseParents();

        double parentAverage = countAverage(candidateForParents);

        List<Pair<Individual, Individual>> futureParents = arrangePairs(candidateForParents);

        List<Individual> resultsOfCrossing = new ArrayList<>();

        for(Pair<Individual,Individual> parents : futureParents)
        {
            Pair<Individual, Individual> children = produceOffSpring(parents);
            resultsOfCrossing.add(children.getValue0());
            resultsOfCrossing.add(children.getValue1());

        }

        double crossAverage = countAverage(resultsOfCrossing);

        List<Individual> childrenAfterMutating = proceedMutation(resultsOfCrossing);

        double mutateAverage = countAverage(childrenAfterMutating);

        for(Individual child : childrenAfterMutating)
        {
            tryAddChildToPopulation(nextPopulation, child);
        }



        population = nextPopulation;

       // asigneMapsOfFrostbite();
       // System.out.println("Frostbite asigne");



    }

    private void tryAddChildToPopulation(List<Individual> nextGeneration ,Individual child) {
        int counter = 0;

        child.setFitness(graph.ColorGraph(child.colors));

        boolean isDuplicate = isDuplicate(nextGeneration, child);
        boolean shouldBeBanned = bannedFitness.contains(child.getFitness());
        boolean incorrectIndividual = child.getFitness() == parameters.getMaxUsedColor()+1;

  /*      while ((isDuplicate
                || incorrectIndividual
                || shouldBeBanned)
                && counter < 5)
        {
            child.mutate(frostbite);
            isDuplicate = isDuplicate(nextGeneration, child);
            child.setFitness(graph.colorGraph(child.colors));
            shouldBeBanned = bannedFitness.contains(child.getFitness());
            incorrectIndividual = child.getFitness() == parameters.getMaxUsedColor()+1;
            counter++;

            //report(isDuplicate, shouldBeBanned, incorrectIndividual);

        }*/

    /*    if (counter == 5)
        {
            while (isDuplicate
                    || incorrectIndividual)
            {
                child = parameters.getInitializer().Init();
                isDuplicate = isDuplicate(nextGeneration, child);
                child.setFitness(graph.colorGraph(child.colors));
                incorrectIndividual = child.getFitness() == parameters.getMaxUsedColor()+1;
                //report(isDuplicate, shouldBeBanned, incorrectIndividual);
            }
        }*/

        if (isDuplicate)
            child.setFitness(parameters.getMaxUsedColor()+1);

        countFitnessFault(child.getFitness());
        nextGeneration.add(child);
    }

    private void report(boolean isDuplicate, boolean shouldBeBanned, boolean incorrectIndividual) {
        if (isDuplicate)
            System.out.println("Jestem duplikatem");
        if (incorrectIndividual)
            System.out.println("Nieprawidłowy osobnik");
        if (shouldBeBanned)
            System.out.println("Zaduzo osobnikow jednego");
    }

    private List<Individual> proceedMutation(List<Individual> resultsOfCrossing) {

        for (Individual individual : resultsOfCrossing)
        {
                individual.mutate(frostbite);
        }
        return resultsOfCrossing;
    }



    private boolean isDuplicate(List<Individual> nextPopulation, Individual child) {
        boolean isDuplicate = false;
        for(int i = 0; i < nextPopulation.size() && isDuplicate == false; i++)
        {
            Individual fromPopulation = nextPopulation.get(i);
            isDuplicate = Arrays.equals(fromPopulation.colors, child.colors);
        }
        return isDuplicate;
    }

    public List<Individual> chooseParents()
    {
        int parentsNumber = parameters.getSizeOfPopulation() - parameters.getNumberOfElite();
        List<Individual> theChoosens = new ArrayList<>(parentsNumber);
        for(int i = 0; i < parentsNumber; i++)
        {
            if(i == parameters.getK1())
            {
                parameters.setTournamentSize((int) Math.ceil(parameters.getAverageTournamentSize()));
            }
            theChoosens.add(parameters.getChooser().choose(population));
        }

        return theChoosens;
    }

    public List<Pair<Individual,Individual>> arrangePairs(List<Individual> candidatesForParents)
    {

        int pairsQuantity = (parameters.getSizeOfPopulation() - parameters.getNumberOfElite()) / 2;
        List<Pair<Individual,Individual>> pairs = new ArrayList<>();
        for(int i = 0; i < pairsQuantity; i++)
        {
            Individual firstParent = candidatesForParents.get(RandomHelper.giveRandomNumberFromZeroTo_N_Exclusive(candidatesForParents.size()));
            candidatesForParents.remove(firstParent);

            Individual secondParent = candidatesForParents.get(RandomHelper.giveRandomNumberFromZeroTo_N_Exclusive(candidatesForParents.size()));
            candidatesForParents.remove(secondParent);

            pairs.add(new Pair<>(firstParent, secondParent));
        }

        return pairs;
    }

    public Pair<Individual, Individual> produceOffSpring(Pair<Individual, Individual> parents) {

        Individual firstParent = parents.getValue0();
        Individual secondParent = parents.getValue1();

        Pair<Individual, Individual> children;

        if(RandomHelper.IfItHappenWithAskedProbability(parameters.getChanceToCross()))
            children = parameters.getCrosser().cross(firstParent, secondParent);
        else
            children = new Pair<>(Individual.deepCloning(firstParent), Individual.deepCloning(secondParent));

        children.getValue0().setFitness(graph.ColorGraph(children.getValue0().colors));
        children.getValue1().setFitness(graph.ColorGraph(children.getValue1().colors));

        return children;
    }

    private List<Individual> takeEliteToNextGeneration()
    {
        List<Individual> nextGeneration = new ArrayList<>(population.subList(0,parameters.getNumberOfElite()));

        Individual individual;

        int individualPosition;
        for (individualPosition = 0; individualPosition < nextGeneration.size(); individualPosition++)
        {
            individual = nextGeneration.get(individualPosition);
            countFitnessFault(individual.getFitness());
        }
        return nextGeneration;
    }

    //Przykład polimorfizmu inkluzyjnego, późne wiązanie
    private Individual Mutate(Individual individual) {
        for (int i = 0; i < Individual.getNumberOfGens(); i++) {
            if (IsGeneShouldBeMutatet()) {
                int newValueOfGene = parameters.getMutating().mutate(individual.getColorOnPosition(i));
                individual.assignColor(i, newValueOfGene);
            }
        }
        return individual;
    }

    private boolean IsGeneShouldBeMutatet() {
        if (parameters.getChanceToMutationCasualGen() >= Math.random())
            return true;
        else
            return false;

    }

    public void print() {
        System.out.println("Drukuje osobnika");
        for (Individual shema : population) {
            shema.print();
            System.out.println();
        }
        System.out.println();
    }

    public double getFitnessAverage() {
        return fitnessAverage;
    }

    private void resetBan()
    {
        bannedFitness.clear();
        fitnessToBan.clear();
    }

    private void countFitnessFault(int fitness){
        if (! bannedFitness.contains(fitness))
        {
            Integer numberOfFault = fitnessToBan.get(fitness);
            if (numberOfFault == null)
                fitnessToBan.put(fitness, 1);
            else
                fitnessToBan.put(fitness, numberOfFault +1);

            if(fitnessToBan.get(fitness) == parameters.getMaxNumberIndividualWithTheSameFitness())
                bannedFitness.add(fitness);
        }
        else {
            Integer numberOfFault = fitnessToBan.get(fitness);
            fitnessToBan.put(fitness, numberOfFault +1);
        }
    }

    public boolean isProgress() {
        return progress;
    }

    public void setProgress(boolean progress) {
        this.progress = progress;
    }

    public static  double countAverage(List<Individual> individuals)
    {
        double sum = 0;
        for (Individual i : individuals)
        {
            sum += i.getFitness();
        }
        return sum / individuals.size();

    }

    public void asigneFrostbite()
    {
        for (int i = 0; i < frostbite.length; i++)
        {
            boolean onlyOneValueOnThisPosition = true;
            int genOfFirstIndividualONiPosition = population.get(0).colors[i];
            int j = 1;
            while (j < population.size() && onlyOneValueOnThisPosition)
            {
                onlyOneValueOnThisPosition = genOfFirstIndividualONiPosition == population.get(j).colors[i];
            }
            frostbite[i] = onlyOneValueOnThisPosition;
        }
    }
    public void asigneMapsOfFrostbite()
    {
        clearMapsWhichShowWhatGenAppearOnCertainPosition();
        for (int i = 0; i < Individual.getNumberOfGens(); i++)
        {
            for (int j = 0;j < population.size(); j++)
            {
                int gen = population.get(j).colors[i];
                Integer appearOfGen = mapsWhichShowWhatGenAppearOnCertainPosition.get(i).get(gen);
                if (appearOfGen == null) {
                    mapsWhichShowWhatGenAppearOnCertainPosition.get(i).put(gen,1);
                }
                else {
                    mapsWhichShowWhatGenAppearOnCertainPosition.get(i).put(gen,appearOfGen +1);
                }
            }

        }
    }


    private void clearMapsWhichShowWhatGenAppearOnCertainPosition()
    {
        for(Map<Integer, Integer> mapOfFrostbite : mapsWhichShowWhatGenAppearOnCertainPosition)
        {
            mapOfFrostbite.clear();
        }
    }
}
