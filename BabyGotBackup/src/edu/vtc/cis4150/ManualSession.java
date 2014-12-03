/**
 * Session.java
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
import java.security.*;  
import java.security.spec.KeySpec;

import javax.crypto.*;  
import javax.crypto.spec.*;  

import java.io.*; 


/**
 * Session - a backup session
 * @author YOURNAMEHERE
 */
public class ManualSession extends Session{

	/**
	 * create a backup session
	 */
	public ManualSession(boolean encrypt, boolean compress) {
		_isEncrypted = encrypt;
		_isCompressed = compress;
		Date curr = new Date();
		_creationDate = curr;
		_lastModifiedDate = curr;
		_files = new ArrayList<File>();
		_backupToFile = new HashMap<File, File>();
		_pass = "bootylicious";
		repOK();
	}
	
	/**
	 * set compression state
	 */
	public void setCompressed(boolean comp) {
		_isCompressed = comp;
	}

	/**
	 * get compressed state
	 */
	public boolean getCompressed(){
		return _isCompressed;
	}
	
	/**
	 * set encryption state
	 */
	public void setEncrypted(boolean encrypt) {
		_isEncrypted = encrypt;
	}
	
	/**
	 * get backup to file map
	 * @return above map
	 */
	public HashMap<File, File> getBackupToFileMap() {
		return _backupToFile;
	}

	/**
	 * get encryption state
	 */
	public boolean getEncrypted() {
		return _isEncrypted;
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
			Files.copy(file.toPath(), (new File(_backupLocation + "file")).toPath(), StandardCopyOption.COPY_ATTRIBUTES);
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
			file = new File(_backupLocation + file.getPath() + file.getName());
			file.delete();
		}
		_lastModifiedDate = new Date();
		repOK();
	}

	/**
	 * clear the backup file ArrayList. this will remove any files previously added.
	 *  if the session has been backed up this will delete the actual files in
	 *  the backup location
	 * @throws IOException 
	 */
	public void clearFiles() throws IOException {
		if(_isBackedUp){
			for(File file : _files) {
				Files.delete((new File(_backupLocation + file)).toPath());
			}
		}
		_files.clear();
		_lastModifiedDate = new Date();
		repOK();
	}

	/**
	 * pull a file from the file ArrayList. if the session has been backed
	 *  up this will also pull the actual file from the backup location
	 *  
	 *  NOTE: This isn't actually used. get rid of it later
	 *  
	 * @param file
	 * @return the file that was pulled. returns null if file doesn't exist
	 * @throws IOException 
	 */
	public File pullFile(File file) throws IOException {
		if (_isBackedUp && _files.contains(file))
			Files.delete((new File(_backupLocation + file)).toPath());
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
	 * @throws Exception 
	 */
	public File copyFile(File file) throws Exception {
		File temp = null;
		if (_files.contains(file)) {
			temp = new File(file.getPath());
			if (_isCompressed)
				temp = compress(temp);
			if (_isEncrypted)
				temp = encrypt(temp);
			if (temp.equals(file)) {
				Files.copy(temp.toPath(),(new File(temp.getPath() + "tmp")).toPath());
				temp = new File(temp.getPath() + "tmp");
			}
			Files.move(temp.toPath(), (new File(_backupLocation + "/" + temp.getName())).toPath(), StandardCopyOption.REPLACE_EXISTING); 
			File result = new File(_backupLocation + "/" + temp.getName());
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
		   FileOutputStream fos = new FileOutputStream(file.getPath() + ".zip");
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
		    		return new File(file.getPath() + ".zip");
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
		    	ZipInputStream zis = new ZipInputStream(new FileInputStream(file));
		    	ZipEntry ze = zis.getNextEntry();
		
		    	while(ze!=null){
		            FileOutputStream fos = new FileOutputStream(_backupLocation + "/temp");             
		 
		            int len;
		            while ((len = zis.read(buffer)) > 0) {
		       		fos.write(buffer, 0, len);
		            }
		 
		            fos.close();   
		            ze = zis.getNextEntry();
		    	}
		 
		        zis.closeEntry();
		    	zis.close();
		    	File result = new File(_backupLocation + "/temp");
		    	return result;
		 
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
	public File encrypt(File file) throws Exception {
		
		try{
			FileInputStream inFile = new FileInputStream(file);
			FileOutputStream outFile = new FileOutputStream(file.getName() + ".enc");
			
			byte[] salt = new byte [8];
			SecureRandom secureRandom = new SecureRandom();
			secureRandom.nextBytes(salt);
			FileOutputStream saltOutFile = new FileOutputStream(file.getName() + "salt.enc");
			saltOutFile.write(salt);
			saltOutFile.close();
			
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			KeySpec keySpec = new PBEKeySpec(_pass.toCharArray(), salt, 65536, 256);
			SecretKey secretKey = factory.generateSecret(keySpec);
			SecretKey secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
			
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
			cipher.init(Cipher.ENCRYPT_MODE, secret);
			
			byte[] input = new byte[64];
			int bytesRead;
			
			while ((bytesRead = inFile.read(input)) != -1)
			{
				byte[] output = cipher.update(input, 0, bytesRead);
				if (output != null)
					outFile.write(output);
			}
			
			byte[] output = cipher.doFinal();
			if (output != null)
				outFile.write(output);
			
			inFile.close();
			outFile.flush();
			outFile.close();
			
			return new File(file.getName() + ".enc");
		}
		
		catch(IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public File decrypt(File file) throws Exception {
		
		try{
			FileInputStream saltFis = new FileInputStream(file.getName() + "salt.enc");
			byte[] salt = new byte[8];
			saltFis.read(salt);
			saltFis.close();
	
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			KeySpec keySpec = new PBEKeySpec(_pass.toCharArray(), salt, 65536,256);
			SecretKey tmp = factory.generateSecret(keySpec);
			SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
	
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secret);
			FileInputStream fis = new FileInputStream(file.getName() + ".enc");
			FileOutputStream fos = new FileOutputStream(file.getName() + ".dec");
			
			byte[] in = new byte[64];
			int read;
			while ((read = fis.read(in)) != -1) {
				byte[] output = cipher.update(in, 0, read);
				if (output != null)
					fos.write(output);
			}
	
			byte[] output = cipher.doFinal();
			if (output != null)
				fos.write(output);
			fis.close();
			fos.flush();
			fos.close();
			
			return new File(file.getName() + ".dec");
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
			return null;
		}
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
	
	public String getBackupDirectory() {
		return _backupLocation;
	}

	public void restoreFile(File file) throws Exception {
		if (_backupToFile.containsKey(file)) {
		File location = _backupToFile.get(file);
		if (_files.contains(location)) {
			File temp = new File(file.getPath());
			if (_isCompressed)
				temp = decompress(temp);
			if (_isEncrypted)
				temp = decrypt(temp);
			Files.move(temp.toPath(), location.toPath(), StandardCopyOption.REPLACE_EXISTING);
			_lastModifiedDate = new Date();
			repOK();
		}
		}
	}

	/**
	 * delete a backup and remove any reference to it from session
	 * @throws Exception 
	 */
	public void deleteBackup(File delete) throws Exception {
		Files.delete(delete.toPath());
		_backupToFile.remove(delete);
		
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
	
	private static String _pass;
	private HashMap<File, File> _backupToFile;
	private ArrayList<File> _files; // never null, elements in ArrayList never null
	private boolean _isEncrypted; //booleans can't be null in java
	private boolean _isCompressed;
	private Date _creationDate; // never null
	private Date _lastModifiedDate; // never null
	private String _backupLocation; // may be null
	private boolean _isBackedUp; 
}
