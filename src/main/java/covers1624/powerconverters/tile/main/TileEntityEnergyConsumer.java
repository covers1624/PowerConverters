package covers1624.powerconverters.tile.main;

import covers1624.powerconverters.registry.PowerSystemRegistry.PowerSystem;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Map.Entry;

public abstract class TileEntityEnergyConsumer<T> extends TileEntityBridgeComponent<T> {
	public TileEntityEnergyConsumer(PowerSystem powerSystem, int voltageNameIndex, Class<T> adjacentClass) {
		super(powerSystem, voltageNameIndex, adjacentClass);
		type = "Consumer";
	}

	protected double storeEnergy(double energy, boolean simulate) {
		for (Entry<ForgeDirection, TileEntityEnergyBridge> bridge : getBridges().entrySet()) {
			if (!isGettingRedstone()) {
				energy = bridge.getValue().storeEnergy(energy, simulate);
			}
			if (energy <= 0) {
				return 0;
			}
		}
		return energy;
	}

	protected double getTotalEnergyDemand() {

		double demand = 0;

		for (Entry<ForgeDirection, TileEntityEnergyBridge> bridge : getBridges().entrySet()) {
			demand += (bridge.getValue().getEnergyStoredMax() - bridge.getValue().getEnergyStored());
		}

		return demand;
	}

	public abstract double getInputRate();
}
