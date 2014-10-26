package powercrystals.powerconverters.handler;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.reference.Reference;
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

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent event) {
		System.out.println("Detected A Config Change");
		if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
			ConfigurationHandler.loadConfiguration();
		}
	}

}
