package covers1624.powerconverters.tile.main;

import covers1624.powerconverters.registry.PowerSystemRegistry.PowerSystem;

public abstract class TileEntityEnergyProducer<T> extends TileEntityBridgeComponent<T> {
    public TileEntityEnergyProducer(PowerSystem powerSystem, int voltageNameIndex, Class<T> adjacentClass) {
        super(powerSystem, voltageNameIndex, adjacentClass);
        type = "Producer";
    }

    public abstract double produceEnergy(double energy);
}
