package covers1624.powerconverters.tile.redstoneflux;

import cofh.api.energy.IEnergyReceiver;
import covers1624.powerconverters.handler.ConfigurationHandler;
import covers1624.powerconverters.init.PowerSystems;
import covers1624.powerconverters.tile.main.TileEntityEnergyBridge;
import covers1624.powerconverters.tile.main.TileEntityEnergyConsumer;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityRedstoneFluxConsumer extends TileEntityEnergyConsumer<IEnergyReceiver> implements IEnergyReceiver {

    private double lastReceivedRF;

    public TileEntityRedstoneFluxConsumer() {
        super(PowerSystems.powerSystemRedstoneFlux, 0, IEnergyReceiver.class);
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
        return !ConfigurationHandler.disableRFConsumer;
    }

    @Override
    public int getEnergyStored(ForgeDirection arg0) {
        TileEntityEnergyBridge bridge = getFirstBridge();
        if (bridge == null) {
            return 0;
        }
        return (int) (bridge.getEnergyStored() / getPowerSystem().getScaleAmmount());
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection arg0) {
        TileEntityEnergyBridge bridge = getFirstBridge();
        if (bridge == null) {
            return 0;
        }
        return (int) (bridge.getEnergyStoredMax() / getPowerSystem().getScaleAmmount());
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int receiveMax, boolean simulate) {
        if (getFirstBridge() == null) {
            return 0;
        }

        int actualRF = getPowerSystem().getScaleAmmount() * receiveMax;
        int rfNotStored = (int) (actualRF - storeEnergy(actualRF, simulate));
        return rfNotStored / getPowerSystem().getScaleAmmount();
    }

}
