package covers1624.powerconverters.tile.main;

import covers1624.powerconverters.api.registry.PowerSystemRegistry.PowerSystem;
import covers1624.powerconverters.block.BlockPowerConverter;
import covers1624.powerconverters.util.BlockPosition;
import covers1624.powerconverters.util.IAdvancedLogTile;
import covers1624.powerconverters.util.INeighboorUpdateTile;
import covers1624.powerconverters.waila.IWailaSync;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TileEntityBridgeComponent<T> extends TileEntity implements INeighboorUpdateTile, IAdvancedLogTile, IWailaSync {
	private Map<ForgeDirection, TileEntityEnergyBridge> adjacentBridges = new HashMap<ForgeDirection, TileEntityEnergyBridge>();
	private Map<ForgeDirection, T> adjacentTiles = new HashMap<ForgeDirection, T>();

	private Class<?> adjacentClass;
	protected PowerSystem powerSystem;
	protected int voltageIndex;
	protected String type;

	private boolean initialized;

	protected TileEntityBridgeComponent(PowerSystem powersystem, int voltageNameIndex, Class<T> adjacentClass) {
		this.powerSystem = powersystem;
		this.voltageIndex = voltageNameIndex;
		this.adjacentClass = adjacentClass;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();

		if (!initialized && !tileEntityInvalid) {
			onNeighboorChanged();
			initialized = true;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onNeighboorChanged() {
		Map<ForgeDirection, TileEntityEnergyBridge> adjacentBridges = new HashMap<ForgeDirection, TileEntityEnergyBridge>();
		Map<ForgeDirection, T> adjacentTiles = new HashMap<ForgeDirection, T>();

		for (ForgeDirection d : ForgeDirection.VALID_DIRECTIONS) {
			TileEntity te = BlockPosition.getAdjacentTileEntity(this, d);
			if (te != null && te instanceof TileEntityEnergyBridge) {
				adjacentBridges.put(d, (TileEntityEnergyBridge) te);
			} else if (te != null && adjacentClass.isAssignableFrom(te.getClass())) {
				adjacentTiles.put(d, (T) te);
			}
		}

		this.adjacentBridges = adjacentBridges;
		this.adjacentTiles = adjacentTiles;
	}

	public PowerSystem getPowerSystem() {
		return powerSystem;
	}

	public boolean isConnected() {
		return adjacentTiles.size() > 0;
	}

	public boolean isSideConnected(int side) {
		return adjacentTiles.get(ForgeDirection.getOrientation(side)) != null;
	}

	public boolean isSideConnectedClient(int side) {
		TileEntity te = BlockPosition.getAdjacentTileEntity(this, ForgeDirection.getOrientation(side));
		return te != null && adjacentClass.isAssignableFrom(te.getClass());
	}

	public int getVoltageIndex() {
		return voltageIndex;
	}

	public TileEntityEnergyBridge getFirstBridge() {
		return adjacentBridges.size() == 0 ? null : (TileEntityEnergyBridge) adjacentBridges.values().toArray()[0];
	}

	protected Map<ForgeDirection, TileEntityEnergyBridge> getBridges() {
		return adjacentBridges;
	}

	protected Map<ForgeDirection, T> getTiles() {
		return adjacentTiles;
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		if (voltageIndex == 0) {
			voltageIndex = tagCompound.getInteger("voltageIndex");
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setInteger("voltageIndex", voltageIndex);
	}

	public boolean isGettingRedstone() {
		Block block = worldObj.getBlock(xCoord, yCoord, zCoord);
		if (block instanceof BlockPowerConverter) {
			BlockPowerConverter blockPowerConverter = (BlockPowerConverter) block;
			return blockPowerConverter.isGettingRedstone();
		}
		return false;
	}

	public void addWailaInfo(List<String> info) {
		info.add("Type: " + type);
		info.add("PowerSystem: " + powerSystem.getName());
		if (type.equals("Consumer")) {
			info.add("Consumer Disabled: " + powerSystem.consumerDissabled());
		} else if (type.equals("Producer")) {
			info.add("Producer Disabled: " + powerSystem.producerDissabled());
		}
	}

	@Override
	public void getTileInfo(List<IChatComponent> info, ForgeDirection side, EntityPlayer player, boolean debug) {
		info.add(new ChatComponentText("Is getting Redstone: " + isGettingRedstone()));

	}
}
