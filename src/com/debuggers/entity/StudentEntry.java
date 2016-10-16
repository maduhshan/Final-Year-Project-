/*
/// Software Engineering project II - Stochastic Search Project Allocation System
/// Debuggers 
/// Date                      Author/(Reviewer)                  Description
/// --------------------------------------------------------------------------         
/// 12 Jun 2016               Vishan Abeyrathna                  Creation
/// 17 Jun 2016               Shanalie Silva                     Added (Line 65-76)
/// 24 Jun 2016               Shanalie Silva                     Added (Line 81-104)
 */
package com.debuggers.entity;

import java.util.Vector;

import com.debuggers.gui.MainScreen;
//import com.debuggers.main.Main;

public class StudentEntry {

	private String name;
	private boolean hasProjectPreassigned;
	private Vector<String> orderedPreferences;  
	private int numberOfStatedPreferences; 
	private String projectAssigned = null;
	private int disapointmentScore =0;
	
	public StudentEntry(String n) {
		name = n;
	}
	
	/**
	 * Get the studnet name
	 * @return Name of the student
	 */
	public String getStudentName() {
		return name;
	}
	/**
	 *Set has project pre assigned 
	 *@param  true or false
	 */
	public void setHasProjectPreassigned(boolean bool) {
		hasProjectPreassigned = bool;
	}
	/**
	 *Set OrderedPreference 
	 *@param  Vector of preference list
	 */
	public void setOrderedPreferences(Vector<String> v) {
		orderedPreferences = v;
		numberOfStatedPreferences = orderedPreferences.size();
	}
	/**
	 *getters for ordered preferences 
	 *@return  vector list of preferences
	 */
	public Vector<String> getOrderedPreferences() {
		return orderedPreferences;
	}
	/**
	 * PreAssign a project to the studnet
	 *@param string of project name
	 */
	public void preassignProject(String pname) {
		if(orderedPreferences.size()==1 && hasProjectPreassigned) {
			projectAssigned = pname;
		}
	}
	/**
	 * Check whether the student has preassigned project
	 *@param string of project name
	 */
	public boolean hasPreassignedProject() {
		if(hasProjectPreassigned && projectAssigned != null) {
			return true;
		}
		return false;
	}
	/**
	 * Get the number of stated Preference by student
	 *@param number of stated preference
	 */
	public int getNumberOfStatedPreferences() {
		return numberOfStatedPreferences;
	}
	/**
	 * Add a project to the ordered Preferences
	 *@param string of project name
	 */
	public void addProject(String pname) {
		if(!hasPreference(pname)) {
			orderedPreferences.addElement(pname);
		}
	}
	/**
	 * Get a random preference from the orderedPerferences
	 *@return string of project name
	 */
	public String getRandomPreference() {
		return orderedPreferences.get(MainScreen.RND.nextInt(orderedPreferences.size()));
	}
	/**
	 * Check whether the paramater prefences includes in the orderedPreference
	 *@param true if include false if not included 
	 */
	public boolean hasPreference(String preference) {
		return orderedPreferences.contains(preference.intern());
	}
	
	
	/**
	 * Checks whether the project is in the ordered list of this student
	 * if not return -1
	 * if it is then check whether its already assigned to this student or top-ranked if it is true return 0
	 * if not return the element number in the list 
     * @param project name 
     * @return Return the rank
     */
	public int getRanking(String project){
		if(hasPreassignedProject()){
			return 0;
		}else if(hasPreference(project.intern())){
			return orderedPreferences.indexOf(project.intern()); 			
		}else{
			return -1;
		}
	}

	/**
	 *Get the disspoinment Score
	 *@return dispointmentScore
	 */
	public int getDisapointmentScore() {
		return disapointmentScore;
	}
	/**
	 * Set the disapointment Score
	 */
	public void setDisapointmentScore() {
		if(hasProjectPreassigned){
			this.disapointmentScore =0;
		}else{
			this.disapointmentScore = 10 * numberOfStatedPreferences;
		}
	
	}
	public String toString() {
		return "Name: "+name+" prefereces:"+getOrderedPreferences();
	}
}
