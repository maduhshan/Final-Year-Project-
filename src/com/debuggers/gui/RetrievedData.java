package com.debuggers.gui;


import java.awt.EventQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.debuggers.entity.PreferenceTable;
import com.debuggers.entity.StudentEntry;

import javax.swing.JLabel;
import javax.swing.JTable;

import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.SystemColor;

public class RetrievedData extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static PreferenceTable preferenceTable;

	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RetrievedData frame = new RetrievedData(preferenceTable);
					frame.setVisible(true);
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RetrievedData(PreferenceTable p) {
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/Social-Movements-Project.png"));
		preferenceTable=p;
		
		setTitle("Stochastic Search Project Allocation System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 714, 546);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(169, 169, 169));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(42, 89, 612, 239);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		Vector<StudentEntry> allStudents= p.getAllStudentEntries();
	
		String[] columns = {"Student","Prearranged","Preference 1","Preference 2","	Preference 3","	Preference 4","	Preference 5","	Preference 6	","Preference 7","	Preference 8	","Preference 9	","Preference 10"};
		DefaultTableModel model = new DefaultTableModel(); 
	    model.setColumnIdentifiers(columns);
	    for(StudentEntry student:allStudents){
	    	Object[] data = new Object [student.getOrderedPreferences().size()+2];
	    	data[0]=student.getStudentName();
	    	
	    	data[1]=student.hasPreassignedProject()?"Yes":"No";
	    	for(int c=2;c<student.getOrderedPreferences().size()+2;c++){
	    		data[c]=student.getOrderedPreferences().get(c-2);
	    	}
	        model.addRow(data);
	    	
	    }
		table.setModel(model);
		
		TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
		table.setRowSorter(sorter);
		
		JButton btnNewButton = new JButton("Generic Algorithm");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new GeneraticAlgorithmScreen(p);
				contentPane.setVisible(false);
				 dispose();
			}
		});
		btnNewButton.setBounds(136, 397, 137, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Simulated Annealing");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SimulatedAnnealingScreen(p);
				contentPane.setVisible(false);
				dispose();
			}
		});
		btnNewButton_1.setBounds(421, 397, 150, 23);
		contentPane.add(btnNewButton_1);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				new MainScreen();
				dispose();
				}
		});
		lblNewLabel_1.setIcon(new ImageIcon("images/bug.png"));
		lblNewLabel_1.setBounds(513, 467, 32, 34);
		contentPane.add(lblNewLabel_1);
		
		JLabel label = new JLabel("DEBUGGERS");
		label.setForeground(Color.BLACK);
		label.setFont(new Font("AR DESTINE", Font.BOLD, 19));
		label.setBounds(563, 467, 125, 29);
		contentPane.add(label);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(100, 149, 237));
		panel.setForeground(new Color(100, 149, 237));
		panel.setBounds(10, 11, 674, 34);
		contentPane.add(panel);
		
		JLabel lblNewLabel = new JLabel("Extracted Data From The Project Allocation File");
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("PMingLiU-ExtB", Font.BOLD, 22));
		
		JLabel lblDataTable = new JLabel("Raw Data Table");
		lblDataTable.setForeground(new Color(0, 0, 0));
		lblDataTable.setBackground(new Color(204, 51, 0));
		lblDataTable.setFont(new Font("PMingLiU-ExtB", Font.BOLD, 20));
		lblDataTable.setBounds(44, 56, 610, 37);
		contentPane.add(lblDataTable);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(102, 153, 204));
		panel_1.setBounds(42, 56, 612, 34);
		contentPane.add(panel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Find the Best / Optimal Solution");
		lblNewLabel_2.setForeground(SystemColor.inactiveCaptionBorder);
		lblNewLabel_2.setFont(new Font("PMingLiU-ExtB", Font.BOLD, 20));
		lblNewLabel_2.setBounds(216, 351, 355, 35);
		contentPane.add(lblNewLabel_2);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(SystemColor.windowBorder);
		panel_2.setBounds(42, 339, 612, 102);
		contentPane.add(panel_2);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
		 
		int columnIndexToSort = 1;
		sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));
		int columnIndexForName = 0;
		sortKeys.add(new RowSorter.SortKey(columnIndexForName, SortOrder.ASCENDING));
		sorter.setSortKeys(sortKeys);
		sorter.sort();
	}
}
