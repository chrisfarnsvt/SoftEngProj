import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.SwingConstants;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.JList;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import net.miginfocom.swing.MigLayout;


public class ScheduleDialog extends JDialog {

	private final JPanel Header = new JPanel();

	/**
	 * Create the dialog.
	 */
	public ScheduleDialog(JFrame parent) {
		
		super(parent, "schedule", false);
		
		setBounds(100, 100, 358, 346);
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		Header.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(Header);
		Header.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		{
			JLabel lblUpcomingBackups = new JLabel("Upcoming Backups");
			lblUpcomingBackups.setHorizontalAlignment(SwingConstants.CENTER);
			lblUpcomingBackups.setFont(new Font("Tahoma", Font.PLAIN, 17));
			Header.add(lblUpcomingBackups);
		}
		
		JPanel UpcomingBackups = new JPanel();
		getContentPane().add(UpcomingBackups);
		UpcomingBackups.setLayout(new GridLayout(0, 3, 0, 0));
		JLabel label = new JLabel("Last Backup");
		label.setFont(new Font("Tahoma", Font.BOLD, 13));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		UpcomingBackups.add(label);
		JLabel label_1 = new JLabel("File");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		UpcomingBackups.add(label_1);
		JLabel label_2 = new JLabel("Next Backup");
		label_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		UpcomingBackups.add(label_2);
		
		Component verticalStrut = Box.createVerticalStrut(10);
		UpcomingBackups.add(verticalStrut);
		
		Component verticalStrut_1 = Box.createVerticalStrut(10);
		UpcomingBackups.add(verticalStrut_1);
		
		Component verticalStrut_2 = Box.createVerticalStrut(10);
		UpcomingBackups.add(verticalStrut_2);
		JLabel lblNewLabe1 = new JLabel("11/16/2014 3:45PM");
		UpcomingBackups.add(lblNewLabe1);
		lblNewLabe1.setHorizontalAlignment(SwingConstants.CENTER);
		{
			JLabel lblNewLabe2 = new JLabel("File.txt");
			UpcomingBackups.add(lblNewLabe2);
			lblNewLabe2.setHorizontalAlignment(SwingConstants.CENTER);
		}
		JLabel lblNewLabe3 = new JLabel("11/16/2014 4:45PM");
		UpcomingBackups.add(lblNewLabe3);
		lblNewLabe3.setHorizontalAlignment(SwingConstants.CENTER);
		
		this.setVisible(true);
	}

}
