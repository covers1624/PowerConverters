package powercrystals.powerconverters.power.buildcraft;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;
import buildcraft.api.power.IPowerEmitter;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import buildcraft.api.power.PowerHandler.Type;
import powercrystals.powerconverters.temp.BlockPosition;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.init.PowerSystems;
import powercrystals.powerconverters.power.TileEntityEnergyProducer;

public class TileEntityBuildCraftProducer extends TileEntityEnergyProducer<IPowerReceptor> implements IPowerEmitter {

	public TileEntityBuildCraftProducer() {

		super(PowerSystems.powerSystemBuildCraft, 0, IPowerReceptor.class);
	}

	@Override
	public double produceEnergy(double energy) {

		double mj = energy / PowerSystems.powerSystemBuildCraft.getInternalEnergyPerOutput();

		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			TileEntity tile = BlockPosition.getAdjacentTileEntity(this, dir);
			IPowerReceptor receptor;
			if (tile == null)
				continue;
			if (tile instanceof IPowerReceptor) {
				receptor = (IPowerReceptor) tile;
			} else
				continue;
			PowerReceiver receiver = receptor.getPowerReceiver(dir.getOpposite());
			if (receiver != null) {
				double usedMJ = Math.min(MathHelper.floor_double(receiver.powerRequest()), mj);
				mj -= usedMJ;
				receiver.receiveEnergy(Type.ENGINE, usedMJ, dir.getOpposite());
			}
		}
		return mj * PowerSystems.powerSystemBuildCraft.getInternalEnergyPerOutput();
	}

	@Override
	public boolean canEmitPowerFrom(ForgeDirection side) {
		return true;
	}

}
