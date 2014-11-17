/**
 * ScheduledManager.java
 * BGB
 */
package edu.vtc.cis4150;

import java.util.List;

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

	private List<Session> scheduledSessions; // never null, elements in list never null
	private System system; // never null
	private Session linkedSession; // may be null
}