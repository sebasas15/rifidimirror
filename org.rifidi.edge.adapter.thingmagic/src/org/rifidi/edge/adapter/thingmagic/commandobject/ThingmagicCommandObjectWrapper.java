/*******************************************************************************
 * Copyright (c) 2014 Transcends, LLC.
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU 
 * General Public License as published by the Free Software Foundation; either version 2 of the 
 * License, or (at your option) any later version. This program is distributed in the hope that it will 
 * be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You 
 * should have received a copy of the GNU General Public License along with this program; if not, 
 * write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, 
 * USA. 
 * http://www.gnu.org/licenses/gpl-2.0.html
 *******************************************************************************/
package org.rifidi.edge.adapter.thingmagic.commandobject;


/**
 * 
 * 
 * @author Matthew Dean
 */
public class ThingmagicCommandObjectWrapper {
	/** The name of the property that this command get/sets */
	private String propertyName;
	/** The command to be executed */
	private ThingmagicCommandObject commandObject;

	/**
	 * @param propertyName
	 *            The name of the property that will be get/set
	 * @param commandObject
	 *            The command to be executed
	 */
	public ThingmagicCommandObjectWrapper(String propertyName,
			ThingmagicCommandObject commandObject) {
		this.propertyName = propertyName;
		this.commandObject = commandObject;
	}

	/**
	 * @return the propertyName
	 */
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * @return the commandObject
	 */
	public ThingmagicCommandObject getCommandObject() {
		return commandObject;
	}
}
