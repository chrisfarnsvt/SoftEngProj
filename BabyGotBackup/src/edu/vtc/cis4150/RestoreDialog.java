package edu.vtc.cis4150;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

/**
 * The dialogue for restoring a file
 * @author YOURNAMEHERE
 *
 */

public class RestoreDialog extends JDialog implements ActionListener{
	
	private static final long serialVersionUID = 3120046731931658230L;
	private JDialog restoreFrm;
	private JButton idButton;
	private JButton fileButton;
	private JButton sessionButton;
	private JButton archiveButton;
	private BackupSystem system;

	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public RestoreDialog(JFrame parent, BackupSystem s) {
		
		restoreFrm = new JDialog(parent, "Backup", true);
		system = s;
		initUI();
		restoreFrm.setVisible(true);
		
	}
	
	/**
	 * Initializes the ui
	 */
	public void initUI(){
		
		restoreFrm.setBounds(100, 100, 450, 300);
		restoreFrm.getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		restoreFrm.getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblNewLabel = new JLabel("Sort By:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(lblNewLabel);
		{
			idButton = new JButton("Session ID");
			idButton.setAlignmentY(0.0f);
			contentPanel.add(idButton);
			idButton.addActionListener(this);
		}
		{
			fileButton = new JButton("File");
			contentPanel.add(fileButton);
			fileButton.addActionListener(this);
		}
		{
			sessionButton = new JButton("Session");
			contentPanel.add(sessionButton);
			sessionButton.addActionListener(this);
		}
		{
			archiveButton = new JButton("Archive");
			contentPanel.add(archiveButton);
			archiveButton.addActionListener(this);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == idButton){
			restoreFrm.dispose();
			new InfoPane(restoreFrm, system.getIndex(), 1);
		}
		if(e.getSource() == fileButton){
			restoreFrm.dispose();
			new InfoPane(restoreFrm, system.getIndex(), 2);
		}
		if(e.getSource() == sessionButton){
			restoreFrm.dispose();
			new InfoPane(restoreFrm, system.getIndex(), 3);
		}
		if(e.getSource() == archiveButton){
			restoreFrm.dispose();
			new InfoPane(restoreFrm, system.getIndex(), 4);
		}
		
	}
}
