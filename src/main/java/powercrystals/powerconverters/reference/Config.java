package powercrystals.powerconverters.reference;

import java.io.File;
@Deprecated
public class Config {

	protected static File _configFolder;

	public static void setConfigFolderBase(File folder) {
		_configFolder = new File(folder.getAbsolutePath() + "/" + getConfigBaseFolder() + "/" + getModId().toLowerCase() + "/");
	}

	public static File getClientConfig() {
		return new File(_configFolder.getAbsolutePath() + "/client.cfg");
	}

	public static File getCommonConfig() {
		return new File(_configFolder.getAbsolutePath() + "/common.cfg");
	}

	public static String getConfigBaseFolder() {
		return "covers1624";
	}

	private static String getModId() {
		return Reference.MOD_ID;
	}
}