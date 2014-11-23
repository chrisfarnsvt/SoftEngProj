/**
 * Session.java
 * BGB
 */
package edu.vtc.cis4150;

import java.util.ArrayList;
import java.util.Date;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Session - a backup session
 * @author YOURNAMEHERE
 */
public class Session{

	/**
	 * create a backup session
	 */
	public Session(boolean encrypt, boolean compress) {
		_isEncrypted = encrypt;
		_isCompressed = compress;
		_files = new ArrayList<File>();
	}
	
	/**
	 * create a backup session including ID
	 */
	public Session(boolean encrypt, boolean compress, int id) {
		_isEncrypted = encrypt;
		_isCompressed = compress;
		_sessID = id;
		_files = new ArrayList<File>();
	}
	/**
	 * add a file to be backed up to the backup file ArrayList. if the session
	 *  has already been backed up this will add the file to the backup
	 *  location
	 * @param file the file to be added
	 * @throws IOException 
	 */
	public void addFile(File file) throws IOException {
		if (_isBackedUp) {
			//add in checks for compress, encrypt
			Files.copy(file.toPath(), (new File(_backupLocation)).toPath(), StandardCopyOption.COPY_ATTRIBUTES);
		}
		if (!_files.contains(file))
			_files.add(file);
	}

	/**
	 * remove a file from the backup file ArrayList, if it exists. if the session
	 *  has been backed up this will delete the file from the backup location
	 * @param file the file to be removed
	 * @throws Exception 
	 */
	public void removeFile(File file) throws Exception {
		_files.remove(file);
		if(_isBackedUp){
			//file = new File(backupLocation + file.getPath() + file.getName());
			//file.delete();
		}
		
	}

	/**
	 * clear the backup file ArrayList. this will remove any files previously added.
	 *  if the session has been backed up this will delete the actual files in
	 *  the backup location
	 */
	public void clearFiles() {
		_files.clear();
		if(_isBackedUp){
			//do the thing
		}
	}

	/**
	 * pull a file from the file ArrayList. if the session has been backed
	 *  up this will also pull the actual file from the backup location
	 * @param file
	 * @return the file that was pulled
	 */
	public File pullFile(File file) {
		if (_files.contains(file)){
			return _files.get(_files.indexOf(file));
		}
		return null;
	}

	/**
	 * copy a file from the file ArrayList to backup directory.
	 * This is basically the meat of "backup(file)"
	 * @param file the file to be copied
	 * @return the copied file
	 */
	public File copyFile(File file) {
		return null;
	}

	/**
	 * view the files added to the file ArrayList
	 * @return the file ArrayList to be viewed
	 */
	public ArrayList<File> viewFiles() {
		return null;
	}

	/**
	 * set the backup location of the backup session
	 * @param filepath the backup location of the backup session
	 */
	public void setBackupLocation(String filepath) {
		_backupLocation = filepath;
	}
	
	/**
	 * 
	 */
	public void setSessionID(int id) {
		_sessID = id;
	}
	
	/**
	 * backup the files to the designated backup location. this will be called
	 *  when the session is being added to the index. if files have been
	 *  backed up compression, encryption will not change
	 */
	public void backupFiles() {
		
		_isBackedUp = true;
	}

	/**
	 * validate rep invariants
	 */
	private void repOK() {
	}
	private int _sessID;
	private ArrayList<File> _files; // never null, elements in ArrayList never null
	private boolean _isEncrypted; // never null
	private boolean _isCompressed; // never null
	private Date _creationDate; // never null
	private Date _lastModifiedDate; // never null
	private String _backupLocation; // may be null
	private boolean _isBackedUp; // never null
}
