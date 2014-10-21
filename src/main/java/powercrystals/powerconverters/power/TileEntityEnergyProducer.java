package powercrystals.powerconverters.power;

public abstract class TileEntityEnergyProducer<T> extends TileEntityBridgeComponent<T> {
	public TileEntityEnergyProducer(PowerSystem powerSystem, int voltageNameIndex, Class<T> adjacentClass) {
		super(powerSystem, voltageNameIndex, adjacentClass);
	}

	public abstract double produceEnergy(double energy);
}
