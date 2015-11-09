package covers1624.powerconverters.pipe;

import covers1624.powerconverters.network.PacketPipeline;
import covers1624.powerconverters.network.packets.ConnectionMaskStateChangePacket;
import covers1624.powerconverters.util.BlockPosition;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by covers1624 on 11/4/2015.
 */
public class ConnectionMask {

	private ForgeDirection direction = ForgeDirection.UNKNOWN;
	private ConnectionSource connectionSource = ConnectionSource.UNKNOWN;
	private ConnectionType connectionType = ConnectionType.UNKNOWN;

	private ConnectionMask(){

	}

	public ConnectionMask(int dir, ConnectionSource connectionSource, ConnectionType connectionType){
		this(ForgeDirection.values()[dir], connectionSource, connectionType);
	}

	public ConnectionMask(ForgeDirection direction, ConnectionSource connectionSource, ConnectionType connectionType){
		this.direction = direction;
		this.connectionSource = connectionSource;
	}

	public ForgeDirection getDirection() {
		return direction;
	}

	public ConnectionSource getConnectionSource() {
		return connectionSource;
	}

	public ConnectionType getConnectionType() {
		return connectionType;
	}

	/**
	 * Fired when the state of the connection mask has changed and should be sent to the client for render processing.
	 */
	public static void onStateChange(BlockPosition blockPos, World world, IConnectionMask maskAccess){
		PacketPipeline.instance().sendToAllAround(new ConnectionMaskStateChangePacket(blockPos, maskAccess), new NetworkRegistry.TargetPoint(world.provider.dimensionId, blockPos.x, blockPos.y, blockPos.z, 48));
	}

	/**
	 * Writes an array of ConnectionMasks to NBT.
	 */
	public static void writeArrayToNBT(NBTTagCompound tagCompound, ConnectionMask[] data){
		if (tagCompound != null && data != null){
			NBTTagList tagList = new NBTTagList();
			tagCompound.setInteger("Size", data.length);
			for (int i = 0; i < data.length; i++) {
				ConnectionMask mask = data[i];
				NBTTagCompound maskData = new NBTTagCompound();
				maskData.setInteger("Index", i);
				mask.writeToNBT(maskData);
				tagList.appendTag(maskData);
			}
			tagCompound.setTag("Data", tagList);
		}
	}

	public static ConnectionMask[] readArrayFromNBT(NBTTagCompound data){
		if (data != null){
			ConnectionMask[] connectionMaskArray = new ConnectionMask[data.getInteger("Size")];
			NBTTagList tagList = data.getTagList("Data", 10);
			for (int i = 0; i < tagList.tagCount(); i++) {
				NBTTagCompound maskData = tagList.getCompoundTagAt(i);
				connectionMaskArray[maskData.getInteger("Index")] = new ConnectionMask().readFromNBT(maskData);
			}
			return connectionMaskArray;
		}
		return null;
	}

	public ConnectionMask writeToNBT(NBTTagCompound nbtTagCompound){
		nbtTagCompound.setInteger("Direction", direction.ordinal());
		nbtTagCompound.setInteger("ConnectionSource", connectionSource.ordinal());
		nbtTagCompound.setInteger("ConnectionType", connectionType.ordinal());
		return this;
	}

	public ConnectionMask readFromNBT(NBTTagCompound nbtTagCompound){
		direction = ForgeDirection.values()[nbtTagCompound.getInteger("Direction")];
		connectionSource = ConnectionSource.values()[nbtTagCompound.getInteger("ConnectionSource")];
		connectionType = ConnectionType.values()[nbtTagCompound.getInteger("ConnectionType")];
		return this;
	}

	public enum ConnectionSource {
		LOCAL, // Connections to itself;
		RF, // Connections to interface: cofh.api.energy.IEnergyConnection;
		EU, // Connections to interface: ic2.api.energy.tile.IEnergyTile;
		UNKNOWN //Always Last;
	}

	public enum ConnectionType{
		INPUT,
		OUTPUT,
		BOTH,
		UNKNOWN
	}
}
