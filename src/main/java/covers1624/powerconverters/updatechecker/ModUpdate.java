package covers1624.powerconverters.updatechecker;

import covers1624.powerconverters.reference.Reference;
import net.minecraft.item.ItemStack;

public class ModUpdate {
	
	private String modName;
	private String version;
	private ItemStack updateIcon;
	
	public ModUpdate(String modName, String version, ItemStack updateIcon) {
		this.modName = modName;
		this.version = version;
		this.updateIcon = updateIcon;
	}
	public ModUpdate(UpdateCheckThread checkThread, ItemStack updateIcon) {
		if (checkThread.modUpdated() == Reference.MOD_ID) {
			this.modName = Reference.MOD_NAME;
		}else {
			this.modName = checkThread.modUpdated();
		}
		this.version = String.valueOf(checkThread.newVersion());
		this.updateIcon = updateIcon;
	}
	
	public String getModName(){
		return this.modName;
	}
	
	public String getModVersion(){
		return this.version;
	}
	
	public ItemStack getUpdateIcon(){
		return updateIcon;
	}

}
