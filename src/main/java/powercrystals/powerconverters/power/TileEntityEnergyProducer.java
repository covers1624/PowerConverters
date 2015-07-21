package powercrystals.powerconverters.power;

import powercrystals.powerconverters.power.PowerSystemRegistry.PowerSystem;

public abstract class TileEntityEnergyProducer<T> extends TileEntityBridgeComponent<T> {
	public TileEntityEnergyProducer(PowerSystem powerSystem, int voltageNameIndex, Class<T> adjacentClass) {
		super(powerSystem, voltageNameIndex, adjacentClass);
	}

	public abstract double produceEnergy(double energy);
}
