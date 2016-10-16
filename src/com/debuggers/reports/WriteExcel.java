package com.debuggers.reports;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.debuggers.core.CandidateAssignment;

public class WriteExcel {
	private Map<String, Object[]> info;

	public String writeBestSolution(Hashtable<String, CandidateAssignment> fittestSol, String algorithm)
			throws Exception {
		// Create blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		// Create a blank sheet
		XSSFSheet spreadsheet = workbook.createSheet("Valid Solution with Assessment Quality");
		// Create row object
		XSSFRow row;
		// This data needs to be written (Object[])
		info = new TreeMap<String, Object[]>();

		info.put("1", new Object[] { "STUDENT NAME", "PROJECT ALLOCATED", "ASSESSMENT QUALITY (100-High,1-Low)" });

		Iterator<String> it = fittestSol.keySet().iterator();
		int i = 2;
		while (it.hasNext()) {
			String studentName = it.next();
			info.put(String.valueOf(i), new Object[] { studentName, fittestSol.get(studentName).getAssignedProject(),(100/(double) fittestSol.get(studentName).getEnergy())/100 });
			i++;
		}
		// Iterate over data and write to sheet
		Set<String> keyid = info.keySet();
		int rowid = 0;
		for (String key : keyid) {
			row = spreadsheet.createRow(rowid++);
			Object[] objectArr = info.get(key);
			int cellid = 0;
			for (Object obj : objectArr) {
				Cell cell = row.createCell(cellid++);
				try {
					cell.setCellValue((String) obj);
				} catch (ClassCastException ex) {
					cell.setCellValue((Double) obj);
					CellStyle style = workbook.createCellStyle();
					style.setDataFormat(workbook.createDataFormat().getFormat("0%"));
					cell.setCellStyle(style);
					
				}

			}
		}
		// Write the workbook in file system
		FileOutputStream out = new FileOutputStream(new File("reports\\Optimal Solution using " + algorithm + ".xlsx"));
		
		workbook.write(out);
		out.close();
		workbook.close();
		return new File("reports\\Optimal Solution using " + algorithm + ".xlsx").getAbsolutePath();

	}

	public String writeProjectDistribution(Hashtable<String, Vector<String>> projectdistribution, String algorithm)
			throws Exception {
		// Create blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		// Create a blank sheet
		XSSFSheet spreadsheet = workbook.createSheet("Pojects Allocations for Student");
		// Create row object
		XSSFRow row;
		// This data needs to be written (Object[])
		info = new TreeMap<String, Object[]>();

		info.put("1", new Object[] { "PROJECT NAME", "NO OF STUDENTS" ,"STUDENT NAME 1","STUDENT NAME 2"});

		int i = 2;
		Iterator<String> it1 = projectdistribution.keySet().iterator();
		while (it1.hasNext()) {
			String projectName = it1.next();
			Vector<String> studentNames = projectdistribution.get(projectName);
			int no= 2+studentNames.size();
			Object[] values=new Object[no];
			values[0]=projectName;
			values[1]=studentNames.size();
			
			for(int c=2;c<no;c++){
				values[c]=studentNames.get(c-2);
			}
			info.put(String.valueOf(i),values);
			i++;
		}
		// Iterate over data and write to sheet
		Set<String> keyid = info.keySet();
		int rowid = 0;
		for (String key : keyid) {
			row = spreadsheet.createRow(rowid++);
			Object[] objectArr = info.get(key);
			int cellid = 0;
			for (Object obj : objectArr) {
				Cell cell = row.createCell(cellid++);
				try {
					cell.setCellValue((String) obj);
				} catch (ClassCastException ex) {
					cell.setCellValue((Integer) obj);
				}

			}
		}
		// Write the workbook in file system
		FileOutputStream out = new FileOutputStream(
				new File("reports\\Project Allocation using " + algorithm + ".xlsx"));
	
		workbook.write(out);
		out.close();
		workbook.close();
		return new File("reports\\Project Allocation using " + algorithm + ".xlsx").getAbsolutePath();

	}

	public void printAllocationsnotpreferredbystudent(Vector<CandidateAssignment> studentwhoGotwhatTheyDidnotpreffer,
			String algorithm) {

		// Create blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		// Create a blank sheet
		XSSFSheet spreadsheet = workbook.createSheet("Allocations Didn't Preffer by Studnet");
		// Create row object
		XSSFRow row;
		// This data needs to be written (Object[])
		info = new TreeMap<String, Object[]>();

		info.put("1", new Object[] { "STUDENT NAME", "PROJECT ALLOCATED", "DISSPOINTMENT RATE" });

		int i = 2;
		for (CandidateAssignment ca : studentwhoGotwhatTheyDidnotpreffer) {
			info.put(String.valueOf(i), new Object[] { ca.getStudentEntry().getStudentName(), ca.getAssignedProject(),
					ca.getStudentEntry().getDisapointmentScore() });
			i++;
		}

		// Iterate over data and write to sheet
		Set<String> keyid = info.keySet();
		int rowid = 0;
		for (String key : keyid) {
			row = spreadsheet.createRow(rowid++);
			Object[] objectArr = info.get(key);
			int cellid = 0;
			for (Object obj : objectArr) {
				Cell cell = row.createCell(cellid++);
				try {
					cell.setCellValue((String) obj);
				} catch (ClassCastException ex) {
					cell.setCellValue((Integer) obj);
				}

			}
		}
		// Write the workbook in file system
		FileOutputStream out;
		try {
			out = new FileOutputStream(
					new File("reports\\Allocations Didn't Preffer by Studnet Generated by" + algorithm + ".xlsx"));
			workbook.write(out);
			out.close();
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
