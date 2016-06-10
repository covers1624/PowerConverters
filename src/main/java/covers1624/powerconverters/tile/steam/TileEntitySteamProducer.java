package covers1624.powerconverters.tile.steam;

import covers1624.lib.util.BlockPosition;
import covers1624.powerconverters.PowerConverters;
import covers1624.powerconverters.handler.ConfigurationHandler;
import covers1624.powerconverters.init.PowerSystems;
import covers1624.powerconverters.tile.main.TileEntityEnergyProducer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public class TileEntitySteamProducer extends TileEntityEnergyProducer<IFluidHandler> implements IFluidHandler {
    private FluidTank tank;

    public TileEntitySteamProducer() {
        super(PowerSystems.powerSystemSteam, 0, IFluidHandler.class);
        tank = new FluidTank(10000);
    }

    @Override
    public double produceEnergy(double energy) {
        if (PowerConverters.steamId == -1 || ConfigurationHandler.disableSteamProducer) {
            return energy;
        }
        energy = energy / PowerSystems.powerSystemSteam.getScaleAmmount();
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
            BlockPosition bp = new BlockPosition(this);
            bp.orientation = direction;
            bp.moveForwards(1);
            TileEntity te = worldObj.getTileEntity(bp.x, bp.y, bp.z);

            if (te != null && te instanceof IFluidHandler) {
                FluidStack fluidStack = new FluidStack(FluidRegistry.getFluid(PowerConverters.steamId), (int) energy);
                energy -= ((IFluidHandler) te).fill(bp.orientation.getOpposite(), fluidStack, true);
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

        return new FluidTankInfo[] { tank.getInfo() };
    }

}
