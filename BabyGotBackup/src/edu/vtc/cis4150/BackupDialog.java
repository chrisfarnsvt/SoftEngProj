package edu.vtc.cis4150;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.swing.JScrollPane;


public class BackupDialog implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JFrame parent;
	private System system;
	private JDialog backupFrm;
	private JTextField fileLocation;
	private JTextField backupLocation;
	private JButton fileBtn1;
	private JButton fileBtn2;
	private JRadioButton scheduleRadio;
	private JRadioButton manualRadio;
	private JComboBox<String> comboBox;
	private JButton createBackup;
	private JCheckBox encryptCheck;
	private JCheckBox compressCheck;
	private JButton addFileBtn;
	private DefaultListModel<String> fileListModel;
	private ManualSession newSession;

	/**
	 * Create the application.
	 */
	public BackupDialog(JFrame parentFrm, System s) {
		
		newSession = new ManualSession(false, false);
		
		parent  = parentFrm;
		system = s;
		
		backupFrm = new JDialog(parent, "Backup", true);
		initUI();
		backupFrm.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public final void initUI() {
		backupFrm.setIconImage(Toolkit.getDefaultToolkit().getImage(BackupDialog.class.getResource("/images/hdd1.png")));
		backupFrm.setTitle("Backup");
		backupFrm.setBounds(100, 100, 358, 459);
		backupFrm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		backupFrm.setResizable(false);
		
				ButtonGroup backupType = new ButtonGroup();
		
				manualRadio = new JRadioButton("Manual Backup");
				manualRadio.setFont(new Font("SansSerif", Font.PLAIN, 13));
				manualRadio.setBounds(10, 7, 170, 23);
				backupType.add(manualRadio);
				manualRadio.addActionListener(this);
				
				scheduleRadio = new JRadioButton("Schedule Backup");
				scheduleRadio.setFont(new Font("SansSerif", Font.PLAIN, 13));
				scheduleRadio.setBounds(10, 33, 170, 23);
				backupType.add(scheduleRadio);
				scheduleRadio.addActionListener(this);
				
				comboBox = new JComboBox<String>();
				comboBox.setFont(new Font("SansSerif", Font.PLAIN, 11));
				comboBox.setEnabled(false);
				comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"hourly", "daily", "weekly"}));
				comboBox.setBounds(20, 63, 129, 20);
				
				encryptCheck = new JCheckBox("Encrypt");
				encryptCheck.setFont(new Font("SansSerif", Font.PLAIN, 13));
				encryptCheck.setBounds(10, 90, 105, 23);
				
				compressCheck = new JCheckBox("Compressed");
				compressCheck.setFont(new Font("SansSerif", Font.PLAIN, 13));
				compressCheck.setBounds(10, 116, 105, 23);
				
				JLabel lblNewLabel = new JLabel("File Location");
				lblNewLabel.setBounds(10, 335, 129, 16);
				lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
				
				fileLocation = new JTextField();
				fileLocation.setBounds(10, 362, 190, 23);
				backupFrm.getContentPane().setLayout(null);
				backupFrm.getContentPane().add(manualRadio);
				backupFrm.getContentPane().add(scheduleRadio);
				backupFrm.getContentPane().add(comboBox);
				backupFrm.getContentPane().add(encryptCheck);
				backupFrm.getContentPane().add(compressCheck);
				backupFrm.getContentPane().add(lblNewLabel);
				backupFrm.getContentPane().add(fileLocation);
				
				fileBtn1 = new JButton(" ...");
				fileBtn1.setBounds(210, 362, 39, 23);
				backupFrm.getContentPane().add(fileBtn1);
				fileBtn1.addActionListener(this);
				
				JLabel lblBackupLocation = new JLabel("Backup Location");
				lblBackupLocation.setFont(new Font("SansSerif", Font.PLAIN, 13));
				lblBackupLocation.setBounds(10, 146, 157, 23);
				backupFrm.getContentPane().add(lblBackupLocation);
				
				fileListModel = new DefaultListModel<String>();
				
				JScrollPane listView = new JScrollPane();
				listView.setBounds(10, 234, 258, 90);
				backupFrm.getContentPane().add(listView);
				JList<String> fileList = new JList<String>(fileListModel);
				listView.setViewportView(fileList);
				
				backupLocation = new JTextField();
				backupLocation.setBounds(10, 180, 190, 23);
				backupFrm.getContentPane().add(backupLocation);
				
				fileBtn2 = new JButton(" ...");
				fileBtn2.setBounds(210, 180, 39, 23);
				backupFrm.getContentPane().add(fileBtn2);
				fileBtn2.addActionListener(this);
				
				createBackup = new JButton("Create Backup");
				createBackup.setFont(new Font("SansSerif", Font.PLAIN, 11));
				createBackup.setBounds(94, 396, 169, 23);
				backupFrm.getContentPane().add(createBackup);
				createBackup.addActionListener(this);
				
				JLabel lblFiles = new JLabel("Files");
				lblFiles.setFont(new Font("SansSerif", Font.PLAIN, 13));
				lblFiles.setBounds(10, 209, 46, 14);
				backupFrm.getContentPane().add(lblFiles);
				
				addFileBtn = new JButton("Add File");
				addFileBtn.setFont(new Font("SansSerif", Font.PLAIN, 11));
				addFileBtn.addActionListener(this);
				addFileBtn.setBounds(257, 362, 71, 23);
				backupFrm.getContentPane().add(addFileBtn);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == createBackup){
			//we would handle the backup creation here
			//for now we will jsut close the window
			backupFrm.dispose();
		}
		if(e.getSource() == manualRadio) {
			 comboBox.setEnabled(false);
		 }
		 if(e.getSource() == scheduleRadio) {
			 comboBox.setEnabled(true);
		 }
		 if(e.getSource() == fileBtn1) {
			 final JFileChooser fc = new JFileChooser();
			 int returnVal = fc.showOpenDialog(backupFrm);
			 String location = fc.getSelectedFile().getAbsolutePath();
			 if(location!=null)
			 fileLocation.setText(location);
		 }
		 if(e.getSource() == fileBtn2) {
			 final JFileChooser fc = new JFileChooser();
			 fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			 fc.setAcceptAllFileFilterUsed(false);
			 int returnVal = fc.showOpenDialog(backupFrm);
			 String location = fc.getSelectedFile().getAbsolutePath();
			 if(location!=null)
			 backupLocation.setText(location);
		 }
		 if (e.getSource() == createBackup) {
			 newSession.setBackupLocation(backupLocation.getText());
			 newSession.setCompressed(compressCheck.isSelected());
			 newSession.setEncrypted(encryptCheck.isSelected());
			 try {
				newSession.backupFiles();
			 } catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			system.addSessionToIndex(newSession);
		 }
		 if(e.getSource() == addFileBtn) {
			 try {
				newSession.addFile(new File(fileLocation.getText()));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 fileListModel.addElement(fileLocation.getText());
		 }
	}
}

