package com.FinalInfo;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

public class NewUserScreen extends JFrame {

	private JPanel contentPane;
	private static String project_id="";
	private JButton btnNext;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					NewUserScreen frame = new NewUserScreen(project_id);
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
	public NewUserScreen(String project_ID) {
		
		this.project_id=project_ID;
		
		this.setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 100, 306, 221);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		btnNext = new JButton("Next");
		btnNext.setFont(new Font("Dialog", Font.BOLD, 13));
		btnNext.setBounds(88, 87, 97, 29);
		panel.add(btnNext);
		
		JLabel lblPlease = new JLabel("Your project ID is "+ project_id);
		lblPlease.setForeground(Color.YELLOW);
		lblPlease.setBounds(0, 13, 277, 37);
		panel.add(lblPlease);
		lblPlease.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlease.setFont(new Font("Calibri", Font.BOLD, 16));
		
		btnNext.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				OptionsMenu o = new OptionsMenu(project_id, "new");
				o.setVisible(true);
				dispose();
			}
		});
	}

}
