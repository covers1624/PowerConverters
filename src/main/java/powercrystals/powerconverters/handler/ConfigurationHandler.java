package powercrystals.powerconverters.handler;

import java.io.File;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import powercrystals.powerconverters.gui.options.EnumCapeResulution;
import powercrystals.powerconverters.power.PowerSystem;
import powercrystals.powerconverters.reference.Reference;
import powercrystals.powerconverters.util.LogHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ConfigurationHandler {

	public Configuration configuration;

	public static ConfigurationHandler INSTANCE;
	// don't load from config as it is internal only.
	public static boolean stopRain = false;
	public static boolean logDebug;

	public static EnumCapeResulution capeResulution;

	public static int bridgeBufferSize;

	public static int throttleSteamConsumer;
	public static int throttleSteamProducer;
	public static int throttleIC2LVProducer;
	public static int throttleIC2MVProducer;
	public static int throttleIC2HVProducer;
	public static int throttleIC2EVProducer;

	public static boolean altRecipes;

	public static boolean doFlatBedrock;

	public ConfigurationHandler(File config) {
		INSTANCE = this;
		if (configuration == null) {
			configuration = new Configuration(config);
		}
		loadConfiguration();
	}

	public void loadConfiguration() {
		bridgeBufferSize = configuration.get(Reference.BASIC_CATEGORY, "BridgeBufferSize", 160000000).getInt();

		logDebug = configuration.get(Reference.BASIC_CATEGORY, "Log Debug Messages", false, "Set this to true to see all debug messages.").getBoolean();

		doFlatBedrock = configuration.get(Reference.BASIC_CATEGORY, "Do Flat Bedrock", false, "Set this to false for normal Bedrock.").getBoolean();

		altRecipes = configuration.get(Reference.BASIC_CATEGORY, "AlternateRecipes", false, "ThermalExpansion Recipes").getBoolean();

		throttleSteamConsumer = configuration.get("Throttles", "Steam.Consumer", 1000, "mB/t").getInt();
		throttleSteamProducer = configuration.get("Throttles", "Steam.Producer", 1000, "mB/t").getInt();
		throttleIC2LVProducer = configuration.get("Throttles", "IC2.Consumer.LV", 1, "Packets/t").getInt();
		throttleIC2MVProducer = configuration.get("Throttles", "IC2.Consumer.MV", 1, "Packets/t").getInt();
		throttleIC2HVProducer = configuration.get("Throttles", "IC2.Consumer.HV", 1, "Packets/t").getInt();
		throttleIC2EVProducer = configuration.get("Throttles", "IC2.Consumer.EV", 1, "Packets/t").getInt();

		PowerSystem.loadConfig(configuration);

		// if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
		// loadCapeRes();
		// }

		configuration.save();
	}

	@SideOnly(Side.CLIENT)
	public void loadCapeRes() {
		String cape = configuration.get("CapeResulution", EnumCapeResulution.configCategory, "HIGH", "This Value is used to determine how large the cape textures are.").getString();
		capeResulution = EnumCapeResulution.parse(cape);
	}

	@SideOnly(Side.CLIENT)
	public void saveCapeRes() {
		ConfigCategory category = configuration.getCategory(EnumCapeResulution.configCategory);
		Property current = category.get("CapeResulution");
		LogHelper.info("Current: " + current.getString());
		if (!current.getString().equals(capeResulution.toString())) {
			current.set(capeResulution.toString());
			category.remove("CapeResulution");
			category.put("CapeResulution", current);
			configuration.save();
			LogHelper.info("After: " + current.getString());
		}
		LogHelper.info("No Change Needed.");
	}
}
