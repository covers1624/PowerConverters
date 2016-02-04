package covers1624.powerconverters.updatechecker;

import covers1624.powerconverters.reference.Reference;
import covers1624.powerconverters.util.LogHelper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class UpdateCheckThread extends Thread {

	private String updateURL = "https://raw.github.com/covers1624/PowerConverters/master/UpdateInfo.update";

	private boolean checkComplete = false;
	private boolean newVersionAvalable = false;
	private float newVersion = 0F;
	private float currentVersion = 0F;

	public UpdateCheckThread() {
		super("PowerConverters Update Thread");
	}

	@Override
	public void run() {
		try {
			URL versionFile = new URL(updateURL);
			BufferedReader reader = new BufferedReader(new InputStreamReader(versionFile.openStream()));
			String remoteString = reader.readLine();
			String[] localSplit = Reference.MOD_VERSION.split("-");
			String[] remoteSplit = remoteString.split("-");

			if (compareVersions(localSplit[1], remoteSplit[1])) {
				newVersionAvalable = true;
				LogHelper.info("We Have An update");
			} else {
				LogHelper.info("We Have No Update");
			}
			checkComplete = true;
		} catch (Exception e) {
			LogHelper.error("Unable to check for update!");
			e.printStackTrace();
		}
	}

	private boolean compareVersions(String local, String remote) {
		if (Float.parseFloat(local) > Float.parseFloat(remote)) {
			newVersion = Float.parseFloat(remote);
			currentVersion = Float.parseFloat(local);
			return true;
		}
		return false;
	}

	public float getNewVersion() {
		return newVersion;
	}

	public boolean checkFinished() {
		return checkComplete;
	}

	public boolean newVersion() {
		return newVersionAvalable;
	}

}
