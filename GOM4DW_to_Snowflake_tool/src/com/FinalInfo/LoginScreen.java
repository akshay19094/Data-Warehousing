package com.FinalInfo;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

public class LoginScreen extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JButton btnLogin;
	private JLabel lblInvalidProject;
	private JButton btnBack;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginScreen frame = new LoginScreen();
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
	public LoginScreen() {
		
		//this.setResizable(false);
		this.setTitle("Login Screen");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 100, 460, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel lblEnterLogin = new JLabel("Enter Project ID");
		lblEnterLogin.setForeground(Color.YELLOW);
		lblEnterLogin.setFont(new Font("Calibri", Font.BOLD, 16));
		lblEnterLogin.setBounds(90, 67, 122, 16);
		contentPane.add(lblEnterLogin);
		
		textField = new JTextField();
		textField.setBounds(242, 63, 66, 22);
		contentPane.add(textField);
		textField.setColumns(10);
		
		btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Dialog", Font.BOLD, 13));
		btnLogin.setBounds(171, 123, 97, 25);
		this.getRootPane().setDefaultButton(btnLogin);
		contentPane.add(btnLogin);
		
		lblInvalidProject = new JLabel("");
		lblInvalidProject.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblInvalidProject.setBounds(320, 66, 97, 16);
		contentPane.add(lblInvalidProject);
		
		btnBack = new JButton("Back");
		btnBack.setFont(new Font("Dialog", Font.BOLD, 13));
		btnBack.setBounds(171, 167, 97, 25);
		contentPane.add(btnBack);
		
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String txt=textField.getText().toString().toLowerCase();
				if(!txt.isEmpty())
				{
					if(validateProjectID(txt)!=null)
					{
						OptionsMenu o=new OptionsMenu(txt, "old");
						o.setVisible(true);
						dispose();
					}
					else
					{
						JOptionPane.showMessageDialog(contentPane, txt+"  does not exist ", "Invalid Project ID", 1);
					}
				}
				else
				{
					JOptionPane.showInternalMessageDialog(getContentPane(), "Please enter Project ID", "Invalid input", JOptionPane.INFORMATION_MESSAGE);
				}
				 
			}
		});
		
		btnBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				WelcomeScreen ws=new WelcomeScreen();
				ws.setVisible(true);
			}
		});
		
	}
	
	public Connection validateProjectID(String id)
	{
		try
		{
			DatabaseConnection db_con=new DatabaseConnection();
			
			Connection conn=db_con.getConnection(id);
			return conn;
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(contentPane, " Project ID "+id+" DOES NOT exist ", "Invalid Project ID", 1);
			return null;
		}
		
		
		
	}
}
