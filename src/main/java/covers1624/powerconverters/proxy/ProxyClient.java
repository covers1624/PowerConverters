package covers1624.powerconverters.proxy;

import covers1624.powerconverters.client.render.TileUniversalConduitRender;
import covers1624.powerconverters.tile.conduit.TileEnergyConduit;
import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ProxyClient implements IPCProxy {

	@Override
	public void initRendering() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEnergyConduit.class, new TileUniversalConduitRender());
	}

	@Override
	public EntityPlayer getClientPlayer() {
		return Minecraft.getMinecraft().thePlayer;
	}

	@Override
	public World getClientWorld() {
		return Minecraft.getMinecraft().theWorld;
	}

}
