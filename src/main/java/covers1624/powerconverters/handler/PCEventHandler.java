package covers1624.powerconverters.handler;

import covers1624.powerconverters.PowerConverters;
import covers1624.powerconverters.reference.Reference;
import covers1624.powerconverters.util.LogHelper;
import net.minecraftforge.fluids.FluidRegistry.FluidRegisterEvent;
import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PCEventHandler {

	public static int ticksInGame = 0;

	@SubscribeEvent
	public void onFluidRegisterEvent(FluidRegisterEvent event) {
		LogHelper.info(event.fluidName);
		if (event.fluidName.equals("Steam")) {
			PowerConverters.steamId = event.fluidID;
		} else if (event.fluidName.equals("steam") && PowerConverters.steamId == -1) {
			PowerConverters.steamId = event.fluidID;
		}
	}

	@SubscribeEvent
	public void onConfigChanged(OnConfigChangedEvent event) {
		if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
			ConfigurationHandler.INSTANCE.loadConfiguration();
		}
	}
}
