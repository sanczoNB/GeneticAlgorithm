package Genetic;

import Helpers.RandomHelper;
import Helpers.TextFileWriter;

/**
 * Created by sanczo on 2016-03-10.
 */
public class Colloring {

    public static int colorsNumber;

    private int[] colors;

    private int fitness;

    private double normalizedFitness;

    private double rouletteRating;

    private static int numberOfVertex;

    public double moveFitness1;

    public double moveFitness2;




    public Colloring() {
        colors = new int[Colloring.numberOfVertex];
    }
    public Colloring(int numberOfVertex){
        colors = new int[numberOfVertex];
    }

    public static void setNumberOfVertex(int numberOfVertex)
    {
        Colloring.numberOfVertex = numberOfVertex;
    }

    public static int getNumberOfVertex()
    {
        return Colloring.numberOfVertex;
    }

    public void initialColors()
    {
        for(int i =0; i < colors.length; i++)
        {
            colors[i] = RandomHelper.giveRandomNumberFromZeroTo_N_Exclusive(colorsNumber);

        }
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


    public static Colloring deepCloning(Colloring protoplasta)
    {
        Colloring deepClone = new Colloring();

        for(int i =0;i < numberOfVertex ;i++)
        {
            deepClone.assignColor(i, protoplasta.getColorOnPosition(i));
        }
        return deepClone;
    }

    public void setColorAtPosition(int position, int color)
    {
        colors[position] = color;
    }

    public int getActualNumberOfVertex()
    {
        return colors.length;
    }


}
