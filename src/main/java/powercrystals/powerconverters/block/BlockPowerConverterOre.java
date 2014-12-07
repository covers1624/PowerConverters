package powercrystals.powerconverters.block;

import powercrystals.powerconverters.gui.PCCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockPowerConverterOre extends Block{

	public BlockPowerConverterOre() {
		super(Material.rock);
		setHardness(3.0F);
		setResistance(5.0F);
		setStepSound(soundTypePiston);
		setCreativeTab(PCCreativeTab.tab);
		setBlockName("powerconverters.ore");
		
		setHarvestLevel("pickaxe", 2);
	}
}
