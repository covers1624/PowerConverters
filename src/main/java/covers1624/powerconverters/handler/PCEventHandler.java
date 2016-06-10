package covers1624.powerconverters.handler;

import covers1624.powerconverters.PowerConverters;
import covers1624.powerconverters.manager.RecipeStateManager;
import covers1624.powerconverters.network.PacketPipeline;
import covers1624.powerconverters.network.packets.ConfigSyncPacket;
import covers1624.powerconverters.reference.Reference;
import covers1624.powerconverters.util.LogHelper;
import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fluids.FluidRegistry.FluidRegisterEvent;

public class PCEventHandler {

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
            ConfigurationHandler.loadConfiguration();
        }
    }

    @SideOnly(Side.SERVER)//TODO Local lan recipe sync.
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onClientJoin(PlayerEvent.PlayerLoggedInEvent event) {
        LogHelper.info("Attempting to send recipe packet to player: %s", event.player.getCommandSenderName());
        if (ConfigurationHandler.sendRecipesToClient) {
            if (event.player instanceof EntityPlayerMP) {
                PacketPipeline.instance().sendTo(RecipeStateManager.instance().craftSyncPacket(), (EntityPlayerMP) event.player);
                LogHelper.info("Sending recipe packet to player: %s was a success!");
            } else {
                LogHelper.bigFatal("Player is not an instance of EntityPlayerMP! Unable to send Recipe Sync Packet!");
            }
        } else {
            LogHelper.bigWarn("RECIPE SENDING IS TURNED OFF! This may cause recipe syncing errors on the client!");
        }
        LogHelper.info("Sending server config to client %s", event.player.getCommandSenderName());
        try {
            PacketPipeline.instance().sendTo(new ConfigSyncPacket(ConfigurationHandler.getClientSyncTag()), (EntityPlayerMP) event.player);
        } catch (Exception e) {
            LogHelper.error("Unable to send ConfigSyncPacket!");
            e.printStackTrace();
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onClientDisconnected(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        RecipeStateManager.instance().loadDefaults();
        ConfigurationHandler.restoreConfig();
    }
}
