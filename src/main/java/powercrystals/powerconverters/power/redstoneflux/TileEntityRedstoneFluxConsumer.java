package powercrystals.powerconverters.power.redstoneflux;

import net.minecraftforge.common.util.ForgeDirection;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.common.TileEntityEnergyBridge;
import powercrystals.powerconverters.init.PowerSystems;
import powercrystals.powerconverters.power.TileEntityEnergyConsumer;
import cofh.api.energy.IEnergyHandler;

public class TileEntityRedstoneFluxConsumer extends TileEntityEnergyConsumer<IEnergyHandler> implements IEnergyHandler {

	private double lastReceivedRF;

	public TileEntityRedstoneFluxConsumer() {
		super(PowerSystems.powerSystemRedstoneFlux, 0, IEnergyHandler.class);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
	}

	@Override
	public double getInputRate() {
		double last = lastReceivedRF;
		lastReceivedRF = 0;
		return last;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection arg0) {

		return true;
	}

	@Override
	public int extractEnergy(ForgeDirection arg0, int arg1, boolean arg2) {

		return 0;
	}

	@Override
	public int getEnergyStored(ForgeDirection arg0) {
		TileEntityEnergyBridge bridge = getFirstBridge();
		if (bridge == null)
			return 0;
		return (int) (bridge.getEnergyStored() / getPowerSystem().getInternalEnergyPerInput());
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection arg0) {
		TileEntityEnergyBridge bridge = getFirstBridge();
		if (bridge == null)
			return 0;
		return (int) (bridge.getEnergyStoredMax() / getPowerSystem().getInternalEnergyPerInput());
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int recieveMax, boolean simulate) {
		TileEntityEnergyBridge bridge = getFirstBridge();
		if (bridge == null)
			return 0;
		int RF = getPowerSystem().getInternalEnergyPerInput() * recieveMax;
		int recievedRF = (int) (RF - storeEnergy(RF));
		if (!simulate) {
			lastReceivedRF = (int) (recievedRF / getPowerSystem().getInternalEnergyPerInput());
			return (int)lastReceivedRF;
		}
		return (int) (recievedRF / getPowerSystem().getInternalEnergyPerInput());
	}

}
