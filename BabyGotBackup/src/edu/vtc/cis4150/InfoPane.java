package edu.vtc.cis4150;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;

import java.awt.GridLayout;

import javax.swing.JTree;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The panel containing information for the backups, before a restore
 * @author YOURNAMEHERE
 *
 */

public class InfoPane implements ActionListener{

	private JDialog dialog;
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
	private int selectedIndex;
	private JButton deleteButton;
	private Boolean curIsEncrypted;
	private Boolean curIsCompressed;

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
		new HashMap<String, Session>();
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
			ArrayList<File> files = (s).viewFiles();
			for(File f: files) {
				top.add(new DefaultMutableTreeNode(f.getName()));
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
			
			ArrayList<File> files = (s).viewFiles();
			for(File f: files) {
				sess.add(new DefaultMutableTreeNode(f.getName()));
			}
			top.add(sess);
		}
	}
	
	/**
	 * Add an ID to the session
	 */
	public void AddBackupSessionID() {
		ArrayList<Session> sessions = index.viewSessions();
		if(sessions.size() != 0)
		for (Session s: sessions){
			DefaultMutableTreeNode sess = new DefaultMutableTreeNode("Session " + (((ScheduledSession)s).getSessionID()));
			ArrayList<File> files = s.viewFiles();
			for(File f: files) {
				sess.add(new DefaultMutableTreeNode(f.getName()));
			}
			top.add(sess);
		}
	}
		
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(int type) {
		dialog =  new JDialog(dialog, "Info", true);
		dialog.setIconImage(Toolkit.getDefaultToolkit().getImage(BackupDialog.class.getResource("/images/hdd1.png")));
		dialog.getContentPane().setLayout(new GridLayout(0, 3, 0, 0));
		
		dialog.setBounds(100, 100, 521, 300);
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		top = new DefaultMutableTreeNode("Backups");
		
		switch (type) {
			case(1):
				AddBackupSessionID();
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
		panel.setLayout(null);
		detailsList = new JList<String>(detailsListModel);
		detailsView = new JScrollPane(detailsList);
		detailsView.setBounds(0, 0, 168, 239);
		panel.add(detailsView);
		
		restoreButton = new JButton("Restore");
		restoreButton.setBounds(0, 238, 85, 23);
		restoreButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		restoreButton.setMaximumSize(new Dimension(75, 25));
		restoreButton.addActionListener(this);
		panel.add(restoreButton);
	
		deleteButton = new JButton("Delete");
		deleteButton.setBounds(83, 238, 85, 23);
		deleteButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		deleteButton.setMaximumSize(new Dimension(75, 25));
		deleteButton.addActionListener(this);
		panel.add(deleteButton);
		
	}
	
	public void fileListChanged(TreeSelectionEvent tse) {
		versionListModel.removeAllElements();
		detailsListModel.removeAllElements();
		versionList.removeAll();
		detailsList.removeAll();
		
		//take no action if the selected node isnt there anymore
		if(tse.getNewLeadSelectionPath() == null)
			return;
		
		curSelection = tse.getNewLeadSelectionPath().getLastPathComponent().toString();
		selectedIndex = top.getIndex((TreeNode)tse.getNewLeadSelectionPath().getLastPathComponent());
		File curr = getFileFromName(curSelection);
		versionListModel.addElement(curSelection);

		BasicFileAttributes attr;
		try {
			attr = Files.readAttributes(curr.toPath(), BasicFileAttributes.class); 
			detailsListModel.addElement(curSelection);
			detailsListModel.addElement("Backup Size - " + Double.toString(curr.length()/1000) + "kb");
			Date created = new Date(attr.creationTime().to(TimeUnit.MILLISECONDS));
			detailsListModel.addElement("Backup Created On - " + created.toString());
			if(curIsEncrypted)
				detailsListModel.addElement("Encrypted");
			else
				detailsListModel.addElement("Not Encrypted");
			if(curIsCompressed)
				detailsListModel.addElement("Compressed");
			else
				detailsListModel.addElement("Not Compressed");
		} catch (IOException e) {
			e.printStackTrace();
		}
		}

	//for handling the restore button press
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == restoreButton) {
			File curr = getFileFromName(curSelection);
			for(Session s : index.viewSessions()) {
				for(File f: s.viewFiles()){
					String ext = ".bgb";
					if((s).getEncrypted())
						ext = ".enc";
					if((s).getCompressed())
						ext = ".zip";
				if ((f.getName()+ext).equals(curr.getName()))
					try {
						(s).restoreFile(curr);
						
					} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, "Restore Failed.");
							e1.printStackTrace();
							return;
						}
				}
			}
			JOptionPane.showMessageDialog(null, "Restore Successful!");
		}
		if(e.getSource() == deleteButton) {
			File curr = getFileFromName(curSelection);
			for(Session s : index.viewSessions()) {
				for(File f: (s).viewFiles()) {
						String ext = ".bgb
						if((s).getEncrypted())
							ext = ".enc";
						if((s).getCompressed())
							ext = ".zip";
					if ((f.getName()+ext).equals(curr.getName()))
						try {
							(s).removeFile(f);
							DefaultTreeModel model = (DefaultTreeModel)fileList.getModel();
							model.removeNodeFromParent((MutableTreeNode)top.getChildAt(selectedIndex));
							return;
							//top.remove(selectedIndex);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				}
			}
		}
		
	}
	
	private File getFileFromName(String name) {
		//right now when the resore button is pressed we traverse every file in every session to find the right file to operate on.
		//this is probably fixable by using a different data structure (Map?) to maintain sessions
		//-Colin
		File ret = null;
		for (Session s : index.viewSessions()) {
			for (File f: (s).viewFiles()){
				if (f.getName() == name);
					try {
						String ext = ".bgb";
						if((s).getEncrypted())
							ext = ".enc";
						if((s).getCompressed())
							ext = ".zip";
						String temp = s.getBackupDirectory() + File.separator + name;
						if(temp.substring((temp.length()-3), temp.length()).equals(ext))
							ret = new File(temp);
						else
							ret = new File(s.getBackupDirectory() + File.separator + name + ext);

						curIsEncrypted = s.getEncrypted();
						curIsCompressed = s.getCompressed();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
			}
		}
		return ret;
	}
}
