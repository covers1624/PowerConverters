package powercrystals.powerconverters.init;

import powercrystals.powerconverters.common.BlockPowerConverterCommon;
import powercrystals.powerconverters.common.ItemBlockPowerConverterCommon;
import powercrystals.powerconverters.common.TileEntityCharger;
import powercrystals.powerconverters.common.TileEntityEnergyBridge;
import powercrystals.powerconverters.power.buildcraft.BlockPowerConverterBuildCraft;
import powercrystals.powerconverters.power.buildcraft.ItemBlockPowerConverterBuildCraft;
import powercrystals.powerconverters.power.buildcraft.TileEntityBuildCraftConsumer;
import powercrystals.powerconverters.power.buildcraft.TileEntityBuildCraftProducer;
import powercrystals.powerconverters.power.conduit.BasicEnergyConduit;
import powercrystals.powerconverters.power.conduit.BlockEnergyConduit;
import powercrystals.powerconverters.power.conduit.ItemBlockEnergyConduit;
import powercrystals.powerconverters.power.factorization.BlockPowerConverterFactorization;
import powercrystals.powerconverters.power.factorization.ItemBlockPowerConverterFactorization;
import powercrystals.powerconverters.power.factorization.TileEntityPowerConverterFactorizationConsumer;
import powercrystals.powerconverters.power.factorization.TileEntityPowerConverterFactorizationProducer;
import powercrystals.powerconverters.power.ic2.BlockPowerConverterIndustrialCraft;
import powercrystals.powerconverters.power.ic2.ItemBlockPowerConverterIndustrialCraft;
import powercrystals.powerconverters.power.ic2.TileEntityIndustrialCraftConsumer;
import powercrystals.powerconverters.power.ic2.TileEntityIndustrialCraftProducer;
import powercrystals.powerconverters.power.railcraft.BlockPowerConverterRailCraft;
import powercrystals.powerconverters.power.railcraft.ItemBlockPowerConverterRailCraft;
import powercrystals.powerconverters.power.railcraft.TileEntityRailCraftConsumer;
import powercrystals.powerconverters.power.railcraft.TileEntityRailCraftProducer;
import powercrystals.powerconverters.power.te.BlockPowerConverterThermalExpansion;
import powercrystals.powerconverters.power.te.ItemBlockPowerConverterThermalExpansion;
import powercrystals.powerconverters.power.te.TileEntityThermalExpansionConsumer;
import powercrystals.powerconverters.power.te.TileEntityThermalExpansionProducer;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class ModBlocks {

	public static Block conduitBlock;
	
	public static Block converterBlockCommon;
	public static Block converterBlockBuildCraft;
	public static Block converterBlockIndustrialCraft;
	public static Block converterBlockSteam;
	public static Block converterBlockFactorization;
	public static Block converterBlockThermalExpansion;

	public static void init() {
		
		conduitBlock = new BlockEnergyConduit();
		GameRegistry.registerBlock(conduitBlock, ItemBlockEnergyConduit.class, conduitBlock.getUnlocalizedName());
		GameRegistry.registerTileEntity(BasicEnergyConduit.class, "basicConduit");
		
		converterBlockCommon = new BlockPowerConverterCommon();
		GameRegistry.registerBlock(converterBlockCommon, ItemBlockPowerConverterCommon.class, converterBlockCommon.getUnlocalizedName());
		GameRegistry.registerTileEntity(TileEntityEnergyBridge.class, "powerConverterEnergyBridge");
		GameRegistry.registerTileEntity(TileEntityCharger.class, "powerConverterUniversalCharger");
		// MJ
		if (Loader.isModLoaded("BuildCraft|Energy")) {
			converterBlockBuildCraft = new BlockPowerConverterBuildCraft();
			GameRegistry.registerBlock(converterBlockBuildCraft, ItemBlockPowerConverterBuildCraft.class, converterBlockBuildCraft.getUnlocalizedName());
			GameRegistry.registerTileEntity(TileEntityBuildCraftConsumer.class, "powerConverterBCConsumer");
			GameRegistry.registerTileEntity(TileEntityBuildCraftProducer.class, "powerConverterBCProducer");
		}
		// RF
		if (Loader.isModLoaded("ThermalExpansion")) {
			converterBlockThermalExpansion = new BlockPowerConverterThermalExpansion();
			GameRegistry.registerBlock(converterBlockThermalExpansion, ItemBlockPowerConverterThermalExpansion.class, converterBlockThermalExpansion.getUnlocalizedName());
			GameRegistry.registerTileEntity(TileEntityThermalExpansionConsumer.class, "powerConverterTEConsumer");
			GameRegistry.registerTileEntity(TileEntityThermalExpansionProducer.class, "powerConverterTEProducer");
		}
		// EU
		if (Loader.isModLoaded("IC2")) {
			converterBlockIndustrialCraft = new BlockPowerConverterIndustrialCraft();
			GameRegistry.registerBlock(converterBlockIndustrialCraft, ItemBlockPowerConverterIndustrialCraft.class, converterBlockIndustrialCraft.getUnlocalizedName());
			GameRegistry.registerTileEntity(TileEntityIndustrialCraftConsumer.class, "powerConverterIC2Consumer");
			GameRegistry.registerTileEntity(TileEntityIndustrialCraftProducer.class, "powerConverterIC2Producer");
		}
		// Steam
		if (Loader.isModLoaded("Railcraft") || Loader.isModLoaded("factorization")) {
			converterBlockSteam = new BlockPowerConverterRailCraft();
			GameRegistry.registerBlock(converterBlockSteam, ItemBlockPowerConverterRailCraft.class, converterBlockSteam.getUnlocalizedName());
			GameRegistry.registerTileEntity(TileEntityRailCraftConsumer.class, "powerConverterSteamConsumer");
			GameRegistry.registerTileEntity(TileEntityRailCraftProducer.class, "powerConverterSteamProducer");
		}
		// GC
		if (Loader.isModLoaded("factorization")) {
			converterBlockFactorization = new BlockPowerConverterFactorization();
			GameRegistry.registerBlock(converterBlockFactorization, ItemBlockPowerConverterFactorization.class, converterBlockFactorization.getUnlocalizedName());
			GameRegistry.registerTileEntity(TileEntityPowerConverterFactorizationConsumer.class, "powerConverterFZConsumer");
			GameRegistry.registerTileEntity(TileEntityPowerConverterFactorizationProducer.class, "powerConverterFZProducer");
		}

	}

}
