package powercrystals.powerconverters.power.conduit;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.util.ForgeDirection;
import powercrystals.powerconverters.power.conduit.grid.IGridController;
import powercrystals.powerconverters.power.conduit.grid.INode;
import powercrystals.powerconverters.util.BlockPosition;
import powercrystals.powerconverters.util.IAdvancedLogTile;
import powercrystals.powerconverters.util.IUpdateTileWithCords;
import powercrystals.powerconverters.util.LogHelper;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;

public class TileEnergyConduit extends TileEntity implements INode, IEnergyHandler, IUpdateTileWithCords, IAdvancedLogTile {

	private IEnergyReceiver[] receiverCache = null;
	private IEnergyProvider[] providerCache = null;

	private boolean readFromNBT = false;// Seems to be what is used to check if it has been initialized.
	private boolean deadCache = false; // Used to re-check the adjacent Tiles.
	boolean isNode = false;
	int energyForGrid = 0;
	EnergyNetwork grid;

	@Override
	public boolean isNotValid() {
		return this.tileEntityInvalid;
	}

	@Override
	public void validate() {
		super.validate();
		deadCache = true;
		receiverCache = null;
		providerCache = null;
		// It is placed here so we only tick on the server side, as it is not registered on the client.
		if (worldObj.isRemote) {
			return;
		}
		EnergyNetwork.HANDLER.addConduitForTick(this);
	}

	@Override
	public void invalidate() {
		super.invalidate();
		if (grid != null) {
			grid.removeConduit(this);
			grid.storage.modifyEnergyStored(-energyForGrid);
			int c = 0;
			grid.regenerate();
			deadCache = true;
			grid = null;
		}
	}

	// Shim
	private void addCache(TileEntity tile) {
		if (tile == null) {
			return;
		}
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.yCoord;

		if (x < xCoord) {
			addCache(tile, 5);
		} else if (x > xCoord) {
			addCache(tile, 4);
		} else if (z < zCoord) {
			addCache(tile, 3);
		} else if (z > zCoord) {
			addCache(tile, 2);
		} else if (y < yCoord) {
			addCache(tile, 1);
		} else if (y > yCoord) {
			addCache(tile, 0);
		}
	}

	// Actual Method
	private void addCache(TileEntity tile, int side) {
		if (receiverCache != null) {
			receiverCache[side] = null;
		}
		if (providerCache != null) {
			providerCache[side] = null;
		}
		if (tile instanceof TileEnergyConduit) {

		} else if (tile instanceof IEnergyConnection) {// Make sure it is a IEnergyConnection.
			if (((IEnergyConnection) tile).canConnectEnergy(ForgeDirection.VALID_DIRECTIONS[side])) {// And that it can connect.
				if (tile instanceof IEnergyReceiver) {
					if (receiverCache == null) {
						receiverCache = new IEnergyReceiver[6];
					}
					receiverCache[side] = (IEnergyReceiver) tile;
				}
				if (tile instanceof IEnergyProvider) {
					if (providerCache == null) {
						providerCache = new IEnergyProvider[6];
					}
					providerCache[side] = (IEnergyProvider) tile;
				}
			}
		} // TODO IC2

	}

	private void reCache() {
		if (deadCache) {
			for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				addCache(BlockPosition.getAdjacentTileEntity(this, dir));
			}
			deadCache = false;
			// This method is only ever called from the same thread as the tick handler
			// so this method can be safely called *here* without worrying about threading
			updateInternalTypes(EnergyNetwork.HANDLER);
		}
	}

	private void incorporateTiles() {
		if (grid == null) {
			boolean hasGrid = false;
			for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				if (readFromNBT)
					continue;
				if (BlockPosition.blockExists(this, dir)) {
					TileEnergyConduit pipe = BlockPosition.getAdjacentTileEntity(this, dir, TileEnergyConduit.class);
					if (pipe != null) {
						if (pipe.grid != null && pipe.canInterface(this, dir)) {
							if (hasGrid) {
								pipe.grid.mergeGrid(grid);
							} else {
								pipe.grid.addConduit(this);
								hasGrid = grid != null;
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void firstTick(IGridController grid) {
		LogHelper.info("First Grid Tick");
		if (worldObj == null || worldObj.isRemote || grid != EnergyNetwork.HANDLER) {
			return;
		}
		if (this.grid == null) {
			incorporateTiles();
			setGrid(new EnergyNetwork(this));
		}
		readFromNBT = true;
		reCache();
		markDirty();
	}

	/**
	 * Seems to be used to check if the conduit is a node.
	 */
	@Override
	public void updateInternalTypes(IGridController grid) {
		if (deadCache || grid != EnergyNetwork.HANDLER) {
			return;
		}
		isNode = true; // TODO TEMP!!!
		if (this.grid != null) {
			this.grid.addConduit(this);
		}
		// Packet to send updates regarding grid.
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		// TODO Auto-generated method stub
		return 0;
	}

	// Called by the grid to pull energy from adjacent blocks to avoid bottle necks.
	public void extract(ForgeDirection forgeDirection, EnergyStorage tempStorage) {
		// TODO Auto-generated method stub

	}

	// Called by the grid to transfer energy from the grid to adjacent tiles.
	public int transfer(ForgeDirection forgeDirection, int sideDistribute) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Sets the EnergyNetwork the cable is located on.
	 * 
	 * @param energyNetwork
	 */
	public void setGrid(EnergyNetwork energyNetwork) {
		grid = energyNetwork;

	}

	public boolean canInterface(TileEnergyConduit teConduit, ForgeDirection dir) {
		if (receiverCache[dir.ordinal()] != null || providerCache[dir.ordinal()] != null) {
			return true;
		}
		return false;
	}

	@Override
	public void onNeighboorChanged(int x, int y, int z) {
		if (worldObj.isRemote | deadCache) {
			return;
		}
		TileEntity tile = worldObj.getTileEntity(x, y, z);

		if (x < xCoord) {
			addCache(tile, 5);
		} else if (x > xCoord) {
			addCache(tile, 4);
		} else if (z < zCoord) {
			addCache(tile, 3);
		} else if (z > zCoord) {
			addCache(tile, 2);
		} else if (y < yCoord) {
			addCache(tile, 1);
		} else if (y > yCoord) {
			addCache(tile, 0);
		}
	}

	@Override
	public void getTileInfo(List<IChatComponent> info, ForgeDirection side, EntityPlayer player, boolean debug) {
		info.add(text("-Energy-"));
		if (grid != null) {
			/* TODO: advanced monitoring */
			if (debug) {
				if (isNode) {
					info.add(text("Throughput All: " + grid.distribution));
					info.add(text("Throughput Side: " + grid.distributionSide));
				}
			}

			if (!debug) {
				float sat = 0;
				if (grid.getNodeCount() != 0)
					sat = (float) (Math.ceil(grid.storage.getEnergyStored() / (float) grid.storage.getMaxEnergyStored() * 1000f) / 10f);
				info.add(text("Saturation: " + sat));
			}
		} else if (!debug)
			info.add(text("Null Grid"));
		if (debug) {
			if (grid != null) {
				info.add(text("Grid:" + grid));
				info.add(text("Conduits: " + grid.getConduitCount() + ", Nodes: " + grid.getNodeCount()));
				info.add(text("Grid Max: " + grid.storage.getMaxEnergyStored() + ", Grid Cur: " + grid.storage.getEnergyStored()));
				// info.add(text("Caches: (RF, EU):({" + Arrays.toString(receiverCache) + "," + Arrays.toString(providerCache) + "}, " + ic2Cache + ")"));
			} else {
				info.add(text("Null Grid"));
			}
			info.add(text("Node: " + isNode + ", Energy: " + energyForGrid));
			return;
		}
	}

	public IChatComponent text(String str) {
		return new ChatComponentText(str);
	}

}
