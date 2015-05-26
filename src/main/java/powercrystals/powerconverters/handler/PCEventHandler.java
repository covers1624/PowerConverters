package powercrystals.powerconverters.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.profiler.Profiler;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fluids.FluidRegistry.FluidRegisterEvent;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.fx.ParticleRenderDispatcher;
import powercrystals.powerconverters.gui.options.PCOptionsGuiButton;
import powercrystals.powerconverters.reference.Reference;
import powercrystals.powerconverters.util.LogHelper;
import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PCEventHandler {

	public static int ticksInGame = 0;

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
		if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
			ConfigurationHandler.INSTANCE.loadConfiguration();
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onGuiOpen(InitGuiEvent.Post event) {
		// LogHelper.info("Gui Opened");
		GuiScreen screen = event.gui;
		if (screen instanceof GuiOptions) {
			GuiOptions optionsGui = (GuiOptions) screen;
			// LogHelper.info("Adding Button @" + (optionsGui.width / 2 - 155) + "," + (optionsGui.height / 6 + 48 - 6));
			event.buttonList.add(new PCOptionsGuiButton(500, optionsGui.width / 2 - 155, optionsGui.height / 6 + 48 - 6, "Power Converters"));
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void clientTickEvent(ClientTickEvent event) {
		if (event.phase == Phase.END) {
			GuiScreen gui = Minecraft.getMinecraft().currentScreen;
			if (gui == null || gui.doesGuiPauseGame()) {
				ticksInGame++;
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onRenderWorldLast(RenderWorldLastEvent event) {
		Profiler profiler = Minecraft.getMinecraft().mcProfiler;

		profiler.startSection("powerconverters-walkparticles");
		ParticleRenderDispatcher.dispatch();
		profiler.endSection();
	}
}
