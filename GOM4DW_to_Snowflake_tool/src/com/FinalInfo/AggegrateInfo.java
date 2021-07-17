package com.FinalInfo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
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
import java.awt.Font;
import java.awt.Frame;

public class AggegrateInfo extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private 	JButton cancelButton;
	private 	JButton btnAdd;
	private JButton btnDelete;
	private String [] col_names;
	private DefaultTableModel model;
	private int row_index=-1;
	private JTable table;
	private JButton saveButton; 
	private JComboBox freq_comboBox;
	private String [] freqs= {"Yearly","Monthly","Weekly","Daily"};
	
	private HashMap<String,String> Attributes;
	private ArrayList<String>enteredCat;
	
	ArrayList<ArrayList<String>> aggregateInfo;
	private boolean validcat=true,validCompute=true,validAttr=true;
	private JLabel lblNote;
	private String source="";
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			AggegrateInfo dialog = new AggegrateInfo();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public AggegrateInfo(ArrayList<String>enteredCat,HashMap<String,String> Attributes,ArrayList<ArrayList<String>> aggregateInfo_old,JButton btnAddAggrInfo,String source) {
		
		this.source=source;
		this.enteredCat=enteredCat;
		this.Attributes=Attributes;
		this.setTitle("Aggregate Information");
		aggregateInfo= new ArrayList<ArrayList<String>>();
		setBounds(500, 100, 826, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				saveButton = new JButton("Save");
				saveButton.setFont(new Font("Dialog", Font.BOLD, 13));
				saveButton.setActionCommand("Save");
				buttonPane.add(saveButton);
				getRootPane().setDefaultButton(saveButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setFont(new Font("Dialog", Font.BOLD, 13));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}

		//		btnAdd = new JButton("Add");
		//		btnAdd.setBounds(106, 179, 77, 25);
		//		contentPanel.add(btnAdd);
		//
		//		btnDelete = new JButton("Delete");
		//		btnDelete.setBounds(231, 179, 77, 25);

		col_names= new String[6];
		model = new DefaultTableModel();
		col_names[0]="Aggegate";
		col_names[1]="Computed From";
		col_names[2]="Category";	
		col_names[3]="Frequency";
		col_names[4]="Duration";
		col_names[5]="Duration Frequency";
				

		model.setColumnIdentifiers(col_names);
		model.setRowCount(1);

		
		freq_comboBox=  new JComboBox<String>(freqs);
		contentPanel.add(freq_comboBox);
		

		table = new JTable();
		table.setBounds(12, 32, 393, 127);
		table.setRowSelectionAllowed(true);
		table.setForeground(Color.BLUE);
		table.setModel(model);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		table.getColumnModel().getColumn(4).setPreferredWidth(50);
		table.getColumnModel().getColumn(5).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(freq_comboBox));
		table.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(freq_comboBox));
		contentPanel.add(table);
		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);


		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(22, 32, 782, 127);
		contentPanel.add(scrollPane);
		
			btnAdd = new JButton("Add Blank row");
			btnAdd.setFont(new Font("Dialog", Font.BOLD, 13));
			btnAdd.setBounds(280, 172, 125, 25);
			contentPanel.add(btnAdd);
		
			btnDelete = new JButton("Delete row");
			btnDelete.setFont(new Font("Dialog", Font.BOLD, 13));
			btnDelete.setBounds(428, 172, 101, 25);
			contentPanel.add(btnDelete);
			
//			lblNote = new JLabel("Press enter after entering the information in last row");
//			lblNote.setForeground(Color.RED);
//			lblNote.setBackground(Color.RED);
//			lblNote.setFont(new Font("Tahoma", Font.PLAIN, 12));
//			lblNote.setBounds(7, 202, 438, 16);
//			contentPanel.add(lblNote);
		


		//		table = new JTable();
		//		table.setBounds(12, 13, 408, 153);
		//		table.setRowSelectionAllowed(true);
		//		table.setForeground(Color.BLUE);
		//		table.setModel(model);
		//		contentPanel.add(table);
		//
		//		JScrollPane scrollPane = new JScrollPane(table);
		//		scrollPane.setBounds(12, 13, 408, 153);
		//		contentPanel.add(scrollPane);

	    fill_prevInfo(aggregateInfo_old);
		addListeners(btnAddAggrInfo);
	}
	
	public void fill_prevInfo(ArrayList<ArrayList<String>> aggregateInfo_old)
	{
		ArrayList<ArrayList<String>> aggr=aggregateInfo_old;
		if(aggr!=null && aggr.size()>0)
		{
			model.removeRow(0);
			for(int i=0;i<aggr.size();i++)
			{
				ArrayList<String> inner=aggr.get(i);
				if(inner!=null && inner.size()>0)
				{
					model.addRow(new Object[]{inner.get(0),inner.get(1),inner.get(2),inner.get(3),inner.get(4),inner.get(5)});
				}
				
			}
			
		}
	}

	public void addListeners(JButton btnAddAggrInfo)
	{
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub2
				int res=JOptionPane.showConfirmDialog(contentPanel, "This data will not be saved.\n Do you want to continue?", "Cancel Confirmation", 2);
				if(res==JOptionPane.OK_OPTION)
				{
					dispose();
					Frame f[]=Frame.getFrames();
					for(Frame s:f)
					{
						System.out.println(s.getTitle());
						if( source.equalsIgnoreCase("new") && s.getTitle().toString().equalsIgnoreCase("Create new Information"))
						{
							btnAddAggrInfo.setEnabled(true);
							s.setVisible(true);
						}
						else if(source.equalsIgnoreCase("modify") && s.getTitle().toString().equalsIgnoreCase("Modify Information"))
						{
							btnAddAggrInfo.setEnabled(true);
							s.setVisible(true);
						}
					}
					
				}

			}
		});
		
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				model.addRow(new Object[]{String.valueOf(" "),String.valueOf(" ")});

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
		
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				validAttr=true;
				validcat=true;
				saveData();
				if(validAttr && validcat && validCompute)
					dispose();
				
				btnAddAggrInfo.setEnabled(true);
			}
		});
	}
	
	
	public void saveData()
	{
		ArrayList<String> inner;
		for(int i=0;i<table.getRowCount();i++)
		{
			if(isValueExist(i, 0)==false && isValueExist(i, 1)==false && isValueExist(i, 2)==false)
			{
			   continue;
			   
			}
			else if(isValueExist(i, 0) && isValueExist(i, 1) )
			{
				inner = new ArrayList<String>(Collections.nCopies(6, " "));
				
				
				inner.set(0,table.getValueAt(i, 0).toString().trim()); // aggregate
				if(validateComputedFrom(table.getValueAt(i, 1).toString().trim())) // computed from
				{
					inner.set(1,table.getValueAt(i, 1).toString().trim());
				}
				else
				{
					validAttr=false;
					inner = new ArrayList<String>(Collections.nCopies(6, " "));
				}
				
				if( isValueExist(i, 2))
				{
					if(  validateCategory(table.getValueAt(i, 2).toString().trim())) // category
					{
						inner.set(2,table.getValueAt(i, 2).toString().trim());
					}
					else
					{
						validcat=false;
						inner = new ArrayList<String>(Collections.nCopies(6, " "));
					}
				}
				
				
				
				
				
				if(isValueExist(i, 3))
				{
					inner.set(3,table.getValueAt(i, 3).toString().trim()); //freq
				}
				
				if(isValueExist(i, 4))
				{
					inner.set(4,table.getValueAt(i, 4).toString().trim()); //duration
					if(isValueExist(i, 5))
					{
						inner.set(5,table.getValueAt(i, 5).toString().trim()); //duration freq
					}
				}
				
				if(!inner.get(0).trim().isEmpty())
				aggregateInfo.add(inner);
			}
			else
			{
				JOptionPane.showMessageDialog(contentPanel, "Value Missing in row " +i, "Missing Value Error", 1);
				validAttr=false;
				validcat=false;
			}
		}
	}
	
	public boolean isValueExist(int r,int c)
	{
		try
		{
			String inp= table.getValueAt(r, c).toString();
			System.out.println(inp.length());
			if(inp.length()==0 || inp.equals(" "))
				return false;
			
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	
	
	public boolean validateCategory(String cat)
	{
		cat=cat.trim().toLowerCase();
		String category[] = null;
		System.out.println(cat);
		if(cat!=null && cat.length()>0)
		{
			if(cat.contains(","))
			{
				category=cat.split(",");
			}
			else
			{
				category=new String[1];
				category[0]=cat;
			}
		}
		
		
		if(enteredCat!=null && enteredCat.size()>0)
		{
			for(int i=0;i<category.length;i++)
			{
				if(!enteredCat.contains(category[i].trim().toLowerCase()))
				{
					JOptionPane.showMessageDialog(contentPanel, category[i]+ " not a valid category", "Invalid Entry", 1);
					return false;
				}
			}
			
			return true;
		
		}
		if(enteredCat==null || enteredCat.size()==0 && !cat.trim().isEmpty())
		{
			JOptionPane.showInternalMessageDialog(getContentPane(), "No category exists", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
//		else
//		{
//			return false;
//		}
		return true;
		
	}
	
	public boolean validateComputedFrom(String compute)
	{
		String attr[] = null;
		compute=compute.trim().toLowerCase();
		if(compute!=null && compute.length()>0)
		{
			if(compute.contains(","))
			{
				attr=compute.split(",");
			}
			else
			{
				attr=new String[1];
				attr[0]=compute;
			}
		}
		if(Attributes!=null && Attributes.size()>0)
		{
			for(int i=0;i<attr.length;i++)
			{
				if(!Attributes.containsKey(attr[i].trim().toLowerCase()))
				{
					JOptionPane.showMessageDialog(contentPanel, attr[i]+ " not a valid attribute", "Invalid Entry", 1);
					return false;
				}
			}
			return true;
		}
		else
		{
			return false;
		}
		
	}

}
