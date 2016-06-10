package covers1624.powerconverters.tile.main;

import covers1624.lib.util.BlockPosition;
import covers1624.powerconverters.api.bridge.BridgeSideData;
import covers1624.powerconverters.handler.ConfigurationHandler;
import covers1624.powerconverters.network.packets.EnergyBridgeSyncPacket;
import covers1624.powerconverters.util.INeighboorUpdateTile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TileEntityEnergyBridge extends TileEntity implements INeighboorUpdateTile {
    private double energyStored;
    private double energyStoredMax = ConfigurationHandler.bridgeBufferSize;
    private double energyScaledClient;

    private double energyStoredLast;
    private boolean isInputLimited;

    private Map<ForgeDirection, TileEntityEnergyProducer<?>> producerTiles;
    private Map<ForgeDirection, BridgeSideData> clientSideData;
    private Map<ForgeDirection, Double> producerOutputRates;

    private boolean initialized;

    public TileEntityEnergyBridge() {
        producerTiles = new HashMap<ForgeDirection, TileEntityEnergyProducer<?>>();
        clientSideData = new HashMap<ForgeDirection, BridgeSideData>();
        producerOutputRates = new HashMap<ForgeDirection, Double>();
        for (ForgeDirection d : ForgeDirection.VALID_DIRECTIONS) {
            clientSideData.put(d, new BridgeSideData());
            producerOutputRates.put(d, 0D);
        }
    }

    public double getEnergyStored() {
        return energyStored;
    }

    public double getEnergyStoredMax() {
        return energyStoredMax;
    }

    public double storeEnergy(double energy, boolean simulate) {
        double toStore = Math.min(energy, energyStoredMax - energyStored);
        if (simulate) {
            return energy - toStore;
        }
        energyStored += toStore;
        return energy - toStore;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (!initialized) {
            onNeighboorChanged();
            initialized = true;
        }

        if (!worldObj.isRemote) {
            double energyRemaining = Math.min(energyStored, energyStoredMax);
            double energyNotProduced = 0;
            for (Entry<ForgeDirection, TileEntityEnergyProducer<?>> prod : producerTiles.entrySet()) {
                if (!prod.getValue().isGettingRedstone()) {
                    if (energyRemaining > 0) {
                        energyNotProduced = prod.getValue().produceEnergy(energyRemaining);
                        if (energyNotProduced > energyRemaining) {
                            energyNotProduced = energyRemaining;
                        }
                        producerOutputRates.put(prod.getKey(), (energyRemaining - energyNotProduced) / prod.getValue().getPowerSystem().getScaleAmmount());
                        energyRemaining = energyNotProduced;
                    } else {
                        prod.getValue().produceEnergy(0);
                        producerOutputRates.put(prod.getKey(), 0D);
                    }
                }
            }
            energyStored = Math.max(0, energyRemaining);

            if ((energyStored == energyStoredLast && energyStored == energyStoredMax) || energyStored > energyStoredLast) {
                isInputLimited = false;
            } else {
                isInputLimited = true;
            }

            energyStoredLast = energyStored;
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
        this.producerTiles = producerTiles;
    }

    public BridgeSideData getDataForSide(ForgeDirection dir) {
        if (!worldObj.isRemote) {
            BridgeSideData data = new BridgeSideData();
            BlockPosition pos = new BlockPosition(this);
            pos.orientation = dir;
            pos.moveForwards(1);

            TileEntity tile = worldObj.getTileEntity(pos.x, pos.y, pos.z);
            if (tile != null && tile instanceof TileEntityBridgeComponent) {
                if (tile instanceof TileEntityEnergyConsumer) {
                    data.isConsumer = true;
                    data.outputRate = ((TileEntityEnergyConsumer<?>) tile).getInputRate();
                }
                if (tile instanceof TileEntityEnergyProducer) {
                    data.isProducer = true;
                    data.outputRate = producerOutputRates.get(dir);
                }
                TileEntityBridgeComponent<?> component = (TileEntityBridgeComponent<?>) tile;
                data.powerSystem = component.getPowerSystem();
                data.isConnected = component.isConnected();
                data.side = dir;
                data.voltageNameIndex = component.getVoltageIndex();
            }
            // Block block = worldObj.getBlock(p.x, p.y, p.z);
            // int meta = worldObj.getBlockMetadata(p.x, p.y, p.z);
            // d.displayStack = new ItemStack(block, 1, meta);

            return data;
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
        return isInputLimited;
    }

    @SideOnly(Side.CLIENT)
    public void setIsInputLimited(boolean isInputLimited) {
        this.isInputLimited = isInputLimited;
    }

    public double getEnergyScaled() {
        if (worldObj.isRemote) {
            return energyScaledClient;
        } else {
            return (120 * (energyStored / energyStoredMax));
        }
    }

    public void setEnergyScaled(double scaled) {
        energyScaledClient = scaled;
    }

    public void addWailaInfo(List<String> info) {
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setDouble("energyStored", energyStored);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        energyStored = nbtTagCompound.getDouble("energyStored");
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
        return new EnergyBridgeSyncPacket(tagCompound, xCoord, yCoord, zCoord);
    }
}
