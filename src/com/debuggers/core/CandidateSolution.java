/*
/// Software Engineering project II - Stochastic Search Project Allocation System
/// Debuggers 
/// Date                      Author/(Reviewer)                  Description
/// --------------------------------------------------------------------------         
/// 19 Jun 2016               Shanalie Silva                     Creation and  Added Line(16-33)
/// 24 Jun 2016               Shanalie Silva                     Added Line(44-69)
 */
package com.debuggers.core;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import com.debuggers.entity.PreferenceTable;
import com.debuggers.entity.StudentEntry;
import com.debuggers.gui.MainScreen;
//import com.debuggers.main.Main;

public class CandidateSolution {

	private Hashtable<String, CandidateAssignment> table;
	private final int PENALTY =1000;

	
	/**
     *Constructor for CandidateSolution which will create table of candidateAssignments  using the preference table
     */
	public CandidateSolution(PreferenceTable p) {
		this.table = new Hashtable<String, CandidateAssignment>();
		for(StudentEntry student : p.getAllStudentEntries()) {
			table.put(student.getStudentName(), new CandidateAssignment(student));
		}
	}

	/**
     * To get the candidateAssignemt for a particular student 
     * @param Student's name 
     * @return the candidateAssignment for the student name passed
     */
	public CandidateAssignment getAssignmentFor(String sname) {
		return table.get(sname);
	}
	
	/**
     * Randomly pick a candidateAssignment object from the table 
     * @return the candidateAssignment randomly from the table 
     */
	public CandidateAssignment getRandomAssignment() {
		Vector<String> keySet = new Vector<String>(table.keySet());
		return table.get(keySet.elementAt(MainScreen.RND.nextInt(keySet.size())));
	}
    /**
     * Creates a new hashtable call "assignProjects" 
     * Loop the "table" hastable and each candidateAssignment object
     * Use that object get the relevent Energy and add to the totalEnergy of all candidateAssignments
     * Get the currentpreference of that object and check for replicated occurrences of the same project
     * if its the first occurence add the project to assisgnedprojects hashtable
     * if not charge a pentality for each occurence 
     * @return sum of the energy of every assignment made by sol plus the sum of all penalties applied for violations 
     */
	
	public int getEnergy() {
		int energy = 0, counter = 0;
		Iterator<String> it = table.keySet().iterator();
		Hashtable<String, Integer> collisionCounter = new Hashtable<String, Integer>();
		
		while (it.hasNext()) {
			String sname = it.next();
			energy += table.get(sname).getEnergy();
			if (!collisionCounter.containsKey(table.get(sname).getAssignedProject()))  {
				collisionCounter.put(table.get(sname).getAssignedProject(), 1);
			} else {
				counter++;
			}
        }
		energy += (counter*PENALTY);
		return energy;
	}
	 /**
     * Call the getEnergy function and get the value 
     * Do a simple inversion so that a high score becomes a low score and so that a low score becomes a high score
     * @return Result value of the inversion
     */
	public int getFitness(){
		return -(getEnergy());
	}
	 /**
     *	Get the Size of the table
     * @return the size of the table 
     */
	public int size() {
		return table.size();
	}
	 /**
     * Call the clearTable to clear the Table hashtable
     */
	public void ClearTable(){
		table.clear();
	}
	 /**
     * To add new genes to the table
     */
	public void setGene(CandidateAssignment ca){
		this.table.put(ca.getStudentEntry().getStudentName(),ca);
	}
	 /**
     * Getters for the table
     * @return the table
     */
	public Hashtable<String, CandidateAssignment> getTable(){
		return table;
	}
	 /**
     * Set a hashtable to this.table
     * @param the table
     */
	public void  setTable(Hashtable<String,CandidateAssignment> tableSol ){
		Iterator<String> it = table.keySet().iterator();
		while (it.hasNext()) {
			String sname = it.next();
			CandidateAssignment ca= table.get(sname);
			ca.setcurrentPreference(tableSol.get(sname).getAssignedProject());
        }
	}
	 /**
     * Randomly selects an assignment and get a new preference assigned to it
     * Check whether the new prefrence is better than the previouse prefrence if only assign it
     * Put the newely assigned assignement back to the table
     * @param the table
     */
	public CandidateAssignment randomizeAssignment(){
		CandidateAssignment ca = getRandomAssignment();
		int oldPrefEngery = ca.getEnergy();
		ca.randomizeAssignment();
		int newPrefEngery = ca.getEnergy();
		if(oldPrefEngery<newPrefEngery){
			ca.undoChange();
		}
		this.table.put(ca.getStudentEntry().getStudentName().intern(), ca);
		return ca;
	}
}