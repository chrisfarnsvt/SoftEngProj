package edu.vtc.cis4150;
import javax.swing.AbstractButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JButton;

/**
 * The dialog for displaying scheduled backups
 * @author YOURNAMEHERE
 *
 */

public class ScheduleDialog extends JDialog implements ActionListener{
	
	private static final long serialVersionUID = 2497764586856976834L;
	private ArrayList<Session> sessions;
	private JTable backupTable;
	private JButton stopButton;
	private HashMap<Integer, ScheduledSession> selectionToSession;

	/**
	 * Create the dialog.
	 */
	public ScheduleDialog(JFrame parent, BackupSystem system) {
		
		super(parent, "schedule", false);
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(BackupDialog.class.getResource("/images/hdd1.png")));
		
		selectionToSession = new HashMap<Integer, ScheduledSession>();
		setBounds(100, 100, 358, 346);
		getContentPane().setLayout(null);
		
		backupTable = new JTable(new DefaultTableModel(new Object[]{"Session ID", "Next Backup"},0));
		
		backupTable.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(backupTable);
		scrollPane.setBounds(0, 0, 342, 284);
		getContentPane().add(scrollPane);
		
		stopButton = new JButton("Stop Backing Up");
		stopButton.setBounds(0, 284, 342, 23);
		getContentPane().add(stopButton);
		stopButton.addActionListener(this);
		sessions = BackupSystem.getIndex().viewSessions();
		
		fillTable();
		
		this.setVisible(true);
	}
	
	
	/**
	 * Fills the table that makes up the dialog
	 */
	public void fillTable() {

		DefaultTableModel tm = (DefaultTableModel) backupTable.getModel();
		Object[] data = {"place", "holder"};
		int row = 0;
		int currID = -1;

		//tm.addRow(data);
		System.out.println(sessions.size());
		for(Session session: sessions) {
				if(session instanceof ScheduledSession) {
					if(((ScheduledSession) session).getSessionID() != currID) {
						currID = ((ScheduledSession) session).getSessionID();
						data[0] = "Session" + ((ScheduledSession) session).getSessionID();
						data[1] = ((ScheduledSession) session).getScheduledBackupTime().getTime().toString();
						tm.addRow(data);
						selectionToSession.put(row, (ScheduledSession) session);
				}
			}
		}	
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == stopButton) {
			int r = backupTable.getSelectedRow();
			selectionToSession.get(r).setContinueVal(false);
		}
		
	}

}
