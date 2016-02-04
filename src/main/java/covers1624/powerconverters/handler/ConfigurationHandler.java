package covers1624.powerconverters.handler;

import covers1624.powerconverters.init.recipes.RecipeModuleDiscoverer;
import covers1624.powerconverters.reference.Reference;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigurationHandler {

	public static Configuration configuration;
	@Deprecated
	public static ConfigurationHandler INSTANCE;

	public static String recipeState;

	public static boolean logDebug;

	public static int bridgeBufferSize;

	// Recipes
	@Deprecated
	public static boolean useThermalExpansionRecipes;
	@Deprecated
	public static boolean useTechRebornRecipes;

	// Features
	public static boolean doUpdateCheck;
	public static boolean sendRecipesToClient;
	public static boolean ignoreRecipesFromServer;

	// Devices
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

	public static void init(File config) {
		if (configuration == null) {
			configuration = new Configuration(config);
		}
		loadConfiguration();
	}

	public static void loadConfiguration() {
		bridgeBufferSize = configuration.get(Reference.BASIC_CATEGORY, "BridgeBufferSize", 160000000).getInt();

		logDebug = configuration.get(Reference.BASIC_CATEGORY, "Log Debug Messages", false, "Set this to true to see all debug messages in the console.").getBoolean();

		doUpdateCheck = configuration.get(Reference.BASIC_CATEGORY, "Do Update Check", true, "Set to false and PowerConverters will not check for an update").getBoolean();
		sendRecipesToClient = configuration.get(Reference.BASIC_CATEGORY, "Send Recipes To Client", true, "Setting this to false will dissable recipe syncing with the server").getBoolean();
		ignoreRecipesFromServer = configuration.get(Reference.BASIC_CATEGORY, "Ignore Server Recipes", false, "Setting this to true will dissable the client using the server recipes.").getBoolean();
		recipeState = configuration.getString("Recipe Type", Reference.RECIPES_CATEGORY, "Default", "Different recipe types will show up here, They can also be changed in-game with a command or via the mod config menu.", RecipeModuleDiscoverer.loadedModules.toArray(new String[RecipeModuleDiscoverer.loadedModules.size()]));

		// throttleIC2LVProducer = configuration.get(Reference.THROTTLES_CATEGORY, "IC2.Consumer.LV", 1, "Packets/t").getInt();
		// throttleIC2MVProducer = configuration.get(Reference.THROTTLES_CATEGORY, "IC2.Consumer.MV", 1, "Packets/t").getInt();
		// throttleIC2HVProducer = configuration.get(Reference.THROTTLES_CATEGORY, "IC2.Consumer.HV", 1, "Packets/t").getInt();
		// throttleIC2EVProducer = configuration.get(Reference.THROTTLES_CATEGORY, "IC2.Consumer.EV", 1, "Packets/t").getInt();

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
