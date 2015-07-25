package covers1624.powerconverters.init;

import covers1624.powerconverters.handler.ConfigurationHandler;
import covers1624.powerconverters.worldgen.FlatBedrockWorldGen;
import cpw.mods.fml.common.registry.GameRegistry;

public class WorldGenerators {

	public static void init() {
		if (ConfigurationHandler.doFlatBedrock) {
			GameRegistry.registerWorldGenerator(new FlatBedrockWorldGen(), 1);
		}

	}

}
