package com.debuggers.core;

import com.debuggers.entity.PreferenceTable;

public class Population {

    CandidateSolution[] individuals;
    /*
     * Constructors
     */
    // Create a population
    public Population(int populationSize, boolean initialise,PreferenceTable p) {
        individuals = new CandidateSolution[populationSize];
        // Initialise population
        if (initialise) {
            // Loop and create individuals
            for (int i = 0; i < size(); i++) {
                CandidateSolution newIndividual = new CandidateSolution(p);
                saveIndividual(i, newIndividual);
            }
        }
    }

    /* Getters */
    public CandidateSolution getIndividual(int index) {
        return individuals[index];
    }

    public CandidateSolution getFittest() {
        CandidateSolution fittest = individuals[0];
        // Loop through individuals to find fittest
        for (int i = 0; i < size(); i++) {
            if (fittest.getFitness() <= getIndividual(i).getFitness()) {
                fittest = getIndividual(i);
            }
        }
        return fittest;
    }

    /* Public methods */
    // Get population size
    public int size() {
        return individuals.length;
    }

    // Save individual
    public void saveIndividual(int index, CandidateSolution indiv) {
        individuals[index] = indiv;
    }
}
