package Genetic;

import Choosers.Chooser;
import Crossers.Crosser;
import Initialization.Initializer;
import Mutatet.Mutating;

/**
 * Created by sanczo on 2016-04-05.
 */
public class AlgorithmParameters {

    private static AlgorithmParameters instance;

    private int sizeOfPopulation;

    private double chanceToMutationCasualGen;

    private double chanceToMutationFrostGen;

    private double chanceToCross;

    private Chooser chooser;

    private Crosser crosser;

    private Mutating mutating;

    private Initializer initializer;

    private int maxGenValue;

    private int maxNumberOfIterationsWithoutProgress;

    private int maxNumberIndividualWithTheSameFitness;

    private int numberOfElite;

    private double averageTournamentSize;

    //How many times is held first type of tournamet
    private int k1 = -1;

    //How many times is held first type of tournamet
    private int k2 = -1;

    private int tournamentSize;


    private AlgorithmParameters()
    {

    }

    public static AlgorithmParameters getInstance()
    {
        if (instance == null)
            instance = new AlgorithmParameters();
        return instance;
    }

    public int getSizeOfPopulation() {
        return sizeOfPopulation;
    }

    public void setSizeOfPopulation(int sizeOfPopulation) {
        this.sizeOfPopulation = sizeOfPopulation;
    }

    public double getChanceToMutationCasualGen() {
        return chanceToMutationCasualGen;
    }

    public void setChanceToMutationCasualGen(double chanceToMutation) {
        this.chanceToMutationCasualGen = chanceToMutation;
    }

    public double getChanceToCross() {
        return chanceToCross;
    }

    public void setChanceToCross(double chanceToCross) {
        this.chanceToCross = chanceToCross;
    }

    public Chooser getChooser() {
        return chooser;
    }

    public void setChooser(Chooser chooser) {
        this.chooser = chooser;
    }

    public Crosser getCrosser() {
        return crosser;
    }

    public void setCrosser(Crosser crosser) {
        this.crosser = crosser;
    }

    public Mutating getMutating() {
        return mutating;
    }

    public void setMutating(Mutating mutating) {
        this.mutating = mutating;
    }

    public Initializer getInitializer() {
        return initializer;
    }

    public void setInitializer(Initializer initializer) {
        this.initializer = initializer;
    }

    public int getMaxGenValue() {
        return maxGenValue;
    }

    public void setMaxGenValue(int maxGenValue) {
        this.maxGenValue = maxGenValue;
    }

    public int getMaxNumberOfIterationsWithoutProgress() {
        return maxNumberOfIterationsWithoutProgress;
    }

    public void setMaxNumberOfIterationsWithoutProgress(int maxNumberOfIterationsWithoutProgress) {
        this.maxNumberOfIterationsWithoutProgress = maxNumberOfIterationsWithoutProgress;
    }

    public int getMaxNumberIndividualWithTheSameFitness() {
        return maxNumberIndividualWithTheSameFitness;
    }

    public void setMaxNumberIndividualWithTheSameFitness(int maxNumberIndividualWithTheSameFitness) {
        this.maxNumberIndividualWithTheSameFitness = maxNumberIndividualWithTheSameFitness;
    }

    public int getNumberOfElite() {
        return numberOfElite;
    }

    public void setNumberOfElite(int numberOfElite) {
        this.numberOfElite = numberOfElite;
    }

    public void setAverageTournamentSize(double averageTournamentSize) {
        this.averageTournamentSize = averageTournamentSize;
        this.tournamentSize = (int)Math.floor(averageTournamentSize);
    }

    public void countTournametParametrs()
    {
        int averageFlor = (int) Math.floor(averageTournamentSize);
        int numberOfNonElite = sizeOfPopulation - numberOfElite;

        k1 = (int)Math.round((averageTournamentSize  - averageFlor) * numberOfNonElite);
        k2 = numberOfNonElite - k1;
    }

    public int getK1() {
        if(k1 == -1)
            countTournametParametrs();
        return k1;
    }

    public int getK2() {
        if (k2 == -1)
            countTournametParametrs();
        return k2;
    }

    public int getTournamentSize() {
        return tournamentSize;
    }

    public void setTournamentSize(int tournamentSize) {
        this.tournamentSize = tournamentSize;
    }

    public double getAverageTournamentSize() {
        return averageTournamentSize;
    }

    public double getChanceToMutationFrostGen() {
        return chanceToMutationFrostGen;
    }

    public void setChanceToMutationFrostGen(double chanceToMutationFrostGen) {
        this.chanceToMutationFrostGen = chanceToMutationFrostGen;
    }
}
