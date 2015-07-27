package covers1624.powerconverters.handler;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import covers1624.powerconverters.reference.Reference;

public class ConfigurationHandler {

	public Configuration configuration;
	public static ConfigurationHandler INSTANCE;

	public static boolean logDebug;

	public static int bridgeBufferSize;

	// Throttles
	public static int throttleSteamConsumer;
	public static int throttleSteamProducer;
	public static int throttleIC2LVProducer;
	public static int throttleIC2MVProducer;
	public static int throttleIC2HVProducer;
	public static int throttleIC2EVProducer;

	// Recipes
	public static boolean useThermalExpansionRecipes;
	public static boolean useTechRebornRecipes;

	// Features
	public static boolean doFlatBedrock;
	public static boolean doUpdateCheck;

	// Functionality

	public static boolean dissableRFProducer;
	public static boolean dissableRFConsumer;

	public static boolean dissableIC2Producer;
	public static boolean dissableIC2Consumer;

	public static boolean dissableFactorizationProducer;
	public static boolean dissableFactorizationConsumer;

	public static boolean dissableSteamProducer;
	public static boolean dissableSteamConsumer;

	public static boolean dissableUniversalCharger;
	public static boolean dissableUniversalUnCharger;

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

		dissableRFProducer = configuration.getBoolean("Dissable RF Producer", Reference.DEVICES_CATEGORY, false, "Dissables the RF Producer");
		dissableRFConsumer = configuration.getBoolean("Dissable RF Consumer", Reference.DEVICES_CATEGORY, false, "Dissables the RF Consumer");

		dissableIC2Producer = configuration.getBoolean("Dissable IC2 Producers", Reference.DEVICES_CATEGORY, false, "Dissables the IC2 Producers");
		dissableIC2Consumer = configuration.getBoolean("Dissable IC2 Consumers", Reference.DEVICES_CATEGORY, false, "Dissables the IC2 Consumers");

		dissableFactorizationProducer = configuration.getBoolean("Dissable Factorization Producer", Reference.DEVICES_CATEGORY, false, "Dissables the Factorization Producer");
		dissableFactorizationConsumer = configuration.getBoolean("Dissable Factorization Consumer", Reference.DEVICES_CATEGORY, false, "Dissables the Factorization Consumer");

		dissableSteamProducer = configuration.getBoolean("Dissable Steam Producer", Reference.DEVICES_CATEGORY, false, "Dissables the Steam Producer");
		dissableSteamConsumer = configuration.getBoolean("Dissable Steam Consumer", Reference.DEVICES_CATEGORY, false, "Dissables the Steam Consumer");

		dissableUniversalCharger = configuration.getBoolean("Dissable Universal Charger", Reference.DEVICES_CATEGORY, false, "Dissables the Universal Charger");
		dissableUniversalUnCharger = configuration.getBoolean("Dissable Universal Un Charger", Reference.DEVICES_CATEGORY, false, "Dissables the Universal Un Charger");

		// TODO
		// PowerSystemOld.loadConfig(configuration);

		configuration.save();
	}
}
