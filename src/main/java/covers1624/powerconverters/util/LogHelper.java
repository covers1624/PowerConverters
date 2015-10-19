package covers1624.powerconverters.util;

import covers1624.powerconverters.handler.ConfigurationHandler;
import cpw.mods.fml.common.FMLLog;
import org.apache.logging.log4j.Level;

public class LogHelper {

	public static void log(Level logLevel, Object object) {
		FMLLog.log("PowerConverters", logLevel, String.valueOf(object));
	}

	public static void all(Object object) {
		log(Level.ALL, object);
	}

	public static void debug(Object object) {
		log(Level.DEBUG, object);
	}

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

	/**
	 * Formatable
	 */
	public static void all(String object, Object... format) {
		log(Level.ALL, String.format(object, format));
	}

	public static void debug(String object, Object... format) {
		log(Level.DEBUG, String.format(object, format));
	}

	public static void error(String object, Object... format) {
		log(Level.ERROR, String.format(object, format));
	}

	public static void fatal(String object, Object... format) {
		log(Level.FATAL, String.format(object, format));
	}

	public static void info(String object, Object... format) {
		log(Level.INFO, String.format(object, format));
	}

	public static void off(String object, Object... format) {
		log(Level.OFF, String.format(object, format));
	}

	public static void trace(String object, Object... format) {
		if (ConfigurationHandler.logDebug) {
			log(Level.INFO, String.format(object, format));
		} else {
			log(Level.TRACE, String.format(object, format));
		}

	}

	public static void warn(String object, Object... format) {
		log(Level.WARN, String.format(object, format));
	}

	public static void bigFatal(String format, Object... data) {
		StackTraceElement[] trace = Thread.currentThread().getStackTrace();
		fatal("****************************************");
		fatal("* " + format, data);
		for (int i = 2; i < 8 && i < trace.length; i++) {
			fatal("*  at %s%s", trace[i].toString(), i == 7 ? "..." : "");
		}
		fatal("****************************************");
	}

}
