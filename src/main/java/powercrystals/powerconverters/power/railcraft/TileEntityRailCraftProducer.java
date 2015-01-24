package powercrystals.powerconverters.power.railcraft;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.handler.ConfigurationHandler;
import powercrystals.powerconverters.init.PowerSystems;
import powercrystals.powerconverters.power.TileEntityEnergyProducer;
import powercrystals.powerconverters.util.BlockPosition;
import powercrystals.powerconverters.util.LogHelper;

public class TileEntityRailCraftProducer extends TileEntityEnergyProducer<IFluidHandler> implements IFluidHandler {
	private FluidTank _tank;

	public TileEntityRailCraftProducer() {
		super(PowerSystems.powerSystemSteam, 0, IFluidHandler.class);
		_tank = new FluidTank(1 * FluidContainerRegistry.BUCKET_VOLUME);
	}

	@Override
	public double produceEnergy(double energy) {
		energy = energy / PowerSystems.powerSystemSteam.getInternalEnergyPerOutput();
		for (int i = 0; i < 6; i++) {
			BlockPosition bp = new BlockPosition(this);
			bp.orientation = ForgeDirection.getOrientation(i);
			bp.moveForwards(1);
			TileEntity te = worldObj.getTileEntity(bp.x, bp.y, bp.z);

			if (te != null && te instanceof IFluidHandler) {
				energy -= ((IFluidHandler) te).fill(bp.orientation.getOpposite(), new FluidStack(PowerConverterCore.steamId, (int) energy), true);
			}
			if (energy <= 0) {
				return 0;
			}
		}
		
		return energy * PowerSystems.powerSystemSteam.getInternalEnergyPerOutput();

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
