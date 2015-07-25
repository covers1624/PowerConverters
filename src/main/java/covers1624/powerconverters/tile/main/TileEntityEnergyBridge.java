package covers1624.powerconverters.tile.main;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import covers1624.powerconverters.api.bridge.BridgeSideData;
import covers1624.powerconverters.handler.ConfigurationHandler;
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
	private Map<ForgeDirection, BridgeSideData> _clientSideData;
	private Map<ForgeDirection, Double> _producerOutputRates;

	private boolean _initialized;

	public TileEntityEnergyBridge() {
		_producerTiles = new HashMap<ForgeDirection, TileEntityEnergyProducer<?>>();
		_clientSideData = new HashMap<ForgeDirection, BridgeSideData>();
		_producerOutputRates = new HashMap<ForgeDirection, Double>();
		for (ForgeDirection d : ForgeDirection.VALID_DIRECTIONS) {
			_clientSideData.put(d, new BridgeSideData());
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

			return d;
		} else {
			return _clientSideData.get(dir);
		}
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

	public void setEnergyScaled(int scaled) {
		_energyScaledClient = scaled;
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
}
