package powercrystals.powerconverters.init;

import powercrystals.powerconverters.handler.ConfigurationHandler;
import powercrystals.powerconverters.worldgen.FlatBedrockWorldGen;
import cpw.mods.fml.common.registry.GameRegistry;

public class WorldGenerators {

	public static void init() {
		if (ConfigurationHandler.doFlatBedrock) {
			GameRegistry.registerWorldGenerator(new FlatBedrockWorldGen(), 1);
		}

	}

}
