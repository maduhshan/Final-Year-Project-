package com.debuggers.gui;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.debuggers.entity.PreferenceTable;

import java.awt.Button;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.StringTokenizer;

import java.awt.event.ActionEvent;
import java.awt.TextField;

import java.awt.Toolkit;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class MainScreen {

	private JFrame frmStochasticSearchProject;
    private File file;
    PreferenceTable p ;
    public static Random RND = new Random();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainScreen window = new MainScreen();
					window.frmStochasticSearchProject.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainScreen() {
		initialize();
		this.frmStochasticSearchProject.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmStochasticSearchProject = new JFrame();
		frmStochasticSearchProject.getContentPane().setBackground(new Color(192, 192, 192));
		frmStochasticSearchProject.getContentPane().setForeground(new Color(0, 0, 0));
		frmStochasticSearchProject.setIconImage(Toolkit.getDefaultToolkit().getImage("images/Social-Movements-Project.png"));
		frmStochasticSearchProject.setTitle("WELCOME");
		frmStochasticSearchProject.setBounds(100, 100, 714, 451);
		frmStochasticSearchProject.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmStochasticSearchProject.getContentPane().setLayout(null);
		// File Path
		TextField textField = new TextField();
		textField.setEditable(false);
		textField.setText("Select a file to Upload");
		textField.setBounds(219, 278, 191, 30);
		frmStochasticSearchProject.getContentPane().add(textField);

		// Upload Button
		Button UploadFile = new Button("Upload File");
		UploadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					// get the file
					file = fileChooser.getSelectedFile();
					//file extenstion
					String ext = file.getName().substring(file.getName().length() -3,file.getName().length());
					//check whether it tsv
					if(!ext.intern().equals("tsv")){
						JOptionPane.showMessageDialog(frmStochasticSearchProject, "Please Upload File in tsv format", "Incorrect File Format", JOptionPane.ERROR_MESSAGE);
					}else{
						//check for the correct content format
						if(isFileContentCorrect(file)){
							textField.setText(file.getAbsolutePath());
							UploadFile.setVisible(false);
						}else{
						   JOptionPane.showMessageDialog(frmStochasticSearchProject, "File should contain Student Name,Preassigned,Perference List(1-10) columns respectively", "Incorrect Content Format", JOptionPane.ERROR_MESSAGE);
						}
						
					}
					

				}
			}
		});
		UploadFile.setBounds(416, 278, 109, 30);
		frmStochasticSearchProject.getContentPane().add(UploadFile);
		
		Button Proceed = new Button("Proceed");
		Proceed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			  // pass the preference table to the next window
				p= new PreferenceTable(file.getAbsolutePath());
				p.fillPreferencesOfAll(10);
				RetrievedData rd = new RetrievedData(p);
				rd.setVisible(true);
				frmStochasticSearchProject.setVisible(false);
			}
		});
		Proceed.setBounds(417, 278, 108, 30);
		frmStochasticSearchProject.getContentPane().add(Proceed);
		
		Button Default = new Button("Use the Default File ");
		Default.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    p = new PreferenceTable("Project allocation data.tsv");
				p.fillPreferencesOfAll(10);
				RetrievedData rd = new RetrievedData(p);
				rd.setVisible(true);
				frmStochasticSearchProject.setVisible(false);
			}
		});
		Default.setBounds(219, 360, 306, 30);
		frmStochasticSearchProject.getContentPane().add(Default);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(100, 149, 237));
		panel.setBounds(20, 21, 650, 193);
		frmStochasticSearchProject.getContentPane().add(panel);
		
		JLabel lblNewLabel = new JLabel("STOHASTIC SEARCH");
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Algerian", Font.PLAIN, 60));
		
		JLabel lblNewLabel_1 = new JLabel("PROJECT ALLOCATION SYSTEM");
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Algerian", Font.PLAIN, 40));
		
		JLabel lblNewLabel_3 = new JLabel("By Debuggers");
		panel.add(lblNewLabel_3);
		lblNewLabel_3.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel_3.setFont(new Font("Simplified Arabic Fixed", Font.PLAIN, 18));
		
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(580, 280, 90, 80);
		frmStochasticSearchProject.getContentPane().add(lblNewLabel_2);
		lblNewLabel_2.setIcon(new ImageIcon("images/bugger.png"));
		
		JLabel lblNewLabel_4 = new JLabel("OR");
		lblNewLabel_4.setForeground(Color.BLACK);
		lblNewLabel_4.setFont(new Font("Stencil", Font.PLAIN, 36));
		lblNewLabel_4.setBounds(310, 314, 54, 40);
		frmStochasticSearchProject.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setIcon(new ImageIcon("images/choose-512.png"));
		lblNewLabel_5.setBounds(162, 272, 30, 36);
		frmStochasticSearchProject.getContentPane().add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("");
		lblNewLabel_6.setIcon(new ImageIcon("images/choose-512.png"));
		lblNewLabel_6.setBounds(162, 360, 30, 30);
		frmStochasticSearchProject.getContentPane().add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Upload Project Allocation File To Process");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_7.setBounds(141, 218, 436, 40);
		frmStochasticSearchProject.getContentPane().add(lblNewLabel_7);

	}
	//Check File content is in correct format
	@SuppressWarnings("resource")
	private boolean isFileContentCorrect(File file){
		try {
			FileInputStream stream = new FileInputStream(file);	
			StringTokenizer tokens = new StringTokenizer(new BufferedReader(new InputStreamReader(stream)).readLine(), "\t");
			
			if(tokens.countTokens()!=12){
				return false;
			}
		
		}
		 catch (FileNotFoundException e) {
			System.out.println("File is not found!");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return true;
	}
}
