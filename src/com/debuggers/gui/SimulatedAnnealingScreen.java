package com.debuggers.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.debuggers.core.CandidateAssignment;
import com.debuggers.core.CandidateSolution;
import com.debuggers.core.DisplayOptimalSolution;

import com.debuggers.core.SimulatedAnnealing;
import com.debuggers.entity.PreferenceTable;

import javax.swing.ImageIcon;

import javax.swing.JPanel;

import javax.swing.RowSorter;
import javax.swing.SortOrder;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SimulatedAnnealingScreen {
	private JFrame frmStochasticSearchProject;
	private JTextField txtBestSolution;
	private JTextField EngeryVal;
	private JTable table;
	 static PreferenceTable preferenceTable;
	    SimulatedAnnealing SA;
	    DisplayOptimalSolution Display;
	    private JTextField textField;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SimulatedAnnealingScreen window = new SimulatedAnnealingScreen(preferenceTable);
					window.frmStochasticSearchProject.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @param p 
	 */
	public SimulatedAnnealingScreen(PreferenceTable p) {
		preferenceTable=p;
		initialize();
		this.frmStochasticSearchProject.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmStochasticSearchProject = new JFrame();
		frmStochasticSearchProject.setIconImage(Toolkit.getDefaultToolkit().getImage("images/Social-Movements-Project.png"));
		frmStochasticSearchProject.getContentPane().setBackground(new Color(169, 169, 169));
		frmStochasticSearchProject.setBackground(new Color(255, 102, 255));
		frmStochasticSearchProject.setForeground(new Color(123, 104, 238));
		frmStochasticSearchProject.setTitle("Stochastic Search Project Allocation System");
		frmStochasticSearchProject.setBounds(100, 100, 972, 612);
		frmStochasticSearchProject.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmStochasticSearchProject.getContentPane().setLayout(null);
		
		JLabel lblCharts = new JLabel("Charts");
		lblCharts.setBounds(27, 240, 113, 43);
		frmStochasticSearchProject.getContentPane().add(lblCharts);
		lblCharts.setFont(new Font("Sitka Display", Font.BOLD, 28));
		
		
		JButton btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RetrievedData data = new RetrievedData(preferenceTable);
				data.setVisible(true);
				frmStochasticSearchProject.setVisible(false);
			}
		});
		btnBack.setIcon(new ImageIcon("images/60577.png"));
		btnBack.setFont(new Font("Times New Roman", Font.BOLD, 18));
		btnBack.setForeground(new Color(0, 0, 0));
		btnBack.setBackground(new Color(255, 255, 255));
		btnBack.setBounds(12, 529, 137, 33);
		frmStochasticSearchProject.getContentPane().add(btnBack);
		
		JLabel lblNewLabel_2 = new JLabel("No of Times to Execute ");
		lblNewLabel_2.setFont(new Font("OCR A Extended", Font.BOLD, 17));
		lblNewLabel_2.setBounds(22, 80, 299, 29);
		frmStochasticSearchProject.getContentPane().add(lblNewLabel_2);
		
		JLabel lblFitness = new JLabel("Best Execution");
		lblFitness.setFont(new Font("OCR A Extended", Font.BOLD, 20));
		lblFitness.setBounds(21, 204, 189, 33);
		frmStochasticSearchProject.getContentPane().add(lblFitness);
		
		txtBestSolution = new JTextField();
		txtBestSolution.setEditable(false);
		txtBestSolution.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 20));
		txtBestSolution.setColumns(10);
		txtBestSolution.setBounds(306, 206, 90, 30);
		frmStochasticSearchProject.getContentPane().add(txtBestSolution);
		
		JLabel lblEnergy = new JLabel("Energy");
		lblEnergy.setFont(new Font("OCR A Extended", Font.BOLD, 20));
		lblEnergy.setBounds(22, 160, 105, 33);
		frmStochasticSearchProject.getContentPane().add(lblEnergy);
		
		EngeryVal = new JTextField();
		EngeryVal.setEnabled(false);
		EngeryVal.setEditable(false);
		EngeryVal.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		EngeryVal.setColumns(10);
		EngeryVal.setBounds(306, 165, 90, 30);
		frmStochasticSearchProject.getContentPane().add(EngeryVal);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(414, 80, 519, 441);
		frmStochasticSearchProject.getContentPane().add(scrollPane);
		
		
		
		table = new JTable();
		scrollPane.setViewportView(table);
		String[] columns = {"STUDENT NAME","ALLOCATED PROJECT"};
		DefaultTableModel model = new DefaultTableModel(); 
	    model.setColumnIdentifiers(columns);
	    table.setModel(model);
	    
		JButton btn_PDF = new JButton("Export Result to PDF");
		btn_PDF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path = Display.GenerateOptimalSolutionReport();
				JOptionPane.showMessageDialog(frmStochasticSearchProject, "Locate the file in :-  "+path);
			}
		});
		btn_PDF.setEnabled(false);
		btn_PDF.setBounds(235, 418, 161, 30);
		frmStochasticSearchProject.getContentPane().add(btn_PDF);
		
		JButton btn_excel = new JButton("Export Result to Excel");
		btn_excel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path=Display.DisplayTheFittestSolution();
				JOptionPane.showMessageDialog(frmStochasticSearchProject, "Locate the file in :-  "+path);
			}
		});
		btn_excel.setEnabled(false);
		btn_excel.setBounds(27, 418, 161, 30);
		frmStochasticSearchProject.getContentPane().add(btn_excel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new MainScreen();
				frmStochasticSearchProject.setVisible(false);
			}
		});
		lblNewLabel_1.setIcon(new ImageIcon("images/bug.png"));
		lblNewLabel_1.setBounds(780, 533, 31, 29);
		frmStochasticSearchProject.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_3 = new JLabel("DEBUGGERS");
		lblNewLabel_3.setForeground(new Color(0, 0, 0));
		lblNewLabel_3.setFont(new Font("AR DESTINE", Font.BOLD, 19));
		lblNewLabel_3.setBounds(821, 533, 125, 29);
		frmStochasticSearchProject.getContentPane().add(lblNewLabel_3);
		
		JButton btn_NoOFGener = new JButton("No of \r\nGenerations Vs Fitness");
		btn_NoOFGener.setEnabled(false);
		btn_NoOFGener.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.CreateLineChart();
			}
		});
		btn_NoOFGener.setIcon(new ImageIcon("images/linchart.jpg"));
		btn_NoOFGener.setBounds(43, 334, 334, 43);
		frmStochasticSearchProject.getContentPane().add(btn_NoOFGener);
		
		JButton btn_Analysis = new JButton(" Analysis of \r\nAllocated Prefernces");
		btn_Analysis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.getStudentAssignmentsByPreference();
			}
		});
		btn_Analysis.setEnabled(false);
		btn_Analysis.setIcon(new ImageIcon("images/barchart.gif"));
		btn_Analysis.setBounds(46, 282, 331, 43);
		frmStochasticSearchProject.getContentPane().add(btn_Analysis);
		
		JButton btnProjectDistributionReport = new JButton("Project Distribution Report");
		btnProjectDistributionReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path = Display.GenerateProjectDistributionOnStudnets();
				JOptionPane.showMessageDialog(frmStochasticSearchProject, "Locate the file in :-"+path);
			}
		});
		btnProjectDistributionReport.setEnabled(false);
		btnProjectDistributionReport.setBounds(108, 467, 200, 30);
		frmStochasticSearchProject.getContentPane().add(btnProjectDistributionReport);
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setBackground(new Color(102, 153, 204));
		editorPane.setForeground(new Color(0, 0, 0));
		editorPane.setBounds(22, 252, 374, 139);
		frmStochasticSearchProject.getContentPane().add(editorPane);
		
		
		JButton btnExecuteAlgorithm = new JButton("Execute Algorithm");
		btnExecuteAlgorithm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				    SA=new SimulatedAnnealing(preferenceTable);
					
					CandidateSolution bestSol = new CandidateSolution(preferenceTable);
					bestSol.setTable(SA.getOptimalSolution());
					
					EngeryVal.setText(Integer.toString(bestSol.getEnergy()));
					txtBestSolution.setText(Integer.toString(SA.getExecutionNo()));
					
					Display = new DisplayOptimalSolution(SA.getOptimalSolution(), SA.getOptimalValues(), "Simulated Annealing");
					
					EngeryVal.setEnabled(true);
					txtBestSolution.setEnabled(true);
					btn_Analysis.setEnabled(true);
					btn_excel.setEnabled(true);
					btn_NoOFGener.setEnabled(true);
					btnProjectDistributionReport.setEnabled(true);
					btn_PDF.setEnabled(true);
					addDataToTable(SA.getOptimalSolution());
			}

			private void addDataToTable(Hashtable<String, CandidateAssignment> optimalSolution) {

				String[] columns = {"STUDENT NAME","ALLOCATED PROJECT"};
				DefaultTableModel model = new DefaultTableModel(); 
			    model.setColumnIdentifiers(columns);
			    
				 Iterator<String> it = optimalSolution.keySet().iterator();
				 while (it.hasNext()) {
					
				 String studentName = it.next();
				 Object[] data = new Object[]{studentName,optimalSolution.get(studentName).getAssignedProject()};
				 model.addRow(data);
				 }
			    table.setModel(model);
			    
			    TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
				table.setRowSorter(sorter);
	
				List<RowSorter.SortKey> sortKeys = new ArrayList<>();
				 
				int columnIndexToSort = 0;
				sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));
	
				sorter.setSortKeys(sortKeys);
				sorter.sort();
				
			}
		});
		btnExecuteAlgorithm.setFont(new Font("Times New Roman", Font.BOLD, 18));
		btnExecuteAlgorithm.setBounds(108, 120, 213, 30);
		frmStochasticSearchProject.getContentPane().add(btnExecuteAlgorithm);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setText("10");
		textField.setBounds(306, 82, 90, 30);
		frmStochasticSearchProject.getContentPane().add(textField);
		textField.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_1.setForeground(new Color(100, 149, 237));
		panel_1.setBackground(new Color(100, 149, 237));
		panel_1.setBounds(12, 11, 908, 43);
		frmStochasticSearchProject.getContentPane().add(panel_1);
		
		JLabel lblSimulatedAnnealing = new JLabel("SIMULATED ANNEALING ");
		panel_1.add(lblSimulatedAnnealing);
		lblSimulatedAnnealing.setForeground(Color.BLACK);
		lblSimulatedAnnealing.setFont(new Font("PMingLiU-ExtB", Font.BOLD, 25));
		lblSimulatedAnnealing.setBackground(SystemColor.textInactiveText);
	}
}


