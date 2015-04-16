package powercrystals.powerconverters.init;

import net.minecraft.block.Block;
import powercrystals.powerconverters.common.BlockPowerConverterCommon;
import powercrystals.powerconverters.common.ItemBlockPowerConverterCommon;
import powercrystals.powerconverters.common.TileEntityCharger;
import powercrystals.powerconverters.common.TileEntityEnergyBridge;
import powercrystals.powerconverters.power.factorization.BlockPowerConverterFactorization;
import powercrystals.powerconverters.power.factorization.ItemBlockPowerConverterFactorization;
import powercrystals.powerconverters.power.factorization.TileEntityPowerConverterFactorizationConsumer;
import powercrystals.powerconverters.power.factorization.TileEntityPowerConverterFactorizationProducer;
import powercrystals.powerconverters.power.ic2.BlockPowerConverterIndustrialCraft;
import powercrystals.powerconverters.power.ic2.ItemBlockPowerConverterIndustrialCraft;
import powercrystals.powerconverters.power.ic2.TileEntityIndustrialCraftConsumer;
import powercrystals.powerconverters.power.ic2.TileEntityIndustrialCraftProducer;
import powercrystals.powerconverters.power.redstoneflux.BlockPowerConverterRedstoneFlux;
import powercrystals.powerconverters.power.redstoneflux.ItemBlockPowerConverterRedstoneFlux;
import powercrystals.powerconverters.power.redstoneflux.TileEntityRedstoneFluxConsumer;
import powercrystals.powerconverters.power.redstoneflux.TileEntityRedstoneFluxProducer;
import powercrystals.powerconverters.power.steam.BlockPowerConverterSteam;
import powercrystals.powerconverters.power.steam.ItemBlockPowerConverterSteam;
import powercrystals.powerconverters.power.steam.TileEntitySteamConsumer;
import powercrystals.powerconverters.power.steam.TileEntitySteamProducer;
import powercrystals.powerconverters.util.RFHelper;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {

	public static Block conduitBlock;

	public static Block converterBlockCommon;
	// public static Block converterBlockBuildCraft;
	public static Block converterBlockIndustrialCraft;
	public static Block converterBlockSteam;
	public static Block converterBlockFactorization;
	public static Block converterBlockRedstoneFlux;

	public static void init() {

		converterBlockCommon = new BlockPowerConverterCommon();
		GameRegistry.registerBlock(converterBlockCommon, ItemBlockPowerConverterCommon.class, converterBlockCommon.getUnlocalizedName());
		GameRegistry.registerTileEntity(TileEntityEnergyBridge.class, "powerConverterEnergyBridge");
		GameRegistry.registerTileEntity(TileEntityCharger.class, "powerConverterUniversalCharger");

		// RF
		if (Loader.isModLoaded("ThermalExpansion") || Loader.isModLoaded("BuildCraft") || RFHelper.getiEnergyHandlerResults()) {
			converterBlockRedstoneFlux = new BlockPowerConverterRedstoneFlux();
			GameRegistry.registerBlock(converterBlockRedstoneFlux, ItemBlockPowerConverterRedstoneFlux.class, converterBlockRedstoneFlux.getUnlocalizedName());
			GameRegistry.registerTileEntity(TileEntityRedstoneFluxConsumer.class, "powerConverterRFConsumer");
			GameRegistry.registerTileEntity(TileEntityRedstoneFluxProducer.class, "powerConverterRFProducer");
		}
		// EU
		if (Loader.isModLoaded("IC2")) {
			converterBlockIndustrialCraft = new BlockPowerConverterIndustrialCraft();
			GameRegistry.registerBlock(converterBlockIndustrialCraft, ItemBlockPowerConverterIndustrialCraft.class, converterBlockIndustrialCraft.getUnlocalizedName());
			GameRegistry.registerTileEntity(TileEntityIndustrialCraftConsumer.class, "powerConverterIC2Consumer");
			GameRegistry.registerTileEntity(TileEntityIndustrialCraftProducer.class, "powerConverterIC2Producer");
		}
		// Steam
		// THIS IT A TEMP CHANGE
		// if (PowerConverterCore.steamId != -1) {
		converterBlockSteam = new BlockPowerConverterSteam();
		GameRegistry.registerBlock(converterBlockSteam, ItemBlockPowerConverterSteam.class, converterBlockSteam.getUnlocalizedName());
		GameRegistry.registerTileEntity(TileEntitySteamConsumer.class, "powerConverterSteamConsumer");
		GameRegistry.registerTileEntity(TileEntitySteamProducer.class, "powerConverterSteamProducer");
		// }
		// GC
		if (Loader.isModLoaded("factorization")) {
			converterBlockFactorization = new BlockPowerConverterFactorization();
			GameRegistry.registerBlock(converterBlockFactorization, ItemBlockPowerConverterFactorization.class, converterBlockFactorization.getUnlocalizedName());
			GameRegistry.registerTileEntity(TileEntityPowerConverterFactorizationConsumer.class, "powerConverterFZConsumer");
			GameRegistry.registerTileEntity(TileEntityPowerConverterFactorizationProducer.class, "powerConverterFZProducer");
		}

	}

}
