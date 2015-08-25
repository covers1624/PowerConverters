package covers1624.powerconverters.asm;

import java.util.Map;

import covers1624.powerconverters.util.LogHelper;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import net.minecraft.launchwrapper.Launch;

@MCVersion("1.7.10")
public class ASMHandler implements IFMLLoadingPlugin {

	static {
		// Yes i know what you are thinking, "WHY THE FUCK IS HE NOT ALLOWING US TO ASM HIS CODE".
		// Simple Answer, "FUCK OFF CUNT!".
		// If you have a problem with the way i run my mod.
		// TELL ME. Don't just ASM the fuck out of it.

		// Most of the stuff in here can be turned off with configs.
		// If you still feel that you need to turn something off.
		// Go ahead and turn it off in the config.
		LogHelper.info("Adding PowerConverters to Transformer Exclusions list.");
		Launch.classLoader.addTransformerExclusion("covers1624.powerconverters.");
	}

	@Override
	public String[] getASMTransformerClass() {
		return null;
	}

	@Override
	public String getModContainerClass() {
		return null;
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {

	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

}
