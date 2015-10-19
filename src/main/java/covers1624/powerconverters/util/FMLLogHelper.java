package covers1624.powerconverters.util;

import cpw.mods.fml.relauncher.FMLRelaunchLog;
import org.apache.logging.log4j.Level;

public class FMLLogHelper {

	public static void logException(Level level, String data, Throwable ex) {
		FMLRelaunchLog.log.getLogger().log(level, data, ex);
	}

	public static void log(Level level, Object obj) {
		FMLRelaunchLog.log.getLogger().log(level, String.valueOf(obj));
	}

}
