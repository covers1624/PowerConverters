package powercrystals.powerconverters.handler;

import java.io.File;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import powercrystals.powerconverters.power.PowerSystem;
import powercrystals.powerconverters.reference.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ConfigurationHandler {

	public static Configuration configuration;

	public static boolean stopRain;

	public static int bridgeBufferSize;

	public static int throttleSteamConsumer;
	public static int throttleSteamProducer;
	public static int throttleIC2LVProducer;
	public static int throttleIC2MVProducer;
	public static int throttleIC2HVProducer;
	public static int throttleIC2EVProducer;


	public static boolean altRecipes;
	
	public static boolean doFlatBedrock;

	public static void init(File config) {
		if (configuration == null) {
			configuration = new Configuration(config);
		}
		loadConfiguration();
	}

	public static void loadConfiguration() {
		bridgeBufferSize = configuration.get(Reference.BASIC_CATEGORY, "BridgeBufferSize", 160000000).getInt();

		stopRain = configuration.get("RAIN", "Stop Rain Server Side", false, "THIS IS INTERNAL USE ONLY").getBoolean();

		doFlatBedrock = configuration.get(Configuration.CATEGORY_GENERAL, "Do Flat Bedrock, false", "Set this to false for normal Bedrock.").getBoolean();
		
		altRecipes = configuration.get(Reference.BASIC_CATEGORY, "AlternateRecipes", false, "ThermalExpansion Recipes").getBoolean();

		throttleSteamConsumer = configuration.get("Throttles", "Steam.Consumer", 1000, "mB/t").getInt();
		throttleSteamProducer = configuration.get("Throttles", "Steam.Producer", 1000, "mB/t").getInt();
		throttleIC2LVProducer = configuration.get("Throttles", "IC2.Consumer.LV", 1, "Packets/t").getInt();
		throttleIC2MVProducer = configuration.get("Throttles", "IC2.Consumer.MV", 1, "Packets/t").getInt();
		throttleIC2HVProducer = configuration.get("Throttles", "IC2.Consumer.HV", 1, "Packets/t").getInt();
		throttleIC2EVProducer = configuration.get("Throttles", "IC2.Consumer.EV", 1, "Packets/t").getInt();

		PowerSystem.loadConfig(configuration);

		configuration.save();
	}

}
