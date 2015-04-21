package powercrystals.powerconverters.handler;

import net.minecraft.server.MinecraftServer;
import powercrystals.powerconverters.util.LogHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;

public class RainHandler {

	private int lastPoll = 400;

	@SubscribeEvent
	public void tickEnd(TickEvent event) {
		if (event.side == Side.SERVER && MinecraftServer.getServer().worldServers[0] != null) {
			if (lastPoll > 0) {
				--lastPoll;
				return;
			}

			if (MinecraftServer.getServer().worldServers[0].isRaining() && ConfigurationHandler.stopRain) {
				lastPoll = 400;
				LogHelper.info("Automated Rain Stopage");
				MinecraftServer.getServer().worldServers[0].getWorldInfo().setRaining(false);
			}

		}
	}
}
