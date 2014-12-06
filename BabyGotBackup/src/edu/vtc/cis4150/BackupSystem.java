/**
 * System.java BGB
 */
package edu.vtc.cis4150;

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
	}

	/**
	 * validate rep invariants
	 */
	private void repOK() {
		assert (_index != null);
	}
	
	private Index _index; // never null
	private String defaultBackupLocation;

	public void addSessionToIndex(ManualSession newSession) {
		_index.pushSession(newSession);
		
	}
	public void addSessionToIndex(NetworkedSession newSession) {
		_index.pushSession(newSession);
		
	}
	public Index getIndex() {
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
