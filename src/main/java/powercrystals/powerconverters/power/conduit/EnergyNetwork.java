package powercrystals.powerconverters.power.conduit;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import powercrystals.powerconverters.power.conduit.grid.GridTickHandler;
import powercrystals.powerconverters.power.conduit.grid.IGrid;
import powercrystals.powerconverters.util.BlockPosition;
import cofh.api.energy.EnergyStorage;
import covers1624.repack.cofh.lib.util.ArrayHashList;
import covers1624.repack.cofh.lib.util.LinkedHashList;

public class EnergyNetwork implements IGrid {

	public static final int TRANSFER_RATE = 1000;
	public static final int STORAGE = TRANSFER_RATE * 6;
	static final GridTickHandler<EnergyNetwork, TileEnergyConduit> HANDLER = GridTickHandler.energy;

	private ArrayHashList<TileEnergyConduit> nodeSet = new ArrayHashList<TileEnergyConduit>();
	LinkedHashList<TileEnergyConduit> conduitSet;
	private TileEnergyConduit master;
	private int overflowSelector;
	private boolean regenerating = false;
	EnergyStorage storage = new EnergyStorage(480, 80);

	public int distribution;
	public int distributionSide;

	protected EnergyNetwork() {
		storage.setCapacity(STORAGE);
		storage.setMaxTransfer(TRANSFER_RATE);
	}

	public EnergyNetwork(TileEnergyConduit base) {
		this();
		conduitSet = new LinkedHashList<TileEnergyConduit>();
		regenerating = true;
		addConduit(base);
		regenerating = false;
	}

	public int getNodeShare(TileEnergyConduit conduit) {
		int size = nodeSet.size();
		if (size <= 1) {
			return storage.getEnergyStored();
		}
		int amt = 0;
		if (master == conduit) {
			amt = storage.getEnergyStored() % size;
		}
		return amt + storage.getEnergyStored() / size;
	}

	public void addConduit(TileEnergyConduit conduit) {
		if (conduitSet.add(conduit)) {
			if (!conduitAdded(conduit)) {
				return;
			}
			if (conduit.isNode) {
				if (nodeSet.add(conduit)) {
					nodeAdded(conduit);
				}
			} else if (!nodeSet.isEmpty()) {
				int share = getNodeShare(conduit);
				if (nodeSet.remove(conduit)) {
					conduit.energyForGrid = storage.extractEnergy(share, false);
					nodeRemoved(conduit);

				}
			}
		}
	}

	public void removeConduit(TileEnergyConduit conduit) {
		conduitSet.remove(conduit);
		if (!nodeSet.isEmpty()) {
			int share = getNodeShare(conduit);
			if (nodeSet.remove(conduit)) {
				conduit.energyForGrid = storage.extractEnergy(share, false);
				nodeRemoved(conduit);
			}
		}
	}

	public void regenerate() {
		regenerating = true;
		HANDLER.regenerateGrid(this);
	}

	public boolean isRegenerating() {
		return regenerating;
	}

	@Override
	public void doGridPreUpdate() {
		if (regenerating) {
			return;
		}
		if (nodeSet.isEmpty()) {
			HANDLER.removeGrid(this);
			return;
		}
		EnergyStorage tempStorage = storage;
		if (tempStorage.getEnergyStored() >= tempStorage.getMaxEnergyStored()) {
			return;
		}
		ForgeDirection[] directions = ForgeDirection.VALID_DIRECTIONS;
		for (TileEnergyConduit conduit : nodeSet) {
			for (int i = 6; i-- > 0;) {
				conduit.extract(directions[i], tempStorage);
			}
		}
	}

	@Override
	public void doGridUpdate() {
		if (regenerating)
			return;
		if (nodeSet.isEmpty()) {
			HANDLER.removeGrid(this);
			return;
		}
		EnergyStorage storage = this.storage;
		if (storage.getEnergyStored() <= 0)
			return;
		ForgeDirection[] directions = ForgeDirection.VALID_DIRECTIONS;
		int size = nodeSet.size();
		int toDistribute = storage.getEnergyStored() / size;
		int sideDistribute = toDistribute / 6;

		distribution = toDistribute;
		distributionSide = sideDistribute;

		int overflow = overflowSelector = (overflowSelector + 1) % size;
		TileEnergyConduit master = nodeSet.get(overflow);

		if (sideDistribute > 0)
			for (TileEnergyConduit cond : nodeSet)
				if (cond != master) {
					int e = 0;
					for (int i = 6; i-- > 0;)
						e += cond.transfer(directions[i], sideDistribute);
					if (e > 0)
						storage.modifyEnergyStored(-e);
				}

		toDistribute += storage.getEnergyStored() % size;
		sideDistribute = toDistribute / 6;

		if (sideDistribute > 0) {
			int e = 0;
			for (int i = 6; i-- > 0;)
				e += master.transfer(directions[i], sideDistribute);
			if (e > 0)
				storage.modifyEnergyStored(-e);
		} else if (toDistribute > 0) {
			int e = 0;
			for (int i = 6; i-- > 0 && e < toDistribute;)
				e += master.transfer(directions[i], toDistribute - e);
			if (e > 0)
				storage.modifyEnergyStored(-e);
		}

	}

	@Override
	public void markSweep() {
		destroyGrid();
		if (conduitSet.isEmpty()) {
			return;
		}
		TileEnergyConduit main = conduitSet.poke();
		LinkedHashList<TileEnergyConduit> oldSet = conduitSet;
		nodeSet.clear();
		conduitSet = new LinkedHashList<TileEnergyConduit>(Math.min(oldSet.size() / 6, 5));

		LinkedHashList<TileEnergyConduit> toCheck = new LinkedHashList<TileEnergyConduit>();
		LinkedHashList<TileEnergyConduit> checked = new LinkedHashList<TileEnergyConduit>();
		BlockPosition bp = new BlockPosition(0, 0, 0);
		ForgeDirection[] dir = ForgeDirection.VALID_DIRECTIONS;
		toCheck.add(main);
		checked.add(main);

		while (!toCheck.isEmpty()) {
			main = toCheck.shift();
			addConduit(main);
			World world = main.getWorldObj();
			for (int i = 6; i-- > 0;) {
				bp.x = main.xCoord;
				bp.y = main.yCoord;
				bp.z = main.zCoord;
				bp.step(dir[i]);
				if (world.blockExists(bp.x, bp.y, bp.z)) {
					TileEntity te = bp.getTileEntity(world);
					if (te instanceof TileEnergyConduit) {
						TileEnergyConduit teConduit = (TileEnergyConduit) te;
						if (main.canInterface(teConduit, dir[i ^ 1]) && checked.add(teConduit)) {
							toCheck.add(teConduit);
						}
					}
				}
			}
			oldSet.remove(main);
		}
		if (!oldSet.isEmpty()) {
			EnergyNetwork newGrid = new EnergyNetwork();
			newGrid.conduitSet = oldSet;
			newGrid.regenerating = true;
			newGrid.markSweep();
		}
		if (nodeSet.isEmpty()) {
			HANDLER.removeGrid(this);
		} else {
			HANDLER.addGrid(this);
		}
		rebalanceGrid();
		regenerating = false;
	}

	public void destroyGrid() {
		master = null;
		regenerating = true;
		for (TileEnergyConduit currentConduit : nodeSet) {
			destroyNode(currentConduit);
		}
		for (TileEnergyConduit currentConduit : conduitSet) {
			destroyConduit(currentConduit);
		}
		HANDLER.removeGrid(this);
	}

	public void destroyNode(TileEnergyConduit conduit) {
		conduit.energyForGrid = getNodeShare(conduit);
		conduit.grid = null;
	}

	public void destroyConduit(TileEnergyConduit conduit) {
		conduit.grid = null;
	}

	public boolean canMergeGrid(EnergyNetwork otherGrid) {
		if (otherGrid == null)
			return false;
		return true;
	}

	public void mergeGrid(EnergyNetwork grid) {
		if (grid == this)
			return;
		boolean r = regenerating || grid.regenerating;
		grid.destroyGrid();
		if (!regenerating & r) {
			regenerate();
		}
		regenerating = true;
		for (TileEnergyConduit conduit : grid.conduitSet) {
			addConduit(conduit);
		}
		regenerating = r;
		grid.conduitSet.clear();
		grid.nodeSet.clear();
	}

	public void nodeAdded(TileEnergyConduit conduit) {
		if (master == null) {
			master = conduit;
			HANDLER.addGrid(this);
		}
		rebalanceGrid();
		storage.modifyEnergyStored(conduit.energyForGrid);
	}

	public void nodeRemoved(TileEnergyConduit conduit) {
		rebalanceGrid();
		if (conduit == master) {
			if (nodeSet.isEmpty()) {
				master = null;
				HANDLER.removeGrid(this);
			} else {
				master = nodeSet.get(0);
			}
		}
	}

	public boolean conduitAdded(TileEnergyConduit conduit) {
		if (conduit.grid != null) {
			if (conduit.grid != this) {
				conduitSet.remove(conduit);
				if (canMergeGrid(conduit.grid)) {
					mergeGrid(conduit.grid);
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			conduit.setGrid(this);
		}
		return true;
	}

	public void rebalanceGrid() {
		storage.setCapacity(nodeSet.size() * STORAGE);
	}

	public int getConduitCount() {
		return conduitSet.size();
	}

	public int getNodeCount() {
		return nodeSet.size();
	}

}
