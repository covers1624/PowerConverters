package covers1624.powerconverters.network.packets;

import covers1624.powerconverters.PowerConverters;
import covers1624.powerconverters.api.bridge.BridgeSideData;
import covers1624.powerconverters.network.AbstractPacket;
import covers1624.powerconverters.tile.main.TileEntityEnergyBridge;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class EnergyBridgeSyncPacket extends AbstractPacket {

    private NBTTagCompound tagCompound;

    public EnergyBridgeSyncPacket() {
    }

    public EnergyBridgeSyncPacket(NBTTagCompound tag, int x, int y, int z) {
        tagCompound = tag;
        tagCompound.setInteger("X", x);
        tagCompound.setInteger("Y", y);
        tagCompound.setInteger("Z", z);
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        ByteBufUtils.writeTag(buffer, tagCompound);

        // byte[] bytes = null;
        // boolean failed = false;
        // try {
        // / bytes = CompressedStreamTools.compress(tagCompound);
        // } catch (IOException e) {
        // failed = true;
        // LogHelper.fatal("Failed to convert NBTTagCompound to Byte array, This is possibly a fatal error but possibly not.");
        // e.printStackTrace();
        // }
        // if (!failed && bytes.length > 0) {
        // String noBytes = Double.valueOf(bytes.length).toString();
        // String kBytes = Double.valueOf(bytes.length / 1024).toString();
        // String mBytes = Double.valueOf((bytes.length / 1024) / 1024).toString();
        // LogHelper.info(String.format("Writing packet to network... Bytes: %s, Kb: %s, Mb: %s", noBytes, kBytes, mBytes));
        // }
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        tagCompound = ByteBufUtils.readTag(buffer);

    }

    @Override
    public void handleClientSide(EntityPlayer player) {
        int x = tagCompound.getInteger("X");
        int y = tagCompound.getInteger("Y");
        int z = tagCompound.getInteger("Z");
        TileEntity tileEntity = PowerConverters.proxy.getClientWorld().getTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityEnergyBridge) {
            TileEntityEnergyBridge energyBridge = (TileEntityEnergyBridge) tileEntity;
            for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
                NBTTagCompound tag = tagCompound.getCompoundTag(String.valueOf(dir.ordinal()));
                BridgeSideData data = new BridgeSideData();
                data.loadFromNBT(tag);
                energyBridge.setClientDataForSide(dir, data);
            }
            energyBridge.setIsInputLimited(tagCompound.getBoolean("InputLimited"));
            energyBridge.setEnergyScaled(tagCompound.getDouble("Energy"));
        } else {
            throw new RuntimeException(String.format("Tile @ %s, %s, %s, Does not match the server.", x, y, z));
        }
    }

    @Override
    public void handleServerSide(EntityPlayer player) {
    }

}
