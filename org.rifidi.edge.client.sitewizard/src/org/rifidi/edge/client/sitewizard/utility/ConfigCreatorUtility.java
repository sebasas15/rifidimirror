/* 
 * ConfigCreatorUtility.java
 *  Created:	Jul 25, 2008
 *  Project:	RiFidi Emulator - A Software Simulation Tool for RFID Devices
 *  				http://www.rifidi.org
 *  				http://rifidi.sourceforge.net
 *  Copyright:	Pramari LLC and the Rifidi Project
 *  License:	Lesser GNU Public License (LGPL)
 *  				http://www.opensource.org/licenses/lgpl-license.html
 */
package org.rifidi.edge.client.sitewizard.utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.rifidi.edge.client.sitewizard.constants.ConfigurationConstants;
import org.rifidi.edge.client.sitewizard.creator.ReaderObject;

/**
 * This class will create a config.ini file that refers to the desired
 * 
 * @author Matthew Dean - matt@pramari.com
 */
public class ConfigCreatorUtility {

	/**
	 * 
	 */
	private static final String CONFIGURATION_FOLDER = "/configuration/";

	/**
	 * 
	 */
	private static final String CONFIG_INI = "/config.ini";

	/**
	 * 
	 */
	private ConfigCreatorUtility() {
	}

	/**
	 * The singleton instance
	 */
	private static ConfigCreatorUtility instance = new ConfigCreatorUtility();

	/**
	 * @return the instance
	 */
	public static ConfigCreatorUtility getInstance() {
		return instance;
	}

	/**
	 * Create the folders
	 * 
	 * @param siteName
	 */
	private void createFolders(String siteName) {
		File f = new File(ConfigurationConstants.SERVER_FOLDER + siteName + CONFIGURATION_FOLDER);
		f.mkdirs();
	}

	/**
	 * Creates a config file.
	 * 
	 * @param instanceName
	 */
	public void createConfigFile(String siteName,
			List<ReaderObject> readerObjects, List<String> pluginPaths) {
		this.createFolders(siteName);
		File f = new File(ConfigurationConstants.SERVER_FOLDER + siteName + CONFIGURATION_FOLDER
				+ CONFIG_INI);
		FileWriter fwriter = null;
		try {
			f.createNewFile();
			fwriter = new FileWriter(f);
			fwriter.write(ConfigurationConstants.OSGI_BUNDLES);

			for (String s : pluginPaths) {
				fwriter.write(ConfigurationConstants.PLUGIN_FOLDER_PATH + s
						+ ConfigurationConstants.COMMA);
			}

			boolean skip = true;
			for (ReaderObject ro : readerObjects) {
				if (skip) {
					skip = false;
				} else {
					fwriter.write(ConfigurationConstants.COMMA);
				}
				fwriter.write(ConfigurationConstants.READER_FOLDER_PATH
						+ ro.getInclude());
			}
			fwriter.write(ConfigurationConstants.NEWLINE);

			fwriter.write(ConfigurationConstants.OSGI_BUNDLES_DEFAULTSTARTLEVEL
					+ ConfigurationConstants.NEWLINE);

			// FIXME: Need to create log folder, and set the log folder here.

			fwriter.write(ConfigurationConstants.OSGI_CASCADED
					+ ConfigurationConstants.NEWLINE);

			fwriter.write(ConfigurationConstants.ECLIPSE_IGNORE_APP);

			fwriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fwriter != null) {
				try {
					fwriter.close();
				} catch (IOException e) {
					// Don't care
				}
			}
		}
	}
}
