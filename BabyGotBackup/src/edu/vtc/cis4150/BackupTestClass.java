package edu.vtc.cis4150;

import java.io.File;
import java.io.IOException;

public class BackupTestClass {
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {		
		File file1 = new File("C:/Users/Allan/Desktop/test1.txt");
		File file2 = new File("C:/Users/Allan/Desktop/test2.txt");
		
		ManualSession session = new ManualSession(false, false);
		
		//session.setBackupLocation("C:/Users/Allan/Desktop/backuplocation/");
		//session.setBackupLocation("//TRAVIS-PC/Users/Public/test/");
		
		session.addFile(file1);
		session.addFile(file2);
		
		session.backupFiles();
	}

}
