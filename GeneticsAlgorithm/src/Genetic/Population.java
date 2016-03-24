package Genetic;

import Choosers.Chooser;
import Crossers.Crosser;
import Exceptions.FlatPopulationException;
import Helpers.RandomHelper;
import Initialization.Initializer;
import MyGraph.Graph;
import Mutatet.Mutating;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanczo on 2016-03-10.
 */
public class Population {


    private int size;

    public List<Colloring> population;

    private Graph graph;

    private Colloring bestIndividual;

    private Colloring worstIndividual;

    private int fitnessSum;

    int colorsNumber;

    private double chanceToMutation;

    private double chanceToCross;

    private Chooser chooser;

    private Crosser crosser;

    private Mutating mutating;

    private Initializer initializer;

    private double fitnessAverage;

    public Population(int colorsNumber, int size, double chanceToCross, double chanceToMutation, Graph graph, Chooser chooser, Crosser crosser, Mutating mutating, Initializer initializer) {
        this.colorsNumber = colorsNumber;
        this.size = size;
        this.chanceToCross = chanceToCross;
        this.chanceToMutation = chanceToMutation;
        this.chooser = chooser;
        this.crosser = crosser;
        this.mutating = mutating;
        this.initializer = initializer;

        this.graph = graph;
    }


    public Colloring getBestIndividual() {
        return bestIndividual;
    }


    public Colloring getWorstIndividual() {
        return worstIndividual;
    }

    public void initialPopulation() {

        Colloring.colorsNumber = colorsNumber;

        for (int i = 0; i < size; i++) {
            initializer.Init(population.get(i));
        }

    }

    public void fillPopulation() {
        population = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            population.add(new Colloring());
        }
    }

    public void CountPopulationParameters() throws FlatPopulationException {
        resetTheBestIndividual();
        resetTheWorstIndividual();
        resetFitnessSum();

        for (Colloring individual : population) {
            int fitness = graph.getFitness(individual);
            individual.setFitness(fitness);

            if (checkIsItTheBestIndividual(individual))
                this.bestIndividual = individual;
            if (checkIsItTheWorstIndividual(individual))
                this.worstIndividual = individual;
            addToFitnessSum(individual);
        }
        countNormalizedFitness();
        setRouletteRating();

        CountAverage();

    }

    private void CountAverage() {
        fitnessAverage = ((double) fitnessSum) / ((double) size);
    }

    private void addToFitnessSum(Colloring individual) {
        fitnessSum += individual.getFitness();
    }

    private boolean checkIsItTheWorstIndividual(Colloring individual) {
        if (worstIndividual == null)
            return true;
        else
            return individual.getFitness() > worstIndividual.getFitness();
    }

    private boolean checkIsItTheBestIndividual(Colloring individual) {
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
        for (Colloring individual : population) {
            double moveFitness = (worstFitness - individual.getFitness());
            individual.moveFitness1 = moveFitness;
            sum += moveFitness;
            individual.setNormalizedFitness(moveFitness);

        }

        double sum2 = 0;

        for (int i = 0; i < population.size(); i++) {
            Colloring individual = population.get(i);
            double moveFitness = individual.getNormalizedFitness();
            individual.setNormalizedFitness(moveFitness / sum);
            if (individual.moveFitness1 != moveFitness)
                System.out.println("Cos sie zepsulo " + i);
            sum2 += moveFitness;

        }


        ThrowExceptionIfPopulationsIsTotalyFlat(fitnessSum);

    }

    private void ThrowExceptionIfPopulationsIsTotalyFlat(double fitnessSum) throws FlatPopulationException {
        if (fitnessSum == 0)
            throw new FlatPopulationException();
    }

    public void setRouletteRating() {
        double sum = 0;
        for (Colloring individual : population) {
            sum += individual.getNormalizedFitness();
            individual.setRouletteRating(sum);
        }
    }


    public void selectNewPopulation() {

        List<Colloring> nextPopulation = new ArrayList<>(size);

        while (nextPopulation.size() != size) {
            addChildren(nextPopulation);
        }

        population = nextPopulation;


    }

    private void addChildren(List<Colloring> nextPopulation) {

        Colloring firstParent = chooser.choose(population);
        Colloring secondParent;
        do {
            secondParent = population.get(RandomHelper.giveRandomNumberFromZeroTo_N_Exclusive(size));
        } while (secondParent == firstParent);

        Colloring child;
        if (RandomHelper.IfItHappenWithAskedProbability(chanceToCross))
            child = crosser.cross(firstParent, secondParent);
        else
            child = Colloring.deepCloning(firstParent);

        Colloring childAfterMutation = Mutate(child);

        nextPopulation.add(childAfterMutation);
    }

    //Przykład polimorfizmu inkluzyjnego, późne wiązanie
    private Colloring Mutate(Colloring individual) {
        for (int i = 0; i < Colloring.getNumberOfVertex(); i++) {
            if (IsGeneShouldBeMutatet()) {
                int newValueOfGene = mutating.mutate(individual.getColorOnPosition(i));
                individual.assignColor(i, newValueOfGene);
            }
        }
        return individual;
    }

    private boolean IsGeneShouldBeMutatet() {
        if (chanceToMutation >= Math.random())
            return true;
        else
            return false;

    }

    public void print() {
        System.out.println("Drukuje osobnika");
        for (Colloring shema : population) {
            shema.print();
            System.out.println();
        }
        System.out.println();
    }

    public double getFitnessAverage() {
        return fitnessAverage;
    }
}
