/**
 * System.java BGB
 */
package edu.vtc.cis4150;

import org.apache.log4j.BasicConfigurator;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * System - A backup utility system
 * @author YOURNAMEHERE
 */
public class BackupSystem {

	/**
	 * create the backup utility system
	 */
	public BackupSystem() {
		_index = new Index();
		BasicConfigurator.configure();
		try {
			startScheduler();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		repOK();
	}

	/**
	 * validate rep invariants
	 */
	private void repOK() {
		assert (_index != null);
	}
	
	private static Index _index; // never null
	private String defaultBackupLocation;

	private void startScheduler() throws SchedulerException {
		
		JobDetail job = JobBuilder.newJob(CheckScheduleJob.class)
				.withIdentity("CheckSchedule")
				.build();
		
		Trigger trigger = TriggerBuilder.newTrigger()
				.withSchedule(
						SimpleScheduleBuilder.simpleSchedule()
						.withIntervalInSeconds(10)		//For testing - change to '.withIntervalInMinutes(30)' for final.
						.repeatForever()).build();
		
		SchedulerFactory schFactory = new StdSchedulerFactory();
		Scheduler sch = schFactory.getScheduler();
	
		sch.start();
		sch.scheduleJob(job, trigger);
	}
	public void addSessionToIndex(Session newSession) {
		_index.pushSession(newSession);
		
	}
	public void addSessionToIndex(NetworkedSession newSession) {
		_index.pushSession(newSession);
		
	}
	public static Index getIndex() {
		// TODO Auto-generated method stub
		return _index;
	}

	public void setDefaultBackupLocation(String text) {
		defaultBackupLocation = text;
		
	}
	
	public String getDefaultBackupLocation() {
		return defaultBackupLocation;
	}
}
