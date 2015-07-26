package covers1624.powerconverters.handler;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import covers1624.powerconverters.reference.Reference;

public class ConfigurationHandler {

	public Configuration configuration;

	public static ConfigurationHandler INSTANCE;
	public static boolean logDebug;

	public static int bridgeBufferSize;

	public static int throttleSteamConsumer;
	public static int throttleSteamProducer;
	public static int throttleIC2LVProducer;
	public static int throttleIC2MVProducer;
	public static int throttleIC2HVProducer;
	public static int throttleIC2EVProducer;

	public static boolean useThermalExpansionRecipes;
	public static boolean useTechRebornRecipes;

	public static boolean doFlatBedrock;

	public static boolean doUpdateCheck;

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
		doUpdateCheck = configuration.get(Reference.BASIC_CATEGORY, "Do Update Check", true, "Set to false and PowerConverters will not check for an update").getBoolean();

		useThermalExpansionRecipes = configuration.get(Reference.RECIPES_CATEGORY, "Thermal Expansion", false, "Thermal Expansion Recipes").getBoolean();
		useTechRebornRecipes = configuration.get(Reference.RECIPES_CATEGORY, "Tech Reborn", false, "Tech Reborn Recipes").getBoolean();

		// TODO Throttles
		throttleSteamConsumer = configuration.get(Reference.THROTTLES_CATEGORY, "Steam.Consumer", 1000, "mB/t").getInt();
		throttleSteamProducer = configuration.get(Reference.THROTTLES_CATEGORY, "Steam.Producer", 1000, "mB/t").getInt();
		throttleIC2LVProducer = configuration.get(Reference.THROTTLES_CATEGORY, "IC2.Consumer.LV", 1, "Packets/t").getInt();
		throttleIC2MVProducer = configuration.get(Reference.THROTTLES_CATEGORY, "IC2.Consumer.MV", 1, "Packets/t").getInt();
		throttleIC2HVProducer = configuration.get(Reference.THROTTLES_CATEGORY, "IC2.Consumer.HV", 1, "Packets/t").getInt();
		throttleIC2EVProducer = configuration.get(Reference.THROTTLES_CATEGORY, "IC2.Consumer.EV", 1, "Packets/t").getInt();

		// TODO
		// PowerSystemOld.loadConfig(configuration);

		configuration.save();
	}
}
