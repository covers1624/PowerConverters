package powercrystals.powerconverters.handler;

import net.minecraft.server.MinecraftServer;
import powercrystals.powerconverters.util.LogHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;

public class RainHandler {

	
	@SubscribeEvent
	public void tickEnd(TickEvent event) {
		if(event.side == Side.SERVER){
			if (MinecraftServer.getServer().worldServers[0] != null) {
				if (MinecraftServer.getServer().worldServers[0].isRaining() && ConfigurationHandler.stopRain) {
					LogHelper.info("Automated Rain Stopage");
					MinecraftServer.getServer().worldServers[0].getWorldInfo().setRaining(false);
				}
			}
		}
	}
}
