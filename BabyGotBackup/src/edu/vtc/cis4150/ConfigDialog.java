package edu.vtc.cis4150;
import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

/**
 * Configuration dialog.
 * @author YOURNAMEHERE
 *
 */

public class ConfigDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = -8041453801782812282L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JButton chooseDirectoryButton;
	private JButton cancel;
	private JButton okButton;
	private BackupSystem system;

	/**
	 * Create the dialog.
	 */
	public ConfigDialog(JFrame parent, BackupSystem sys) {

		super(parent, "schedule", false);
		
		system = sys;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(BackupDialog.class.getResource("/images/hdd1.png")));
		setTitle("Configure");
		
		setBounds(100, 100, 305, 171);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblDefaultBackupLocation = new JLabel("Default Backup Location");
		lblDefaultBackupLocation.setBounds(20, 24, 128, 14);
		contentPanel.add(lblDefaultBackupLocation);
		
		textField = new JTextField();
		textField.setBounds(20, 49, 157, 20);
		textField.setText(system.getDefaultBackupLocation());
		contentPanel.add(textField);
		textField.setColumns(10);
		
		okButton = new JButton("OK");
		okButton.setBounds(83, 86, 89, 23);
		okButton.addActionListener(this);
		contentPanel.add(okButton);
		
		cancel = new JButton("Cancel");
		cancel.setBounds(182, 86, 89, 23);
		cancel.addActionListener(this);
		contentPanel.add(cancel);
		
		chooseDirectoryButton = new JButton("...");
		chooseDirectoryButton.setBounds(191, 48, 34, 23);
		chooseDirectoryButton.addActionListener(this);
		contentPanel.add(chooseDirectoryButton);
		
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == chooseDirectoryButton){
			 final JFileChooser fc = new JFileChooser();
			 fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			 fc.setAcceptAllFileFilterUsed(false);
			 fc.showOpenDialog(contentPanel);
			 String location = fc.getSelectedFile().getAbsolutePath();
			textField.setText(location);
		}
		if(e.getSource() == okButton){
				system.setDefaultBackupLocation(textField.getText());
			this.dispose();
		}
		if(e.getSource() == cancel){
			this.dispose();
		}
	}
}
