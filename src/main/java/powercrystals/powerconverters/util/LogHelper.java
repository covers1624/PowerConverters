package powercrystals.powerconverters.util;

import org.apache.logging.log4j.Level;

import powercrystals.powerconverters.handler.ConfigurationHandler;
import cpw.mods.fml.common.FMLLog;

public class LogHelper {

	public static void log(Level logLevel, Object object) {
		FMLLog.log("PowerConverters", logLevel, String.valueOf(object));
	}

	public static void all(Object object) {
		log(Level.ALL, object);
	}

	// public static void debug(Object object) {
	// log(Level.DEBUG, object);
	// }

	public static void error(Object object) {
		log(Level.ERROR, object);
	}

	public static void fatal(Object object) {
		log(Level.FATAL, object);
	}

	public static void info(Object object) {
		log(Level.INFO, object);
	}

	public static void off(Object object) {
		log(Level.OFF, object);
	}

	/**
	 * This will log out to either TRACE or INFO, depending on "Log Debug Messages" config option.
	 * 
	 * @param Object
	 *            ,The Thing to log.
	 *
	 */
	public static void trace(Object object) {
		if (ConfigurationHandler.logDebug) {
			info(object);
		} else {
			log(Level.TRACE, object);
		}

	}

	public static void warn(Object object) {
		log(Level.WARN, object);
	}
}
