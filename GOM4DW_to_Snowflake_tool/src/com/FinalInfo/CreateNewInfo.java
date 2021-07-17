package com.FinalInfo;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

//import com.sun.media.sound.ModelAbstractChannelMixer;

public class CreateNewInfo extends JFrame {

	private String projectID;
	private JPanel contentPane;
	private JTextField txtInfo;
	private JLabel lblEnterInformation ;
	private JLabel lblFrequency;
	private JComboBox FreqcomboBox;
	private JPanel history_panel;
	private JLabel lblHistory;
	private JLabel lblDuration;
	private JTextField txtDuration;
	private JComboBox duration_comboBox;
	private JPanel attribute_panel;
	private JTable attribute_table;
	private JButton btnAdd;
	private JButton btnDelete;
	private DefaultTableModel attribute_model;
	private DefaultTableModel category_model;
	private String [] col_names;
	private JLabel lblComputed;
	private int row_index;
	private String dataTypes []= {"float","int","double","text","date","Data Object","timestamp","timeuuid","uuid","inet","boolean","set<text>","set<int>","set<double>",
			"set<timestamp>","set<timeuuid>","list<int>","list<text>","list<float>","map<text,text>","map<text,int>","map<int,int>","map<int,text>","map<text,double>","map<timestamp,text>","map<timestamp,int>"
			,"map<timestamp,float>"};
	private String change_Type[]={"no_update","type-1","type-2"};
	private JComboBox dataType_comboBox;
	private JComboBox changeType_comboBox;
	private JPanel category_panel;
	private JLabel lblCategoriesAndSubcategories;
	private JTable category_subcategory_table;
	private JScrollPane category_scrollPane;
	private JButton btnAdd_cat;
	private JButton btnDelete_cat;
	private JButton btnAddCategoryAttributes;
	private JButton btnSave;
	private String durationFreq;
	private String Frequency;
	private ArrayList<String>freqs= new ArrayList<String>(Arrays.asList("ho","da","we","mo","ye"));
	ArrayList<String> enteredCategories=new ArrayList<String>();

	private boolean valid_info=false,valid_duration=false,valid_category=false,valid_subCat=false,validAttr=false,validData=false,valid_freq=false;
	private boolean valid_durationNUM=false;
	private JButton btnAddAggregateInformation;
	private JButton btnBack;

	HashMap<String,ArrayList<String>> category_subcategoriesHM;
	ArrayList<String> changeType=new ArrayList<String>();
	HashMap<String,String>Attributes;

	HashMap<String,HashMap<String,String>> category_attribute;
	ArrayList<ArrayList<String>> aggregateInfo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String projectID="";
					CreateNewInfo frame = new CreateNewInfo(projectID);
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
	public CreateNewInfo(String projectID) {

		this.setResizable(false);
		this.projectID=projectID;
		category_subcategoriesHM =new HashMap<String,ArrayList<String>>();
		Attributes = new HashMap<String,String>();
		category_attribute =new HashMap<String,HashMap<String,String>>();
		aggregateInfo =new 	ArrayList<ArrayList<String>>();


		this.setTitle("Create New DataObject");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 100, 679, 690);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblEnterInformation = new JLabel("Enter DataObject");
		lblEnterInformation.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		lblEnterInformation.setBounds(110, 38, 149, 16);
		contentPane.add(lblEnterInformation);

		txtInfo = new JTextField();
		txtInfo.setBounds(289, 34, 258, 22);
		contentPane.add(txtInfo);
		txtInfo.setColumns(10);

		String [] freqs= {"Yearly","Monthly","Weekly","Daily","Hourly","None"};

		history_panel = new JPanel();
		history_panel.setBackground(Color.DARK_GRAY);
		history_panel.setBounds(12, 84, 637, 93);
		contentPane.add(history_panel);
		history_panel.setLayout(null);
		FreqcomboBox = new JComboBox(freqs);
		FreqcomboBox.setBounds(157, 49, 98, 22);
		FreqcomboBox.setSelectedItem(null);
		history_panel.add(FreqcomboBox);

		lblFrequency = new JLabel("Period");
		lblFrequency.setHorizontalAlignment(SwingConstants.CENTER);
		lblFrequency.setForeground(Color.YELLOW);
		lblFrequency.setBounds(39, 51, 122, 16);
		history_panel.add(lblFrequency);
		lblFrequency.setFont(new Font("Calibri", Font.BOLD, 16));

		lblHistory = new JLabel("History ");
		lblHistory.setHorizontalAlignment(SwingConstants.CENTER);
		lblHistory.setForeground(Color.YELLOW);
		lblHistory.setBackground(Color.YELLOW);
		lblHistory.setFont(new Font("Calibri", Font.BOLD, 18));
		lblHistory.setBounds(275, 13, 71, 16);
		history_panel.add(lblHistory);

		lblDuration = new JLabel("Duration");
		lblDuration.setForeground(Color.YELLOW);
		lblDuration.setFont(new Font("Calibri", Font.BOLD, 16));
		lblDuration.setBounds(332, 50, 76, 16);
		history_panel.add(lblDuration);

		txtDuration = new JTextField();
		txtDuration.setToolTipText("Enter number");
		txtDuration.setBounds(430, 46, 82, 22);
		history_panel.add(txtDuration);
		txtDuration.setColumns(10);

		String [] duration= {"Years","Months","Weeks","Days","Hours","None"};
		duration_comboBox = new JComboBox(duration);
		duration_comboBox.setBounds(537, 46, 88, 22);
		duration_comboBox.setSelectedItem(null);
		history_panel.add(duration_comboBox);

		attribute_panel = new JPanel();
		attribute_panel.setBackground(Color.DARK_GRAY);
		attribute_panel.setBounds(12, 200, 297, 288);
		contentPane.add(attribute_panel);
		attribute_panel.setLayout(null);


		dataType_comboBox= new JComboBox<String>(dataTypes);
		contentPane.add(dataType_comboBox);
		
		
		

		col_names= new String[2];
		attribute_model = new DefaultTableModel();
		col_names[0]="Attribute Name";
		col_names[1]="Data Type";



		attribute_model.setColumnIdentifiers(col_names);
		//attribute_model.setRowCount(1);

		attribute_table = new JTable();
		attribute_table.setBounds(143, 13, 371, 91);
		attribute_table.setRowSelectionAllowed(true);
		attribute_table.setForeground(Color.BLUE);
		attribute_table.setModel(attribute_model);
		attribute_table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

		attribute_table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(dataType_comboBox));

		attribute_panel.add(attribute_table);

		JScrollPane attribute_scrollPane = new JScrollPane(attribute_table);
		attribute_scrollPane.setBounds(12, 40, 276, 168);
		attribute_panel.add(attribute_scrollPane);

		btnAdd = new JButton("Add Blank Row");
		btnAdd.setFont(new Font("Dialog", Font.PLAIN, 13));
		btnAdd.setBounds(29, 221, 127, 25);
		attribute_panel.add(btnAdd);

		btnDelete = new JButton("Delete Row");
		btnDelete.setFont(new Font("Dialog", Font.PLAIN, 13));
		btnDelete.setBounds(168, 221, 108, 25);
		attribute_panel.add(btnDelete);

		lblComputed = new JLabel("DataObject and Contains Obj Attr");
		lblComputed.setBackground(Color.YELLOW);
		lblComputed.setForeground(Color.YELLOW);
		lblComputed.setFont(new Font("Calibri", Font.BOLD, 16));
		lblComputed.setBounds(20, 2, 250, 25);
		attribute_panel.add(lblComputed);

//		JLabel label = new JLabel("Press enter after entering the information in last row");
//		label.setForeground(new Color(255, 0, 0));
//		label.setFont(new Font("Tahoma", Font.PLAIN, 12));
//		label.setBounds(0, 259, 298, 16);
//		attribute_panel.add(label);

		category_panel = new JPanel();
		category_panel.setBackground(Color.DARK_GRAY);
		category_panel.setBounds(333, 200, 316, 288);
		contentPane.add(category_panel);
		category_panel.setLayout(null);

		lblCategoriesAndSubcategories = new JLabel("Categories and Contains Categories");
		lblCategoriesAndSubcategories.setForeground(Color.YELLOW);
		lblCategoriesAndSubcategories.setHorizontalAlignment(SwingConstants.CENTER);
		lblCategoriesAndSubcategories.setFont(new Font("Calibri", Font.BOLD, 16));
		lblCategoriesAndSubcategories.setBounds(41, 7, 250, 16);
		category_panel.add(lblCategoriesAndSubcategories);

		col_names= new String[3];
		category_model = new DefaultTableModel();
		col_names[0]="Category";
		col_names[1]="Contained Category";
		col_names[2]="Change Type";

		changeType_comboBox= new JComboBox<String>(change_Type);
		contentPane.add(changeType_comboBox);



		category_model.setColumnIdentifiers(col_names);
		//category_model.setRowCount(1);

		category_subcategory_table = new JTable();
		category_subcategory_table.setBounds(22, 46, 263, 119);
		category_subcategory_table.setRowSelectionAllowed(true);
		category_subcategory_table.setForeground(Color.BLUE);
		category_subcategory_table.setModel(category_model);
		category_panel.add(category_subcategory_table);
		category_subcategory_table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

		category_subcategory_table.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(changeType_comboBox));

		category_panel.add(category_subcategory_table);

		category_scrollPane = new JScrollPane(category_subcategory_table);
		category_scrollPane.setBounds(10, 41, 294, 168);
		category_panel.add(category_scrollPane);

		btnAdd_cat = new JButton("Add Blank Row");
		btnAdd_cat.setFont(new Font("Dialog", Font.PLAIN, 13));
		btnAdd_cat.setBounds(20, 221, 138, 25);
		category_panel.add(btnAdd_cat);

		btnDelete_cat = new JButton("Delete Row");
		btnDelete_cat.setFont(new Font("Dialog", Font.PLAIN, 13));
		btnDelete_cat.setBounds(177, 221, 115, 25);
		category_panel.add(btnDelete_cat);

//		JLabel lblPressEnterAfter = new JLabel("Press enter after entering the information in last row");
//		lblPressEnterAfter.setFont(new Font("Tahoma", Font.PLAIN, 12));
//		lblPressEnterAfter.setForeground(Color.RED);
//		lblPressEnterAfter.setBounds(0, 258, 304, 16);
//		category_panel.add(lblPressEnterAfter);

		btnAddCategoryAttributes = new JButton("Add category Attributes");

		btnAddCategoryAttributes.setFont(new Font("Dialog", Font.BOLD, 13));
		btnAddCategoryAttributes.setBounds(205, 511, 233, 25);
		contentPane.add(btnAddCategoryAttributes);

		btnSave = new JButton("Save");
		btnSave.setFont(new Font("Dialog", Font.BOLD, 13));
		btnSave.setBounds(238, 605, 68, 25);
		contentPane.add(btnSave);

//		btnAddAggregateInformation = new JButton("Add Aggregate Information");
//
//		btnAddAggregateInformation.setFont(new Font("Dialog", Font.BOLD, 13));
//		btnAddAggregateInformation.setBounds(206, 555, 231, 25);
//		contentPane.add(btnAddAggregateInformation);
//
		btnBack = new JButton("Back");
		btnBack.setFont(new Font("Dialog", Font.BOLD, 13));
		btnBack.setBounds(333, 605, 77, 25);
		contentPane.add(btnBack);

//		if(this.isVisible()==true)
//		{
//			btnAddCategoryAttributes.setEnabled(true);
//		}

		addListeners();
	}

	public void addListeners()
	{

		FreqcomboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub

				int state=e.getStateChange();
				if(e.getSource()==FreqcomboBox)
				{
					Frequency=FreqcomboBox.getSelectedItem().toString().toLowerCase();

				}
				else
				{
					JOptionPane.showMessageDialog(contentPane, "Kindly Choose one option");
				}

			}
		});
		
		duration_comboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub

				int state=e.getStateChange();
				if(e.getSource()==duration_comboBox)
				{
					durationFreq=duration_comboBox.getSelectedItem().toString().toLowerCase();

				}
				else
				{
					JOptionPane.showMessageDialog(contentPane, "Kindly Choose one option");
				}

			}
		});


		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				attribute_model.addRow(new Object[]{String.valueOf(""),String.valueOf("")});

			}
		});



		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				row_index=attribute_table.getSelectedRow();
				if(row_index!=-1)
				{
					attribute_model.removeRow(row_index);
					attribute_model.fireTableDataChanged();
					row_index=-1;
				}
				else
				{
					JOptionPane.showMessageDialog(contentPane, "No row Selected");
				}
			}	


		});

		btnAdd_cat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				category_model.addRow(new Object[]{String.valueOf(""),String.valueOf("")});

			}
		});



		btnDelete_cat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				row_index=category_subcategory_table.getSelectedRow();
				if(row_index!=-1)
				{
					category_model.removeRow(row_index);
					category_model.fireTableDataChanged();
					row_index=-1;
				}
				else
				{
					JOptionPane.showMessageDialog(contentPane, "No row Selected");
				}
			}	


		});

		
		
		btnAddCategoryAttributes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				validateSubCategory();
				if(valid_category && valid_subCat)
				{
					if(enteredCategories.size()>0)
					{

						categoryAttributes ca=new categoryAttributes(enteredCategories,category_attribute,btnAddCategoryAttributes,"new");
						ca.setVisible(true);
						btnAddCategoryAttributes.setEnabled(false);
						category_attribute=ca.category_attribute;
						
					}
					else
					{
						btnAddCategoryAttributes.setEnabled(false);
						JOptionPane.showMessageDialog(contentPane, "No category entered", "Warning Message", 1);
						btnAddCategoryAttributes.setEnabled(true);
					}

				}
				//btnAddCategoryAttributes.setEnabled(true);

			}
		});

//		btnAddAggregateInformation.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//
//				validateAttributes();
//				
//				if(category_subcategoriesHM.size()==0) // if add caregory attributes is not called
//					validateSubCategory();
//				
//				System.out.println(valid_category+"  "+validAttr+" "+enteredCategories.size()+" "+Attributes.size());
//				if(enteredCategories.size()>0 || Attributes.size()>0 && validAttr && valid_category && validData)
//				{
//					
//					AggegrateInfo aI = new AggegrateInfo(enteredCategories, Attributes,aggregateInfo,btnAddAggregateInformation,"new");
//					aI.setVisible(true);
//					btnAddAggregateInformation.setEnabled(false);
//					aggregateInfo =aI.aggregateInfo;
//					//btnAddAggregateInformation.setEnabled(true);
//				
//				}
//				else if(enteredCategories.size()==0 && Attributes.size()==0)
//				{
//					btnAddAggregateInformation.setEnabled(false);
//					JOptionPane.showMessageDialog(contentPane, "No attribute entered or there is some error ", "Error in attributes or Category table", 1);
//					validAttr=false;
//					btnAddAggregateInformation.setEnabled(true);
////					validData=false;
////					valid_category=false;
////					valid_subCat=false;
//				}
//				//btnAddAggregateInformation.setEnabled(true);
//			}
//		});

		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				category_model.fireTableDataChanged();
				attribute_model.fireTableDataChanged();

				ValidateInput();

				//if(category_subcategoriesHM.size()==0)
					validateSubCategory();
				//if(Attributes.size()==0)
					validateAttributes();

				System.out.println("Checked");
				System.out.println("category "+category_subcategoriesHM.size());
				System.out.println("attribute "+Attributes.size());


				if(enteredCategories.size()!=0 && category_attribute.size()==0)
				{
					JOptionPane.showMessageDialog(contentPane, "Minimum one category with attribute is mandatory", "Mandatory information", 2);
					valid_category=false;
				}
				
				if(Attributes==null || Attributes.size()==0)
				{
					validAttr=true;
					validData=true;
				}
				
				if(Attributes.size()==0 && enteredCategories.size()==0)
				{
					JOptionPane.showInternalMessageDialog(getContentPane(), "Minimum one Attribute /Category Attribute Required", "Error", JOptionPane.ERROR_MESSAGE);
					validAttr=false;
					valid_category=false;
				}

				if(valid_category  && valid_info && valid_subCat && validAttr && validData && valid_freq && valid_duration)
				{
					RequirementsClass rc= new RequirementsClass(projectID, txtInfo.getText().toString().trim());
					if(valid_durationNUM)
						rc.setDurationNumber(Integer.parseInt(txtDuration.getText().toString().trim()));
					
					rc.setcategory_subcategory(category_subcategoriesHM);
					rc.setAggregateInfo(aggregateInfo);
					
					rc.setFrequency(Frequency);
					
					rc.setAttribute(Attributes);
					System.out.println(durationFreq);
					rc.setDurationFreq(durationFreq);
					rc.set_categoryAttribute(category_attribute);
					rc.setCategories(enteredCategories);

					rc.setchangeType(changeType);
					System.out.println(changeType);
					System.out.println("xyz"+category_subcategoriesHM);

//					DatabaseConnection dbcon= new DatabaseConnection();
//					
//					com.FinalInfo.dbQueries dbObj=new dbQueries(dbcon.getConnection(projectID));
//					if(dbObj.InsertDataIntoDatabase(rc)) {
//						JOptionPane.showMessageDialog(contentPane, "Saved Successfully", "Data Save Status", 2);
//						dispose();
//					}
//					else
//					{
//						JOptionPane.showMessageDialog(contentPane, "Can not save Data! Error Occured. Retry", "Data Save Status", 1);
//					}
					confirmUserInput cup=new confirmUserInput(rc,"create");
					cup.setVisible(true);
					
				}
				else
				{
					System.out.println("valid_category "+ valid_category );
					System.out.println("valid_duration "+ valid_duration );
					System.out.println("valid_info "+ valid_info );
					System.out.println("valid_subCat "+ valid_subCat );
					System.out.println("validAttr "+ validAttr );
					System.out.println("validData "+ validData );
					System.out.println("valid_freq "+ valid_freq );
					
				}
			}
		});
		
		btnBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int res=JOptionPane.showConfirmDialog(contentPane, "changes will not be saved .Do you want to continue?", "Confirm", 1);
				if(res==JOptionPane.OK_OPTION)
				{
					
					Frame f[]=getFrames();
					for(Frame s:f)
					{
						//System.out.println(s.getTitle());
						if(s.getTitle().toString().equalsIgnoreCase("Options menu"))
						{
							s.setVisible(true);
						}
					}
					dispose();
					
					
				}
			}
		});

	}

	public void ValidateInput()
	{
		valid_info=false;
		valid_duration=false;
		valid_durationNUM=false;
		valid_freq=false;
		String field=txtInfo.getText().toString();
		if(field.length()==0 )
		{
			JOptionPane.showMessageDialog(contentPane, "Information can not be blank", "Error", 1);
			valid_info=false;
		}
		else if(checkForSpecialCharacters(field)==true)
		{
			JOptionPane.showMessageDialog(contentPane, "Information can not have special Characters", "Error", 1);
			valid_info=false;
		}
		else if(startWithCharacter(field)==false)
		{
			JOptionPane.showMessageDialog(contentPane, "Information should start with an alphabet", "Error", 1);
			valid_info=false;
		}
		else
		{
			valid_info=true;
		}

		try
		{
			String inp= txtDuration.getText().toString();
			if(!inp.isEmpty()) {
				Integer.parseInt(inp);
				valid_durationNUM=true;
			}
			else
			{
				valid_durationNUM=false;
			}
			

		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(contentPane, "Duration should be Number", " Invalid Input", 1);
			valid_durationNUM=false;
		}

		if(Frequency!=null && !Frequency.trim().isEmpty() && durationFreq!=null && !durationFreq.trim().isEmpty() )
		{
			String fr=Frequency.substring(0, 2).trim().toLowerCase();
			String durFr=durationFreq.substring(0,2).trim().toLowerCase();
			System.out.println(freqs.indexOf(fr)+" "+freqs.indexOf(durFr));
			if(freqs.indexOf(fr)>freqs.indexOf(durFr))
			{
				JOptionPane.showMessageDialog(contentPane, "Period should be lower than Duration", " Invalid Input", 1);
				valid_freq=false;
			}
			else
			{
				valid_freq=true;
			}
		}
		else
		{
			valid_freq=true;
		}
		
		if((!(durationFreq==null) && !durationFreq.equalsIgnoreCase("None")) && (txtDuration==null || txtDuration.getText().toString().trim().isEmpty()))
		{
			JOptionPane.showMessageDialog(contentPane, "Enter in duration. Please check ", " Invalid Input", 1);
			valid_duration=false;
		}
		else
		{
			valid_duration=true;
		}

	}

	public void validateSubCategory()
	{
		category_subcategoriesHM = new HashMap<String,ArrayList<String>>();
		enteredCategories =new ArrayList<String>();
		valid_subCat=true;
		valid_category=false;

		String categoryName="",subCategoryOf="";
		if(category_subcategory_table.getRowCount()==0)
		{
			valid_category=true;
		}
		
		for(int i=0;i<category_subcategory_table.getRowCount();i++)
		{

			if(isValueExist(i, 0,category_subcategory_table))
			{
				categoryName=category_subcategory_table.getValueAt(i, 0).toString().trim().toLowerCase();
				String change_Type= category_subcategory_table.getValueAt(i, 2).toString().trim().toLowerCase();
				changeType.add(change_Type);
				System.out.println(categoryName +" "+changeType);
				if(checkForSpecialCharacters(categoryName)==true)
				{
					JOptionPane.showInternalMessageDialog(contentPane, "Category name can not contain special characters", "Syntax Error", JOptionPane.ERROR_MESSAGE);
					valid_category=false;
					break;
				}
				else if(startWithCharacter(categoryName)==false)
				{
					JOptionPane.showInternalMessageDialog(contentPane, "Category name should start with alphabet", "Syntax Error", JOptionPane.ERROR_MESSAGE);
					valid_category=false;
					break;
				}
				if(!enteredCategories.contains(categoryName))
				 enteredCategories.add(categoryName);

				if(isValueExist(i, 1,category_subcategory_table)) {
					subCategoryOf = category_subcategory_table.getValueAt(i, 1).toString().trim().toLowerCase();
					System.out.println(subCategoryOf.length());
					System.out.println(categoryName + " " + subCategoryOf);
					if (enteredCategories.contains(subCategoryOf)) {
						if (category_subcategoriesHM.containsKey(subCategoryOf)) {
							ArrayList<String> temp = category_subcategoriesHM.get(subCategoryOf);
							temp.add(categoryName);
							category_subcategoriesHM.put(subCategoryOf, temp);
						} else {
							ArrayList<String> temp = new ArrayList<String>();
							temp.add(categoryName);
							category_subcategoriesHM.put(subCategoryOf, temp);
						}

						valid_subCat = true;
					} else {
						JOptionPane.showMessageDialog(contentPane, subCategoryOf + " does not exist as Category", "Invalid Entry", 1);
						valid_subCat = false;
					}
				}
//				else
//				{
//					if(!category_subcategoriesHM.containsKey(categoryName))
//					{
//						category_subcategoriesHM.put(categoryName, null);
//					}
//					else
//					{
//						JOptionPane.showMessageDialog(contentPane, categoryName+ " already exist" );
//						valid_category=false;
//					}
//				}
				valid_category=true;
			}
			else if(isValueExist(i, 1,category_subcategory_table))
			{
				JOptionPane.showMessageDialog(contentPane, "Category Name can not be null" );
				valid_category=false;
				break;
			}
//			else
//			{
//				valid_category=true;
//			}

		}
	}

	public void validateAttributes()
	{
		attribute_model.fireTableDataChanged();
		validAttr=false;
		validData=false;
		Attributes = new HashMap<String,String>();
		for(int i=0;i<attribute_table.getRowCount();i++)
		{
			if(isValueExist(i, 0,attribute_table))
			{
				if(isValueExist(i, 1,attribute_table))
				{
					String attr=attribute_table.getValueAt(i, 0).toString().trim().toLowerCase();
					
					if(checkForSpecialCharacters(attr)==true)
					{
						JOptionPane.showInternalMessageDialog(contentPane, "Attribute can not contain special characters", "Syntax Error", JOptionPane.ERROR_MESSAGE);
						validAttr=false;
						break;
						
						
					}
					else if(startWithCharacter(attr)==false)
					{
						JOptionPane.showInternalMessageDialog(contentPane, "Attribute should start with alphabet", "Syntax Error", JOptionPane.ERROR_MESSAGE);
						validAttr=false;
						break;
					}
					
					String dataType= attribute_table.getValueAt(i, 1).toString().trim().toLowerCase();
					if(!Attributes.containsKey(attr))
					{
						Attributes.put(attr, dataType);
						validAttr=true;
					}
					else
					{
						JOptionPane.showMessageDialog(contentPane, attr +" Already Present", "Duplicate Entry", 2);
						validAttr=false;
					}
					validData=true;
				}
				else
				{
					JOptionPane.showMessageDialog(contentPane, "Data type missing in computed from table", " ", 2);
					validData=false;
					break;
				}
				
			}

		}
	}

	public boolean isValueExist(int r,int c,JTable table)
	{
		try
		{
			String inp= table.getValueAt(r, c).toString().toLowerCase();
			//System.out.println(inp.length());
			if(inp.length()==0 || inp.equals(" "))
				return false;

			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	
	public boolean checkForSpecialCharacters(String inp)
	{
		Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(inp);
		return m.find();
		
	}
	
	public boolean startWithCharacter(String inp)
	{
		char c=inp.charAt(0);
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') ? true :false;	
	}
}
