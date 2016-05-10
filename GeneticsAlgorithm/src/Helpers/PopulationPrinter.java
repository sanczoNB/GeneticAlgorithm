package Helpers;

import Genetic.Individual;
import Genetic.Population;

import java.io.*;
import java.util.List;

/**
 * Created by sanczo on 2016-04-27.
 */
public class PopulationPrinter {

    private String directoryPath = ".";
    private PrintWriter writer;

    public PopulationPrinter(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public PopulationPrinter() {
    }

    public void printPopulation(Population p)
    {
        File filePath = new File(directoryPath+"/Population"+p.getPopulationNumber()+".txt");
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(filePath)));

            writer.println("Log dla populacji nr. " + p.getPopulationNumber());
            writer.println("Najlepszy osobnik: " + p.getBestIndividual()+"\t");
            writer.print("Najgorszy osobnik: " + p.getWorstIndividual() + "\t");
            writer.print("Srednia w populacj: " + p.getFitnessAverage()+"\n");

            List<Individual> population = p.getPopulation();

            population.stream().forEach(individual -> writer.println(java.util.Arrays.toString(individual.gens)));

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
