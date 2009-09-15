/*
 * 
 * InvalidStateException.java
 *  
 * Created:     Sep 15th, 2009
 * Project:       Rifidi Edge Server - A middleware platform for RFID applications
 *                   http://www.rifidi.org
 *                   http://rifidi.sourceforge.net
 * Copyright:   Pramari LLC and the Rifidi Project
 * License:      The software in this package is published under the terms of the GPL License
 *                   A copy of the license is included in this distribution under RifidiEdge-License.txt 
 */
package org.rifidi.edge.core.exceptions;

/**
 * Thrown if the current request cannot complete due to the object not being in
 * the correct state to complete the request
 * 
 * @author Kyle Neumeier - kyle@pramari.com
 * @author Jochen Mader - jochen@pramari.com
 * 
 */
public class InvalidStateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public InvalidStateException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public InvalidStateException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public InvalidStateException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public InvalidStateException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

}
