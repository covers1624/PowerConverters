package powercrystals.powerconverters.handler;

import java.io.File;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import powercrystals.powerconverters.power.PowerSystem;
import powercrystals.powerconverters.reference.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ConfigurationHandler {

	public static Configuration configuration;

	public static Property bridgeBufferSize;

	public static Property throttleSteamConsumer;
	public static Property throttleSteamProducer;
	public static Property throttleIC2LVProducer;
	public static Property throttleIC2MVProducer;
	public static Property throttleIC2HVProducer;
	public static Property throttleIC2EVProducer;

	public static Property altRecipes;

	public static void init(File config) {
		if (configuration == null) {
			configuration = new Configuration(config);
		}
		loadConfiguration();
	}

	public static void loadConfiguration() {
		bridgeBufferSize = configuration.get(Reference.BASIC_CATEGORY, "BridgeBufferSize", 160000000);

		altRecipes = configuration.get(Reference.BASIC_CATEGORY, "AlternateRecipes", false, "ThermalExpansion Recipes");

		throttleSteamConsumer = configuration.get("Throttles", "Steam.Consumer", 1000);
		throttleSteamConsumer.comment = "mB/t";
		throttleSteamProducer = configuration.get("Throttles", "Steam.Producer", 1000);
		throttleSteamProducer.comment = "mB/t";
		throttleIC2LVProducer = configuration.get("Throttles", "IC2.Consumer.LV", 1);
		throttleIC2LVProducer.comment = "Packets/t";
		throttleIC2MVProducer = configuration.get("Throttles", "IC2.Consumer.MV", 1);
		throttleIC2MVProducer.comment = "Packets/t";
		throttleIC2HVProducer = configuration.get("Throttles", "IC2.Consumer.HV", 1);
		throttleIC2HVProducer.comment = "Packets/t";
		throttleIC2EVProducer = configuration.get("Throttles", "IC2.Consumer.EV", 1);
		throttleIC2EVProducer.comment = "Packets/t";

		PowerSystem.loadConfig(configuration);

		configuration.save();
	}
	

}
