/**
 * Index.java
 * BGB
 */
package edu.vtc.cis4150;

import java.util.List;

/**
 * Index - an index of active and archived sessions
 * @author Travis Friend
 */
public class Index{

	/**
	 * create the index
	 */
	public Index() {
	}
	
	/**
	 * add a session to the active list of sessions in the index
	 * @param session the session to be added
	 */
	public void pushSession(Session session) {
	}

	/**
	 * add a list of sessions to the active list of sessions in the index
	 * @param sessions the list of sessions to be added
	 */
	public void pushSessions(List<Session> sessions) {
	}

	/**
	 * transfer a session from the active list of sessions to the archived list 
	 *  of sessions
	 * @param session the session to be transferred
	 */
	public void setSessionArchived(Session session) {
	}

	/**
	 * transfer a list of sessions from the active list of sessions to the 
	 *  archived list of sessions
	 * @param sessions the sessions to be transferred
	 */
	public void setSessionsArchived(List<Session> sessions) {
	}

	/**
	 * transfer a session from the archived list of sessions to the active 
	 *  list of sessions
	 * @param session the session to be transferred
	 */
	public void setSessionUnarchived(Session session) {
	}

	/**
	 * transfer a list of sessions from the archived list of sessions to the 
	 *  active list of sessions
	 * @param sessions the sessions to be transferred
	 */
	public void setSessionsUnarchived(List<Session> sessions) {
	}


	/**
	 * get a session from the active list of sessions using a session. 
	 *  session will be pulled from the active list of sessions
	 * @param session the session to be pulled
	 * @return the pulled session
	 */
	public Session getSession(Session session) {
		return null;
	}

	/**
	 * get a session from the active list of sessions using a session ID 
	 *  assigned to an automated backup session. The session will be pulled
	 *  from the active list of sessions
	 * @param sessionID the session id corresponding to the session to be pulled
	 * @return the pulled session
	 */
	public Session getSession(int sessionID) {
		return null;
	}
	
	/**
	 * get a list of sessions from the active list of sessions using a list of 
	 *  sessions. sessions will be pulled from the active list of sessions
	 * @param sessions the sessions to be pulled
	 * @return the pulled sessions
	 */
	public List<Session> getSessions(List<Session> sessions) {
		return null;
	}
	
	/**
	 * get a session from the archived list of sessions using a session. 
	 *  session will be pulled from the archived list of sessions
	 * @param session the session to be pulled
	 * @return the pulled session
	 */
	public Session getArchivedSession(Session session) {
		return null;
	}

	/**
	 * get a list of sessions from the archived list of sessions using a list 
	 *  of sessions. sessions will be pulled from the archived list of sessions
	 * @param sessions the sessions to be pulled
	 * @return the pulled sessions
	 */
	public List<Session> getArchivedSessions(List<Session> sessions) {
		return null;
	}

	/**
	 * view the active list of sessions. sessions will not be pulled from the 
	 *  active list
	 * @return the sessions to be viewed
	 */
	public List<Session> viewSessions() {
		return null;
	}
	
	/**
	 * view the archived list of sessions. sessions will not be pulled from 
	 *  the archived list of sessions.
	 * @return the sessions to be viewed
	 */
	public List<Session> viewArchivedSessions() {
		return null;
	}
	
	/**
	 * clear the archived list of sessions. sessions in the archived list will 
	 *  be removed and permanently destroyed
	 */
	public void clearArchive() {
	}

	/**
	 * validate rep invariants
	 */
	private void repOK() {
	}

	private List<Session> sessions; // never null, elements in list never null
	private List<Session> archivedSessions; // never null, elements in list never null
}