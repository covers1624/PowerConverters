package powercrystals.powerconverters.updatechecker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import powercrystals.powerconverters.reference.Reference;
import powercrystals.powerconverters.util.FMLLogHelper;
import powercrystals.powerconverters.util.LogHelper;


public class UpdateCheckThread extends Thread {
	
	private String modID;
	private String updateURL;
	
	private boolean checkComplete = false;
	private boolean newVersionAvalable = false;
	private float newVersion = 0F;
	private float currentVersion = 0F;
	
	public UpdateCheckThread(String _modID){
		this(_modID, null);
	}

	public UpdateCheckThread(String _modID, String releseURL) {
		super("PowerConverters Update Thread");
		modID = _modID;
		if (releseURL == null) {
			updateURL = "https://raw.github.com/covers1624/" + modID + "/master/UpdateInfo.update";
		}else {
			updateURL = releseURL;
		}
		
	}

	@Override
	public void run() {
		try {
			URL versionFile = new URL(updateURL);
			BufferedReader reader = new BufferedReader(new InputStreamReader(versionFile.openStream()));
			String updaterVersion = reader.readLine();
			String[] localSplit = Reference.MOD_VERSION.split("-");
			String[] remoteSplit = updaterVersion.split("-");
			
			if (compareVersions(localSplit[1], remoteSplit[1])) {
				newVersionAvalable = true;
				LogHelper.info("We Have An update!");
			}else {
				LogHelper.info("We Have No Update :(");
			}
			checkComplete = true;
		} catch (Exception e) {
			FMLLogHelper.logException(Level.INFO, "It Broke!", e);
		}
	}
	
	private boolean compareVersions(String local, String remote){
		float localVersion = Float.parseFloat(local);
		float remoteVersion = Float.parseFloat(remote);
		
		if (remoteVersion > localVersion) {
			newVersion= remoteVersion;
			currentVersion = localVersion;
			return true;
		}
		
		return false;
	}
	public float getCurrentVersion(){
		return currentVersion;
	}
	
	public float getNewVersion(){
		return newVersion;
	}
	
	public boolean checkFinished(){
		return checkComplete;
	}
	
	public boolean newVersion(){
		return newVersionAvalable;
	}
	public String modUpdated(){
		return modID;
	}

}
