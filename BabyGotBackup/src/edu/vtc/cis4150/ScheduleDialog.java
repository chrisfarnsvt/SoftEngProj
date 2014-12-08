package edu.vtc.cis4150;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.BoxLayout;

//please

public class ScheduleDialog extends JDialog {
	
	private static final long serialVersionUID = 2497764586856976834L;
	private ArrayList<Session> sessions;
	private JTable backupTable;

	/**
	 * Create the dialog.
	 */
	public ScheduleDialog(JFrame parent, BackupSystem system) {
		
		super(parent, "schedule", false);
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(BackupDialog.class.getResource("/images/hdd1.png")));
		
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		setBounds(100, 100, 358, 346);
		
		backupTable = new JTable(new DefaultTableModel(new Object[]{"Session ID", "Next Backup"},0));
		
		backupTable.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(backupTable);
		getContentPane().add(scrollPane);
		
		sessions = BackupSystem.getIndex().viewSessions();
		
		fillTable();
		
		this.setVisible(true);
	}
	
	public void fillTable() {

		DefaultTableModel tm = (DefaultTableModel) backupTable.getModel();
		Object[] data = {"place", "holder"};

		//tm.addRow(data);
		System.out.println(sessions.size());
		for(Session session: sessions) {
			if(session instanceof ScheduledSession) {
				data[0] = "Session" + ((ScheduledSession) session).getSessionID();
				data[1] = ((ScheduledSession) session).getScheduledBackupTime().getTime().toString();
				tm.addRow(data);
			}
		}
	}

}
