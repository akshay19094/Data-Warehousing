package com.FinalInfo;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

//import com.sun.media.sound.Toolkit;

import java.awt.Color;

public class WelcomeScreen extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JButton btnNewUser ;
	private JButton btnLogin;
	private JLabel lblWaitInfo;
	private JButton btnExit;
	private boolean enable=false;
	String frame_name ="windows";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WelcomeScreen frame = new WelcomeScreen();
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
	public WelcomeScreen() {
		this.setResizable(false);
		this.setTitle("Welcome to the Tool");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(500, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLabel lblWelcomeToReqiurements = new JLabel("Welcome to Reqiurements Capturing Tool");
		lblWelcomeToReqiurements.setBackground(new Color(0, 51, 51));
		lblWelcomeToReqiurements.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		lblWelcomeToReqiurements.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblWelcomeToReqiurements, BorderLayout.NORTH);
		
		panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		//panel.setBackground(Color.LIGHT_GRAY);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		btnNewUser = new JButton("New User ? Click Here");
		btnNewUser.setFont(new Font("Dialog", Font.BOLD, 13));
		
		btnNewUser.setBounds(114, 62, 206, 25);
		panel.add(btnNewUser);
		
		btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Dialog", Font.BOLD, 13));
		btnLogin.setBounds(138, 113, 163, 25);
		panel.add(btnLogin);
		
		lblWaitInfo = new JLabel("Press your choice and wait for next screen to show up");
		lblWaitInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblWaitInfo.setForeground(Color.YELLOW);
		lblWaitInfo.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		lblWaitInfo.setBounds(26, 25, 396, 19);
		panel.add(lblWaitInfo);
		
		btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Dialog", Font.BOLD, 13));
		btnExit.setBounds(173, 162, 97, 25);
		panel.add(btnExit);
		
		addActionListeners();
	}

	public void addActionListeners()
	{             
		DatabaseConnection db_con = new DatabaseConnection();
                btnNewUser.addActionListener(new ActionListener() {                       
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//lblWaitInfo.setText("Please wait . project is being created");
				btnExit.setEnabled(false);
				btnLogin.setEnabled(false);
                                //System.out.println("in action performed");
				String pid=db_con.newConnection();
				System.out.println(pid);
				if(!pid.contains("-"))
				{
					//lblWaitInfo.setText(" ");
					NewUserScreen nu=new NewUserScreen(pid);
					nu.setVisible(true);
					close();
				}
				else
				{
					JOptionPane.showMessageDialog(contentPane, "Error in Creating Tables.", "Error", 2);
					
				}
				
				btnExit.setEnabled(true);
				btnLogin.setEnabled(true);
			}
		});
		
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				LoginScreen ls=new LoginScreen();
				ls.setVisible(true);
				//close();
				dispose();
			}
		});
		
		btnExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
	}
	
	public void close()
	{
		WindowEvent winClosingEvent =new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		getToolkit().getSystemEventQueue().postEvent(winClosingEvent);
	}
	
	
}
