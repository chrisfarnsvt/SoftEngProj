/**
 * ScheduledManager.java
 * BGB
 */
package edu.vtc.cis4150;

import java.util.ArrayList;

/**
 * ScheduleManager - a manager for the session schedules
 * @author YOURNAMEHERE
 */
public class ScheduleManager {

	/**
	 * create the schedule manager
	 */
	public ScheduleManager() {
	}

	/**
	 * validate rep invariants
	 */
	private void repOK() {
	}

	private ArrayList<Session> scheduledSessions; // never null, elements in ArrayList never null
	private BackupSystem system; // never null
	private Session linkedSession; // may be null
}