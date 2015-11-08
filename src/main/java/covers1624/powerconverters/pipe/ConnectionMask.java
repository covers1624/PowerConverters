package covers1624.powerconverters.pipe;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by covers1624 on 11/4/2015.
 */
public class ConnectionMask {

	private ForgeDirection direction = ForgeDirection.UNKNOWN;
	private ConnectionSource connectionSource = ConnectionSource.UNKNOWN;
	private ConnectionType connectionType = ConnectionType.UNKNOWN;

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

	public void writeToNBT(NBTTagCompound nbtTagCompound){
		nbtTagCompound.setInteger("Direction", direction.ordinal());
		nbtTagCompound.setInteger("ConnectionSource", connectionSource.ordinal());
		nbtTagCompound.setInteger("ConnectionType", connectionType.ordinal());
	}

	public void readFromNBT(NBTTagCompound nbtTagCompound){
		direction = ForgeDirection.values()[nbtTagCompound.getInteger("Direction")];
		connectionSource = ConnectionSource.values()[nbtTagCompound.getInteger("ConnectionSource")];
		connectionType = ConnectionType.values()[nbtTagCompound.getInteger("ConnectionType")];
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
