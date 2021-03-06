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
package org.rifidi.edge.tools.diagnostics;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.osgi.framework.console.CommandProvider;
import org.rifidi.edge.api.AbstractRifidiApp;
import org.rifidi.edge.api.AppState;
import org.rifidi.edge.notification.TagReadEvent;

import com.espertech.esper.client.EPOnDemandQueryResult;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.StatementAwareUpdateListener;

/**
 * This is an application that lets the user query recently seen tags and tags
 * that can currently be seen.
 * 
 * @author Kyle Neumeier - kyle@pramari.com
 * 
 */
public class TagApp extends AbstractRifidiApp {

	/** The amount of time to keep tags around as recent tags */
	private String recentTagTimeout;
	private Log logger = LogFactory.getLog(TagApp.class);

	/**
	 * 
	 * @param group
	 * @param name
	 */
	public TagApp(String group, String name) {
		super(group, name);
	}
	
	/* (non-Javadoc)
	 * @see org.rifidi.edge.api.AbstractRifidiApp#lazyStart()
	 */
	@Override
	public boolean lazyStart() {
		String lazyStart= getProperty(LAZY_START, "true");
		return Boolean.parseBoolean(lazyStart);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rifidi.edge.api.AbstractRifidiApp#_start()
	 */
	@Override
	protected void _start() {

		// esper statement that creates a window.
		addStatement("create window recenttags.win:time(" + recentTagTimeout
				+ ") as TagReadEvent");

		addStatement("create window curtags.std:firstunique(tag.ID, readerID, antennaID).win:time(10 sec)"
				+ "as TagReadEvent");

		//addStatement("insert into recenttags select * from ReadCycle[select * from tags]");
		addStatement("insert into recenttags select * from ReadCycle[tags]");

		addStatement("insert into curtags select * from recenttags");
		
	}

	/**
	 * 
	 * @param readerID
	 * @return
	 */
	List<TagReadEvent> getRecentTags(String readerID) {
		if (getState() != AppState.STARTED) {
			logger.warn("TagApp not started. Use 'startapp <AppID>'");
			return new ArrayList<TagReadEvent>();
		}
		List<TagReadEvent> recentTags = new LinkedList<TagReadEvent>();
		EPOnDemandQueryResult result = executeQuery("select * from recenttags where readerID=\""
				+ readerID + "\"");
		if (result.getArray() != null) {
			for (EventBean event : result.getArray()) {
				TagReadEvent tag = (TagReadEvent) event.getUnderlying();
				recentTags.add(tag);
			}
		}
		return recentTags;
	}

	/**
	 * 
	 * @param readerID
	 * @return
	 */
	List<TagReadEvent> getCurrentTags(String readerID) {
		if (getState() != AppState.STARTED) {
			logger.warn("TagApp not started. Use 'startapp <AppID>'");
			return new ArrayList<TagReadEvent>();
		}
		List<TagReadEvent> currentTags = new LinkedList<TagReadEvent>();
		EPOnDemandQueryResult result = executeQuery("select * from curtags where readerID=\""
				+ readerID + "\"");
		if (result.getArray() != null) {
			for (EventBean event : result.getArray()) {
				TagReadEvent tag = (TagReadEvent) event.getUnderlying();
				currentTags.add(tag);
			}
		}
		return currentTags;
	}
	
	public void measureReadRate(final String seconds){
		if (getState() != AppState.STARTED) {
			logger.warn("TagApp not started. Use 'startapp <AppID>'");
			return;
		}

		addStatement("select tags from pattern" +
				"[every tags=ReadCycle[select * from tags] " +
				"until timer:interval("+seconds+" sec)]", 
				new StatementAwareUpdateListener() {
			
					@Override
					public void update(EventBean[] arg0, EventBean[] arg1,
							EPStatement arg2, EPServiceProvider arg3) {
						if (arg0 != null) {
							for (EventBean b : arg0) {
								TagReadEvent[] tags = (TagReadEvent[]) b
										.get("tags");
								float count;
								if(tags==null){
									count = 0f;
								}else{
									count = new Float(tags.length);
								}
								float time = Float.parseFloat(seconds);
								float rate = count / time;

								logger.info("Saw " + count + " tags in "
										+ seconds + " seconds. Rate: " + rate
										+ " tags/second");
							}
						}

					}
				});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rifidi.edge.api.AbstractRifidiApp#initialize()
	 */
	@Override
	public void initialize() {
		this.recentTagTimeout = getProperty("RecentTagTimeout", "10 min");
	}

	/* (non-Javadoc)
	 * @see org.rifidi.edge.api.AbstractRifidiApp#getCommandProider()
	 */
	@Override
	protected CommandProvider getCommandProvider() {
		TagAppCommandProvider provider = new TagAppCommandProvider();
		provider.setTagApp(this);
		return provider;
	}
	
	

}
