package Genetic;

import Helpers.RandomHelper;

import java.util.Collections;

/**
 * Created by sanczo on 2016-03-10.
 */
public class Individual {



    public int[] gens;

    private double fitness = -1;

    private double normalizedFitness;

    private double rouletteRating;

    private static int numberOfGens;

    private static AlgorithmParameters param;

    static {
        param = AlgorithmParameters.getInstance();
    }

    public Individual() {
        gens = new int[Individual.numberOfGens];
    }
    public Individual(int numberOfGens){
        gens = new int[numberOfGens];
    }

    public static void setNumberOfGens(int numberOfGens)
    {
        Individual.numberOfGens = numberOfGens;
    }

    public static int getNumberOfGens()
    {
        return Individual.numberOfGens;
    }



    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {

        this.fitness = fitness;
    }

    public double getNormalizedFitness() {
        return normalizedFitness;
    }

    public void setNormalizedFitness(double normalizedFitness) {
        this.normalizedFitness = normalizedFitness;
    }

    public double getRouletteRating() {
        return rouletteRating;
    }

    public void setRouletteRating(double rouletteRating) {
        this.rouletteRating = rouletteRating;
    }

    public void assignGen(int index, int color)
    {
        gens[index] = color;
    }

    public int getGenAtPosition(int index)
    {
        return gens[index];
    }

    public void print() {


        System.out.println("Moje dopasowanie wynosi" + fitness);
    }


    public static Individual deepCloning(Individual protoplasta)
    {
        Individual deepClone = new Individual();

        for(int i =0;i < numberOfGens;i++)
        {
            deepClone.assignGen(i, protoplasta.getGenAtPosition(i));
        }
        return deepClone;
    }

    public void setGenAtPosition(int position, int color)
    {
        gens[position] = color;
    }


    public void mutate(boolean[] frostbite) {

        int frostMutationCounter = 1;
        int casualMutationCounter = 1;

        for (int i = 0; i < gens.length; i++)
        {
            boolean isFrozenGen = frostbite[i];
            double posibilityOfMutation = isFrozenGen ? param.getChanceToMutationFrostGen() / frostMutationCounter : param.getChanceToMutationCasualGen() / casualMutationCounter;

            if (RandomHelper.IfItHappenWithAskedProbability(posibilityOfMutation))
            {
                int maxValue = Individual.getNumberOfGens() - (i + 1);
                param.getMutating().setMinValue(0);
                param.getMutating().setMaxValue(maxValue);

                gens[i] = param.getMutating().mutate(gens[i]);
            }
            if (isFrozenGen)
                frostMutationCounter++;
            else
                casualMutationCounter++;
        }
    }

    @Override
    public String toString() {
        return Double.toString(fitness);
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Individual) {

            Individual other = (Individual) obj;

            return java.util.Arrays.equals(this.gens, other.gens);

        }
        else
            return false;
    }
}
