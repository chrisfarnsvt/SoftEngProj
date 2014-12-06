/**
 * NetworkedSession.java
 * BGB
 */
package edu.vtc.cis4150;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import jcifs.smb.SmbFile;


/**
 * Session - a backup session
 * @author YOURNAMEHERE
 */
public class NetworkedSession implements Session{

	/**
	 * create a backup session
	 */
	public NetworkedSession(boolean encrypt, boolean compress) {
		_isEncrypted = encrypt;
		_isCompressed = compress;
		Date curr = new Date();
		_creationDate = curr;
		_lastModifiedDate = curr;
		_files = new ArrayList<File>();
		_backupToFile = new HashMap<SmbFile, File>();
	}
	
	/**
	 * add a file to be backed up to the backup file ArrayList. if the session
	 *  has already been backed up this will add the file to the backup
	 *  location
	 * @param file the file to be added
	 * @throws Exception 
	 */
	public void addFile(File file) throws Exception {
		if (_isBackedUp) {
			//add in checks for compress, encrypt
			_smbHandler.createFile(file);
		}
		if (!_files.contains(file))
			_files.add(file);
		_lastModifiedDate = new Date();
		repOK();
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
			_smbHandler.deleteFile(file);
		}
		_lastModifiedDate = new Date();
		repOK();
	}

	/**
	 * clear the backup file ArrayList. this will remove any files previously added.
	 *  if the session has been backed up this will delete the actual files in
	 *  the backup location
	 * @throws Exception 
	 */
	public void clearFiles() throws Exception {
		if(_isBackedUp){
			for(File file : _files){
				_smbHandler.deleteFile(file);
			}
		}
		_files.clear();
		_lastModifiedDate = new Date();
		repOK();
	}

	/**
	 * pull a file from the file ArrayList. if the session has been backed
	 *  up this will also pull the actual file from the backup location
	 * @param file
	 * @return the file that was pulled. returns null if file doesn't exist
	 * @throws IOException 
	 */
	public File pullFile(File file) throws Exception {
		if (_isBackedUp && _files.contains(file))
			_smbHandler.deleteFile(file);
		if (_files.contains(file)) {
			_files.remove(file);
			_lastModifiedDate = new Date();
			repOK();
			return file;
		}
		return null;
	}

	/**
	 * copy a file from the file ArrayList to backup directory.
	 * This is basically the meat of "backup(file)"
	 * @param file the file to be copied
	 * @return the copied file
	 * @throws IOException 
	 */
	public File copyFile(File file) throws Exception {
		File temp = null;
		if (_files.contains(file)) {
			temp = new File(file.getPath());
			if (_isEncrypted)
				encrypt(temp);
			if (_isCompressed)
				compress(temp);
			_smbHandler.createFile(file); 
			SmbFile result = new SmbFile(_backupLocation + temp.getName());
			_lastModifiedDate = new Date();
			_backupToFile.put(result, file);
			repOK();
			return temp;
		}
		return null;
	}

	/**
	 * Compresses a file to the backup directory. Compression methods adapted for our use from
	 * http://www.mkyong.com/java
	 * @param file the file to compress
	 */
	public File compress(File file) {
		byte[] buffer = new byte[1024];
		try {
		   FileOutputStream fos = new FileOutputStream(_backupLocation + file.getPath() + ".zip");
		    		ZipOutputStream zos = new ZipOutputStream(fos);
		    		ZipEntry ze= new ZipEntry(file.getName());
		    		zos.putNextEntry(ze);
		    		FileInputStream in = new FileInputStream(file.getPath());
		 
		    		int len;
		    		while ((len = in.read(buffer)) > 0) {
		    			zos.write(buffer, 0, len);
		    		}
		    		in.close();
		    		zos.closeEntry();
		    		zos.close();
		    		return file;
		    	}
		    	catch(IOException ex){
		    	   ex.printStackTrace();
		    	   return null;
		    	}
	}
	
	/**
	 * decompress a file
	 * @param file the file to restore to
	 */
	public File decompress(File file) {
		     byte[] buffer = new byte[1024];
		 
		     try{
		    	ZipInputStream zis = new ZipInputStream(new FileInputStream(_backupLocation + file.getName() + ".zip"));
		    	ZipEntry ze = zis.getNextEntry();
		    	File temp = file;
		 
		    	while(ze!=null){
		            FileOutputStream fos = new FileOutputStream(temp);             
		 
		            int len;
		            while ((len = zis.read(buffer)) > 0) {
		       		fos.write(buffer, 0, len);
		            }
		 
		            fos.close();   
		            ze = zis.getNextEntry();
		    	}
		 
		        zis.closeEntry();
		    	zis.close();
		    	return file;
		 
		    }
		    catch(IOException ex) {
		       ex.printStackTrace();
		       return null;
		    }
	}    
	
	/**
	 * Encrypts a file
	 * @param file the file to encrypt
	 * @return the encrypted file
	 */
	public File encrypt(File file) {
		return null;
	}
	
	/**
	 * view the files added to the file ArrayList
	 * @return the file ArrayList to be viewed
	 */
	public ArrayList<File> viewFiles() {
		return _files;
	}

	/**
	 * set the backup location of the backup session
	 * @param filepath the backup location of the backup session
	 */
	public void setBackupLocation(String filepath) {
		_backupLocation = filepath;
		_lastModifiedDate = new Date();
		repOK();
	}
	
	/**
	 * backup the files to the designated backup location. this will be called
	 *  when the session is being added to the index. if files have been
	 *  backed up compression, encryption will not change
	 * @throws Exception 
	 */
	public void backupFiles() throws Exception {
		for (File file : _files) {
			copyFile(file);
		}
		_isBackedUp = true;
		repOK();
	}
	
	public void setAuth(String address, String user, String password) throws Exception{
		this._smbHandler = new SmbHandler(address, user, password);
	}
		
	public void restoreFile(File file) throws Exception {
		SmbFile sFile = new SmbFile(_backupLocation+file.getName());
		if (_backupToFile.containsKey(sFile)) {
			File location = _backupToFile.get(sFile);
		if (_files.contains(file)) {
			File temp = new File(file.getPath());
			if (_isCompressed)
				temp = decompress(temp);
			if (_isEncrypted)
				temp = decrypt(temp);
			_smbHandler.getFile(location.toPath(), file);
			_lastModifiedDate = new Date();
			repOK();
		}
		}
	}

	/**
	 * restore the files to the orginal file location. this will be called
	 *  when the session is being added to the index. if files have been
	 *  backed up compression, encryption will not change
	 * @throws Exception 
	 */
	public void restoreFiles(ArrayList<File> files) throws Exception {
		for (File file : files) {
			if (_backupToFile.containsKey(file))
				restoreFile(file);
		}
		repOK();
	}

	/**
	 * validate rep invariants
	 */
	private void repOK() {
		assert (_files != null);
		for (File file : _files)
			assert (file != null);
		assert (_creationDate != null);
		assert (_lastModifiedDate != null);
	}
	

	private ArrayList<File> _files; // never null, elements in ArrayList never null
	private boolean _isEncrypted; //booleans can't be null in java
	private boolean _isCompressed;
	private Date _creationDate; // never null
	private Date _lastModifiedDate; // never null
	private String _backupLocation; // may be null
	private boolean _isBackedUp;
	private SmbHandler _smbHandler;
	private HashMap<SmbFile, File> _backupToFile;
	
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
	public String getBackupDirectory() {
		// TODO Auto-generated method stub
		return null;
	}
	public File decrypt(File file) throws Exception {
		return file;
	}

	@Override
	public void addBackupMapEntry(File backup, File source) {
		// TODO Auto-generated method stub
	}

	@Override
	public HashMap<File, File> getBackupToFileMap() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setCompressed(boolean selected) {
		// TODO Auto-generated method stub
		
	}

	public void setEncrypted(boolean selected) {
		// TODO Auto-generated method stub
		
	}

}
