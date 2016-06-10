package covers1624.powerconverters.block;

import covers1624.powerconverters.PowerConverters;
import covers1624.powerconverters.reference.Reference;
import covers1624.powerconverters.tile.redstoneflux.TileEntityRedstoneFluxConsumer;
import covers1624.powerconverters.tile.redstoneflux.TileEntityRedstoneFluxProducer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPowerConverterRedstoneFlux extends BlockPowerConverter {

    public BlockPowerConverterRedstoneFlux() {
        super(2);
        setBlockName("powerconverters.rf");
        setCreativeTab(PowerConverters.creativeTab);
    }

    @Override
    public TileEntity createTileEntity(World world, int meta) {

        if (meta == 0) {
            return new TileEntityRedstoneFluxConsumer();
        } else if (meta == 1) {
            return new TileEntityRedstoneFluxProducer();
        }
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ir) {

        icons[0] = ir.registerIcon(Reference.MOD_PREFIX + "tile.powerconverters.rf.consumer.off");
        icons[1] = ir.registerIcon(Reference.MOD_PREFIX + "tile.powerconverters.rf.consumer.on");
        icons[2] = ir.registerIcon(Reference.MOD_PREFIX + "tile.powerconverters.rf.producer.off");
        icons[3] = ir.registerIcon(Reference.MOD_PREFIX + "tile.powerconverters.rf.producer.on");
    }

}
