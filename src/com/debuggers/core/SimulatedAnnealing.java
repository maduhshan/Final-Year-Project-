package com.debuggers.core;

import java.util.Hashtable;
import java.util.TreeMap;

import com.debuggers.entity.PreferenceTable;

public class SimulatedAnnealing {

	Hashtable<String, CandidateAssignment> BestSol = null;
	Hashtable<String, CandidateAssignment> fittessSol = null;
	TreeMap<Integer, Integer> FitnessTable = new TreeMap<Integer, Integer>();
	int BestCost = 0;
	int ExecutionNo=0;



	public SimulatedAnnealing(PreferenceTable preferenceTable) {
		int cost = 0;
		for (int i = 1; i < 11; i++) {
			cost = AnnealingScheduling(preferenceTable);
			FitnessTable.put(i, cost);
			if (BestCost > cost || i == 1) {
				BestCost = cost;
				setExecutionNo(i);
				fittessSol = BestSol;
			}
		}
		CandidateSolution cs = new CandidateSolution(preferenceTable);
		cs.setTable(fittessSol);
		System.out.println("Engery : " + cs.getEnergy());
		System.out.println("minimum value : " + BestCost);

	}

	private int AnnealingScheduling(PreferenceTable preferenceTable) {

		// Set initial temp
		double temp = 1000;
		// Cooling rate
		double coolingRate = 0.03;

		// Initialize initial solution
		CandidateSolution currentSolution = new CandidateSolution(preferenceTable);
			
//		// get current cost
//		setBestCost(currentSolution.getEnergy());

		// Set as current best
		CandidateSolution bestSolution = new CandidateSolution(preferenceTable);
		bestSolution.setTable(currentSolution.getTable());
				
		// Loop until system has cooled
		while (temp > 1) {
			   
			// Create new neighbour tour
			CandidateSolution newSolution = new CandidateSolution(preferenceTable);
			newSolution.setTable(currentSolution.getTable());

			// Get the cities at selected positions in the tour
			newSolution.randomizeAssignment();
			newSolution.randomizeAssignment();

			// Get energy of solutions
			// Decide if we should accept the neighbour
			if (acceptanceProbability(currentSolution.getEnergy(), newSolution.getEnergy(), temp) > Math.random()) {
				currentSolution.setTable(newSolution.getTable());
				//currentSolution = newSolution;
			}

			// Keep track of the best solution found
			if (currentSolution.getEnergy() < bestSolution.getEnergy()) {
				bestSolution.setTable(currentSolution.getTable());
				//bestSolution = currentSolution;
			}

			// Cool system
			temp *= 1 - coolingRate;
		
		}

		//System.out.println("Final solution energy: " + bestSolution.getEnergy());
		BestSol = bestSolution.getTable();
		return bestSolution.getEnergy();
	}

	// Calculate the acceptance probability
	public static double acceptanceProbability(int energy, int newEnergy, double temperature) {
		// If the new solution is better, accept it
		if (newEnergy < energy) {
			return 1.0;
		}
		// If the new solution is worse, calculate an acceptance probability
		return Math.exp((energy - newEnergy) / temperature);
	}
	
	//Returnt the Optimal Solution
	public Hashtable<String, CandidateAssignment> getOptimalSolution(){
		return fittessSol;
	}
	//Return the Treemap having the values for charts
	public TreeMap<Integer, Integer> getOptimalValues(){
		return FitnessTable;
	}
	//getter for Best Execution after Iteration of the algorithm 
	public int getExecutionNo() {
		return ExecutionNo;
	}
//set for the executionNo
	public void setExecutionNo(int executionNo) {
		ExecutionNo = executionNo;
	}

}