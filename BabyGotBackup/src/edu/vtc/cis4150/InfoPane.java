package edu.vtc.cis4150;
import java.awt.Dimension;

import javax.swing.JFrame;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;

import java.awt.GridLayout;

import javax.swing.JTree;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class InfoPane implements ActionListener{

	private JDialog dialog;
	private JDialog parent;
	private HashMap<String, Session> fileNameMap;
	private String curSelection;
	private Index index;
	private JTree fileList;
	private JList<String> versionList;
	private JList<String> detailsList;
	private DefaultMutableTreeNode top;
	private JScrollPane versionView;
	private JScrollPane detailsView;
	private DefaultListModel<String> versionListModel;
	private DefaultListModel<String> detailsListModel;
	private JPanel panel;
	private JButton restoreButton;

	/**
	 * Create the application.
	 * 
	 * type corresponds to which display method we are using for the JTree
	 * 1 - ID
	 * 2 - File
	 * 3 - Session
	 * no other values are accepted
	 */
	public InfoPane(JDialog par, Index i, int type) {
		parent = par;
		fileNameMap = new HashMap<String, Session>();
		index = i;
		initialize(type);
		dialog.setVisible(true);
	}
	
	/**
	 * Adds all the backup files in an index to the info pane
	 * 
	 * File are displayed based on their date of creation
	 */
	public void AddBackupFiles() {
		ArrayList<Session> sessions = index.viewSessions();
		if(sessions.size() != 0)
		for (Session s: sessions){
			ArrayList<File> files = ((ManualSession)s).viewFiles();
			for(File f: files) {
				top.add(new DefaultMutableTreeNode(f.getName()));
				//fileNameMap.put(f.getName, value)
				//add text to version field
			}
		}
	}

	/**
	 * Add all the backup sessions to the info pane
	 * 
	 * these sessions are displayed based on their date of creation
	 */
	public void AddBackupSessions() {
		ArrayList<Session> sessions = index.viewSessions();
		if(sessions.size() != 0)
		for (Session s: sessions){
			DefaultMutableTreeNode sess = new DefaultMutableTreeNode("Session");
			top.add(sess);
			
			ArrayList<File> files = ((ManualSession)s).viewFiles();
			for(File f: files) {
				sess.add(new DefaultMutableTreeNode(f.getName()));
			}
		}
	}
		
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(int type) {
		dialog =  new JDialog(dialog, "Info", true);
		dialog.getContentPane().setLayout(new GridLayout(0, 3, 0, 0));
		
		dialog.setBounds(100, 100, 469, 300);
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		top = new DefaultMutableTreeNode("Backups");
		
		switch (type) {
			case(1):
				break;
			case(2):
				AddBackupFiles();
				break;
			case(3):
				AddBackupSessions();
				break;
		}
		
		fileList = new JTree(top);
		fileList.clearSelection();
		
		fileList.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
	        public void valueChanged(TreeSelectionEvent evt) {
	            fileListChanged(evt);
	        }
	    });
		
		JScrollPane treeView = new JScrollPane(fileList);
		dialog.getContentPane().add(treeView);
		

		versionListModel = new DefaultListModel<String>(); 
		versionList = new JList<String>(versionListModel);
		versionView = new JScrollPane(versionList);
		versionListModel.addElement("--Versions--");
		dialog.getContentPane().add(versionView);
		
		detailsListModel = new DefaultListModel<String>(); 
		detailsListModel.addElement("--Details--");
		
		panel = new JPanel();
		dialog.getContentPane().add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		detailsList = new JList<String>(detailsListModel);
		detailsView = new JScrollPane(detailsList);
		panel.add(detailsView);
		
		restoreButton = new JButton("Restore");
		restoreButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		restoreButton.setMaximumSize(new Dimension(150, 25));
		panel.add(restoreButton);
		
	}
	
	public void fileListChanged(TreeSelectionEvent tse) {
		 curSelection = tse.getNewLeadSelectionPath().getLastPathComponent().toString();
		versionListModel.addElement(curSelection + " v0");

		detailsListModel.addElement(curSelection);
		detailsListModel.addElement("size: 40kb");
		detailsListModel.addElement("creation date: 11/28/2014");
		detailsListModel.addElement("Created By: Colin Bates");
	}

	public void actionPerformed(ActionEvent arg0) {
		//the only action that triggers this is the restore button being pressed
		
		//we need a way to lookup the backups by name
		
	}
}
