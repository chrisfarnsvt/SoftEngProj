/**
 * NetworkedSession.java
 * BGB
 */
package edu.vtc.cis4150;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * NetworkedSession - a networked backup session
 * @author YOURNAMEHERE
 */
public class NetworkedSession extends Session {

	/**
	 * create a networked session
	 */
	public NetworkedSession() {
	}
	
	/**
	 * add a file to be backed up to the backup file list. if the session
	 *  has already been backed up this will add the file to the backup
	 *  location
	 * @param file the file to be added
	 */
	public void addFile(File file) {
	}

	/**
	 * remove a file from the backup file list, if it exists. if the session
	 *  has been backed up this will delete the file from the backup location
	 * @param file the file to be removed
	 */
	public void removeFile(File file) {
	}

	/**
	 * clear the backup file list. this will remove any files previously added.
	 *  if the session has been backed up this will delete the actual files in
	 *  the backup location
	 */
	public void clearFiles() {
	}

	/**
	 * pull a file from the file list. if the session has been backed
	 *  up this will also pull the actual file from the backup location
	 * @param file
	 * @return the file that was pulled
	 */
	public File pullFile(File file) {
		return null;
	}

	/**
	 * copy a file from the file list
	 * @param file the file to be copied
	 * @return the copied file
	 */
	public File copyFile(File file) {
		return null;
	}

	/**
	 * view the files added to the file list
	 * @return the file list to be viewed
	 */
	public List<File> viewFiles() {
		return null;
	}

	/**
	 * set the backup location of the backup session
	 * @param filepath the backup location of the backup session
	 */
	public void setBackupLocation(String filepath) {
	}
	
	/**
	 * backup the files to the designated backup location. this will be called
	 *  when the session is being added to the index. if files have been
	 *  backed up compression, encryption will not change
	 */
	public void backupFiles() {
	}
	
	/**
	 * validate rep invariants
	 */
	private void repOK() {
	}

	private String password; // may be null, never empty
	private String username; // never null, never empty
	private List<File> files; // never null, elements in list never null
	private boolean isEncrypted; // never null
	private boolean isCompressed; // never null
	private Date creationDate; // never null
	private Date lastModifiedDate; // never null
	private String backupLocation; // may be null
	private boolean isBackedUp; // never null
}