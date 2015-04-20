package powercrystals.powerconverters.power.conduit;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import powercrystals.powerconverters.power.BlockPowerConverter;
import powercrystals.powerconverters.util.IUpdateTileWithCords;

public class BlockEnergyConduit extends BlockPowerConverter {

	public BlockEnergyConduit(int metaCount) {
		super(metaCount);
	}

	@Override
	public void onNeighborChange(IBlockAccess world, int x, int y, int z, int tileX, int tileY, int tileZ) {
		TileEntity te = world instanceof World ? world.getTileEntity(x, y, z) : world.getTileEntity(x, y, z);
		if (te instanceof IUpdateTileWithCords) {
			((IUpdateTileWithCords) te).onNeighboorChanged(tileX, tileY, tileZ);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int par1) {
		return new TileEnergyConduit();
	}
}
