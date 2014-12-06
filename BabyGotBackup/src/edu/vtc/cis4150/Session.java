/**
 * Session.java
 * BGB
 */
package edu.vtc.cis4150;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;

/**
 * Session - a backup session
 * @author YOURNAMEHERE
 */
public interface Session{

	public abstract void addFile(File file) throws Exception;

	public abstract void removeFile(File file) throws Exception;

	public abstract void clearFiles() throws Exception;

	public abstract File pullFile(File file) throws Exception;

	public abstract File copyFile(File file) throws Exception;

	public abstract ArrayList<File> viewFiles();

	public abstract void setBackupLocation(String filepath);

	public abstract void backupFiles() throws Exception;
	
	public abstract boolean getCompressed();
	
	public abstract boolean getEncrypted();
	
	public abstract HashMap<File, File> getBackupToFileMap();
	
	public abstract String getBackupDirectory();
	
	public abstract void addBackupMapEntry(File backup, File source);

	public abstract void setCompressed(boolean selected);

	public abstract void setEncrypted(boolean selected);
}