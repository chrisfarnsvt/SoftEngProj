/**
 * SessionParser.java
 * BGB
 */
package edu.vtc.cis4150;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
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
		
		//default backup location is always the first line
		if(input.hasNextLine())
			sys.setDefaultBackupLocation(input.nextLine());
		
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
			if (type.equals("1"))
			{
			//	System.out.println("Reading a ScheduledSession");
				int interval = Integer.valueOf(input.nextLine());
				session = new ScheduledSession(encrypted, compressed, interval, false);
				
				boolean cont = Boolean.valueOf(input.nextLine());
				((ScheduledSession) session).setContinueVal(cont);
								
				int version = Integer.valueOf(input.nextLine());
				((ScheduledSession) session).setVersion(version);
				String backupDate = input.nextLine();
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
				cal.setTime(sdf.parse(backupDate));
				((ScheduledSession) session).setScheduledBackupTime(cal);
								
				String backupDir = input.nextLine();
				//	System.out.println(backupDir + " = backupDirectory");
				session.setBackupLocation(backupDir);
								
				String check = input.nextLine();
				while (!check.equals("")) {
					String backup = check;
					//	System.out.println(backup + " = backupFile");
					String source = input.nextLine();
					//	System.out.println(source + " = sourceFile");
					session.addBackupMapEntry(new File(backup), new File(source));
					session.addFile(new File(source));
					if (input.hasNextLine())
						check = input.nextLine();
					else 
						check = "";
				}
			sys.getIndex().pushSession(session);
		}

			//if (type == "2")
				//session = new NetworkedSession();
		}
		input.close();
	}
	
	/**
	 * write a session to a file
	 * @param session the session to be written to a file
	 * @throws Exception 
	 */
	public void writeToFile(Session session) throws Exception {
		PrintWriter bufw = new PrintWriter(new BufferedWriter(new FileWriter(_iniLocation, true)));
		bufw.write("" + session.getEncrypted() + eol   );
		bufw.write("" + session.getCompressed() + eol   );
		if(session instanceof ManualSession)
			bufw.write("0");
		if(session instanceof ScheduledSession)
		{
			bufw.write("1" + eol);
			bufw.write("" + ((ScheduledSession) session).getInterval() + eol);
			bufw.write("" + ((ScheduledSession) session).getContinueVal() + eol);
			bufw.write("" + ((ScheduledSession) session).getVersion() + eol);
			bufw.write("" + ((ScheduledSession) session).getScheduledBackupTime().getTime());
		}

		if(session instanceof NetworkedSession)
			bufw.write("2");
		bufw.write(eol);
		bufw.write(session.getBackupDirectory().trim() + eol);
		for (Map.Entry<File, File> fileEnt: session.getBackupToFileMap().entrySet()) { //foreach for a map. messy, but works
			bufw.write(fileEnt.getKey().getPath() + eol);
			bufw.write(fileEnt.getValue().getPath() + eol);
		}
		bufw.write(eol);
		bufw.close();
	}
	
	private String _iniLocation;
	private final String eol = System.getProperty("line.separator");

	public void writeDefaultBackupLocation(String dbl) throws Exception{
		PrintWriter bufw = new PrintWriter(new BufferedWriter(new FileWriter(_iniLocation, true)));
		bufw.write(dbl + eol);
		bufw.close();
	}
}