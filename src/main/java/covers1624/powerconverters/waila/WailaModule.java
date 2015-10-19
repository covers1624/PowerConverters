package covers1624.powerconverters.waila;

import covers1624.powerconverters.tile.main.TileEntityBridgeComponent;
import covers1624.powerconverters.tile.main.TileEntityEnergyBridge;
import mcp.mobius.waila.api.IWailaRegistrar;

public class WailaModule {

	public static void callBackRegister(IWailaRegistrar registrar) {
		registrar.registerBodyProvider(new WailaComponentProvider(), TileEntityBridgeComponent.class);
		registrar.registerBodyProvider(new WailaBridgeProvider(), TileEntityEnergyBridge.class);
	}

}
