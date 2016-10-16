package com.debuggers.main;

import java.util.Random;


import com.debuggers.core.CandidateSolution;
import com.debuggers.core.DisplayOptimalSolution;
import com.debuggers.core.GenericAlgorithm;
import com.debuggers.core.SimulatedAnnealing;
import com.debuggers.entity.PreferenceTable;


public class Main {
	public static Random RND = new Random();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PreferenceTable p = new PreferenceTable("Project allocation data.tsv");
		p.fillPreferencesOfAll(10);	
		
		//System.out.println(p.getAllStudentEntries());
		CandidateSolution sol = new CandidateSolution(p);
		String name = p.getRandomStudent().getStudentName();
		System.out.println("Assignment: "+sol.getAssignmentFor(name));
		System.out.println("Randon Assignment: "+sol.getRandomAssignment());	
		System.out.println("Fitness "+sol.getFitness());
		
		GenericAlgorithm GA= new GenericAlgorithm(25,p);
		CandidateSolution caga = new CandidateSolution(p);
		caga.setTable(GA.getOptimalSolution());
		System.out.println("GA"+caga.getEnergy());
		
		DisplayOptimalSolution Display1 = new DisplayOptimalSolution(GA.getOptimalSolution(), GA.getOptimalValues(),"Generic Algorithm");
	//	Display1.DisplayTheFittestSolution();
		Display1.CreateLineChart();
		Display1.getStudentAssignmentsByPreference();
		Display1.GenerateOptimalSolutionReport();
        Display1.GenerateProjectDistributionOnStudnets();
		
        SimulatedAnnealing sa = new SimulatedAnnealing(p);
		CandidateSolution cagac = new CandidateSolution(p);
		cagac.setTable(sa.getOptimalSolution());
		System.out.println("sa"+cagac.getEnergy());
		
        DisplayOptimalSolution Display = new DisplayOptimalSolution(sa.getOptimalSolution(), sa.getOptimalValues(),"Simulated Annealing");
		//Display.DisplayTheFittestSolution();
		Display.CreateLineChart();
		Display.getStudentAssignmentsByPreference();
		Display.GenerateOptimalSolutionReport();
		Display.GenerateProjectDistributionOnStudnets();
	}

}
