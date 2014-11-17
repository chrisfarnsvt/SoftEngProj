package edu.vtc.cis4150;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import java.awt.BorderLayout;

import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JButton;


public class InfoPane {

	private JDialog dialog;

	/**
	 * Create the application.
	 */
	public InfoPane(JFrame parent) {
		dialog =  new JDialog(parent, "Info", true);
		initialize();
		dialog.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		dialog.setBounds(100, 100, 450, 300);
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dialog.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTextPane bUpText = new JTextPane();
		bUpText.setText("Backup File(s) Affected");
		bUpText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		dialog.getContentPane().add(bUpText, BorderLayout.WEST);
		
		JTextPane versionText = new JTextPane();
		versionText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		versionText.setText("Versions\n________\n\nv1\nv2\nv3");
		dialog.getContentPane().add(versionText);
		
		JPanel resPanel = new JPanel();
		dialog.getContentPane().add(resPanel, BorderLayout.EAST);
		resPanel.setLayout(new BoxLayout(resPanel, BoxLayout.Y_AXIS));
		
		JTextPane resText = new JTextPane();
		resText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		resText.setText("Results Go Here");
		resPanel.add(resText);
		
		JButton confirmButton = new JButton("Confirm");
		resPanel.add(confirmButton);
		
	}
}
