package covers1624.powerconverters.tile.redstoneflux;

import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyHandler;
import covers1624.powerconverters.init.PowerSystems;
import covers1624.powerconverters.tile.main.TileEntityEnergyBridge;
import covers1624.powerconverters.tile.main.TileEntityEnergyConsumer;

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
		return (int) (bridge.getEnergyStored() / getPowerSystem().getScaleAmmount());
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection arg0) {
		TileEntityEnergyBridge bridge = getFirstBridge();
		if (bridge == null)
			return 0;
		return (int) (bridge.getEnergyStoredMax() / getPowerSystem().getScaleAmmount());
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int recieveMax, boolean simulate) {
		TileEntityEnergyBridge bridge = getFirstBridge();
		if (bridge == null)
			return 0;
		int RF = getPowerSystem().getScaleAmmount() * recieveMax;
		int recievedRF = (int) (RF - storeEnergy(RF));
		if (!simulate) {
			lastReceivedRF = recievedRF / getPowerSystem().getScaleAmmount();
			return (int) lastReceivedRF;
		}
		return recievedRF / getPowerSystem().getScaleAmmount();
	}

}
