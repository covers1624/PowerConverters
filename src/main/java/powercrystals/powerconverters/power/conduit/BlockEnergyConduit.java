package powercrystals.powerconverters.power.conduit;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockEnergyConduit extends BlockContainer {

	public BlockEnergyConduit() {
		super(Material.clay);
		setBlockName("powerconverters.conduit");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return null;
	}

}
