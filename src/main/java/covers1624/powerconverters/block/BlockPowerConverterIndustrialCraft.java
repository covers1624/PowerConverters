package covers1624.powerconverters.block;

import covers1624.powerconverters.PowerConverters;
import covers1624.powerconverters.reference.Reference;
import covers1624.powerconverters.tile.ic2.TileEntityIndustrialCraftConsumer;
import covers1624.powerconverters.tile.ic2.TileEntityIndustrialCraftProducer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPowerConverterIndustrialCraft extends BlockPowerConverter {
    public BlockPowerConverterIndustrialCraft() {
        super(8);
        setBlockName("powerconverters.ic2");
        setCreativeTab(PowerConverters.creativeTab);
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        if (metadata == 0) {
            return new TileEntityIndustrialCraftConsumer(0);
        } else if (metadata == 1) {
            return new TileEntityIndustrialCraftProducer(0);
        } else if (metadata == 2) {
            return new TileEntityIndustrialCraftConsumer(1);
        } else if (metadata == 3) {
            return new TileEntityIndustrialCraftProducer(1);
        } else if (metadata == 4) {
            return new TileEntityIndustrialCraftConsumer(2);
        } else if (metadata == 5) {
            return new TileEntityIndustrialCraftProducer(2);
        } else if (metadata == 6) {
            return new TileEntityIndustrialCraftConsumer(3);
        } else if (metadata == 7) {
            return new TileEntityIndustrialCraftProducer(3);
        }

        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ir) {
        icons[0] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".lv.consumer.off");
        icons[1] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".lv.consumer.on");
        icons[2] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".lv.producer.off");
        icons[3] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".lv.producer.on");
        icons[4] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".mv.consumer.off");
        icons[5] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".mv.consumer.on");
        icons[6] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".mv.producer.off");
        icons[7] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".mv.producer.on");
        icons[8] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".hv.consumer.off");
        icons[9] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".hv.consumer.on");
        icons[10] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".hv.producer.off");
        icons[11] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".hv.producer.on");
        icons[12] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".ev.consumer.off");
        icons[13] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".ev.consumer.on");
        icons[14] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".ev.producer.off");
        icons[15] = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".ev.producer.on");
    }
}
