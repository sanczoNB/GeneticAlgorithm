package Genetic;

import Helpers.RandomHelper;

/**
 * Created by sanczo on 2016-03-10.
 */
public class Individual {



    public int[] colors;

    private int fitness = -1;

    private double normalizedFitness;

    private double rouletteRating;

    private static int numberOfGens;

    private static AlgorithmParameters param;

    static {
        param = AlgorithmParameters.getInstance();
    }

    public Individual() {
        colors = new int[Individual.numberOfGens];
    }
    public Individual(int numberOfGens){
        colors = new int[numberOfGens];
    }

    public static void setNumberOfGens(int numberOfGens)
    {
        Individual.numberOfGens = numberOfGens;
    }

    public static int getNumberOfGens()
    {
        return Individual.numberOfGens;
    }



    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
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

    public void assignColor(int index, int color)
    {
        colors[index] = color;
    }

    public int getColorOnPosition(int index)
    {
        return colors[index];
    }

    public void print() {


        System.out.println("Moje dopasowanie wynosi" + fitness);
    }


    public static Individual deepCloning(Individual protoplasta)
    {
        Individual deepClone = new Individual();

        for(int i =0;i < numberOfGens;i++)
        {
            deepClone.assignColor(i, protoplasta.getColorOnPosition(i));
        }
        return deepClone;
    }

    public void setColorAtPosition(int position, int color)
    {
        colors[position] = color;
    }

    public int getNumberOfVertex()
    {
        return colors.length;
    }


    public void mutate(boolean[] frostbite) {

        int frostMutationCounter = 1;
        int casualMutationCounter = 1;

        for (int i = 0; i < colors.length; i++)
        {
            boolean isFrozenGen = frostbite[i];
            double posibilityOfMutation = isFrozenGen ? param.getChanceToMutationFrostGen() / frostMutationCounter : param.getChanceToMutationCasualGen() / casualMutationCounter;

            if (RandomHelper.IfItHappenWithAskedProbability(posibilityOfMutation))
            {
                colors[i] = AlgorithmParameters.getInstance().getMutating().mutate(colors[i]);
            }
            if (isFrozenGen)
                frostMutationCounter++;
            else
                casualMutationCounter++;
        }
    }

    @Override
    public String toString() {
        return Integer.toString(fitness);
    }
}
