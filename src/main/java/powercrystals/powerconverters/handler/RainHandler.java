package powercrystals.powerconverters.handler;

import net.minecraft.server.MinecraftServer;
import powercrystals.powerconverters.util.LogHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;

public class RainHandler {

	private boolean flag = false;
	private int flag1 = 0;
	private int flag1Default = 100;

	@SubscribeEvent
	public void tickEnd(TickEvent event) {
		if (event.side == Side.SERVER && MinecraftServer.getServer().worldServers[0] != null) {
			if (MinecraftServer.getServer().worldServers[0].isRaining() && ConfigurationHandler.stopRain && flag != true) {
				flag = true;
				LogHelper.info("Automated Rain Stopage");
				MinecraftServer.getServer().worldServers[0].getWorldInfo().setRaining(false);
			}

			if (flag == true) {
				if (flag1 == flag1Default) {
					flag = false;
				} else {
					flag1++;
				}
			}
		}
	}
}
