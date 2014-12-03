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
public abstract class Session{

	public abstract void addFile(File file) throws Exception;

	public abstract void removeFile(File file) throws Exception;

	public abstract void clearFiles() throws Exception;

	public abstract File pullFile(File file) throws Exception;

	public abstract File copyFile(File file) throws Exception;

	public abstract ArrayList<File> viewFiles();

	public abstract void setBackupLocation(String filepath);

	public abstract void backupFiles() throws Exception;
	
}