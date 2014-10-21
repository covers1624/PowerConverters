package powercrystals.powerconverters.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import powercrystals.powerconverters.PowerConverterCore;
import net.minecraftforge.fluids.FluidRegistry.FluidRegisterEvent;

public class EventsHandler {

	@SubscribeEvent
	public void onFluidRegisterEvent(FluidRegisterEvent event) {
		if (event.fluidName.equals("Steam")) {
			PowerConverterCore.steamId = event.fluidID;
		} else if (event.fluidName.equals("steam") && PowerConverterCore.steamId <= 0) {
			PowerConverterCore.steamId = event.fluidID;
		}
	}

}
