package com.FinalInfo;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

//import cassandraApplication.WelcomeCassandra;

public class OptionsMenu extends JFrame {

	private JPanel contentPane;

	private JButton btnCreate;
	private JButton btnModify;
	private JComboBox comboBox;
	private JButton btnViewInfomation;
	private String info_selected;
	private JButton btnBack;
	private JButton btnProceedToMake;
	private JTextField txtProject;
	private JButton btnDataObjectWithinDataObject;
	private JButton btnCategoryChangeType;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					OptionsMenu frame = new OptionsMenu("p1","old");
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public OptionsMenu(String projectId,String userType) {
		
		this.setTitle("Options Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 100, 582, 350);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnCreate= new JButton("Create new Data Object");
		btnCreate.setFont(new Font("Dialog", Font.BOLD, 13));
		btnCreate.setBounds(119, 88, 219, 25);
		contentPane.add(btnCreate);
		
		btnModify = new JButton("Modify Data Object");
		btnModify.setFont(new Font("Dialog", Font.BOLD, 13));
		btnModify.setBounds(120, 129, 219, 25);
		contentPane.add(btnModify);
		
		JLabel lblProject = new JLabel("Project Name ");
		lblProject.setForeground(Color.YELLOW);
		lblProject.setHorizontalAlignment(SwingConstants.CENTER);
		lblProject.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblProject.setBounds(119, 47, 134, 23);
		contentPane.add(lblProject);
		
		
		String p_Info[]=fetchInfoFromDataBase(projectId);
		if(p_Info==null) {
			p_Info=new String[1];
			p_Info[1]="";
		}
		
		
		comboBox = new JComboBox(p_Info);
		comboBox.setBounds(350, 177, 86, 22);
		comboBox.setSelectedItem(null);
		contentPane.add(comboBox);
		
		btnViewInfomation = new JButton("View Data Object");
		btnViewInfomation.setFont(new Font("Dialog", Font.BOLD, 13));
		btnViewInfomation.setBounds(119, 176, 219, 25);
		contentPane.add(btnViewInfomation);
		
		btnBack = new JButton("Back");
		btnBack.setFont(new Font("Dialog", Font.BOLD, 13));
		btnBack.setBounds(212, 258, 97, 25);
		contentPane.add(btnBack);


		/*btnDataObjectWithinDataObject = new JButton("Add data object within another data object");
		btnDataObjectWithinDataObject.setFont(new Font("Dialog", Font.BOLD, 13));
		btnDataObjectWithinDataObject.setBounds(119, 217, 302, 25);
		contentPane.add(btnDataObjectWithinDataObject);

		btnCategoryChangeType = new JButton("Add Category Change Type");
		btnCategoryChangeType.setFont(new Font("Dialog", Font.BOLD, 13));
		btnCategoryChangeType.setBounds(119, 258, 302, 25);
		contentPane.add(btnCategoryChangeType);*/

		btnProceedToMake = new JButton("Proceed to ROLAP Schema");
		btnProceedToMake.setFont(new Font("Dialog", Font.BOLD, 13));
		btnProceedToMake.setBounds(119, 217, 302, 25);
		contentPane.add(btnProceedToMake);
		
		txtProject = new JTextField();
		txtProject.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtProject.setBackground(Color.WHITE);
		txtProject.setForeground(Color.BLACK);
		txtProject.setHorizontalAlignment(SwingConstants.CENTER);
		txtProject.setBounds(265, 47, 116, 22);
		contentPane.add(txtProject);
		txtProject.setColumns(10);
		txtProject.setText(projectId.toUpperCase());
		txtProject.setEditable(false);
		
		
		if(userType.equalsIgnoreCase("new"))
		{
			btnModify.setEnabled(false);
			btnViewInfomation.setEnabled(false);
			btnProceedToMake.setEnabled(false);
		}
		
		
		addListeners(projectId,userType);
	}
	
	public void addListeners(String projectId,String userType)
	{
		btnCreate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				CreateNewInfo cr =new CreateNewInfo(projectId);
				cr.setVisible(true);
				dispose();
			}
		});
		
		btnModify.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				modifyInformation mI=new modifyInformation(projectId);
				mI.setVisible(true);
				dispose();
			}
		});

		/*btnDataObjectWithinDataObject.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DataObjectWithinDataObject dOWDO=new DataObjectWithinDataObject(projectId);
				dOWDO.setVisible(true);
				dispose();
			}
		});

		btnCategoryChangeType.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				CategoryChangeType cCT=new CategoryChangeType(projectId);
				cCT.setVisible(true);
				dispose();
			}
		});
		*/
		
		comboBox.addItemListener(new ItemListener() {


			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub

				if(e.getStateChange()==ItemEvent.SELECTED)
				{
					info_selected=comboBox.getSelectedItem().toString().toLowerCase();
					
				}
				//				if(e.getSource()==Info_comboBox)
				//				{
				//					info_selected=Info_comboBox.getSelectedItem().toString().toLowerCase();
				//					getData(info_selected);
				//				}
				else
				{
					JOptionPane.showMessageDialog(contentPane, "Kindly Choose one option");
				}

			}
		});
		btnViewInfomation.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(info_selected!=null &&!info_selected.trim().isEmpty())
				{
					com.FinalInfo.DatabaseConnection dbcon=new com.FinalInfo.DatabaseConnection();
					Connection con=dbcon.getConnection(projectId);
					dbQueries dbQ=new dbQueries(con);
					try {
						
						RequirementsClass rc=dbQ.getInfo(info_selected);
						confirmUserInput cu=new confirmUserInput(rc,"view");
						cu.setVisible(true);
						dispose();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						//e1.printStackTrace();
						JOptionPane.showMessageDialog(contentPane, "Error in fetching Data Object", "Error Occured", 2);
					}
				}
				else
				{
					JOptionPane.showMessageDialog(contentPane, "Please select Data Object", "No Data Object selected", 1);
				}
				
			}
		});
		
		btnProceedToMake.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				//JOptionPane.showConfirmDialog(contentPane, "Are you sure you want ", title, optionType)
				btnCreate.setEnabled(false);
				btnModify.setEnabled(false);
				btnViewInfomation.setEnabled(false);

				Process p = null;
				try
				{
					p = Runtime.getRuntime().exec("python proceed_to_rolap.py");
					System.out.println("Working Directory = " + System.getProperty("user.dir"));
					p.waitFor();
					/*if(p!=null)
					{
						System.out.println("Output printed to file");
					}*/
					BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
					String line = "";
					while((line = in.readLine()) != null)
					{
						System.out.println(line);
					}
				} catch (IOException | InterruptedException ioException) {
					ioException.printStackTrace();
				}

//				WelcomeCassandra ws=new WelcomeCassandra(projectId,userType);
				
				
				
				//wc.main(new String [1]);
				
			}
		});
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				Frame f[]=getFrames();
				for(Frame s:f)
				{
					System.out.println(s.getTitle());
					if(s.getTitle().equalsIgnoreCase("Login Screen"))
					{
						s.setVisible(true);
					}
					else if (s.getTitle().equalsIgnoreCase("Welcome to the Tool"))
					{
						s.setVisible(true);
					}
				}
			}
		});
	}
	
	public String [] fetchInfoFromDataBase(String projectID)
	{
		com.FinalInfo.DatabaseConnection dbcon=new com.FinalInfo.DatabaseConnection();
		Connection conn=dbcon.getConnection(projectID);
		dbQueries dbq=new dbQueries(conn);
		return(dbq.fetch_pInfo_FromDataBase(projectID));
	}
}
