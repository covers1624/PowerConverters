package covers1624.powerconverters.tile.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import covers1624.powerconverters.api.bridge.BridgeSideData;
import covers1624.powerconverters.handler.ConfigurationHandler;
import covers1624.powerconverters.net.EnergyBridgeSyncPacket;
import covers1624.powerconverters.util.BlockPosition;
import covers1624.powerconverters.util.INeighboorUpdateTile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityEnergyBridge extends TileEntity implements INeighboorUpdateTile {
	private double _energyStored;
	private double _energyStoredMax = ConfigurationHandler.bridgeBufferSize;
	private double _energyScaledClient;

	private double _energyStoredLast;
	private boolean _isInputLimited;

	private Map<ForgeDirection, TileEntityEnergyProducer<?>> _producerTiles;
	private Map<ForgeDirection, BridgeSideData> clientSideData;
	private Map<ForgeDirection, Double> _producerOutputRates;

	private boolean _initialized;

	public TileEntityEnergyBridge() {
		_producerTiles = new HashMap<ForgeDirection, TileEntityEnergyProducer<?>>();
		clientSideData = new HashMap<ForgeDirection, BridgeSideData>();
		_producerOutputRates = new HashMap<ForgeDirection, Double>();
		for (ForgeDirection d : ForgeDirection.VALID_DIRECTIONS) {
			clientSideData.put(d, new BridgeSideData());
			_producerOutputRates.put(d, 0D);
		}
	}

	public double getEnergyStored() {
		return _energyStored;
	}

	public double getEnergyStoredMax() {
		return _energyStoredMax;
	}

	public double storeEnergy(double energy) {
		double toStore = Math.min(energy, _energyStoredMax - _energyStored);
		_energyStored += toStore;
		return energy - toStore;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();

		if (!_initialized) {
			onNeighboorChanged();
			_initialized = true;
		}

		if (!worldObj.isRemote) {
			double energyRemaining = Math.min(_energyStored, _energyStoredMax);
			double energyNotProduced = 0;
			for (Entry<ForgeDirection, TileEntityEnergyProducer<?>> prod : _producerTiles.entrySet()) {
				if (!prod.getValue().isGettingRedstone()) {
					if (energyRemaining > 0) {
						energyNotProduced = prod.getValue().produceEnergy(energyRemaining);
						if (energyNotProduced > energyRemaining) {
							energyNotProduced = energyRemaining;
						}
						_producerOutputRates.put(prod.getKey(), (energyRemaining - energyNotProduced) / prod.getValue().getPowerSystem().getScaleAmmount());
						energyRemaining = energyNotProduced;
					} else {
						prod.getValue().produceEnergy(0);
						_producerOutputRates.put(prod.getKey(), 0D);
					}
				}
			}
			_energyStored = Math.max(0, energyRemaining);

			if ((_energyStored == _energyStoredLast && _energyStored == _energyStoredMax) || _energyStored > _energyStoredLast) {
				_isInputLimited = false;
			} else {
				_isInputLimited = true;
			}

			_energyStoredLast = _energyStored;
		}
	}

	@Override
	public void onNeighboorChanged() {
		Map<ForgeDirection, TileEntityEnergyProducer<?>> producerTiles = new HashMap<ForgeDirection, TileEntityEnergyProducer<?>>();
		for (ForgeDirection d : ForgeDirection.VALID_DIRECTIONS) {
			BlockPosition p = new BlockPosition(this);
			p.orientation = d;
			p.moveForwards(1);
			TileEntity te = worldObj.getTileEntity(p.x, p.y, p.z);
			if (te != null && te instanceof TileEntityEnergyProducer) {
				producerTiles.put(d, (TileEntityEnergyProducer<?>) te);
			}
		}
		_producerTiles = producerTiles;
	}

	public BridgeSideData getDataForSide(ForgeDirection dir) {
		if (!worldObj.isRemote) {
			BridgeSideData d = new BridgeSideData();
			BlockPosition p = new BlockPosition(this);
			p.orientation = dir;
			p.moveForwards(1);

			TileEntity te = worldObj.getTileEntity(p.x, p.y, p.z);
			if (te != null && te instanceof TileEntityBridgeComponent) {
				if (te instanceof TileEntityEnergyConsumer) {
					d.isConsumer = true;
					d.outputRate = ((TileEntityEnergyConsumer<?>) te).getInputRate();
				}
				if (te instanceof TileEntityEnergyProducer) {
					d.isProducer = true;
					d.outputRate = _producerOutputRates.get(dir);
				}
				TileEntityBridgeComponent<?> c = (TileEntityBridgeComponent<?>) te;
				d.powerSystem = c.getPowerSystem();
				d.isConnected = c.isConnected();
				d.side = dir;
				d.voltageNameIndex = c.getVoltageIndex();
			}
			Block block = worldObj.getBlock(p.x, p.y, p.z);
			int meta = worldObj.getBlockMetadata(p.x, p.y, p.z);
			d.displayStack = new ItemStack(block, 1, meta);

			return d;
		} else {
			return clientSideData.get(dir);
		}
	}

	public BridgeSideData[] getClientData() {
		BridgeSideData[] data = new BridgeSideData[6];
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			data[dir.ordinal()] = clientSideData.get(dir);
		}
		return data;
	}

	public void setClientDataForSide(ForgeDirection dir, BridgeSideData data) {
		if (clientSideData.containsKey(dir)) {
			clientSideData.remove(dir);
		}
		clientSideData.put(dir, data);
	}

	public boolean isInputLimited() {
		return _isInputLimited;
	}

	@SideOnly(Side.CLIENT)
	public void setIsInputLimited(boolean isInputLimited) {
		_isInputLimited = isInputLimited;
	}

	public double getEnergyScaled() {
		if (worldObj.isRemote) {
			return _energyScaledClient;
		} else {
			return (120 * (_energyStored / _energyStoredMax));
		}
	}

	public void setEnergyScaled(double scaled) {
		_energyScaledClient = scaled;
	}

	public void addWailaInfo(List<String> info) {
	}

	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeToNBT(par1nbtTagCompound);
		par1nbtTagCompound.setDouble("energyStored", _energyStored);
	}

	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readFromNBT(par1nbtTagCompound);
		_energyStored = par1nbtTagCompound.getDouble("energyStored");
	}

	public EnergyBridgeSyncPacket getNetPacket() {
		BridgeSideData[] bridgeSideData = new BridgeSideData[6];
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			bridgeSideData[dir.ordinal()] = getDataForSide(dir);
		}

		NBTTagCompound tagCompound = new NBTTagCompound();
		for (int side = 0; side < 6; side++) {
			BridgeSideData data = bridgeSideData[side];
			NBTTagCompound tag = new NBTTagCompound();
			data.writeToNBT(tag);
			tagCompound.setTag(String.valueOf(side), tag);
		}
		tagCompound.setBoolean("InputLimited", isInputLimited());
		tagCompound.setDouble("Energy", getEnergyScaled());
		EnergyBridgeSyncPacket syncPacket = new EnergyBridgeSyncPacket(tagCompound, xCoord, yCoord, zCoord);
		return syncPacket;
	}
}
