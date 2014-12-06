/**
 * ScheduledSession.java
 * BGB
 */
package edu.vtc.cis4150;

import java.io.File;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * ScheduledSession - a scheduled backup session
 * @author YOURNAMEHERE
 */
public class ScheduledSession implements Session{

	/**
	 * create a scheduled session
	 */
	public ScheduledSession(Session s, Date d) {
		_session = s;
		_scheduledBackupTime = d;
		_sessionID = ++_sessionIDCount;
		_creationDate = new Date();
		_lastModifiedDate = _creationDate;
		// TODO not finished. Implemented counter so there was no confusion
	}
	
	/**
	 * add a file to be backed up to the backup file ArrayList. if the session
	 *  has already been backed up this will add the file to the backup
	 *  location
	 * @param file the file to be added
	 * @throws Exception 
	 */
	public void addFile(File file) throws Exception {
		_session.addFile(file);
	}

	/**
	 * remove a file from the backup file ArrayList, if it exists. if the session
	 *  has been backed up this will delete the file from the backup location
	 * @param file the file to be removed
	 * @throws Exception 
	 */
	public void removeFile(File file) throws Exception {
		_session.removeFile(file);
	}

	/**
	 * get the session id of the scheduled session
	 * @return the session id
	 */
	public int getSessionID() {
		return _sessionID;
	}
	
	/**
	 * clear the backup file ArrayList. this will remove any files previously added.
	 *  if the session has been backed up this will delete the actual files in
	 *  the backup location
	 * @throws Exception 
	 */
	public void clearFiles() throws Exception {
		_session.clearFiles();
	}

	/**
	 * pull a file from the file ArrayList. if the session has been backed
	 *  up this will also pull the actual file from the backup location
	 * @param file
	 * @return the file that was pulled
	 * @throws Exception 
	 */
	public File pullFile(File file) throws Exception {
		return _session.pullFile(file);
	}

	/**
	 * copy a file from the file ArrayList
	 * @param file the file to be copied
	 * @return the copied file
	 * @throws Exception 
	 */
	public File copyFile(File file) throws Exception {
		return _session.copyFile(file);
	}

	/**
	 * view the files added to the file ArrayList
	 * @return the file ArrayList to be viewed
	 */
	public ArrayList<File> viewFiles() {
		return _session.viewFiles();
	}

	/**
	 * set the backup location of the backup session
	 * @param filepath the backup location of the backup session
	 */
	public void setBackupLocation(String filepath) {
		_session.setBackupLocation(filepath);
	}
	
	/**
	 * backup the files to the designated backup location. this will be called
	 *  when the session is being added to the index. if files have been
	 *  backed up compression, encryption will not change
	 * @throws Exception 
	 */
	public void backupFiles() throws Exception {
		_session.backupFiles();
	}

	/**
	 * set the scheduled backup time of the session
	 * @param date the date in which the next backup will occur
	 */
	public void setScheduledBackupTime(Date date) {
		_scheduledBackupTime = date;
	}
	
	/**
	 * get the last backup session of this schedule
	 * @return the last backup session
	 */
	public Session getLinkedSession() {
		return _session;
	}

	/**
	 * set the last backup session of this schedule
	 * @param session the last backup session
	 */
	public void setLinkedSession(Session session) {
		_session = session;
	}
	
	/**
	 * validate rep invariants
	 */
	private void repOK() {
		assert (_sessionIDCount >= 0);
		assert (_sessionID >= 0);
		assert (_scheduledBackupTime != null);
	}

	private Session _session;
	private static int _sessionIDCount = 0; // >= 0
	private int _sessionID; // >= 0
	private Date _scheduledBackupTime; // never null, may be <, >, = current time
	private Date _creationDate; // never null
	private Date _lastModifiedDate; // never null
	private String _backupLocation; // may be null
	private boolean _isBackedUp; // never null
	@Override
	public boolean getCompressed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getEncrypted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public HashMap<File, File> getBackupToFileMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getBackupDirectory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addBackupMapEntry(File backup, File source) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCompressed(boolean selected) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEncrypted(boolean selected) {
		// TODO Auto-generated method stub
		
	}
}