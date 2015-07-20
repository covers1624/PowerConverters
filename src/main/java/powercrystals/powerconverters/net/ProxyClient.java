package powercrystals.powerconverters.net;

import powercrystals.powerconverters.power.conduit.TileEnergyConduit;
import powercrystals.powerconverters.render.TileUniversalConduitRender;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ProxyClient implements IPCProxy {

	@Override
	public void initRendering() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEnergyConduit.class, new TileUniversalConduitRender());
	}

}
