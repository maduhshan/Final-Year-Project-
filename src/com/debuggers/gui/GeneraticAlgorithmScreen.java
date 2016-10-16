package com.debuggers.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.debuggers.core.CandidateAssignment;
import com.debuggers.core.CandidateSolution;
import com.debuggers.core.DisplayOptimalSolution;
import com.debuggers.core.GenericAlgorithm;
import com.debuggers.entity.PreferenceTable;


import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.SystemColor;
import javax.swing.JEditorPane;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GeneraticAlgorithmScreen {

	private JFrame frmStochasticSearchProject;
	private JTextField tf_fitness;
	private JTable table;
    static PreferenceTable preferenceTable;
    GenericAlgorithm GA;
    DisplayOptimalSolution Display;
    private JTextField textField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GeneraticAlgorithmScreen window = new GeneraticAlgorithmScreen(preferenceTable);
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
	public GeneraticAlgorithmScreen(PreferenceTable p) {
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
		frmStochasticSearchProject.setBounds(100, 100, 944, 636);
		frmStochasticSearchProject.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmStochasticSearchProject.getContentPane().setLayout(null);
		
		JButton btnBack = new JButton("BACK");
		btnBack.setIcon(new ImageIcon("images/60577.png"));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RetrievedData data = new RetrievedData(preferenceTable);
				data.setVisible(true);
				frmStochasticSearchProject.setVisible(false);
			}
		});
		btnBack.setFont(new Font("Times New Roman", Font.BOLD, 18));
		btnBack.setForeground(new Color(0, 0, 0));
		btnBack.setBackground(new Color(255, 250, 250));
		btnBack.setBounds(27, 550, 125, 36);
		frmStochasticSearchProject.getContentPane().add(btnBack);
		
		tf_fitness = new JTextField();
		tf_fitness.setEditable(false);
		tf_fitness.setEnabled(false);
		tf_fitness.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		tf_fitness.setColumns(10);
		tf_fitness.setBounds(355, 162, 91, 30);
		frmStochasticSearchProject.getContentPane().add(tf_fitness);
		
		JSpinner NoOfGenerations = new JSpinner();
		NoOfGenerations.setModel(new SpinnerNumberModel(25, 1, 50, 1));
		NoOfGenerations.setBounds(355, 89, 91, 30);
		frmStochasticSearchProject.getContentPane().add(NoOfGenerations);
		
		JButton btnNoOF = new JButton("No of Generations Vs Fitness");
		btnNoOF.setIcon(new ImageIcon("images/linchart.jpg"));
		btnNoOF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.CreateLineChart();
			}
		});
		btnNoOF.setEnabled(false);
		btnNoOF.setBounds(118, 331, 243, 30);
		frmStochasticSearchProject.getContentPane().add(btnNoOF);
		
		JButton btnAllo = new JButton("Analysis of Allocated Prefences");
		btnAllo.setIcon(new ImageIcon("images/barchart.gif"));
		btnAllo.setEnabled(false);
		btnAllo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.getStudentAssignmentsByPreference();
			}
		});
		btnAllo.setBounds(118, 284, 243, 36);
		frmStochasticSearchProject.getContentPane().add(btnAllo);
		
		JButton exportPdf = new JButton("Export Result To PDF");
		exportPdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path = Display.GenerateOptimalSolutionReport();
				JOptionPane.showMessageDialog(frmStochasticSearchProject, "Locate the file in :-  "+path);
			}
		});
		exportPdf.setEnabled(false);
		exportPdf.setBounds(246, 409, 200, 35);
		frmStochasticSearchProject.getContentPane().add(exportPdf);
		
		JButton exportExcel = new JButton("Export Result To Excel");
		exportExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path=Display.DisplayTheFittestSolution();
				JOptionPane.showMessageDialog(frmStochasticSearchProject, "Locate the file in :-  "+path);
			}
		});
		exportExcel.setEnabled(false);
		exportExcel.setBounds(27, 408, 200, 35);
		frmStochasticSearchProject.getContentPane().add(exportExcel);
		
		JButton projectDistribution = new JButton("Project Distribution Report");
		projectDistribution.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path = Display.GenerateProjectDistributionOnStudnets();
				JOptionPane.showMessageDialog(frmStochasticSearchProject, "Locate the file in :-"+path);
			}
		});
		projectDistribution.setEnabled(false);
		projectDistribution.setBounds(147, 464, 200, 35);
		frmStochasticSearchProject.getContentPane().add(projectDistribution);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(461, 89, 457, 458);
		frmStochasticSearchProject.getContentPane().add(scrollPane);
		
		
		table = new JTable();
		scrollPane.setViewportView(table);
		String[] columns = {"STUDENT NAME","ALLOCATED PROJECT"};
		DefaultTableModel model = new DefaultTableModel(); 
	    model.setColumnIdentifiers(columns);
	    table.setModel(model);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(100, 149, 237));
		panel.setForeground(new Color(100, 149, 237));
		panel.setBounds(10, 20, 908, 43);
		frmStochasticSearchProject.getContentPane().add(panel);
		
		JLabel lblGeneticAlgorithm = new JLabel("GENETIC ALGORITHM");
		panel.add(lblGeneticAlgorithm);
		lblGeneticAlgorithm.setForeground(Color.BLACK);
		lblGeneticAlgorithm.setFont(new Font("PMingLiU-ExtB", Font.BOLD, 25));
		lblGeneticAlgorithm.setBackground(SystemColor.textInactiveText);
		
		JLabel lblNoOfGenerations = new JLabel("No of Generations (Optional)");
		lblNoOfGenerations.setFont(new Font("OCR A Extended", Font.BOLD, 17));
		lblNoOfGenerations.setBounds(24, 87, 308, 29);
		frmStochasticSearchProject.getContentPane().add(lblNoOfGenerations);
		
		JButton button = new JButton("Execute Algorithm");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				   int no = (int) NoOfGenerations.getValue();
					GA= new GenericAlgorithm(no,preferenceTable);
					CandidateSolution optimalSol = new CandidateSolution(preferenceTable);
					optimalSol.setTable(GA.getOptimalSolution());
					
					tf_fitness.setText(Integer.toString(optimalSol.getFitness()));
					textField.setText(Integer.toString(GA.getBestGeneration()));
					
					Display = new DisplayOptimalSolution(GA.getOptimalSolution(), GA.getOptimalValues(), "Generic Algorithm");
					
				    tf_fitness.setEnabled(true);
				    textField.setEnabled(true);
					btnAllo.setEnabled(true);
					btnNoOF.setEnabled(true);
					exportPdf.setEnabled(true);
					exportExcel.setEnabled(true);
					projectDistribution.setEnabled(true);
					
					addDataToTable(GA.getOptimalSolution());
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
		button.setFont(new Font("Times New Roman", Font.BOLD, 18));
		button.setBounds(76, 127, 213, 30);
		frmStochasticSearchProject.getContentPane().add(button);
		
		JLabel lblFitness = new JLabel("Fitness");
		lblFitness.setFont(new Font("OCR A Extended", Font.BOLD, 17));
		lblFitness.setBounds(27, 162, 105, 33);
		frmStochasticSearchProject.getContentPane().add(lblFitness);
		
		JLabel lblHighestFitnessGeneration = new JLabel("Highest Fitness Generation");
		lblHighestFitnessGeneration.setFont(new Font("OCR A Extended", Font.BOLD, 17));
		lblHighestFitnessGeneration.setBounds(27, 187, 349, 43);
		frmStochasticSearchProject.getContentPane().add(lblHighestFitnessGeneration);
		
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setEditable(false);
		textField.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 20));
		textField.setColumns(10);
		textField.setBounds(355, 200, 91, 30);
		frmStochasticSearchProject.getContentPane().add(textField);
		
		JLabel label_1 = new JLabel("DEBUGGERS");
		label_1.setForeground(Color.BLACK);
		label_1.setFont(new Font("AR DESTINE", Font.BOLD, 19));
		label_1.setBounds(793, 563, 125, 29);
		frmStochasticSearchProject.getContentPane().add(label_1);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new MainScreen();
				frmStochasticSearchProject.setVisible(false);
			}
		});
		lblNewLabel.setIcon(new ImageIcon("images/bug.png"));
		lblNewLabel.setBounds(749, 552, 31, 36);
		frmStochasticSearchProject.getContentPane().add(lblNewLabel);
		
		JLabel label_2 = new JLabel("Charts");
		label_2.setFont(new Font("Sitka Display", Font.BOLD, 28));
		label_2.setBounds(27, 240, 113, 43);
		frmStochasticSearchProject.getContentPane().add(label_2);
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setBackground(new Color(102, 153, 204));
		editorPane.setBounds(27, 262, 419, 120);
		frmStochasticSearchProject.getContentPane().add(editorPane);
		
		
	
	
	}
	

}
