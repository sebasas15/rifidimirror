/* 
 *  ConnectionChangeListener.java
 *  Created:	Apr 14, 2009
 *  Project:	RiFidi org.rifidi.edge.client.ale.models
 *  				http://www.rifidi.org
 *  				http://rifidi.sourceforge.net
 *  Copyright:	Pramari LLC and the Rifidi Project
 *  License:	Lesser GNU Public License (LGPL)
 *  				http://www.opensource.org/licenses/lgpl-license.html
 */
package org.rifidi.edge.client.ale.models.listeners;

import org.rifidi.edge.client.ale.models.enums.ConnectionStatus;

/**
 * @author Tobias Hoppenthaler - tobias@pramari.com
 *
 */
public interface ConnectionChangeListener {
	
	public void connectionStatusChanged(ConnectionStatus status);

}