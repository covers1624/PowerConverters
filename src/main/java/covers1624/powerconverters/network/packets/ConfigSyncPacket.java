package covers1624.powerconverters.network.packets;

import covers1624.powerconverters.handler.ConfigurationHandler;
import covers1624.powerconverters.network.AbstractPacket;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by covers1624 on 6/10/2016.
 */
public class ConfigSyncPacket extends AbstractPacket {

    private NBTTagCompound tagCompound;

    public ConfigSyncPacket() {
    }

    public ConfigSyncPacket(NBTTagCompound tagCompound) {
        this.tagCompound = tagCompound;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        ByteBufUtils.writeTag(buffer, tagCompound);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        tagCompound = ByteBufUtils.readTag(buffer);
    }

    @Override
    public void handleClientSide(EntityPlayer player) {
        ConfigurationHandler.setServerConfig(tagCompound);
    }

    @Override
    public void handleServerSide(EntityPlayer player) {

    }
}
