package powercrystals.powerconverters.power.ic2;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import powercrystals.powerconverters.gui.PCCreativeTab;
import powercrystals.powerconverters.power.BlockPowerConverter;
import powercrystals.powerconverters.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPowerConverterIndustrialCraft extends BlockPowerConverter {
	public BlockPowerConverterIndustrialCraft() {
		super(8);
		setBlockName("powerconverters.ic2");
		setCreativeTab(PCCreativeTab.tab);
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		if (metadata == 0)
			return new TileEntityIndustrialCraftConsumer(0);
		else if (metadata == 1)
			return new TileEntityIndustrialCraftProducer(0);
		else if (metadata == 2)
			return new TileEntityIndustrialCraftConsumer(1);
		else if (metadata == 3)
			return new TileEntityIndustrialCraftProducer(1);
		else if (metadata == 4)
			return new TileEntityIndustrialCraftConsumer(2);
		else if (metadata == 5)
			return new TileEntityIndustrialCraftProducer(2);
		else if (metadata == 6)
			return new TileEntityIndustrialCraftConsumer(3);
		else if (metadata == 7)
			return new TileEntityIndustrialCraftProducer(3);

		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir) {
		_icons[0] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".lv.consumer.off");
		_icons[1] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".lv.consumer.on");
		_icons[2] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".lv.producer.off");
		_icons[3] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".lv.producer.on");
		_icons[4] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".mv.consumer.off");
		_icons[5] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".mv.consumer.on");
		_icons[6] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".mv.producer.off");
		_icons[7] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".mv.producer.on");
		_icons[8] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".hv.consumer.off");
		_icons[9] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".hv.consumer.on");
		_icons[10] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".hv.producer.off");
		_icons[11] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".hv.producer.on");
		_icons[12] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".ev.consumer.off");
		_icons[13] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".ev.consumer.on");
		_icons[14] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".ev.producer.off");
		_icons[15] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".ev.producer.on");
	}
}
