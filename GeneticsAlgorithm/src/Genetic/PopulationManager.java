package Genetic;

import Exceptions.FlatPopulationException;
import Genetic.FitnessCalculator.IFitnessCalculator;
import Genetic.GenFrostBite.IGenFrostBite;
import Genetic.VarietyGuard.IndividualCaNotBeTheSameRule;
import Genetic.VarietyGuard.NotAllowedTooManyIndividualsWithTheSameFitness;
import Genetic.VarietyGuard.VarietyGuard;
import Helpers.ProgramDependencies;
import Helpers.RandomHelper;
import Initialization.Initializer;
import org.javatuples.Pair;

import java.util.*;

/**
 * Created by sanczo on 2016-03-10.
 */
public class PopulationManager {

    private Population population;

    //private PopulationPrinter populationPrinter;

   // private TextFileWriter textFileWriter;

    private AlgorithmParameters parameters;

    private Initializer initializer;

    private IFitnessCalculator fitnessCalculator;

    private IGenFrostBite frostBiteGenReaserchear;

    private List<VarietyGuard> varietyGuards;


    //public static final String pathToFolder = "Individuals";

    public PopulationManager()
    {
        this.population = (Population) ProgramDependencies.getDependence("Population");
        //this.textFileWriter = textFileWriter;
        parameters = AlgorithmParameters.getInstance();

        initializer = parameters.getInitializer();
        fitnessCalculator = parameters.getFitnessCalculator();
        frostBiteGenReaserchear = parameters.getFrostGenBiteReasearcher();

        varietyGuards = new LinkedList<>();

        varietyGuards.add(new NotAllowedTooManyIndividualsWithTheSameFitness());
        varietyGuards.add(new IndividualCaNotBeTheSameRule());
    }

    public void selectNextGeneration() {

        List<Individual> elite = new LinkedList<>(population.getNbestIndividual(parameters.getNumberOfElite()));

        population.changeGenerations();

        elite.stream().forEach(individual -> population.add(individual));

        List<Individual> candidateForParents = chooseParents();

        List<Pair<Individual, Individual>> parentsInPairs = arrangePairs(candidateForParents);

        List<Individual> resultsOfCrossing = new ArrayList<>();

        parentsInPairs.stream().forEach(parentPair -> {
            Pair<Individual, Individual> children = produceOffSpring(parentPair);
            resultsOfCrossing.add(children.getValue0());
            resultsOfCrossing.add(children.getValue1());
        });

        List<Individual> childrenAfterMutating = proceedMutation(resultsOfCrossing);

        childrenAfterMutating.forEach(individual
                -> population
                    .add(adoptIndividualToRules(individual)));
    }

    public void setFirstGeneration() throws FlatPopulationException {
        initialPopulation();
        population.CountPopulationParameters();
    }

    private void initialPopulation() {
        for (int i = 0; i < parameters.SizeOfPopulation(); i++) {

            Individual individual = initialIndividual();

            population.add(individual);
        }
    }

    private Individual initialIndividual() {
        Individual individual;
        do {
            individual = initializer.Init();
            double fitness = fitnessCalculator.calculateFitness(individual);
            individual.setFitness(fitness);
        }while (validate(individual));
        return individual;
    }

    private List<Individual> chooseParents()
    {
        int parentsNumber = parameters.SizeOfPopulation() - parameters.getNumberOfElite();
        List<Individual> chosen = new ArrayList<>(parentsNumber);
        for(int i = 0; i < parentsNumber; i++)
        {
            if(i == parameters.getK1())
            {
                parameters.setTournamentSize((int) Math.ceil(parameters.getAverageTournamentSize()));
            }
            chosen.add(parameters.getChooser().choose(population.getOldPopulation()));
        }

        return chosen;
    }

    private List<Pair<Individual,Individual>> arrangePairs(List<Individual> candidatesForParents)
    {

        int pairsQuantity = (parameters.SizeOfPopulation() - parameters.getNumberOfElite()) / 2;
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

    private Pair<Individual, Individual> produceOffSpring(Pair<Individual, Individual> parents) {

        Individual firstParent = parents.getValue0();
        Individual secondParent = parents.getValue1();

        Pair<Individual, Individual> children;

        if(RandomHelper.IfItHappenWithAskedProbability(parameters.getChanceToCross()))
            children = parameters.getCrosser().cross(firstParent, secondParent);
        else
            children = new Pair<>(Individual.deepCloning(firstParent), Individual.deepCloning(secondParent));

        return children;
    }

    private List<Individual> proceedMutation(List<Individual> resultsOfCrossing) {

        boolean[] frostbiteGen = frostBiteGenReaserchear.assignGenFrostBite(population.getOldPopulation());

        for (Individual individual : resultsOfCrossing)
        {
            individual.mutate(frostbiteGen);
        }
        return resultsOfCrossing;
    }

    private Individual adoptIndividualToRules(Individual individual) {

        int counter = 0;

        individual.setFitness(fitnessCalculator.calculateFitness(individual));

        boolean[] frostbiteGen = frostBiteGenReaserchear.assignGenFrostBite(population.getOldPopulation());

       while (validate(individual) == false
                && counter < 5)
        {
            individual.mutate(frostbiteGen);
            individual.setFitness(fitnessCalculator.calculateFitness(individual));
            counter++;
        }

       if (counter == 5)
           individual = initialIndividual();

      return individual;
    }

    private boolean validate(Individual individual) {
        return varietyGuards.stream().allMatch(guard -> guard.isSatisfiedBy(individual, population.getPopulation()) == true);
    }

    public Population getPopulation() {
        population.sortByFitness();
        return population;
    }
}
