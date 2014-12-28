package powercrystals.powerconverters.power.redstoneflux;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import powercrystals.powerconverters.gui.PCCreativeTab;
import powercrystals.powerconverters.power.BlockPowerConverter;
import powercrystals.powerconverters.reference.Reference;

public class BlockPowerConverterRedstoneFlux extends BlockPowerConverter {

	public BlockPowerConverterRedstoneFlux() {
		super(2);
		setBlockName("powerconverters.rf");
		setCreativeTab(PCCreativeTab.tab);
	}

	@Override
	public TileEntity createTileEntity(World world, int meta) {

		if (meta == 0)
			return new TileEntityRedstoneFluxConsumer();
		else if (meta == 1)
			return new TileEntityRedstoneFluxProducer();
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir) {

		_icons[0] = ir.registerIcon(Reference.MOD_PREFIX + "tile.powerconverters.rf.consumer.off");
		_icons[1] = ir.registerIcon(Reference.MOD_PREFIX + "tile.powerconverters.rf.consumer.on");
		_icons[2] = ir.registerIcon(Reference.MOD_PREFIX + "tile.powerconverters.rf.producer.off");
		_icons[3] = ir.registerIcon(Reference.MOD_PREFIX + "tile.powerconverters.rf.producer.on");
	}

}
