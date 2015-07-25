package covers1624.powerconverters.tile.steam;

import covers1624.powerconverters.PowerConverters;
import covers1624.powerconverters.init.PowerSystems;
import covers1624.powerconverters.tile.main.TileEntityEnergyProducer;
import covers1624.powerconverters.util.BlockPosition;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntitySteamProducer extends TileEntityEnergyProducer<IFluidHandler> implements IFluidHandler {
	private FluidTank _tank;

	public TileEntitySteamProducer() {
		super(PowerSystems.powerSystemSteam, 0, IFluidHandler.class);
		_tank = new FluidTank(1 * FluidContainerRegistry.BUCKET_VOLUME);
	}

	@Override
	public double produceEnergy(double energy) {
		if (PowerConverters.steamId == -1) {
			return energy;
		}
		energy = energy / PowerSystems.powerSystemSteam.getScaleAmmount();
		for (int i = 0; i < 6; i++) {
			BlockPosition bp = new BlockPosition(this);
			bp.orientation = ForgeDirection.getOrientation(i);
			bp.moveForwards(1);
			TileEntity te = worldObj.getTileEntity(bp.x, bp.y, bp.z);

			if (te != null && te instanceof IFluidHandler) {
				energy -= ((IFluidHandler) te).fill(bp.orientation.getOpposite(), new FluidStack(PowerConverters.steamId, (int) energy), true);
			}
			if (energy <= 0) {
				return 0;
			}
		}

		return energy * PowerSystems.powerSystemSteam.getScaleAmmount();

	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {

		return null;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {

		return new FluidTankInfo[] { _tank.getInfo() };
	}

}
