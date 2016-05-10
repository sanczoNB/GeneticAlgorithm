package Genetic.VarietyGuard;

import Genetic.AlgorithmParameters;
import Genetic.Individual;
import Genetic.Observer.NewIndividualInPopulationObserver;

import java.util.*;

/**
 * Created by sanczo on 2016-05-05.
 */
public class NotAllowedTooManyIndividualsWithTheSameFitness implements VarietyGuard, NewIndividualInPopulationObserver {

    private Set<Double> bannedFitness;

    private Map<Double, Integer> candidatesToBan;

    private AlgorithmParameters parameters;

    public NotAllowedTooManyIndividualsWithTheSameFitness(){

        bannedFitness = new HashSet<>();
        candidatesToBan = new HashMap<>();

        parameters = AlgorithmParameters.getInstance();

    }

    @Override
    public boolean isSatisfiedBy(Individual individual, List<Individual> population) {

        double fitness = individual.getFitness();

        return bannedFitness.contains(fitness);


    }

    private void updateBanedFitness(double fitness){
        if (! bannedFitness.contains(fitness))
        {
            Integer numberOfFault = candidatesToBan.get(fitness);
            if (numberOfFault == null)
                candidatesToBan.put(fitness, 1);
            else
                candidatesToBan.put(fitness, numberOfFault +1);

            if(candidatesToBan.get(fitness) == parameters.getMaxNumberIndividualWithTheSameFitness())
                bannedFitness.add(fitness);
        }
        else {
            Integer numberOfFault = candidatesToBan.get(fitness);
            candidatesToBan.put(fitness, numberOfFault + 1);
        }
    }

    public void nextPopulation()
    {

        bannedFitness.clear();
        candidatesToBan.clear();

    }

    @Override
    public void individualAdded(Individual individual) {
        updateBanedFitness(individual.getFitness());
    }
}
