package powercrystals.powerconverters.power.conduit;


import java.util.List;

import powercrystals.powerconverters.gui.PCCreativeTab;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockEnergyConduit extends BlockContainer {

	public BlockEnergyConduit() {
		super(Material.clay);
		setBlockName("powerconverters.conduit");
		setCreativeTab(PCCreativeTab.tab);
		setBlockBounds(0.3F, 0.3F, 0.3F, 0.7F, 0.7F, 0.7F);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return null;
	}

}
