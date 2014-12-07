package powercrystals.powerconverters.power.railcraft;

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

public class TileEntityRailCraftProducer extends TileEntityEnergyProducer<IFluidHandler> implements IFluidHandler {
	private FluidTank _tank;

	public TileEntityRailCraftProducer() {
		super(PowerSystems.powerSystemSteam, 0, IFluidHandler.class);
		_tank = new FluidTank(1 * FluidContainerRegistry.BUCKET_VOLUME);
	}

	@Override
	public double produceEnergy(double energy) {
		double steam = Math.min(energy / PowerSystems.powerSystemSteam.getInternalEnergyPerOutput(), ConfigurationHandler.throttleSteamProducer);
		/*
		 * for(int i = 0; i < 6; i++) { BlockPosition bp = new
		 * BlockPosition(this); bp.orientation =
		 * ForgeDirection.getOrientation(i); bp.moveForwards(1); TileEntity te =
		 * worldObj.getBlockTileEntity(bp.x, bp.y, bp.z);
		 * 
		 * if(te != null && te instanceof IFluidHandler) { steam -=
		 * ((IFluidHandler)te).fill(bp.orientation.getOpposite(), new
		 * FluidStack(PowerConverterCore.steamId, steam), true); } if(steam <=
		 * 0) { return 0; } }
		 */

		// return steam *
		// PowerConverterCore.powerSystemSteam.getInternalEnergyPerOutput();

		return _tank.fill(new FluidStack(PowerConverterCore.steamId, (int)steam), true) * PowerSystems.powerSystemSteam.getInternalEnergyPerOutput();
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		int steamID = PowerConverterCore.steamId;

		if (resource == null || resource.fluidID != steamID)
			return null;
		return _tank.drain(resource.amount, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {

		return _tank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return fluid != null && fluid.getID() == PowerConverterCore.steamId;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {

		return new FluidTankInfo[] { _tank.getInfo() };
	}

}
