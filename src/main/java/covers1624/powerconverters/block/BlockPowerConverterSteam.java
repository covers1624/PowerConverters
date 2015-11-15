package covers1624.powerconverters.block;

import covers1624.powerconverters.PowerConverters;
import covers1624.powerconverters.reference.Reference;
import covers1624.powerconverters.tile.steam.TileEntitySteamConsumer;
import covers1624.powerconverters.tile.steam.TileEntitySteamProducer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPowerConverterSteam extends BlockPowerConverter {
	public BlockPowerConverterSteam() {
		super(2);
		setBlockName("powerconverters.steam");
		setCreativeTab(PowerConverters.creativeTab);
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		if (metadata == 0) {
			return new TileEntitySteamConsumer();
		} else if (metadata == 1) {
			return new TileEntitySteamProducer();
		}

		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir) {
		icons[0] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".consumer.off");
		icons[1] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".consumer.on");
		icons[2] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".producer.off");
		icons[3] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".producer.on");
	}
}
