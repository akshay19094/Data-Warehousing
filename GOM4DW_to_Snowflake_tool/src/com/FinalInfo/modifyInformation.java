package com.FinalInfo;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.SQLException;
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

//import com.sun.org.apache.xerces.internal.impl.dv.ValidatedInfo;

public class modifyInformation extends JFrame {

	private JPanel contentPane;
	private String projectID;
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
	private String dataTypes []= {"float","int","double","text","date","Data Object","timestamp","timeuuid","uuid","inet","boolean"};
	private JComboBox dataType_comboBox;
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
	private boolean checkAttr=false;
	private boolean checkCategory=false;
			

	private boolean valid_info=false,valid_duration=false,valid_category=false,valid_subCat=false,validAttr=false,validData=false,valid_freq=false;
	private boolean valid_durationNUM=false;
	private JButton btnAddAggregateInformation;
	private JButton btnBack;
	private JComboBox Info_comboBox;
	private String info_selected="";
	private boolean enable=false;
	private RequirementsClass rc;
	private RequirementsClass old_rc;

	HashMap<String,ArrayList<String>> category_subcategoriesHM;
	HashMap<String,String>Attributes;

	HashMap<String,HashMap<String,String>> category_attribute;
	ArrayList<ArrayList<String>> aggregateInfo;
	private  boolean addCategoriesBtnPressed=false;
	private boolean addAggregatesBtnPressed=false;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String projectID="p4";
					modifyInformation frame = new modifyInformation(projectID);
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
	public modifyInformation(String projectId) {

		this.projectID=projectId;
		category_subcategoriesHM =new HashMap<String,ArrayList<String>>();
		Attributes = new HashMap<String,String>();
		category_attribute =new HashMap<String,HashMap<String,String>>();
		aggregateInfo =new 	ArrayList<ArrayList<String>>();

		this.setTitle("Modify Data Object");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(550, 100, 679, 690);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblEnterInformation = new JLabel("Select Data Object");
		lblEnterInformation.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		lblEnterInformation.setBounds(110, 38, 149, 16);
		contentPane.add(lblEnterInformation);

		String [] freqs= {"yearly","monthly","weekly","daily","hourly","none"};

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
		lblFrequency.setForeground(Color.YELLOW);
		lblFrequency.setBounds(57, 51, 122, 16);
		history_panel.add(lblFrequency);
		lblFrequency.setFont(new Font("Calibri", Font.BOLD, 16));

		lblHistory = new JLabel("History ");
		lblHistory.setForeground(Color.YELLOW);
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

		String [] duration= {"years","months","weeks","days","hours","none"};
		duration_comboBox = new JComboBox(duration);
		duration_comboBox.setBounds(537, 46, 88, 22);
		duration_comboBox.setSelectedItem(null);
		history_panel.add(duration_comboBox);

		attribute_panel = new JPanel();
		attribute_panel.setBackground(Color.DARK_GRAY);
		attribute_panel.setBounds(12, 200, 297, 243);
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
		attribute_scrollPane.setBounds(12, 40, 276, 125);
		attribute_panel.add(attribute_scrollPane);

		btnAdd = new JButton("Add Blank Row");
		btnAdd.setFont(new Font("Dialog", Font.BOLD, 13));
		btnAdd.setBounds(21, 192, 137, 25);
		attribute_panel.add(btnAdd);

		btnDelete = new JButton("Delete Row");
		btnDelete.setFont(new Font("Dialog", Font.BOLD, 13));
		btnDelete.setBounds(170, 192, 118, 25);
		attribute_panel.add(btnDelete);

		lblComputed = new JLabel("DataObject and Contains Obj Attr");
		lblComputed.setForeground(Color.YELLOW);
		lblComputed.setFont(new Font("Calibri", Font.BOLD, 16));
		lblComputed.setBounds(20, 2, 250, 25);
		attribute_panel.add(lblComputed);

//		JLabel label = new JLabel("Press enter after entering the information in last row");
//		label.setFont(new Font("Tahoma", Font.PLAIN, 12));
//		label.setForeground(Color.RED);
//		label.setBounds(-1, 227, 298, 16);
//		attribute_panel.add(label);

		category_panel = new JPanel();
		category_panel.setBackground(Color.DARK_GRAY);
		category_panel.setBounds(333, 200, 316, 243);
		contentPane.add(category_panel);
		category_panel.setLayout(null);

		lblCategoriesAndSubcategories = new JLabel("Categories and Contains Categories");
		lblCategoriesAndSubcategories.setForeground(Color.YELLOW);
		lblCategoriesAndSubcategories.setHorizontalAlignment(SwingConstants.CENTER);
		lblCategoriesAndSubcategories.setFont(new Font("Calibri", Font.BOLD, 16));
		lblCategoriesAndSubcategories.setBounds(41, 7, 240, 16);
		category_panel.add(lblCategoriesAndSubcategories);

		col_names= new String[2];
		category_model = new DefaultTableModel();
		col_names[0]="Category";
		col_names[1]="Conains Category";



		category_model.setColumnIdentifiers(col_names);
		//category_model.setRowCount(1);

		category_subcategory_table = new JTable();
		category_subcategory_table.setBounds(22, 46, 263, 119);
		category_subcategory_table.setRowSelectionAllowed(true);
		category_subcategory_table.setForeground(Color.BLUE);
		category_subcategory_table.setModel(category_model);
		category_panel.add(category_subcategory_table);
		category_subcategory_table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

		category_scrollPane = new JScrollPane(category_subcategory_table);
		category_scrollPane.setBounds(12, 41, 292, 124);
		category_panel.add(category_scrollPane);

		btnAdd_cat = new JButton("Add Blank Row");
		btnAdd_cat.setFont(new Font("Dialog", Font.BOLD, 13));
		btnAdd_cat.setBounds(32, 191, 135, 25);
		category_panel.add(btnAdd_cat);

		btnDelete_cat = new JButton("Delete Row");
		btnDelete_cat.setFont(new Font("Dialog", Font.BOLD, 13));
		btnDelete_cat.setBounds(172, 191, 111, 25);
		category_panel.add(btnDelete_cat);

//		JLabel lblPressEnterAfter = new JLabel("Press enter after entering the information in last row");
//		lblPressEnterAfter.setFont(new Font("Tahoma", Font.PLAIN, 12));
//		lblPressEnterAfter.setForeground(Color.RED);
//		lblPressEnterAfter.setBounds(0, 227, 304, 16);
//		category_panel.add(lblPressEnterAfter);

		btnAddCategoryAttributes = new JButton("Add category Attributes");

		btnAddCategoryAttributes.setFont(new Font("Dialog", Font.BOLD, 13));
		btnAddCategoryAttributes.setBounds(205, 496, 233, 25);
		contentPane.add(btnAddCategoryAttributes);

		btnSave = new JButton("Save");
		btnSave.setFont(new Font("Dialog", Font.BOLD, 13));
		btnSave.setBounds(253, 605, 68, 25);
		contentPane.add(btnSave);

//		btnAddAggregateInformation = new JButton("Add Aggregate Information");
//
//		btnAddAggregateInformation.setFont(new Font("Dialog", Font.BOLD, 13));
//		btnAddAggregateInformation.setBounds(207, 547, 231, 25);
//		contentPane.add(btnAddAggregateInformation);

		btnBack = new JButton("Back");
		btnBack.setFont(new Font("Dialog", Font.BOLD, 13));
		btnBack.setBounds(333, 605, 77, 25);
		contentPane.add(btnBack);

		String p_Info[]=fetchInfoFromDataBase();
		if(p_Info==null) {
			p_Info=new String[1];
			p_Info[1]="";
		}

		Info_comboBox = new JComboBox(p_Info);
		Info_comboBox.setBounds(264, 34, 185, 22);
		Info_comboBox.setSelectedItem(null);
		contentPane.add(Info_comboBox);

		enable=false;
		attribute_table.setEnabled(false);
		category_subcategory_table.setEnabled(false);
		btnAdd.setEnabled(false);
		btnAdd_cat.setEnabled(false);
		btnDelete.setEnabled(false);
		btnDelete_cat.setEnabled(false);
		//btnAddAggregateInformation.setEnabled(false);
		btnAddCategoryAttributes.setEnabled(false);
		FreqcomboBox.setEnabled(false);
		duration_comboBox.setEnabled(false);
		txtDuration.setEnabled(false);
		btnSave.setEnabled(false);

		addListeners();


	}


	public void addListeners()
	{
		
		Info_comboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource()==Info_comboBox)
				{

					System.out.println(attribute_model.getRowCount());
					int r=attribute_model.getRowCount();
					if(attribute_model.getRowCount()!=0)
					{
						while(attribute_model.getRowCount()!=0)
						{
							attribute_model.removeRow(0);
						}
//						for(int i=0;i<r;i++)
//						{
//							attribute_model.removeRow(i);
//							i--;
//							
//						}
					}
					attribute_model.fireTableDataChanged();
					System.out.println(attribute_model.getRowCount());
					if(category_model.getRowCount()!=0)
					{
						if(category_model.getRowCount()!=0)
						{
							r=category_model.getRowCount();
							while(category_model.getRowCount()!=0)
							{
								category_model.removeRow(0);
							}
						}
					}
					
					category_subcategoriesHM =new HashMap<String,ArrayList<String>>();
					Attributes = new HashMap<String,String>();
					category_attribute =new HashMap<String,HashMap<String,String>>();
					aggregateInfo =new 	ArrayList<ArrayList<String>>();
					
					category_model.fireTableDataChanged();
					FreqcomboBox.setSelectedItem(null);
					txtDuration.setText("");
					FreqcomboBox.setSelectedItem(null);
					
					info_selected=Info_comboBox.getSelectedItem().toString().toLowerCase();
					getDatafromDB(info_selected);
					
					//Info_comboBox.setSelectedItem(null);
				
				}
				else
				{
					JOptionPane.showMessageDialog(contentPane, "Kindly Choose one option");
				}
				
			}
		});
		
//		Info_comboBox.addItemListener(new ItemListener() {
//
//
//			@Override
//			public void itemStateChanged(ItemEvent e) {
//				// TODO Auto-generated method stub
//				//System.out.println(e);
//				if(e.getSource()==Info_comboBox)
//				{
//					if(attribute_model.getRowCount()!=0)
//					{
//						for(int i=0;i<attribute_model.getRowCount();i++)
//						{
//							attribute_model.removeRow(i);
//						}
//					}
//					attribute_model.fireTableDataChanged();
//					if(category_model.getRowCount()!=0)
//					{
//						if(category_model.getRowCount()!=0)
//						{
//							for(int i=0;i<category_model.getRowCount();i++)
//							{
//								category_model.removeRow(i);
//							}
//						}
//					}
//					
//					category_subcategoriesHM =new HashMap<String,ArrayList<String>>();
//					Attributes = new HashMap<String,String>();
//					category_attribute =new HashMap<String,HashMap<String,String>>();
//					aggregateInfo =new 	ArrayList<ArrayList<String>>();
//					
//					category_model.fireTableDataChanged();
//					FreqcomboBox.setSelectedItem(null);
//					txtDuration.setText("");
//					FreqcomboBox.setSelectedItem(null);
//					
//					info_selected=Info_comboBox.getSelectedItem().toString().toLowerCase();
//					getDatafromDB(info_selected);
//					
//					//Info_comboBox.setSelectedItem(null);
//				}
//				//				if(e.getSource()==Info_comboBox)
//				//				{
//				//					info_selected=Info_comboBox.getSelectedItem().toString().toLowerCase();
//				//					getData(info_selected);
//				//				}
//				else
//				{
//					JOptionPane.showMessageDialog(contentPane, "Kindly Choose one option");
//				}
//
//			}
//		});
		
		FreqcomboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub

				int state=e.getStateChange();
				if(e.getSource()==FreqcomboBox)
				{
					if(FreqcomboBox.getSelectedItem()!=null)
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

		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//if(enable)
				{
					int res=JOptionPane.showConfirmDialog(contentPane, "Changes will not be saved. Do you want to continue?", "Save Confirmation", 2);
					if(res==JOptionPane.OK_OPTION)
					{
						dispose();
						Frame f[]=getFrames();
						for(Frame s:f)
						{
							//System.out.println(s.getTitle());
							if(s.getTitle().toString().equalsIgnoreCase("Options menu"))
							{
								s.setVisible(true);
							}
						}
					}
				}
				//				else
				//				{
				//					dispose();
				//				}

			}
		});


		btnAddCategoryAttributes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				addCategoriesBtnPressed=true;
				validateSubCategory();
				checkCategory=true;
				if(valid_category && valid_subCat)
				{
					System.out.println(enteredCategories);
					if(enteredCategories.size()>0)
					{

						for(String cat:category_attribute.keySet())
						{
							if(!enteredCategories.contains(cat))
							{
								category_attribute.remove(cat);
							}
							//System.out.println(category_attribute.get(cat).size());
						}
						categoryAttributes ca=new categoryAttributes(enteredCategories,category_attribute,btnAddCategoryAttributes,"modify");
						ca.setVisible(true);
						category_attribute=ca.category_attribute;
					}
					else
					{
						btnAddCategoryAttributes.setEnabled(false);
						JOptionPane.showMessageDialog(contentPane, "No category entered", "Warning Message", 1);
					}

				}
				btnAddCategoryAttributes.setEnabled(true);


			}
		});

//		btnAddAggregateInformation.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				addAggregatesBtnPressed=true;
//				validateAttributes();
//				checkAttr=true;
//				if(category_subcategoriesHM.size()==0) // if add caregory attributes is not called
//					validateSubCategory();
//				System.out.println(valid_category+"  "+validAttr+" "+enteredCategories.size()+" "+Attributes.size());
//				if((enteredCategories.size()>0 || Attributes.size()>0 )&& validAttr && valid_category && validData)
//				{
//					btnAddAggregateInformation.setEnabled(false);
//					AggegrateInfo aI = new AggegrateInfo(enteredCategories, Attributes,aggregateInfo,btnAddAggregateInformation,"modify");
//					aI.setVisible(true);
//					aggregateInfo =aI.aggregateInfo;
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
				
				if(checkAttr==false) { // it means no change was made to computed from table
					validAttr=true;
				validData=true;
			}
				
				if(checkCategory==false) {
					valid_category=true;
					valid_subCat=true;
				}

				System.out.println("Checked");
				System.out.println("category "+category_subcategoriesHM.size());
				System.out.println("attribute "+Attributes.size());

//				if(enteredCategories.size()==0 || category_attribute.size()==0)
//				{
//					JOptionPane.showMessageDialog(contentPane, "Minimum one category with attribute is mandatory", "Mandatory information", 2);
//					valid_category=false;
//				}

//				if(Attributes==null || Attributes.size()==0)
//				{
//					validAttr=true;
//					validData=true;
//				}
				
				if(enteredCategories.size()!=0 && category_attribute.size()==0)
				{
					JOptionPane.showMessageDialog(contentPane, "Minimum one category with attribute is mandatory", "Mandatory information", 2);
					valid_category=false;
				}
				
//				if(Attributes==null || Attributes.size()==0)
//				{
//					validAttr=true;
//					validData=true;
//				}
				
				if(Attributes.size()==0 && enteredCategories.size()==0)
				{
					JOptionPane.showInternalMessageDialog(getContentPane(), "Minimum one Attribute /Category Attribute Required", "Error", JOptionPane.ERROR_MESSAGE);
					validAttr=false;
					valid_category=false;
				}

				if(addCategoriesBtnPressed==false)
				{
					if(enteredCategories.size()>0)
						JOptionPane.showInternalMessageDialog(getContentPane(), "Kindly verify category attributes \n Changes may have affected them", "Warning", JOptionPane.WARNING_MESSAGE);
					else
						addCategoriesBtnPressed=true;
				}
				
				
				
//				if(addAggregatesBtnPressed==false)
//				{
//					JOptionPane.showInternalMessageDialog(getContentPane(), "Kindly verify Aggregate Information \n Changes may have affected them", "Warning", JOptionPane.WARNING_MESSAGE);
//				}
				if(valid_category  && valid_info && valid_subCat && validAttr && validData && valid_freq && valid_duration && addCategoriesBtnPressed )
				{
					RequirementsClass rc= new RequirementsClass(projectID, info_selected);

					if(valid_durationNUM)
						rc.setDurationNumber(Integer.parseInt(txtDuration.getText().toString().trim()));

					
					rc.setcategory_subcategory(category_subcategoriesHM);
					rc.setAggregateInfo(aggregateInfo);

					rc.setFrequency(Frequency);

					rc.setAttribute(Attributes);
					rc.setDurationFreq(durationFreq);
					
					
					rc.set_categoryAttribute(category_attribute);
					rc.setCategories(enteredCategories);

					
					confirmUserInput cup=new confirmUserInput(rc,"modify");
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
					System.out.println("btnCAt Pressed "+addCategoriesBtnPressed);
					//JOptionPane.showMessageDialog(parentComponent, message, title, messageType);

				}
			}
		});
	}


	public void getDatafromDB(String info_selected)
	{
		if(!info_selected.equals(" ") && !info_selected.equals(""))
		{
			com.FinalInfo.DatabaseConnection dbcon=new com.FinalInfo.DatabaseConnection();
			Connection con=dbcon.getConnection(projectID);
			dbQueries dbQ=new dbQueries(con);
			try {

				attribute_table.setEnabled(true);
				category_subcategory_table.setEnabled(true);
				btnAdd.setEnabled(true);
				btnAdd_cat.setEnabled(true);
				btnDelete.setEnabled(true);
				btnDelete_cat.setEnabled(true);
				//btnAddAggregateInformation.setEnabled(true);
				btnAddCategoryAttributes.setEnabled(true);
				FreqcomboBox.setEnabled(true);
				duration_comboBox.setEnabled(true);
				txtDuration.setEnabled(true);
				btnSave.setEnabled(true);


				old_rc=dbQ.getInfo(info_selected);
				fillData(old_rc);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(contentPane, "Can not find this Data Object", "Error Dialog", 2);
			}
		}
		else
		{
			JOptionPane.showMessageDialog(contentPane, "Can not find this Data Object", "Error Dialog", 2);
		}
	}
	public String [] fetchInfoFromDataBase()
	{
		com.FinalInfo.DatabaseConnection dbcon=new com.FinalInfo.DatabaseConnection();
		Connection conn=dbcon.getConnection(projectID);
		dbQueries dbq=new dbQueries(conn);
		return(dbq.fetch_pInfo_FromDataBase(projectID));
	}


	public void fillData(RequirementsClass old_rc)
	{
		Attributes= new HashMap<String,String>();
		System.out.println("POPULATING DATA");
		String frq=old_rc.getFrequency();
		if(frq!=null && !frq.trim().isEmpty())
		{

			FreqcomboBox.setSelectedItem(frq);
			Frequency=frq;

		}

		int durNum=old_rc.getDurationNum();
		if(durNum>0)
		{
			txtDuration.setText(String.valueOf(durNum));
			
		}

		String dur=old_rc.getDurationFreq();
		if(dur!=null && !dur.trim().isEmpty())
		{
			duration_comboBox.setSelectedItem(dur);
			durationFreq=dur;
		}


		HashMap<String, String> infoAttr=old_rc.getAttributes();
		Attributes.putAll(infoAttr);
		System.out.println(attribute_model.getRowCount());
		if(infoAttr!=null && infoAttr.size()>0)
		{
			//attribute_model.removeRow(0);
			for(String cate:infoAttr.keySet())
			{
				attribute_model.addRow(new Object[]{cate,infoAttr.get(cate)});

			}

		}

		HashMap<String,ArrayList<String>> cat_subcat=old_rc.getCategory_subcategory();
		ArrayList<String> cat=old_rc.getCategories(); ///

		if(cat!=null && cat.size()>0)
		{
			//category_model.removeRow(0);
			for(int j=0;j<cat.size();j++)
			{
				if(cat_subcat.containsKey(cat.get(j)))
				{
					for(String cate:cat_subcat.keySet())
					{
						ArrayList<String> subcat=cat_subcat.get(cate);
						category_model.addRow(new Object[]{cate," "});
						for(int i=0;i<subcat.size();i++)
						{
							category_model.addRow(new Object[]{subcat.get(i),cate});
							if(cat.contains(subcat.get(i)))
							cat.remove(subcat.get(i));
						}
					}
				}
				else
				{
					category_model.addRow(new Object[]{cat.get(j),null});
				}
			}


//			for(int i=0;i<categories.size();i++)
//			{
//				cat=categories.get(i);
//				if(cat_subCat.containsKey(cat))
//				{
//					ArrayList<String> subCat=cat_subCat.get(cat);
//					category_model.addRow(new Object[]{cat,String.valueOf(" ")});
//					for(int j=0;j<subCat.size();j++)
//					{
//						category_model.addRow(new Object[]{subCat.get(j),cat});
//						categories.remove(subCat.get(j));
//					}
//					
//				}
//				else
//				{
//					category_model.addRow(new Object[]{cat,String.valueOf(" ")});
//				}
//			}
			

			//for buttons, setting default
			category_attribute=old_rc.getCategory_attribute();
			aggregateInfo=old_rc.getAggregateInfo();
			
		}
	}


	public void ValidateInput()
	{
		valid_info=false;
		valid_duration=false;
		valid_durationNUM=false;
		valid_freq=false;
		String field=info_selected;
		if(field.length()==0 )
		{
			JOptionPane.showMessageDialog(contentPane, "Data Object can not be blank", "Error", 1);
			valid_info=false;
		}
		else if(checkForSpecialCharacters(field)==true)
		{
			JOptionPane.showMessageDialog(contentPane, "Data Object can not have special Characters", "Error", 1);
			valid_info=false;
		}
		else if(startWithCharacter(field)==false)
		{
			JOptionPane.showMessageDialog(contentPane, "Data Object should start with an alphabet", "Error", 1);
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

		if(Frequency!=null && Frequency.length()>0 && !Frequency.equals("") && !Frequency.equals(" ") && durationFreq!=null && durationFreq.length()>0 && !durationFreq.equals("") && !durationFreq.equals(" ") )
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
		
		
		if(Frequency!=null && !Frequency.trim().isEmpty())
		{
			if(durationFreq.trim().isEmpty() || txtDuration.getText().toString().isEmpty() )
			{
				JOptionPane.showInternalMessageDialog(contentPane, " Period and duration both should be present", "Error", 2);
			}
		}
		
		if(!durationFreq.trim().isEmpty()  && !txtDuration.getText().toString().isEmpty())
		{
			if(Frequency.trim().isEmpty())
			{
				JOptionPane.showInternalMessageDialog(contentPane, " Period and duration both should be present", "Error", 2);
			}
		}

		if((!(durationFreq==null) && !durationFreq.equalsIgnoreCase("None")) && (txtDuration==null || txtDuration.getText().toString().trim().isEmpty()))
		{
			JOptionPane.showMessageDialog(contentPane, "Error in duration. Please check ", " Invalid Input", 1);
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
		HashMap<String,String> enteredCat_subcat=new HashMap<String,String>();
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

				if(isValueExist(i, 1,category_subcategory_table))
				{
					subCategoryOf=category_subcategory_table.getValueAt(i, 1).toString().trim().toLowerCase();
					System.out.println(subCategoryOf.length());
					System.out.println(categoryName +" "+subCategoryOf);
					
					
					if(enteredCategories.contains(subCategoryOf))
					{
						if(category_subcategoriesHM.containsKey(subCategoryOf))
						{
							ArrayList<String> temp=category_subcategoriesHM.get(subCategoryOf);
							temp.add(categoryName);
							category_subcategoriesHM.put(subCategoryOf, temp);
						}
						else
						{
							ArrayList<String> temp=new ArrayList<String>();
							temp.add(categoryName);
							category_subcategoriesHM.put(subCategoryOf, temp);
						}
						
						valid_subCat=true;
					}
					else {
						JOptionPane.showMessageDialog(contentPane, subCategoryOf +" does not exist as Category", "Invalid Entry", 1);
						valid_subCat=false;
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
			}

		}
	}

	public void validateAttributes()
	{
		validAttr=true;
		validData=true;
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
						//validAttr=true;
					}
					else
					{
						JOptionPane.showMessageDialog(contentPane, attr +" Already Present", "Duplicate Entry", 2);
						validAttr=false;
					}
					//validData=true;
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
			//if(inp.length()==0 || inp.equals(" "))
			if(inp.trim().isEmpty())
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
