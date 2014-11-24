/**
 * System.java BGB
 */
package edu.vtc.cis4150;

/**
 * System - A backup utility system
 * @author YOURNAMEHERE
 */
public class System {

	/**
	 * create the backup utility system
	 */
	public System() {
		_index = new Index();
	}

	/**
	 * validate rep invariants
	 */
	private void repOK() {
		assert (_index != null);
	}
	
	Index _index; // never null
}