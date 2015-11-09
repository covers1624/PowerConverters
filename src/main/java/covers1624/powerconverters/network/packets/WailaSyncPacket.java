package covers1624.powerconverters.network.packets;

import covers1624.powerconverters.PowerConverters;
import covers1624.powerconverters.network.AbstractPacket;
import covers1624.powerconverters.network.PacketPipeline;
import covers1624.powerconverters.util.LogHelper;
import covers1624.powerconverters.waila.IWailaSync;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by covers1624 on 11/8/2015.
 */
public class WailaSyncPacket extends AbstractPacket {


	private NBTTagCompound tagCompound = new NBTTagCompound();


	public WailaSyncPacket(int x, int y, int z){
		tagCompound.setInteger("x", x);
		tagCompound.setInteger("y", y);
		tagCompound.setInteger("z", z);
	}
	/**
	 * Pass null on client, the tile to update on server.
	 * @param sync
	 */
	public WailaSyncPacket(IWailaSync sync){
		//Null means client, used as a request for the server to respond with data.
		if (sync != null){
			List<String> body = new ArrayList<String>();
			sync.addWailaInfo(body);
			NBTTagList tagList = new NBTTagList();
			for (int i = 0; i < body.size(); i++) {
				NBTTagCompound nbtTagCompound = new NBTTagCompound();
				nbtTagCompound.setInteger("Index", i);
				nbtTagCompound.setString("Data", body.get(i));
				tagList.appendTag(nbtTagCompound);
			}
			tagCompound.setTag("Data", tagList);
		}
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

	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		TileEntity tileEntity = player.worldObj.getTileEntity(tagCompound.getInteger("x"), tagCompound.getInteger("y"), tagCompound.getInteger("z"));
		if(tileEntity instanceof IWailaSync){
			PacketPipeline.instance().sendTo(new WailaSyncPacket((IWailaSync)tileEntity), (EntityPlayerMP) player);
		}
	}
}
