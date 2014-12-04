/**
 * SessionParser.java
 * BGB
 */
package edu.vtc.cis4150;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.util.Map;
import java.util.Scanner;

/**
 * SessionParser - the parser for a stored session
 * @author YOURNAMEHERE
 */
public class SessionParser {

	/**
	 * create the session parser
	 */
	public SessionParser(String ini) {
		_iniLocation = ini;
	}

	/**
	 * parse a session from a file
	 * @param file the file containing the session to be parsed
	 * @throws Exception 
	 */
	public void parseFrom(BackupSystem sys) throws Exception {
		Scanner input = new Scanner(new File(_iniLocation));
		while (input.hasNextLine()) {
			Session session;
			boolean encrypted = (input.nextLine() == "true");
			boolean compressed = (input.nextLine() == "true");
			String type = input.nextLine(); //0 - manual, 1 - scheduled, 2 - networked
			if (type.equals("0")) {
				session = new ManualSession(encrypted, compressed);
				String backupDir = input.nextLine();
				session.setBackupLocation(backupDir);
				String check = input.nextLine();
				while (!check.equals("")) {
					String backup = check;
					String source = input.nextLine();
					session.addBackupMapEntry(new File(backup), new File(source));
					session.addFile(new File(source));
					if (input.hasNextLine())
						check = input.nextLine();
					else 
						check = "";
				}
				sys.getIndex().pushSession(session);
			}
			//if (type == "1")
				//session = new ScheduledSession();
			//if (type == "2")
				//session = new NetworkedSession();
		}
		input.close();
		Files.delete(new File(_iniLocation).toPath());
	}
	
	/**
	 * write a session to a file
	 * @param session the session to be written to a file
	 * @throws Exception 
	 */
	public void writeToFile(Session session) throws Exception {
		FileOutputStream fos = new FileOutputStream(_iniLocation);
		BufferedWriter bufw = new BufferedWriter(new OutputStreamWriter(fos));
		bufw.write("" + session.getEncrypted());
		bufw.newLine();
		bufw.write("" + session.getCompressed());
		bufw.newLine();
		if(session instanceof ManualSession)
			bufw.write("0");
		if(session instanceof ScheduledSession)
			bufw.write("1");
		if(session instanceof NetworkedSession)
			bufw.write("2");
		bufw.newLine();
		bufw.write(session.getBackupDirectory());
		bufw.newLine();
		for (Map.Entry<File, File> fileEnt: session.getBackupToFileMap().entrySet()) { //foreach for a map. messy, but works
			bufw.write(fileEnt.getKey().getPath());
			bufw.newLine();
			bufw.write(fileEnt.getValue().getPath());
			bufw.newLine();
		}
		bufw.newLine();
		bufw.close();
	}
	
	/**
	 * validate rep invariants
	 */
	private void repOK() {
	}
	
	private String _iniLocation;
}