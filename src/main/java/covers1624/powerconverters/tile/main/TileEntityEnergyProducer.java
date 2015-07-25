package covers1624.powerconverters.tile.main;

import covers1624.powerconverters.api.registry.PowerSystemRegistry.PowerSystem;

public abstract class TileEntityEnergyProducer<T> extends TileEntityBridgeComponent<T> {
	public TileEntityEnergyProducer(PowerSystem powerSystem, int voltageNameIndex, Class<T> adjacentClass) {
		super(powerSystem, voltageNameIndex, adjacentClass);
	}

	public abstract double produceEnergy(double energy);
}
