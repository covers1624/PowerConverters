package powercrystals.powerconverters.handler;

import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fluids.FluidRegistry.FluidRegisterEvent;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.gui.PCGuiButton;
import powercrystals.powerconverters.reference.Reference;
import powercrystals.powerconverters.util.LogHelper;
import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PCEventHandler {

	@SubscribeEvent
	public void onFluidRegisterEvent(FluidRegisterEvent event) {
		LogHelper.info(event.fluidName);
		if (event.fluidName.equals("Steam")) {
			PowerConverterCore.steamId = event.fluidID;
		} else if (event.fluidName.equals("steam") && PowerConverterCore.steamId == -1) {
			PowerConverterCore.steamId = event.fluidID;
		}
	}

	@SubscribeEvent
	public void onConfigChanged(OnConfigChangedEvent event) {
		LogHelper.info("Detected A Config Change");
		if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
			ConfigurationHandler.loadConfiguration();
		}
	}

	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent event) {
		// LogHelper.info("Gui Opened");
		GuiScreen screen = event.gui;
		if (screen instanceof GuiOptions) {
			LogHelper.info("Adding Button");
			GuiOptions optionsGui = (GuiOptions) screen;
			event.gui.buttonList.add(new PCGuiButton(500, optionsGui.width / 2, optionsGui.height / 2, 150, 20, "Power Converters"));
		}
	}
}
