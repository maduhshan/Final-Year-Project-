package com.debuggers.core;


import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

import org.jfree.ui.RefineryUtilities;

import com.debuggers.charts.BarChart_AWT;
import com.debuggers.charts.LineChart_AWT;
import com.debuggers.reports.WriteExcel;
import com.debuggers.reports.WritePDF;




public class DisplayOptimalSolution {
	Hashtable<String, CandidateAssignment> fittestSol = null;
	TreeMap<Integer, Integer> FitnessTable = new TreeMap<Integer, Integer>();
	String Algorithm;

	public DisplayOptimalSolution(Hashtable<String, CandidateAssignment> fitSol, TreeMap<Integer, Integer> FitnessTab,
			String AlgorithmName) {
		fittestSol = fitSol;
		FitnessTable = FitnessTab;
		Algorithm = AlgorithmName;

	}

	// Display the fittest solution in excel format
	public String DisplayTheFittestSolution() {	
		WriteExcel e = new WriteExcel();
		try {
			return e.writeBestSolution(fittestSol, Algorithm);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}

	// Generate a Line Chart using the statics of excuting the algorithm few times vs each outcome 
	public void CreateLineChart() {

		LineChart_AWT chart;
		try {
			if(Algorithm.intern().equals("Generic Algorithm")){
				chart = new LineChart_AWT("Algorithm Excution Statistics for" + Algorithm, "Number of Generations Vs Fitness",
						FitnessTable, Algorithm);
			}else{
				chart = new LineChart_AWT("Algorithm Excution Statistics for" + Algorithm, "Number of Execution  vs Energy",
						FitnessTable, Algorithm);
			}
			
			chart.pack();
			RefineryUtilities.centerFrameOnScreen(chart);
			chart.setVisible(true);
		} catch (IOException e) {
			System.out.println("Error Occured!");
			e.printStackTrace();
		}
	}
//Generate a Bar chart to check how many student's got their preferred preferences as their final allocated Project.
//Eg , Number of students who got allocated with their first preference. or the second. 
	public void CreateBarChart(TreeMap<Integer, Integer> totalCountForEachPreferences) {
		BarChart_AWT chart = new BarChart_AWT("Preferences Selection Statistics", "Analysis of allocated preferences",
				totalCountForEachPreferences, Algorithm);
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);

	}
//Generate a Report to show the list of students who got allocated with project which they didn't initally preffer (Not in the their Inital Prefrence List)
	public Vector<CandidateAssignment> getStudentAssignmentsByPreference() {
		TreeMap<Integer, Integer> totalCountForEachPreferences = new TreeMap<Integer, Integer>();
		for (int i = 0; i < 11; i++) {
			totalCountForEachPreferences.put(i, 0);
		}

		Vector<CandidateAssignment> studentwhoGotwhatTheyDidnotpreffer = new Vector<CandidateAssignment>();
		Iterator<String> it = fittestSol.keySet().iterator();
		while (it.hasNext()) {
			String studentName = it.next();
			boolean hasPerferedProejct = fittestSol.get(studentName).HasAssignedProjectPreferedByStudent();
			if (!hasPerferedProejct) {

				totalCountForEachPreferences.put(10, totalCountForEachPreferences.get(10) + 1);
				studentwhoGotwhatTheyDidnotpreffer.add(fittestSol.get(studentName));
			} else {
				int rank = fittestSol.get(studentName).getStudentEntry()
						.getRanking(fittestSol.get(studentName).getAssignedProject());
				totalCountForEachPreferences.put(rank, totalCountForEachPreferences.get(rank) + 1);
			}
		}
		CreateBarChart(totalCountForEachPreferences);
		WriteExcel ex = new WriteExcel();
		ex.printAllocationsnotpreferredbystudent(studentwhoGotwhatTheyDidnotpreffer,Algorithm);
		return studentwhoGotwhatTheyDidnotpreffer;
	}
//Generate Report to show the Project wise distribution among students
	//Eg.How many students got Project X and who are they 
	public String GenerateProjectDistributionOnStudnets() {
		Hashtable<String, Vector<String>> projectdistribution = new Hashtable<String,  Vector<String>>();
		Iterator<String> it = fittestSol.keySet().iterator();
		while (it.hasNext()) {
			Vector<String> studentNames=new Vector<String>();
			String studentName = it.next();
			if (projectdistribution.containsKey(fittestSol.get(studentName).getAssignedProject())) {
				Vector<String> exisitng = projectdistribution.get(fittestSol.get(studentName).getAssignedProject());
				exisitng.add(studentName);
				projectdistribution.replace(fittestSol.get(studentName).getAssignedProject(), exisitng);
			} else {
				studentNames.add(studentName);
				projectdistribution.put(fittestSol.get(studentName).getAssignedProject().intern(),studentNames);
			}
		}
	

		WriteExcel e = new WriteExcel();
		try {
			String path  = e.writeProjectDistribution(projectdistribution, Algorithm);
			return path;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
    
	}
//Generate a PDF using the Optimal Solution 
	public String GenerateOptimalSolutionReport() {
		WritePDF pdf= new WritePDF();
		return pdf.GenerateFittestSolutionPDF(fittestSol,Algorithm);
	}
}
