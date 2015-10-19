package covers1624.powerconverters.tile.steam;

import covers1624.powerconverters.PowerConverters;
import covers1624.powerconverters.handler.ConfigurationHandler;
import covers1624.powerconverters.init.PowerSystems;
import covers1624.powerconverters.tile.main.TileEntityEnergyConsumer;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public class TileEntitySteamConsumer extends TileEntityEnergyConsumer<IFluidHandler> implements IFluidHandler {
	private FluidTank _steamTank;
	private int _mBLastTick;

	public TileEntitySteamConsumer() {
		super(PowerSystems.powerSystemSteam, 0, IFluidHandler.class);
		_steamTank = new FluidTank(10000);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();

		if (_steamTank != null && _steamTank.getFluid() != null) {
			int amount = _steamTank.getFluid().amount;
			double energy = amount * PowerSystems.powerSystemSteam.getScaleAmmount();
			energy = storeEnergy(energy, false);
			int toDrain = amount - (int) (energy / PowerSystems.powerSystemSteam.getScaleAmmount());
			_steamTank.drain(toDrain, true);
			_mBLastTick = toDrain;
		} else {
			_mBLastTick = 0;
		}
	}

	@Override
	public int getVoltageIndex() {
		return 0;
	}

	@Override
	public double getInputRate() {
		return _mBLastTick;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (resource == null || resource.getFluidID() != PowerConverters.steamId || PowerConverters.steamId == -1) {
			return 0;
		}
		return _steamTank.fill(resource, doFill);

	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return fluid != null && fluid.getID() == PowerConverters.steamId && !ConfigurationHandler.dissableSteamConsumer;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[] { _steamTank.getInfo() };
	}
}
