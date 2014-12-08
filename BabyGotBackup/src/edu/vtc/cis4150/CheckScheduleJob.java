package edu.vtc.cis4150;

import java.util.ArrayList;
import java.util.Calendar;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

//import com.sun.istack.internal.logging.Logger;

/**
 * The job that scheduler calls
 * 
 * @author YOURNAMEHERE
 */

public class CheckScheduleJob implements Job {

	/**
	 * Execute the schedule job
	 * @param context
	 * @throws JobExecutionException
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Calendar c = Calendar.getInstance();
		Index index = BackupSystem.getIndex();
		ArrayList<Session> newSessions = new ArrayList<Session>();
		System.out.println("Going through scheduled backups, this may take a moment...");
		for (Session s: index.viewSessions())
		{
			if (s instanceof ScheduledSession)
			{
				if (((ScheduledSession)s).getScheduledBackupTime().after(c) && ((ScheduledSession) s).getContinueVal())
				{
					try {
						newSessions.add(((ScheduledSession) s).executeScheduledBackup());
						System.out.println("Backing up session " + ((ScheduledSession)s).getSessionID());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
			}
		}
		for (Session s : newSessions)
		{
			System.out.println(((ScheduledSession)s).getSessionID());
			index.pushSession(s);
		}
		System.out.println("All backups accounted for.");
	}

}
