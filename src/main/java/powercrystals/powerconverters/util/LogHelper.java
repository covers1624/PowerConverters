package powercrystals.powerconverters.util;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;

public class LogHelper {

	public static void log(Level logLevel, Object object) {
		FMLLog.log("PowerConverters", logLevel, String.valueOf(object));
	}

	public static void all(Object object) {
		log(Level.ALL, object);
	}

	public static void info(Object object) {
		log(Level.INFO, object);
	}

}
