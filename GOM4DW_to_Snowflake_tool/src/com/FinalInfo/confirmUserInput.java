package com.FinalInfo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class confirmUserInput extends JFrame {

	private JPanel contentPane;
	private JTextField txtInfo;

	private JTable attribute_table;
	private JTable cate_table;
	private JTable cat_attr_table;
	private JTable Aggr_table;

	private DefaultTableModel attribute_model;
	private DefaultTableModel category_model;
	private DefaultTableModel category_attribute_model;
	private DefaultTableModel aggr_model;
	private String [] col_names;
	private JLabel lblFreqValue;
	private JLabel lblDurationNum;
	private JLabel lblDurationFreq;
	private JButton btnSave;
	private JButton btnCancel;
	private RequirementsClass rc;
	private String source="";

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					confirmUserInput frame = new confirmUserInput();
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
	public confirmUserInput(RequirementsClass rc,String source) {

		this.rc=rc;
		this.source=source;
		this.setTitle("Confirm Entered Data Object");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, -1, 818, 790);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		col_names= new String[2];
		attribute_model = new DefaultTableModel();
		col_names[0]="Attribute Name";
		col_names[1]="Data Type";
		attribute_model.setColumnIdentifiers(col_names);
		

		col_names= new String[2];
		category_model = new DefaultTableModel();
		col_names[0]="Category";
		col_names[1]="SubCategory of";
		category_model.setColumnIdentifiers(col_names);

		col_names= new String[3];
		category_attribute_model = new DefaultTableModel();
		col_names[0]="Category";
		col_names[1]="Attribute Name";
		col_names[2]="Data Type";	
		category_attribute_model.setColumnIdentifiers(col_names);

		col_names= new String[6];
		aggr_model = new DefaultTableModel();
		col_names[0]="Aggegate";
		col_names[1]="Contains Data Object";
		col_names[2]="Category";	
		col_names[3]="Frequency";
		col_names[4]="Duration";
		col_names[5]="Duration Frequency";
		aggr_model.setColumnIdentifiers(col_names);

//		JPanel panel_1 = new JPanel();
//		panel_1.setBounds(0, 0, 937, 1004);
//		panel_1.setLayout(null);
//		contentPane.add(panel_1);


		//JScrollPane scrollPane_4 = new JScrollPane(panel_1);

//		JPanel aggr_panel = new JPanel();
//		aggr_panel.setBounds(45, 554, 739, 185);
//		contentPane.add(aggr_panel);
//		aggr_panel.setBackground(Color.DARK_GRAY);
//		aggr_panel.setForeground(Color.BLACK);
//		aggr_panel.setLayout(null);
//		
//
//		JLabel lblAggregateInformation = new JLabel("Aggregate Information");
//		lblAggregateInformation.setForeground(Color.YELLOW);
//		lblAggregateInformation.setHorizontalAlignment(SwingConstants.CENTER);
//		lblAggregateInformation.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
//		lblAggregateInformation.setBounds(257, 4, 217, 24);
//		aggr_panel.add(lblAggregateInformation);
//
//		Aggr_table = new JTable(aggr_model);
//		Aggr_table.setBounds(39, 35, 790, 128);
//		Aggr_table.setEnabled(false);
//		aggr_panel.add(Aggr_table);
//
//		JScrollPane scrollPane_3 = new JScrollPane(Aggr_table);
//		scrollPane_3.setBounds(17, 32, 706, 137);
//		aggr_panel.add(scrollPane_3);

		JPanel panel = new JPanel();
		panel.setBounds(54, 81, 694, 76);
		contentPane.add(panel);
		panel.setBackground(Color.DARK_GRAY);
		panel.setLayout(null);

		JLabel lblHistory = new JLabel("History");
		lblHistory.setForeground(Color.YELLOW);
		lblHistory.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		lblHistory.setHorizontalAlignment(SwingConstants.CENTER);
		lblHistory.setBounds(302, 10, 74, 24);
		panel.add(lblHistory);

		JLabel lblFrequency = new JLabel("Period");
		lblFrequency.setForeground(Color.YELLOW);
		lblFrequency.setHorizontalAlignment(SwingConstants.CENTER);
		lblFrequency.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblFrequency.setBounds(41, 43, 112, 16);
		panel.add(lblFrequency);

		lblFreqValue = new JLabel("   -");
		lblFreqValue.setForeground(Color.YELLOW);
		lblFreqValue.setBounds(159, 44, 78, 16);
		panel.add(lblFreqValue);

		JLabel lblDuration = new JLabel("Duration");
		lblDuration.setForeground(Color.YELLOW);
		lblDuration.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblDuration.setBounds(311, 44, 78, 16);
		panel.add(lblDuration);

		lblDurationNum = new JLabel(" -");
		lblDurationNum.setForeground(Color.YELLOW);
		lblDurationNum.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblDurationNum.setBounds(401, 44, 56, 16);
		panel.add(lblDurationNum);

		lblDurationFreq = new JLabel("");
		lblDurationFreq.setForeground(Color.YELLOW);
		lblDurationFreq.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblDurationFreq.setBounds(469, 44, 78, 16);
		panel.add(lblDurationFreq);

		JLabel lblInformationName = new JLabel("Data Object");
		lblInformationName.setBounds(214, 52, 91, 16);
		contentPane.add(lblInformationName);
		lblInformationName.setFont(new Font("Trebuchet MS", Font.BOLD, 16));

		txtInfo = new JTextField();
		txtInfo.setBounds(375, 50, 178, 22);
		contentPane.add(txtInfo);
		txtInfo.setColumns(10);
		txtInfo.setEditable(false);

		JLabel lblHeading = new JLabel("Following was Entered");
		lblHeading.setBounds(191, 13, 403, 22);
		contentPane.add(lblHeading);
		lblHeading.setFont(new Font("Trebuchet MS", Font.BOLD, 22));
		lblHeading.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel cat_attr_panel = new JPanel();
		cat_attr_panel.setBounds(57, 451, 680, 213);
		contentPane.add(cat_attr_panel);
		cat_attr_panel.setBackground(Color.DARK_GRAY);
		cat_attr_panel.setLayout(null);

		cat_attr_table = new JTable(category_attribute_model);
		cat_attr_table.setBounds(135, 38, 584, 120);
		cat_attr_panel.add(cat_attr_table);
		cat_attr_table.setEnabled(false);

		JScrollPane scrollPane_2 = new JScrollPane(cat_attr_table);
		scrollPane_2.setBounds(54, 31, 584, 155);
		cat_attr_panel.add(scrollPane_2);

		JLabel lblCat_attr = new JLabel("Category Attributes");
		lblCat_attr.setForeground(Color.YELLOW);
		lblCat_attr.setHorizontalAlignment(SwingConstants.CENTER);
		lblCat_attr.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		lblCat_attr.setBounds(217, 4, 196, 19);
		cat_attr_panel.add(lblCat_attr);

		JPanel category_panel = new JPanel();
		category_panel.setBounds(401, 193, 358, 245);
		contentPane.add(category_panel);
		category_panel.setLayout(null);
		category_panel.setBackground(Color.DARK_GRAY);

		JLabel cat_subCat = new JLabel("Categories and Contains Categories");
		cat_subCat.setForeground(Color.YELLOW);
		cat_subCat.setHorizontalAlignment(SwingConstants.CENTER);
		cat_subCat.setFont(new Font("Calibri", Font.BOLD, 16));
		cat_subCat.setBounds(62, 11, 215, 16);
		category_panel.add(cat_subCat);

		cate_table = new JTable(category_model);
		cate_table.setForeground(Color.BLUE);
		cate_table.setBounds(14, 40, 379, 124);
		cate_table.setEnabled(false);
		category_panel.add(cate_table);

		JScrollPane scrollPane_1 = new JScrollPane(cate_table);
		scrollPane_1.setBounds(24, 40, 304, 192);
		category_panel.add(scrollPane_1);

		JPanel attribute_panel = new JPanel();
		attribute_panel.setBounds(47, 193, 342, 245);
		contentPane.add(attribute_panel);
		attribute_panel.setLayout(null);
		attribute_panel.setBackground(Color.DARK_GRAY);

		attribute_table = new JTable(attribute_model);

		attribute_table.setForeground(Color.BLUE);
		attribute_table.setBounds(16, 40, 343, 125);
		attribute_table.setEnabled(false);
		attribute_panel.add(attribute_table);

		JScrollPane scrollPane = new JScrollPane(attribute_table);
		scrollPane.setBounds(25, 37, 291, 195);
		attribute_panel.add(scrollPane);

		JLabel label = new JLabel("DataObject and Contains Obj Attr");
		label.setForeground(Color.YELLOW);
		label.setFont(new Font("Calibri", Font.BOLD, 16));
		label.setBounds(118, 7, 137, 25);
		attribute_panel.add(label);

		btnSave = new JButton("Go Ahead and Save Data");
		btnSave.setFont(new Font("Dialog", Font.BOLD, 13));
		btnSave.setBounds(144, 698, 232, 25);
		contentPane.add(btnSave);

		btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Dialog", Font.BOLD, 13));
		btnCancel.setBounds(467, 698, 161, 25);
		contentPane.add(btnCancel);
		
//		scrollPane_4.setBounds(0, 0, 937, 977);
//		contentPane.add(scrollPane_4);
		if(source.equalsIgnoreCase("view"))
		{
			
			contentPane.setEnabled(false);
			btnSave.setVisible(false);
			btnCancel.setText("Back");
			btnCancel.setEnabled(true);
		}
		
		FillInfo();
		addListeners();
	}
	
	public void FillInfo()
	{
		String inp=rc.getp_info();
		if(inp!=null && !inp.trim().isEmpty())
			txtInfo.setText(rc.getp_info());
		
		inp=rc.getFrequency();
		if(inp!=null && !inp.trim().isEmpty())
			lblFreqValue.setText(inp);
		
		int dur=rc.getDurationNum();
		if(dur!=-1)
			lblDurationNum.setText(String.valueOf(dur));
		
		inp=rc.getDurationFreq();
		if(inp!=null && !inp.trim().isEmpty())
			lblDurationFreq.setText(inp);
		
		
		// fill attribute table
		HashMap<String,String> Attributes=rc.getAttributes();
		if(Attributes.size()>0)
		{
			for(String attr:Attributes.keySet())
			{
				attribute_model.addRow(new Object[]{attr,Attributes.get(attr)});
				
			}
			
		}
		
		
		// category Table
		ArrayList<String> categories=rc.getCategories();
		ArrayList<String> copy_categories=new ArrayList<String>();
		copy_categories.addAll(categories);
		HashMap<String,ArrayList<String>> cat_subCat=rc.getCategory_subcategory();
		String cat="";
		if(copy_categories!=null && copy_categories.size()>0)
		{
			for(int i=0;i<copy_categories.size();i++)
			{
				cat=copy_categories.get(i);
				if(cat_subCat.containsKey(cat))
				{
					ArrayList<String> subCat=cat_subCat.get(cat);
					category_model.addRow(new Object[]{cat,String.valueOf(" ")});
					for(int j=0;j<subCat.size();j++)
					{
						category_model.addRow(new Object[]{subCat.get(j),cat});
						copy_categories.remove(subCat.get(j));
					}
					
				}
				else
				{
					category_model.addRow(new Object[]{cat,String.valueOf(" ")});
				}
			}
		}
		
		
		
		//category attributes
		 HashMap<String,HashMap<String,String>> cat_attr=rc.getCategory_attribute();
		 if(cat_attr!=null && cat_attr.size()>0)
		 {
			 for(String cate:cat_attr.keySet())
			 {
				 HashMap<String,String> inner=cat_attr.get(cate);
				 if(inner!=null && inner.size()>0)
				 {
					 for(String attri:inner.keySet())
					 {
						 category_attribute_model.addRow(new Object[]{cate,attri,inner.get(attri)});
					 }
				 }
				 
				 
			 }
			
		 }
		//Aggegrate Info
		ArrayList<ArrayList<String>> aggr=rc.getAggregateInfo();
		if(aggr!=null && aggr.size()>0)
		{
			for(int i=0;i<aggr.size();i++)
			{
				ArrayList<String> inner=aggr.get(i);
				if(inner!=null && inner.size()>0)
				{
					aggr_model.addRow(new Object[]{inner.get(0),inner.get(1),inner.get(2),inner.get(3),inner.get(4),inner.get(5)});
				}
				
			}
			
		}
		
	}
	public void addListeners()
	{
		
		
		btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				DatabaseConnection dbcon= new DatabaseConnection();
				
				com.FinalInfo.dbQueries dbObj=new dbQueries(dbcon.getConnection(rc.getProjectId()));
				if(source.equalsIgnoreCase("modify"))
				   dbObj.deleteFromdatabase(rc.getp_info());
				
				boolean Insertsuc=dbObj.InsertDataIntoDatabase(rc);
				if(Insertsuc && dbObj.duplicateInfoName==true)
				{
					JOptionPane.showMessageDialog(contentPane, "Saved Successfully", "Data Save Status", 2);
					dispose();
					Frame frame[]=Frame.getFrames();
					if(frame!=null)
					{	
						for(Frame f:frame)
						{
							f.dispose();
						}
					}
					
					WelcomeScreen ws=new WelcomeScreen();
					ws.setVisible(true);
				}
				else if(Insertsuc==false)
				{
					JOptionPane.showMessageDialog(contentPane, "Can not save Data! Error Occured. Retry", "Data Save Status", 1);
				}
				else if(dbObj.duplicateInfoName==false)
				{
					JOptionPane.showMessageDialog(contentPane, "This Information Name already exist \n Try different  ", "Data Save Status", 2);
				}
					
					
				
				
				
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				Frame f[]=getFrames();
				for(Frame s:f)
				{
					//System.out.println(s.getTitle());
					if(source.equalsIgnoreCase("view") && s.getTitle().toString().equalsIgnoreCase("Options menu"))
					{
						s.setVisible(true);
					}
				}
			}
		});
	}
}
