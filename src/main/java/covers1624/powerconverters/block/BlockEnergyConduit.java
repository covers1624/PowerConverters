package covers1624.powerconverters.block;

import covers1624.powerconverters.PowerConverters;
import covers1624.powerconverters.gui.PCCreativeTab;
import covers1624.powerconverters.tile.conduit.TileEnergyConduit;
import covers1624.powerconverters.util.IUpdateTileWithCords;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEnergyConduit extends BlockContainer {

	public BlockEnergyConduit() {
		super(Material.rock);
		setBlockName("powerconverters.conduit");
		setCreativeTab(PowerConverters.creativeTab);
		// setBlockBounds(0.75F, 0.75F, 0.75F, 0.75F, 0.75F, 0.75F);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return 22;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void onNeighborChange(IBlockAccess world, int x, int y, int z, int tileX, int tileY, int tileZ) {
		TileEntity te = world instanceof World ? world.getTileEntity(x, y, z) : world.getTileEntity(x, y, z);
		if (te instanceof IUpdateTileWithCords) {
			((IUpdateTileWithCords) te).onNeighboorChanged(tileX, tileY, tileZ);
		}
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int par1) {
		return new TileEnergyConduit();
	}
}
