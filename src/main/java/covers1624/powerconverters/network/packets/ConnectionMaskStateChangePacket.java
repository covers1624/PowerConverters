package covers1624.powerconverters.network.packets;

import covers1624.powerconverters.PowerConverters;
import covers1624.powerconverters.network.AbstractPacket;
import covers1624.powerconverters.pipe.ConnectionMask;
import covers1624.powerconverters.pipe.IConnectionMask;
import covers1624.powerconverters.util.BlockPosition;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by covers1624 on 11/9/2015.
 */
public class ConnectionMaskStateChangePacket extends AbstractPacket{

	private NBTTagCompound packetData = new NBTTagCompound();

	public ConnectionMaskStateChangePacket(BlockPosition blockPos, IConnectionMask maskAccess){
		ConnectionMask.writeArrayToNBT(packetData, maskAccess.getConnectonMaksArray());
		blockPos.writeToNBT(packetData);
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		ByteBufUtils.writeTag(buffer, packetData);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		packetData = ByteBufUtils.readTag(buffer);
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		World world = PowerConverters.proxy.getClientWorld();
		BlockPosition blockPos = new BlockPosition(packetData);
		TileEntity tileEntity = blockPos.getTileEntity(world);
		IConnectionMask maskAccess = (IConnectionMask) tileEntity;
		ConnectionMask[] maskData = ConnectionMask.readArrayFromNBT(packetData);
		maskAccess.setConnectionMaskArray(maskData);
		// markBlockForRenderUpdate, For some unknown reason this is not de-obfuscated but it is with the mappings from the IRC bot.
		world.func_147479_m(blockPos.x, blockPos.y, blockPos.z);
		world.markBlockForUpdate(blockPos.x, blockPos.y, blockPos.z);
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		//Nope.
	}
}
