package powercrystals.powerconverters.power.te;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import powercrystals.powerconverters.gui.PCCreativeTab;
import powercrystals.powerconverters.power.BlockPowerConverter;
import powercrystals.powerconverters.reference.Reference;

public class BlockPowerConverterThermalExpansion extends BlockPowerConverter {

	public BlockPowerConverterThermalExpansion() {
		super(2);
		setBlockName("powerconverters.te");
		setCreativeTab(PCCreativeTab.tab);
	}

	@Override
	public TileEntity createTileEntity(World world, int meta) {

		if (meta == 0)
			return new TileEntityThermalExpansionConsumer();
		else if (meta == 1)
			return new TileEntityThermalExpansionProducer();
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir) {

		_icons[0] = ir.registerIcon(Reference.MOD_PREFIX + "powerconverters.bc" + ".consumer.off");
		_icons[1] = ir.registerIcon(Reference.MOD_PREFIX + "powerconverters.bc" + ".consumer.on");
		_icons[2] = ir.registerIcon(Reference.MOD_PREFIX + "powerconverters.bc" + ".producer.off");
		_icons[3] = ir.registerIcon(Reference.MOD_PREFIX + "powerconverters.bc" + ".producer.on");
	}

}
