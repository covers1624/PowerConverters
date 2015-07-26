package covers1624.powerconverters.updatechecker;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IChatComponent.Serializer;
import covers1624.powerconverters.gui.GuiModUpdateNotification;
import covers1624.powerconverters.handler.ConfigurationHandler;
import covers1624.powerconverters.init.ModBlocks;
import covers1624.powerconverters.reference.Reference;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import cpw.mods.fml.relauncher.Side;

public class UpdateManager {

	private int lastPoll = 400;
	private int lastPoll2 = 400;
	private boolean notificationDisplayed = false;
	private boolean acievementShown = false;
	private UpdateCheckThread updateThread;
	private String location = "http://minecraft.curseforge.com/mc-mods/225064-power-converters";
	private String message = "Hey PowerConverters has an update, It may include some cool stuff";
	GuiModUpdateNotification updateNotification;

	public UpdateManager() {
		updateThread = new UpdateCheckThread(Reference.MOD_ID);
		if (!ConfigurationHandler.doUpdateCheck) {
			return;
		}
		updateThread.start();
	}

	@SubscribeEvent
	public void tickStart(PlayerTickEvent event) {
		if (event.phase != Phase.START) {
			return;
		}

		if (lastPoll > 0) {
			--lastPoll;
			return;
		}
		lastPoll = 400;

		if (!notificationDisplayed && updateThread.checkFinished()) {
			notificationDisplayed = true;
			if (updateThread.newVersion()) {
				if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
					EntityPlayer player = event.player;
					IChatComponent chatComponent;

					chatComponent = Serializer.func_150699_a("[{\"text\":\"" + message + "\",\"color\":\"aqua\"}," + "{\"text\":\" " + EnumChatFormatting.WHITE + "[" + EnumChatFormatting.GREEN + "Download" + EnumChatFormatting.WHITE + "]\"," + "\"color\":\"green\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":" + "{\"text\":\"Click this to download the latest version\",\"color\":\"yellow\"}}," + "\"clickEvent\":{\"action\":\"open_url\",\"value\":\"" + location + "\"}}]");
					player.addChatMessage(chatComponent);
				}

			}
		}

	}

	// @SubscribeEvent
	public void tickEvent(RenderTickEvent event) {
		if (lastPoll2 > 0) {
			--lastPoll2;
			return;
		}
		lastPoll2 = 400;
		if (!acievementShown && updateThread.checkFinished()) {
			if (Minecraft.getMinecraft().theWorld == null) {
				return;
			}
			acievementShown = true;
			if (updateThread.newVersion()) {
				ModUpdate update = new ModUpdate(updateThread, new ItemStack(ModBlocks.converterBlockCommon));
				updateNotification = new GuiModUpdateNotification(Minecraft.getMinecraft(), update);
				updateNotification.update();
			}
		}
	}

}
