package edu.vtc.cis4150;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JScrollPane;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.SwingConstants;

import java.awt.Component;
import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.GridLayout;

import javax.swing.ImageIcon;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Files;

/**
 * @author Colin B
 *
 *This is the main window for Baby Got backup
 *
 * Icons made by Freepik, SimpleIcon from http://www.flaticon.com
 * flaticon.com is licensed by CC BY 3.0
 */

public class UserInterface implements ActionListener{

	private BackupSystem system;
	private JFrame frmBabyGotBackup;
	private JButton backupButton;
	private JButton restoreButton;
	private JButton scheduleButton;
	private JButton configureButton;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserInterface window = new UserInterface();
					window.frmBabyGotBackup.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws Exception 
	 */
	public UserInterface() throws Exception {
		system = new BackupSystem();
		File ini = new File(System.getProperty("user.home") + "/backup.ini");
		if (ini.exists()) {
			System.out.println("gets here");
			SessionParser sp = new SessionParser(System.getProperty("user.home") + "/backup.ini");
			sp.parseFrom(system);
		}
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmBabyGotBackup = new JFrame();
		frmBabyGotBackup.setFont(new Font("Franklin Gothic Medium", Font.PLAIN, 17));
		frmBabyGotBackup.setIconImage(Toolkit.getDefaultToolkit().getImage(UserInterface.class.getResource("/images/hdd1.png")));
		frmBabyGotBackup.setBackground(new Color(240, 240, 240));
		frmBabyGotBackup.setTitle("Baby Got Backup");
		frmBabyGotBackup.setBounds(100, 100, 638, 159);
		
		frmBabyGotBackup.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmBabyGotBackup.addWindowListener(new WindowAdapter() { 
			public void windowClosing(WindowEvent e) {
				try {
					exit();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		frmBabyGotBackup.getContentPane().setLayout(new GridLayout(0, 4, 0, 0));
		
		backupButton = new JButton("Backup");
		backupButton.setBackground(Color.WHITE);
		backupButton.setFont(new Font("Franklin Gothic Medium", Font.PLAIN, 17));
		backupButton.setIcon(new ImageIcon(UserInterface.class.getResource("/images/backup.png")));
		frmBabyGotBackup.getContentPane().add(backupButton);
		backupButton.setSize(frmBabyGotBackup.getWidth()/4, frmBabyGotBackup.getHeight());
		backupButton.setVerticalTextPosition(JButton.BOTTOM);
		backupButton.setHorizontalTextPosition(JButton.CENTER);
		backupButton.addActionListener(this);
		
		restoreButton = new JButton("Restore");
		restoreButton.setBackground(Color.WHITE);
		restoreButton.setFont(new Font("Franklin Gothic Medium", Font.PLAIN, 17));
		restoreButton.setIcon(new ImageIcon(UserInterface.class.getResource("/images/data4.png")));
		frmBabyGotBackup.getContentPane().add(restoreButton);
		restoreButton.setVerticalTextPosition(JButton.BOTTOM);
		restoreButton.setHorizontalTextPosition(JButton.CENTER);
		restoreButton.addActionListener(this);
		
		scheduleButton = new JButton("Schedule");
		scheduleButton.setBackground(Color.WHITE);
		scheduleButton.setFont(new Font("Franklin Gothic Medium", Font.PLAIN, 17));
		scheduleButton.setIcon(new ImageIcon(UserInterface.class.getResource("/images/calendar68.png")));
		frmBabyGotBackup.getContentPane().add(scheduleButton);
		scheduleButton.setVerticalTextPosition(JButton.BOTTOM);
		scheduleButton.setHorizontalTextPosition(JButton.CENTER);
		scheduleButton.addActionListener(this);
		
		configureButton = new JButton("Configure");
		configureButton.setBackground(Color.WHITE);
		configureButton.setFont(new Font("Franklin Gothic Medium", Font.PLAIN, 17));
		configureButton.setIcon(new ImageIcon(UserInterface.class.getResource("/images/machine2.png")));
		frmBabyGotBackup.getContentPane().add(configureButton);
		configureButton.setVerticalTextPosition(JButton.BOTTOM);
		configureButton.setHorizontalTextPosition(JButton.CENTER);
		configureButton.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == backupButton){
			BackupDialog dialog = new BackupDialog(frmBabyGotBackup, system);
		}
		if(e.getSource() == restoreButton){
			RestoreDialog dialog = new RestoreDialog(frmBabyGotBackup, system);
		}
		if(e.getSource() == scheduleButton){
			ScheduleDialog dialog = new ScheduleDialog(frmBabyGotBackup);
		}
		if(e.getSource() == configureButton){
			ConfigDialog dialog = new ConfigDialog(frmBabyGotBackup);
		}
	}

	public void exit() throws Exception {
		String homedir = System.getProperty("user.home");
		SessionParser sp = new SessionParser(homedir + "/backup.ini");
		for (Session s : system.getIndex().viewSessions())
			sp.writeToFile(s);
		System.exit(0);
	}
}
