package covers1624.powerconverters.tile.main;

import java.util.Map.Entry;

import covers1624.powerconverters.api.registry.PowerSystemRegistry.PowerSystem;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityEnergyConsumer<T> extends TileEntityBridgeComponent<T> {
	public TileEntityEnergyConsumer(PowerSystem powerSystem, int voltageNameIndex, Class<T> adjacentClass) {
		super(powerSystem, voltageNameIndex, adjacentClass);
	}

	protected double storeEnergy(double energy) {
		for (Entry<ForgeDirection, TileEntityEnergyBridge> bridge : getBridges().entrySet()) {
			energy = bridge.getValue().storeEnergy(energy);
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
