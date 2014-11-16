import java.awt.Dialog;
import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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


public class BackupDialog implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JDialog backupFrm;
	private JTextField fileLocation;
	private JTextField backupLocation;
	private JButton fileBtn1;
	private JButton fileBtn2;
	private JRadioButton scheduleRadio;
	private JRadioButton manualRadio;
	private JComboBox<String> comboBox;
	private JButton createBackup;

	/**
	 * Create the application.
	 */
	public BackupDialog(JFrame parent) {
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
		backupFrm.setBounds(100, 100, 304, 350);
		backupFrm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		backupFrm.setResizable(false);
		
				ButtonGroup backupType = new ButtonGroup();
		
				manualRadio = new JRadioButton("Manual Backup");
				manualRadio.setBounds(10, 7, 129, 23);
				backupType.add(manualRadio);
				manualRadio.addActionListener(this);
				
				scheduleRadio = new JRadioButton("Schedule Backup");
				scheduleRadio.setBounds(10, 33, 129, 23);
				backupType.add(scheduleRadio);
				scheduleRadio.addActionListener(this);
				
				comboBox = new JComboBox<String>();
				comboBox.setEnabled(false);
				comboBox.setModel(new DefaultComboBoxModel(new String[] {"hourly", "daily", "weekly"}));
				comboBox.setBounds(20, 63, 129, 20);
				
				JCheckBox encryptCheck = new JCheckBox("Encrypt");
				encryptCheck.setBounds(10, 90, 105, 23);
				
				JCheckBox compressCheck = new JCheckBox("Compressed");
				compressCheck.setBounds(10, 116, 105, 23);
				
				JLabel lblNewLabel = new JLabel("File Location");
				lblNewLabel.setBounds(10, 146, 71, 16);
				lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
				
				fileLocation = new JTextField();
				fileLocation.setBounds(10, 173, 190, 23);
				backupFrm.getContentPane().setLayout(null);
				backupFrm.getContentPane().add(manualRadio);
				backupFrm.getContentPane().add(scheduleRadio);
				backupFrm.getContentPane().add(comboBox);
				backupFrm.getContentPane().add(encryptCheck);
				backupFrm.getContentPane().add(compressCheck);
				backupFrm.getContentPane().add(lblNewLabel);
				backupFrm.getContentPane().add(fileLocation);
				
				fileBtn1 = new JButton(" ...");
				fileBtn1.setBounds(210, 173, 39, 23);
				backupFrm.getContentPane().add(fileBtn1);
				fileBtn1.addActionListener(this);
				
				JLabel lblBackupLocation = new JLabel("Backup Location");
				lblBackupLocation.setFont(new Font("Tahoma", Font.PLAIN, 13));
				lblBackupLocation.setBounds(10, 211, 105, 23);
				backupFrm.getContentPane().add(lblBackupLocation);
				
				backupLocation = new JTextField();
				backupLocation.setBounds(10, 245, 190, 23);
				backupFrm.getContentPane().add(backupLocation);
				
				fileBtn2 = new JButton(" ...");
				fileBtn2.setBounds(210, 245, 39, 23);
				backupFrm.getContentPane().add(fileBtn2);
				fileBtn2.addActionListener(this);
				
				createBackup = new JButton("Create Backup");
				createBackup.setBounds(83, 282, 129, 23);
				backupFrm.getContentPane().add(createBackup);
				createBackup.addActionListener(this);
		
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
			 fileLocation.setText(location);
		 }
		 if(e.getSource() == fileBtn2) {
			 final JFileChooser fc = new JFileChooser();
			 int returnVal = fc.showOpenDialog(backupFrm);
			 String location = fc.getSelectedFile().getAbsolutePath();
			 backupLocation.setText(location);
		 }
	}
}

