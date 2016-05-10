package Genetic;

import Comperators.SortByFitnessComparator;
import Exceptions.FlatPopulationException;
import Genetic.Observer.NewIndividualInPopulationObserver;
import Helpers.PermutationConverter;
import Helpers.RandomHelper;
import MyGraph.IGraphService;
import MyGraph.VertexOrder;
import org.javatuples.Pair;


import java.util.*;

/**
 * Created by sanczo on 2016-03-10.
 */
public class Population {

    private List<Individual> population;

    private List<Individual> oldPopulation;

    private IGraphService graphService;

    private Individual bestIndividual;

    private Individual worstIndividual;

    private int fitnessSum;

    private double fitnessAverage;


    private AlgorithmParameters parameters;

    private boolean progress;

    private boolean[] frostbite;

    private List<Map<Integer, Integer>> mapsWhichShowWhatGenAppearOnCertainPosition;

    private int populationNumber;

    private List<NewIndividualInPopulationObserver> addObservers;





    public Population(IGraphService graph) {
        this.graphService = graph;
        parameters = AlgorithmParameters.getInstance();
        population = new ArrayList<>(parameters.SizeOfPopulation());
        frostbite = new boolean[Individual.getNumberOfGens()];
        addObservers = new LinkedList<>();

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



    public void CountPopulationParameters() throws FlatPopulationException {
        //resetTheBestIndividual();
        //resetTheWorstIndividual();
        resetFitnessSum();

        for (Individual individual : population) {
           /* int fitness = graphService.colorGraph(individual.gens);
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
        fitnessAverage = ((double) fitnessSum) / ((double) parameters.SizeOfPopulation());
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
        double worstFitness = worstIndividual.getFitness();

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

    private void report(boolean isDuplicate, boolean shouldBeBanned, boolean incorrectIndividual) {
        if (isDuplicate)
            System.out.println("Jestem duplikatem");
        if (incorrectIndividual)
            System.out.println("Nieprawidłowy osobnik");
        if (shouldBeBanned)
            System.out.println("Zaduzo osobnikow jednego");
    }

    private boolean isDuplicate(List<Individual> nextPopulation, Individual child) {
        boolean isDuplicate = false;
        for(int i = 0; i < nextPopulation.size() && isDuplicate == false; i++)
        {
            Individual fromPopulation = nextPopulation.get(i);
            isDuplicate = Arrays.equals(fromPopulation.gens, child.gens);
        }
        return isDuplicate;
    }

    public List<Individual> getNbestIndividual(int howMany){

        Collections.sort(population, new SortByFitnessComparator());

        List<Individual> bests = new ArrayList<>(population.subList(0,howMany));

        List<Individual> depCloneBests = new LinkedList<>();

        bests.stream().forEach(individual -> depCloneBests.add(Individual.deepCloning(individual)));

        return depCloneBests;
    }
/*
    private List<Individual> takeEliteToNextGeneration()
    {
        List<Individual> nextGeneration = new ArrayList<>(population.subList(0,parameters.getNumberOfElite()));

        Individual individual;

        int individualPosition;
        for (individualPosition = 0; individualPosition < nextGeneration.size(); individualPosition++)
        {
            individual = nextGeneration.get(individualPosition);
            countHowManyIsIndividualWithTheSameFitness(individual.getFitness());
        }
        return nextGeneration;
    }*/

    //Przykład polimorfizmu inkluzyjnego, późne wiązanie
    private Individual Mutate(Individual individual) {
        for (int i = 0; i < Individual.getNumberOfGens(); i++) {
            if (IsGeneShouldBeMutatet()) {
                int newValueOfGene = parameters.getMutating().mutate(individual.getGenAtPosition(i));
                individual.assignGen(i, newValueOfGene);
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

    }
    public void asigneMapsOfFrostbite()
    {
        clearMapsWhichShowWhatGenAppearOnCertainPosition();
        for (int i = 0; i < Individual.getNumberOfGens(); i++)
        {
            for (int j = 0;j < population.size(); j++)
            {
                int gen = population.get(j).gens[i];
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

    public void setFitness(Individual individual)
    {
        int fitness;
        int[] permutation = PermutationConverter.getInstance().covertToPerm(individual.gens);
        graphService.establishVertexPriority(permutation);
        graphService.orderVertexBy(VertexOrder.ByEarlierEstablishedPriority);

        fitness = graphService.GreedyColoring();
        individual.setFitness(fitness);
    }

    public void incrematePopulationNumber()
    {
        populationNumber++;
    }

    public int getPopulationNumber() {
        return populationNumber;
    }

    public int size(){
        return population.size();
    }

    public Individual getIndividualAtPosition(int index) {
        return population.get(index);
    };

    public void add(Individual individual)
    {
        population.add(individual);

        notifyAllNewIndividualInPopulationObserver(individual);
    }

    public List<Individual> getPopulation() {
        return population;
    }

    public List<Individual> getOldPopulation() {
        return oldPopulation;
    }

    public void addNewIndividualInPopulationObserver(NewIndividualInPopulationObserver observer){
        addObservers.add(observer);
    }

    public void removeNewIndividualInPopulationObserver(NewIndividualInPopulationObserver observer){
        addObservers.remove(observer);
    }
    public void notifyAllNewIndividualInPopulationObserver(Individual individual){
        addObservers.stream().forEach(o -> o.individualAdded(individual));
    }

    public void sortByFitness(){
        Collections.sort(population, new SortByFitnessComparator());
    }

    public void changeGenerations(){
        oldPopulation = new LinkedList<>(population);
        population.clear();
    }

}
