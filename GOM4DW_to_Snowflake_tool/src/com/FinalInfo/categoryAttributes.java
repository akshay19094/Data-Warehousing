package com.FinalInfo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;

public class categoryAttributes extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private String dataTypes []= {"float","int","double","text","date","timestamp","timeuuid","uuid","inet","boolean","set<text>","set<int>","set<double>",
			"set<timestamp>","set<timeuuid>","list<int>","list<text>","list<float>","map<text,text>","map<text,int>","map<int,int>","map<int,text>","map<text,double>","map<timestamp,text>","map<timestamp,int>"
			,"map<timestamp,float>"};
	private String [] col_names;
	private DefaultTableModel model;
	private 	JButton btnAdd;
	private JButton btnDelete;
	private int row_index=-1;
	private JComboBox dataType_comboBox;
	private JButton saveButton;
	private JButton cancelButton;
	private JComboBox cat;
	private boolean valid=false;
	
	HashMap<String,HashMap<String,String>> category_attribute;
	private JLabel lblNote;
	private String source="";
	/**
	 * Launch the application.
	 */
//		public static void main(String[] args) {
//			try {
//				ArrayList<String> Categories = null;
//				categoryAttributes dialog = new categoryAttributes(Categories);
//				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//				dialog.setVisible(true);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}

	/**
	 * Create the dialog.
	 */
	public categoryAttributes(ArrayList<String> Categories,HashMap<String,HashMap<String,String>> category_attribute_old,JButton btnaddcatAttr,String source) {

		this.setTitle("Category Attributes");
		this.source=source;
		category_attribute =new HashMap<String,HashMap<String,String>>();
		this.setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
		setBounds(500, 100, 450, 322);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);





		btnAdd = new JButton("Add Blank Row");
		btnAdd.setBounds(92, 179, 123, 25);
		contentPanel.add(btnAdd);

		btnDelete = new JButton("Delete row");
		btnDelete.setBounds(231, 179, 110, 25);
		contentPanel.add(btnDelete);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				saveButton = new JButton("Save");
				saveButton.setActionCommand("Save");
				buttonPane.add(saveButton);
				getRootPane().setDefaultButton(saveButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
//
		dataType_comboBox= new JComboBox<String>(dataTypes);
		contentPanel.add(dataType_comboBox);

		String category[] =new String[Categories.size()];
		for(int i=0;i<Categories.size();i++)
			category[i]=Categories.get(i);

		cat=  new JComboBox<String>(category);
		contentPanel.add(cat);

		col_names= new String[3];
		model = new DefaultTableModel();
		
		col_names[0]="Category";
		col_names[1]="Attribute Name";
		col_names[2]="Data Type";	

		model.setColumnIdentifiers(col_names);
		//model.setRowCount(1);

		table = new JTable();
		table.setBounds(12, 13, 408, 153);
		table.setRowSelectionAllowed(true);
		table.setForeground(Color.BLUE);
		table.setModel(model);
		table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(cat));
		table.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(dataType_comboBox));
		contentPanel.add(table);
		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(12, 13, 408, 153);
		contentPanel.add(scrollPane);
		
//		lblNote = new JLabel("Press enter after entering the information in last row");
//		lblNote.setForeground(Color.RED);
//		lblNote.setBounds(2, 222, 302, 16);
//		contentPanel.add(lblNote);

		previousInfo(category_attribute_old);
		addRows(Categories);
		addListeners(btnaddcatAttr,Categories);

	}

	public void previousInfo(HashMap<String,HashMap<String,String>> category_attribute_old)
	{
		
		HashMap<String,HashMap<String,String>> cat_attr=category_attribute_old;
		 if(cat_attr!=null && cat_attr.size()>0)
		 {
			 //model.removeRow(0);
			 for(String cate:cat_attr.keySet())
			 {
				 HashMap<String,String> inner=cat_attr.get(cate);
				 if(inner!=null && inner.size()>0)
				 {
					 for(String attri:inner.keySet())
					 {
						 model.addRow(new Object[]{cate,attri,inner.get(attri)});
					 }
				 }
				 
				 
			 }
			
		 }
	}
	public void addRows(ArrayList<String> Categories)
	{
		//if(table.getRowCount()==0)
		ArrayList<String> alreadypresentCat=new ArrayList<String>();
		for(int i=0;i<table.getRowCount();i++)
		{
			if(isValueExist(i, 0))
			{
				alreadypresentCat.add(table.getValueAt(i, 0).toString());
			}
		}
		{
			for(int i=0;i<Categories.size();i++)
			{
				if(!alreadypresentCat.contains(Categories.get(i)))
				model.addRow(new Object[]{Categories.get(i),""});
			}
			model.fireTableDataChanged();
			
		}
		
		
	}
	public void addListeners(JButton btnAddcatAttr,ArrayList<String> Categories)
	{
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				model.addRow(new Object[]{String.valueOf(""),String.valueOf("")});

			}
		});



		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				row_index=table.getSelectedRow();
				if(row_index!=-1)
				{
					model.removeRow(row_index);
					model.fireTableDataChanged();
					row_index=-1;
				}
				else
				{
					JOptionPane.showMessageDialog(getContentPane(), "No row Selected");
				}

			}	


		});

		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub2
				int res=JOptionPane.showConfirmDialog(contentPanel, " This Data will not be saved.\n Do you want to continue? ", "Cancel Confirmation", 2);
				if(res==JOptionPane.OK_OPTION)
				{
					dispose();
					Frame f[]=Frame.getFrames();
					System.out.println(source);
					for(Frame s:f)
					{
						System.out.println(s.getTitle());
						if( source.equalsIgnoreCase("new") && s.getTitle().toString().equalsIgnoreCase("Create New DataObject"))
						{
							btnAddcatAttr.setEnabled(true);
							s.setVisible(true);
						}
						else if(source.equalsIgnoreCase("modify") && s.getTitle().toString().equalsIgnoreCase("Modify Data Object"))
						{
							btnAddcatAttr.setEnabled(true);
							s.setVisible(true);
						}
					}
					
				}

			}
		});

		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				saveData(Categories);
				if(valid)
				  dispose();
				btnAddcatAttr.setEnabled(true);
			}
		});

	}

	public void saveData(ArrayList<String>Categories)
	{
		ArrayList<String> Categories_copy = null;
		for(int i=0;i<table.getRowCount();i++)
		{
			if(isValueExist(i, 0)==false && isValueExist(i, 1)==false && isValueExist(i, 2)==false)
			{
			   continue;
			   
			}
			else if(isValueExist(i, 0) && isValueExist(i, 1) && isValueExist(i, 2) )
			{
				String category= table.getValueAt(i, 0).toString().toLowerCase();
				   String attribute=table.getValueAt(i, 1).toString().toLowerCase();
				   String dataType = table.getValueAt(i, 2).toString().toLowerCase();
				   
				   if(category_attribute.containsKey(category))
				   {
					   HashMap<String,String> innerHM= category_attribute.get(category);
					   if(!innerHM.containsKey(attribute))
					   {
						   innerHM.put(attribute, dataType);
						   category_attribute.put(category, innerHM);
					   }
					   else
					   {
						   JOptionPane.showMessageDialog(contentPanel, attribute +" already present", "Duplicate Attribute Entry", 1);
					   }
				   }
				   else
				   {
					   HashMap<String,String> innerHM = new HashMap<String,String>();
					   innerHM.put(attribute, dataType);
					   category_attribute.put(category, innerHM);
				   }
				   
				   valid=true;
				   
//				     Categories_copy=new ArrayList<String>();
//				    Categories_copy.addAll(Categories);
//				    if(Categories_copy.contains(table.getValueAt(i, 0).toString()))
//				    {
//				    	Categories_copy.remove(table.getValueAt(i, 0).toString());
//				    }
			}
			else
			{
				JOptionPane.showMessageDialog(contentPanel, "Value Missing in row " +i, "Missing Value Error", 1);
				valid=false;
			}
		}
		
//		if(Categories_copy!=null)
//		{
//			if(Categories_copy.size()==0)
//			{
//				valid=true;
//			}
//			else
//			{
//				valid =false;
//				JOptionPane.showInternalMessageDialog(contentPanel, "All Categories", title, messageType);
//			}
//		}
	}
	
	public boolean isValueExist(int r,int c)
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
}
