package covers1624.powerconverters.block;

import covers1624.powerconverters.gui.PCCreativeTab;
import covers1624.powerconverters.reference.Reference;
import covers1624.powerconverters.tile.factorization.TileEntityPowerConverterFactorizationConsumer;
import covers1624.powerconverters.tile.factorization.TileEntityPowerConverterFactorizationProducer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPowerConverterFactorization extends BlockPowerConverter {
	public BlockPowerConverterFactorization() {
		super(2);
		setBlockName("powerconverters.fz");
		setCreativeTab(PCCreativeTab.tab);
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		if (metadata == 0)
			return new TileEntityPowerConverterFactorizationConsumer();
		else if (metadata == 1)
			return new TileEntityPowerConverterFactorizationProducer();

		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir) {
		_icons[0] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".consumer.off");
		_icons[1] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".consumer.on");
		_icons[2] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".producer.off");
		_icons[3] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".producer.on");
	}
}
