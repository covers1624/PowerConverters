package covers1624.powerconverters.init;

import covers1624.powerconverters.block.*;
import covers1624.powerconverters.item.*;
import covers1624.powerconverters.tile.factorization.TileEntityPowerConverterFactorizationConsumer;
import covers1624.powerconverters.tile.factorization.TileEntityPowerConverterFactorizationProducer;
import covers1624.powerconverters.tile.ic2.TileEntityIndustrialCraftConsumer;
import covers1624.powerconverters.tile.ic2.TileEntityIndustrialCraftProducer;
import covers1624.powerconverters.tile.main.TileEntityCharger;
import covers1624.powerconverters.tile.main.TileEntityEnergyBridge;
import covers1624.powerconverters.tile.redstoneflux.TileEntityRedstoneFluxConsumer;
import covers1624.powerconverters.tile.redstoneflux.TileEntityRedstoneFluxProducer;
import covers1624.powerconverters.tile.steam.TileEntitySteamConsumer;
import covers1624.powerconverters.tile.steam.TileEntitySteamProducer;
import covers1624.powerconverters.util.RFHelper;
import covers1624.powerconverters.util.TextureUtils;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ModBlocks {

    public static Block converterBlockCommon;
    // public static Block converterBlockBuildCraft;
    public static Block converterBlockIndustrialCraft;
    public static Block converterBlockSteam;
    public static Block converterBlockFactorization;
    public static Block converterBlockRedstoneFlux;

    public static void init() {

        converterBlockCommon = new BlockPowerConverterCommon();
        registerBlock(converterBlockCommon, ItemBlockPowerConverterCommon.class, converterBlockCommon.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileEntityEnergyBridge.class, "EnergyBridge");
        GameRegistry.registerTileEntity(TileEntityCharger.class, "UniversalCharger");

        // RF
        if (Loader.isModLoaded("ThermalExpansion") || Loader.isModLoaded("BuildCraft") || RFHelper.iEnergyHandlerExists) {
            converterBlockRedstoneFlux = new BlockPowerConverterRedstoneFlux();
            registerBlock(converterBlockRedstoneFlux, ItemBlockPowerConverterRedstoneFlux.class, converterBlockRedstoneFlux.getUnlocalizedName());
            GameRegistry.registerTileEntity(TileEntityRedstoneFluxConsumer.class, "RFConsumer");
            GameRegistry.registerTileEntity(TileEntityRedstoneFluxProducer.class, "RFProducer");
        }
        // EU
        if (Loader.isModLoaded("IC2")) {
            converterBlockIndustrialCraft = new BlockPowerConverterIndustrialCraft();
            registerBlock(converterBlockIndustrialCraft, ItemBlockPowerConverterIndustrialCraft.class, converterBlockIndustrialCraft.getUnlocalizedName());
            GameRegistry.registerTileEntity(TileEntityIndustrialCraftConsumer.class, "IC2Consumer");
            GameRegistry.registerTileEntity(TileEntityIndustrialCraftProducer.class, "IC2Producer");
        }
        // Steam
        // THIS IT A TEMP CHANGE
        // if (PowerConverterCore.steamId != -1) {
        converterBlockSteam = new BlockPowerConverterSteam();
        registerBlock(converterBlockSteam, ItemBlockPowerConverterSteam.class, converterBlockSteam.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileEntitySteamConsumer.class, "SteamConsumer");
        GameRegistry.registerTileEntity(TileEntitySteamProducer.class, "SteamProducer");
        // }
        // GC
        if (Loader.isModLoaded("factorization")) {
            converterBlockFactorization = new BlockPowerConverterFactorization();
            registerBlock(converterBlockFactorization, ItemBlockPowerConverterFactorization.class, converterBlockFactorization.getUnlocalizedName());
            GameRegistry.registerTileEntity(TileEntityPowerConverterFactorizationConsumer.class, "FZConsumer");
            GameRegistry.registerTileEntity(TileEntityPowerConverterFactorizationProducer.class, "FZProducer");
        }

    }

    private static void registerBlock(Block block, Class<? extends ItemBlock> itemclass, String name) {
        GameRegistry.registerBlock(block, itemclass, TextureUtils.stripUnlocalizedName(name));
    }

    private static void registerBlock(Block block, String name) {
        GameRegistry.registerBlock(block, TextureUtils.stripUnlocalizedName(name));
    }

}
