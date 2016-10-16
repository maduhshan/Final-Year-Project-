/*
/// Software Engineering project II - Stochastic Search Project Allocation System
/// Debuggers 
/// Date                      Author/(Reviewer)                  Description
/// --------------------------------------------------------------------------         
/// 19 Jun 2016               Shanalie Silva                     Creation and Added Line(12-42)
/// 24 Jun 2016               Shanalie Silva                     Added Line(44-46)
 */
package com.debuggers.core;

import com.debuggers.entity.StudentEntry;

public class CandidateAssignment {

	private StudentEntry student;
	private String previousPreference, currentPreference;
	 /**
     * constructor to map the student and preference 
     */
	public CandidateAssignment(StudentEntry s) {
		student = s;
		randomizeAssignment();
	}
	
	 /**
     * randomly assign a project to a student  
     */
	public void randomizeAssignment() {
		previousPreference = currentPreference;
		currentPreference = student.getRandomPreference().intern();
	}
	 /**
     * Undo changes back to previouse assigned project 
     */ 
	public void undoChange() {
		currentPreference = previousPreference;
	}
	
	public void setcurrentPreference(String current){
		currentPreference=current;
	}
	 /**
     * Getters for StudentEntry object
     * @return Student object
     */
   	public StudentEntry getStudentEntry() {
		return student;
	}
	 /**
     * Getters for Assigned Project
     * @return the currentPreference
     */
    public String getAssignedProject() {
		return currentPreference;
	}
	 /**
     * Calculates the Energy using the rank in the studentEntry
     * @return the engery of the student's currentPreference
     */
	public int getEnergy() {
		return (int) Math.pow(student.getRanking(currentPreference) + 1, 2);
	}
	
	 /**
     * Check whether the assigned current project was initally preferred by the student
     * @return the whether student has initally preffered the current project 
     */
	public boolean HasAssignedProjectPreferedByStudent() {
		if (student.getNumberOfStatedPreferences() - 1 >= student.getRanking(currentPreference)
				|| student.hasPreassignedProject()) {
			//Student has initally preferred the project
			return true;
		} else {
			//Studnet hasn't initally preferred hence calculate the disppoinmentScore
			student.setDisapointmentScore();
			return false;
		}

	}

	 /**
     * toString for CandidateAssignment
     */
	public String toString() {
		return student.getStudentName() + ": " + currentPreference;
	}

}
