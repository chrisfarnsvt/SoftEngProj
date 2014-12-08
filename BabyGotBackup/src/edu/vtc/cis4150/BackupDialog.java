package edu.vtc.cis4150;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.awt.Toolkit;
import java.io.File;
import java.util.Collection;

/**
 * BackupDialog - Dialog for backup
 * @author YOURNAMEHERE
 */

public class BackupDialog implements ActionListener{

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	private JFrame parent;
	private BackupSystem system;
	private JDialog backupFrm;
	private JTextField fileLocation;
	private JTextField backupLocation;
	private JButton fileBtn1;
	private JButton fileBtn2;
	private JButton removeButton;
	private JRadioButton scheduleRadio;
	private JRadioButton manualRadio;
	private JTextField password;
	private JTextField username;
	private JRadioButton sambaRadio;
	private JLabel lblUserName;
	private JLabel lblPassword;
	private JComboBox<String> comboBox;
	private JButton createBackup;
	private JCheckBox encryptCheck;
	private JCheckBox compressCheck;
	JList<String> fileList;
	private JButton addFileBtn;
	private DefaultListModel<String> fileListModel;
	//private ManualSession newSession;
	private Session newSession;
	private JLabel lblBackupLocation;

	/**
	 * Create the application.
	 */
	public BackupDialog(JFrame parentFrm, BackupSystem s) {
		
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
		backupFrm.setBounds(100, 100, 358, 558);
		backupFrm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		backupFrm.setResizable(false);
		
		ButtonGroup backupType = new ButtonGroup();
		ButtonGroup encOrComp = new ButtonGroup();
		
		manualRadio = new JRadioButton("Manual Backup");
		manualRadio.setFont(new Font("SansSerif", Font.PLAIN, 13));
		manualRadio.setBounds(10, 7, 170, 23);
		backupType.add(manualRadio);
		manualRadio.setToolTipText("A one time backup.");
		manualRadio.addActionListener(this);
				
		scheduleRadio = new JRadioButton("Schedule Backup");
		scheduleRadio.setFont(new Font("SansSerif", Font.PLAIN, 13));
		scheduleRadio.setBounds(10, 33, 170, 23);
		backupType.add(scheduleRadio);
		scheduleRadio.setToolTipText("A Backup will occur at the selected interval");
		scheduleRadio.addActionListener(this);

		sambaRadio = new JRadioButton("Samba Backup");
		sambaRadio.setBounds(10, 101, 139, 23);
		sambaRadio.setFont(new Font("SansSerif", Font.PLAIN, 13));
		backupType.add(sambaRadio);
		sambaRadio.setToolTipText("A backup to an external samba server.");
		backupFrm.getContentPane().add(sambaRadio);
		sambaRadio.addActionListener(this);
		
		comboBox = new JComboBox<String>();
		comboBox.setFont(new Font("SansSerif", Font.PLAIN, 11));
		comboBox.setEnabled(false);
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"hourly", "daily", "weekly"}));
		comboBox.setBounds(20, 63, 129, 20);
				
		encryptCheck = new JCheckBox("Encrypt");
		encryptCheck.setFont(new Font("SansSerif", Font.PLAIN, 13));
		encryptCheck.setBounds(10, 186, 105, 23);
		backupFrm.getContentPane().add(encryptCheck);
		encOrComp.add(encryptCheck);
				
		compressCheck = new JCheckBox("Compress");
		compressCheck.setFont(new Font("SansSerif", Font.PLAIN, 13));
		compressCheck.setBounds(10, 212, 105, 23);
		backupFrm.getContentPane().add(compressCheck);
		encOrComp.add(compressCheck);
				
		JLabel lblNewLabel = new JLabel("File Location");
		lblNewLabel.setBounds(10, 242, 129, 16);
		lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
				
		fileLocation = new JTextField();
		fileLocation.setBounds(10, 269, 190, 23);
		backupFrm.getContentPane().setLayout(null);
		backupFrm.getContentPane().add(manualRadio);
		backupFrm.getContentPane().add(scheduleRadio);
				
		backupFrm.getContentPane().add(comboBox);
		backupFrm.getContentPane().add(encryptCheck);
		backupFrm.getContentPane().add(compressCheck);
		backupFrm.getContentPane().add(lblNewLabel);
		backupFrm.getContentPane().add(fileLocation);
				
		fileBtn1 = new JButton(" ...");
		fileBtn1.setBounds(210, 269, 39, 23);
		backupFrm.getContentPane().add(fileBtn1);
		fileBtn1.addActionListener(this);
				
		lblBackupLocation = new JLabel("Backup Location");
		lblBackupLocation.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lblBackupLocation.setBounds(10, 427, 157, 23);
		backupFrm.getContentPane().add(lblBackupLocation);
		
		fileListModel = new DefaultListModel<String>();
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(3, 328, 256, 88);
		backupFrm.getContentPane().add(scroll);
		fileList = new JList<String>(fileListModel);
		scroll.setViewportView(fileList);
				
		backupLocation = new JTextField();
		if(system.getDefaultBackupLocation()!=null)
			backupLocation.setText(system.getDefaultBackupLocation());
		backupLocation.setBounds(10, 461, 190, 23);
		backupFrm.getContentPane().add(backupLocation);
				
		fileBtn2 = new JButton(" ...");
		fileBtn2.setBounds(210, 461, 39, 23);
		backupFrm.getContentPane().add(fileBtn2);
		fileBtn2.addActionListener(this);
				
		createBackup = new JButton("Create Backup");
		createBackup.setFont(new Font("SansSerif", Font.PLAIN, 11));
		createBackup.setBounds(80, 495, 169, 23);
		backupFrm.getContentPane().add(createBackup);
		createBackup.addActionListener(this);
				
		JLabel lblFiles = new JLabel("Files");
		lblFiles.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lblFiles.setBounds(10, 303, 46, 14);
		backupFrm.getContentPane().add(lblFiles);
		
		password = new JPasswordField();
		password.setBounds(100, 159, 159, 20);
		backupFrm.getContentPane().add(password);
		password.setColumns(10);
				
		username = new JTextField();
		username.setBounds(100, 131, 159, 20);
		backupFrm.getContentPane().add(username);
		username.setColumns(10);
		
		lblUserName = new JLabel("User Name");
		lblUserName.setBounds(29, 134, 86, 14);
		backupFrm.getContentPane().add(lblUserName);
				
		lblPassword = new JLabel("Password");
		lblPassword.setBounds(29, 162, 86, 14);
		backupFrm.getContentPane().add(lblPassword);
				
		lblUserName.setEnabled(false);
		lblPassword.setEnabled(false);
		username.setEnabled(false);
		password.setEnabled(false);

		addFileBtn = new JButton("Add File");
		addFileBtn.setFont(new Font("SansSerif", Font.PLAIN, 11));
		addFileBtn.addActionListener(this);
		addFileBtn.setBounds(259, 268, 71, 23);
		backupFrm.getContentPane().add(addFileBtn);
		
		removeButton = new JButton("Remove");
		removeButton.setFont(new Font("SansSerif", Font.PLAIN, 11));
		removeButton.addActionListener(this);
		removeButton.setBounds(257, 328, 95, 88);
		backupFrm.getContentPane().add(removeButton);
		
		//manual backup selected by default
		manualRadio.doClick();
	}
	
	/**
	 * Listener for interaction with UI
	 * 
	 * @param e The event that occurs
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == createBackup){
			//we would handle the backup creation here
			//for now we will just close the window
			backupFrm.dispose();
		}
		if(e.getSource() == manualRadio) {
			lblUserName.setEnabled(false);
			 lblPassword.setEnabled(false);
			 username.setEnabled(false);
			 password.setEnabled(false);
			 comboBox.setEnabled(false);
			 backupLocation.setEnabled(true);
			 fileBtn2.setEnabled(true);
			 lblBackupLocation.setEnabled(true);
			 newSession = new ManualSession(false, false);
		 }
		 if(e.getSource() == scheduleRadio) {
			 lblUserName.setEnabled(false);
			 lblPassword.setEnabled(false);
			 username.setEnabled(false);
			 password.setEnabled(false);
			 comboBox.setEnabled(true);
			 backupLocation.setEnabled(true);
			 fileBtn2.setEnabled(true);
			 lblBackupLocation.setEnabled(true);
			 newSession = new ScheduledSession(false, false, 1, true);
		 }
		 if(e.getSource() == sambaRadio) {
			 lblUserName.setEnabled(true);
			 lblPassword.setEnabled(true);
			 username.setEnabled(true);
			 password.setEnabled(true);
			 comboBox.setEnabled(false);

			 newSession = new NetworkedSession(false, false);
		 }
		 if (e.getSource() == encryptCheck) {
			 if (compressCheck.isSelected())
			 	compressCheck.setSelected(false);
			 }
			 if (e.getSource() == compressCheck) {
			 	if (encryptCheck.isSelected())
			 		encryptCheck.setSelected(false);
			 }
		 if(e.getSource() == fileBtn1) {
			 final JFileChooser fc = new JFileChooser();
			 fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			 fc.showOpenDialog(backupFrm);
			 String location = fc.getSelectedFile().getAbsolutePath();
			 if(location!=null)
			 fileLocation.setText(location);
		 }
		 if(e.getSource() == fileBtn2) {
			 final JFileChooser fc = new JFileChooser();
			 fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			 fc.setAcceptAllFileFilterUsed(false);
			 fc.showOpenDialog(backupFrm);
			 String location = fc.getSelectedFile().getAbsolutePath();
			 if(location!=null)
			 backupLocation.setText(location);
		 }
		 if(e.getSource() == removeButton) {
			 int index = fileList.getSelectedIndex();
			 String file = fileList.getSelectedValue();
			 try {
				 newSession.removeFile(new File(file));
			 fileListModel.remove(index);
			 } catch (Exception e1) {
				 // TODO Auto-generated catch block
				 e1.printStackTrace();
			 }
			 }
		 if (e.getSource() == createBackup) {
			 newSession.setBackupLocation(backupLocation.getText());
			 newSession.setCompressed(compressCheck.isSelected());
			 newSession.setEncrypted(encryptCheck.isSelected());
			 //manual session
			 if(manualRadio.isSelected()) {
			 	 try {
			 		newSession.backupFiles();
			 	 } catch (Exception e1) {
			 		 e1.printStackTrace();
			 	 }
			 	 }
			 if(scheduleRadio.isSelected())
			 {
				int interval = 1;
				switch(String.valueOf(comboBox.getSelectedItem())){
				 	case "hourly":  interval = 1;
				 		break;
				 	case "daily":  interval = 24;
						break;
					case "weekly": interval = 168;
						break;
				 }
				 ((ScheduledSession)newSession).setInterval(interval);
			}
			 if(sambaRadio.isSelected()) {
			 	 try {
			 		newSession.backupFiles();
			 	 } catch (Exception e1) {
			 		 e1.printStackTrace();
			 	 }
			 	 }
			 	system.addSessionToIndex(newSession);
			 	backupFrm.dispose();
		 }
		 
		 if(e.getSource() == addFileBtn) {
			if (manualRadio.isSelected()){	
				try {
					newSession.addFile(new File(fileLocation.getText()));
					if (new File(fileLocation.getText()).isDirectory()) {
						Collection<File> files = FileUtils.listFiles(new File(fileLocation.getText()), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
						for (File file : files) {
							if (!file.isDirectory()) {
								newSession.addFile(file);
								fileListModel.addElement(file.getPath());
							}
						}
					}
					
					else {
						newSession.addFile(new File(fileLocation.getText()));
						fileListModel.addElement(fileLocation.getText());
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				}
			else if (scheduleRadio.isSelected()){	
				try {
					newSession.addFile(new File(fileLocation.getText()));
					fileListModel.addElement(fileLocation.getText());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				}
			 if(sambaRadio.isSelected()) {
				 try {
					 ((NetworkedSession)newSession).setAuth("75.69.70.180", username.getText(), password.getText());
					 if (new File(fileLocation.getText()).isDirectory()) {
						 Collection<File> files = FileUtils.listFiles(new File(fileLocation.getText()), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
						 for (File file : files) {
							 if (!file.isDirectory()) {
								 newSession.addFile(file);
								 fileListModel.addElement(file.getPath());
							 }
						 }
					 }
				 } catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			 }
		 }
	}
}

