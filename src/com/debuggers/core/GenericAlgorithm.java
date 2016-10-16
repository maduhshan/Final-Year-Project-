package com.debuggers.core;


import java.util.Hashtable;
import java.util.TreeMap;

import com.debuggers.entity.PreferenceTable;


public class GenericAlgorithm {

	/* GA parameters */
	private static final double uniformRate = 0.5;
	private static final double mutationRate = 0.015;
	private static final int tournamentSize = 10;
	private static final boolean elitism = true;
    private int bestGeneration=0;
	
	Hashtable<String, CandidateAssignment> fittestSol = null;
	TreeMap<Integer, Integer> FitnessTable = new TreeMap<Integer, Integer>();

	static PreferenceTable p;

	/* Public methods */
	public GenericAlgorithm(int generationNo, PreferenceTable preferenceTable) {

		p = preferenceTable;
		// Implementation of GA
		// Create an initial population
		Population myPop = new Population(100, true, p);
		CandidateSolution optimalSolution=new CandidateSolution(preferenceTable);
		 
		// Evolve our population until we reach an optimum solution
		int generationCount = 0, lowestGenerationCount = 0;
		int prevFitness = myPop.getFittest().getFitness();

		for (int i = 0; i < generationNo; i++) {
			generationCount++;
//			System.out.println("Generation: " + generationCount + "Fittest:" + myPop.getFittest().getFitness());
			FitnessTable.put(generationCount, myPop.getFittest().getFitness());
			if (prevFitness <= myPop.getFittest().getFitness()) {
				lowestGenerationCount = generationCount;
				prevFitness = myPop.getFittest().getFitness();
				
				optimalSolution.setTable(myPop.getFittest().getTable());
//				System.out.println(optimalSolution.getEnergy());
			}
			// Assign the current fitness to the prevfitness variable
			myPop = evolvePopulation(myPop); // Evolve the populations
		}
		System.out.println("Solution found!" + prevFitness);
		System.out.println("Generation: " + lowestGenerationCount);
		
		setBestGeneration(lowestGenerationCount);
		fittestSol=optimalSolution.getTable();

	}


	// Evolve a population
	public static Population evolvePopulation(Population pop) {
		Population newPopulation = new Population(pop.size(), false, p);

		// Keep our best individual
		if (elitism) {
			newPopulation.saveIndividual(0, pop.getFittest());
		}

		// Crossover population
		int elitismOffset = 0;
		if (elitism) {
			elitismOffset = 1;
		}
		// Loop over the population size and create new individuals with
		// crossover
		for (int i = elitismOffset; i < pop.size(); i++) {
			CandidateSolution parentSol1 = tournamentSelection(pop);
			CandidateSolution parentSol2 = tournamentSelection(pop);
			CandidateSolution childOffspring = crossover(parentSol1, parentSol2);
			newPopulation.saveIndividual(i, childOffspring);
		}

		// Mutate population
		for (int i = elitismOffset; i < newPopulation.size(); i++) {
			mutate(newPopulation.getIndividual(i));
		}

		return newPopulation;
	}

	// Crossover individuals
	private static CandidateSolution crossover(CandidateSolution parentSol1, CandidateSolution parentSol2) {
		CandidateSolution childSol = new CandidateSolution(p);
		// Loop through genes

		childSol.ClearTable();
		while (childSol.size() < parentSol1.size() + 1) {
			// Crossover
			if (Math.random() <= uniformRate) {
				childSol.setGene(parentSol1.getRandomAssignment());
			} else {
				childSol.setGene(parentSol2.getRandomAssignment());
			}

			if (childSol.size() == parentSol1.size())
				break;
		}
		return childSol;
	}

	// Mutate an individual
	private static void mutate(CandidateSolution indiv) {
		// Loop through genes
		for (int i = 0; i < indiv.size(); i++) {
			if (Math.random() <= mutationRate) {
				CandidateAssignment ca = indiv.getRandomAssignment();
				ca.randomizeAssignment();
				indiv.setGene(ca);
			}
		}
	}

	// Select individuals for crossover
	private static CandidateSolution tournamentSelection(Population pop) {
		// Create a tournament population
		Population tournament = new Population(tournamentSize, false, p);
		// For each place in the tournament get a random individual
		for (int i = 0; i < tournamentSize; i++) {
			int randomId = (int) (Math.random() * pop.size());
			tournament.saveIndividual(i, pop.getIndividual(randomId));
		}
		// Get the fittest
		CandidateSolution fittest = tournament.getFittest();
		return fittest;

	}
	//Returnt the Optimal Solution
	public Hashtable<String, CandidateAssignment> getOptimalSolution(){
		return fittestSol;
	}
	
	//Return the Treemap having the values for charts
	public TreeMap<Integer, Integer> getOptimalValues(){
		return FitnessTable;
	}

//getter for Best Generation after Iteration of the algorithm 
	public int getBestGeneration() {
		return bestGeneration;
	}

//setter for bestgeneration
	public void setBestGeneration(int bestGeneration) {
		this.bestGeneration = bestGeneration;
	}
}