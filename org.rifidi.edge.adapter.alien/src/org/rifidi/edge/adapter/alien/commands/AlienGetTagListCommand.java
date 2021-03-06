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
package org.rifidi.edge.adapter.alien.commands;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rifidi.edge.adapter.alien.AbstractAlien9800Command;
import org.rifidi.edge.adapter.alien.Alien9800ReaderSession;
import org.rifidi.edge.adapter.alien.commandobject.AlienCommandObject;
import org.rifidi.edge.adapter.alien.commandobject.AlienException;
import org.rifidi.edge.adapter.alien.commandobject.AlienSetCommandObject;
import org.rifidi.edge.adapter.alien.commandobject.GetTagListCommandObject;
import org.rifidi.edge.adapter.alien.messages.AlienTag;
import org.rifidi.edge.adapter.alien.messages.AlienTagReadEventFactory;
import org.rifidi.edge.notification.ReadCycle;
import org.rifidi.edge.notification.TagReadEvent;

/**
 * An Command that runs on an AlienSession to get Tags back from an AlienReader
 * 
 * @author Jochen Mader - jochen@pramari.com
 * @author Kyle Neumeier - kyle@pramari.com
 */
public class AlienGetTagListCommand extends AbstractAlien9800Command {

	/** Logger for this class. */
	private static final Log logger = LogFactory
			.getLog(AlienGetTagListCommand.class);
	/** Tagtypes. */
	private Integer[] tagTypes = new Integer[] { 7, 16, 31 };
	/** Tagtypes to query for: 0 - GEN1 1 - GEN2 2 - ALL */
	private Integer tagType = 1;
	/** The readeriD */
	private AtomicReference<String> reader = new AtomicReference<String>();

	/**
	 * Constructor
	 * 
	 * @param commandID
	 */
	public AlienGetTagListCommand(String commandID) {
		super(commandID);
	}

	/**
	 * Set the name of the reader this command is associated with.
	 * 
	 * @param reader
	 */
	public void setReader(String reader) {
		this.reader.set(reader);
	}

	/**
	 * @return the tagType
	 */
	public int getTagType() {
		return tagType;
	}

	/**
	 * @param tagType
	 *            the tagType to set
	 */
	public void setTagType(int tagType) {
		this.tagType = tagType;
		if (tagType > 2 || tagType < 0) {
			throw new RuntimeException("Tagtype must be 0, 1, or 2");
		}
	}

	@Override
	public void execute() throws TimeoutException {
		try {

			// sending TagType
			AlienCommandObject tagTypeCommand = new AlienSetCommandObject(
					Alien9800ReaderSession.COMMAND_TAG_TYPE,
					tagTypes[getTagType()].toString(),
					(Alien9800ReaderSession) sensorSession);
			tagTypeCommand.setSession((Alien9800ReaderSession) sensorSession);
			tagTypeCommand.execute();

			// Setting the custom tag list format. This lets us get the velocity
			// and RSSI information, in addition to all the regular information.
			// This tag list looks exactly like the list coming back if the
			// TagListFormat was set to "Text", except it has the speed and RSSI
			// information as well.
			AlienCommandObject tagListFormat = new AlienSetCommandObject(
					Alien9800ReaderSession.COMMAND_TAG_LIST_FORMAT, "custom",
					(Alien9800ReaderSession) sensorSession);
			tagListFormat.setSession((Alien9800ReaderSession) sensorSession);
			tagListFormat.execute();

			// Setting the custom tag list format. This lets us get the velocity
			// and RSSI information, in addition to all the regular information.
			// This tag list looks exactly like the list coming back if the
			// TagListFormat was set to "Text", except it has the speed and RSSI
			// information as well.
			AlienCommandObject tagListCustomFormat = new AlienSetCommandObject(
					Alien9800ReaderSession.COMMAND_TAG_LIST_CUSTOM_FORMAT,
					"Tag:%i, Disc:%d %T, Last:%d %T, Count:%r, "
							+ "Ant:%a, Proto:%p, Rssi:%m, Speed:${SPEED}, Dir:${DIR}",
					(Alien9800ReaderSession) sensorSession);
			tagListCustomFormat
					.setSession((Alien9800ReaderSession) sensorSession);
			tagListCustomFormat.execute();

			GetTagListCommandObject getTagListCommandObject = new GetTagListCommandObject(
					(Alien9800ReaderSession) sensorSession);

			AlienTagReadEventFactory factory = new AlienTagReadEventFactory(
					reader.get());
			Set<TagReadEvent> events = new HashSet<TagReadEvent>();
			List<AlienTag> tagList = getTagListCommandObject.executeGet();
			for (AlienTag tag : tagList) {
				events.add(factory.getTagReadEvent(tag));
			}
			ReadCycle cycle = new ReadCycle(events, reader.get(), System
					.currentTimeMillis());
			sensorSession.getSensor().send(cycle);
			
			//TODO: SEND MESSAGE
			//template.send(destination, new ReadCycleMessageCreator(cycle));

		} catch (AlienException e) {
			logger.error("Alien Exception while executing command: "
					+ e.getMessage());
		} catch (IOException e) {
			logger.debug("IOException while executing command: "
					+ e.getMessage());
		} catch (TimeoutException e) {
			logger.warn("Timeout exception while executing command " + this);
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			logger
					.error("Exception while executing command: "
							+ e.getMessage());
		}
	}

}
