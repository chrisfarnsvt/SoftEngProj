/**
 * Session.java
 * BGB
 */
package sep.bgbmobile;

import java.util.ArrayList;
import java.util.Date;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * ScheduledSession - a backup session
 * @author YOURNAMEHERE
 */
public class Session{

	/**
	 * create a backup session
	 */
	public Session() {
	}

	/**
	 * add a file to be backed up to the backup file ArrayList. if the session
	 *  has already been backed up this will add the file to the backup
	 *  location
	 * @param file the file to be added
	 * @throws IOException 
	 */
	public void addFile(File file) throws Exception {
		files.add(file);
		if(isBackedUp){
		/**			FileOutputStream fos = new FileOutputStream(backupLocation + file.getPath() + file.getName());
					FileInputStream fis = new FileInputStream(file);
					byte[] b = new byte[32768];
	    			int n;
	    			while(( n = fis.read( b )) > 0 ) {
	    				fos.write( b, 0, n );	
					}					
	    			fos.close();
	    			fis.close();
		*/}
	}

	/**
	 * remove a file from the backup file ArrayList, if it exists. if the session
	 *  has been backed up this will delete the file from the backup location
	 * @param file the file to be removed
	 * @throws Exception 
	 */
	public void removeFile(File file) throws Exception {
		files.remove(file);
		if(isBackedUp){
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
	}

	/**
	 * pull a file from the file ArrayList. if the session has been backed
	 *  up this will also pull the actual file from the backup location
	 * @param file
	 * @return the file that was pulled
	 */
	public File pullFile(File file) {
		return null;
	}

	/**
	 * copy a file from the file ArrayList
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
		backupLocation = filepath;
	}
	
	/**
	 * backup the files to the designated backup location. this will be called
	 *  when the session is being added to the index. if files have been
	 *  backed up compression, encryption will not change
	 */
	public void backupFiles() {
		
		isBackedUp = true;
	}

	/**
	 * validate rep invariants
	 */
	private void repOK() {
	}
	private int sessID;
	private ArrayList<File> files; // never null, elements in ArrayList never null
	private boolean isEncrypted; // never null
	private boolean isCompressed; // never null
	private Date creationDate; // never null
	private Date lastModifiedDate; // never null
	private String backupLocation; // may be null
	private boolean isBackedUp; // never null
}
