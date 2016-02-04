package covers1624.powerconverters.grid;

import covers1624.powerconverters.pipe.EnergyNetwork;
import covers1624.powerconverters.tile.conduit.TileEnergyConduit;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;

import java.util.LinkedHashSet;

public class GridTickHandler<G extends IGrid, N extends INode> {

	public static final GridTickHandler<EnergyNetwork, TileEnergyConduit> energy = new GridTickHandler<EnergyNetwork, TileEnergyConduit>("Energy");

	private LinkedHashSet<G> tickingGridsToRegenerate = new LinkedHashSet<G>();
	private LinkedHashSet<G> tickingGridsToAdd = new LinkedHashSet<G>();
	private LinkedHashSet<G> tickingGrids = new LinkedHashSet<G>();
	private LinkedHashSet<G> tickingGridsToRemove = new LinkedHashSet<G>();

	private LinkedHashSet<N> conduit = new LinkedHashSet<N>();
	private LinkedHashSet<N> conduitToAdd = new LinkedHashSet<N>();
	private LinkedHashSet<N> conduitToUpd = new LinkedHashSet<N>();

	private final String label;

	public GridTickHandler(String name) {
		label = "GridTickHandler[" + name + "]";
	}

	public void addGrid(G grid) {
		tickingGridsToAdd.add(grid);
		tickingGridsToRemove.remove(grid);
	}

	public void removeGrid(G grid) {
		tickingGridsToRemove.add(grid);
		tickingGridsToAdd.remove(grid);
	}

	public void regenerateGrid(G grid) {
		tickingGridsToRegenerate.add(grid);
	}

	public boolean isGridTicking(G grid) {
		return tickingGrids.contains(grid);
	}

	public void addConduitForTick(N node) {
		conduitToAdd.add(node);
	}

	public void addConduitForUpdate(N node) {
		conduitToUpd.add(node);
	}

	@SubscribeEvent
	public void tick(ServerTickEvent event) {
		if (event.phase == Phase.START) {
			tickStart();
		} else {
			tickEnd();
		}
	}

	public void tickStart() {
		// { Grids that have had significant conduits removed and need to rebuild/split
		if (!tickingGridsToRegenerate.isEmpty()) {
			synchronized (tickingGridsToRegenerate) {
				for (G grid : tickingGridsToRegenerate) {
					grid.markSweep();
				}
				tickingGridsToRegenerate.clear();
			}
		}
		// }

		// { Updating internal types of conduits
		// this pass is needed to handle issues with threading
		if (!conduitToUpd.isEmpty()) {
			synchronized (conduitToUpd) {
				conduit.addAll(conduitToUpd);
				conduitToUpd.clear();
			}
		}

		if (!conduit.isEmpty()) {
			N cond = null;
			try {
				for (N aConduit : conduit) {
					cond = aConduit;
					if (!cond.isNotValid()) {
						cond.updateInternalTypes(this);
					}
				}
				conduit.clear();
			} catch (Throwable e) {
				throw new RuntimeException("Crashing on conduit " + cond, e);
			}
		}
		// }

		// { Early update pass to extract energy from sources
		if (!tickingGrids.isEmpty()) {
			for (G grid : tickingGrids) {
				grid.doGridPreUpdate();
			}
		}
		// }
	}

	public void tickEnd() {
		// { Changes in what grids are being ticked
		if (!tickingGridsToRemove.isEmpty()) {
			synchronized (tickingGridsToRemove) {
				tickingGrids.removeAll(tickingGridsToRemove);
				tickingGridsToRemove.clear();
			}
		}

		if (!tickingGridsToAdd.isEmpty()) {
			synchronized (tickingGridsToAdd) {
				tickingGrids.addAll(tickingGridsToAdd);
				tickingGridsToAdd.clear();
			}
		}
		// }

		// { Ticking grids to transfer energy/etc.
		if (!tickingGrids.isEmpty()) {
			for (G grid : tickingGrids) {
				grid.doGridUpdate();
			}
		}
		// }

		// { Initial update tick for conduits added to the world
		if (!conduitToAdd.isEmpty()) {
			synchronized (conduitToAdd) {
				conduit.addAll(conduitToAdd);
				conduitToAdd.clear();
			}
		}

		if (!conduit.isEmpty()) {
			N cond = null;
			try {
				for (N aConduit : conduit) {
					cond = aConduit;
					if (!cond.isNotValid()) {
						cond.firstTick(this);
					}
				}
				conduit.clear();
			} catch (Throwable e) {
				throw new RuntimeException("Crashing on conduit " + cond, e);
			}
		}
		// }
	}

	@Override
	public String toString() {
		return label + "@" + hashCode();
	}

}
