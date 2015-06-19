package powercrystals.powerconverters.init;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import org.apache.logging.log4j.Level;

import powercrystals.powerconverters.util.FMLLogHelper;
import powercrystals.powerconverters.util.LogHelper;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class Recipes {

	public static boolean buildcraftFound = Loader.isModLoaded("BuildCraft|Energy");
	public static boolean industrialCraftFound = Loader.isModLoaded("IC2");
	public static boolean thermalExpansionFound = Loader.isModLoaded("ThermalExpansion");
	public static boolean factorizationFound = Loader.isModLoaded("factorization");
	public static boolean railcraftFound = Loader.isModLoaded("Railcraft");

	public static void initDefaults() {
		ModRecipes();
		RedstoneFluxRecipes();
		IndustrialCraft2Recipes();
		RailcraftRecipes();
		FactorizationRecipes();
	}

	public static void ModRecipes() {
		GameRegistry.addRecipe(new ItemStack(ModBlocks.converterBlockCommon, 1, 0), "GRG", "LDL", "GRG", Character.valueOf('G'), Items.gold_ingot, Character.valueOf('R'), Items.redstone, Character.valueOf('L'), Blocks.glass, Character.valueOf('D'), Items.diamond);

		GameRegistry.addRecipe(new ItemStack(ModBlocks.converterBlockCommon, 1, 2), "GRG", "ICI", "GRG", Character.valueOf('G'), Items.gold_ingot, Character.valueOf('R'), Items.redstone, Character.valueOf('I'), Items.iron_ingot, Character.valueOf('C'), Blocks.chest);
	}

	public static void RedstoneFluxRecipes() {
		Block blockDynamo = GameRegistry.findBlock("ThermalExpansion", "Dynamo");
		Block engineBlock = GameRegistry.findBlock("BuildCraft|Core", "engineBlock");
		Block engineBlock2 = GameRegistry.findBlock("BuildCraft|Energy", "engineBlock");
		// try {
		if (thermalExpansionFound) {
			LogHelper.trace("ThermalExpansion Found.");
			if (blockDynamo != null) {
				GameRegistry.addRecipe(new ItemStack(ModBlocks.converterBlockRedstoneFlux, 1, 0), "G G", " E ", "G G", Character.valueOf('G'), Items.gold_ingot, Character.valueOf('E'), new ItemStack(blockDynamo, 1, 1));
			} else {
				LogHelper.error("A Error has occored while trying to lookup \"Dynamo\" in GameRegistry. " // Format
						+ "This can be caused by an outdated version of PowerConverters or ThermalExpansion. "// Format
						+ "Please Ensure you are using the Latest version of both before submitting a bug report."// Format
						+ "Due to this the recipes for RedstoneFlux Consumer / Producer will not use ThermalExpansion Components.");
			}
		} else {
			LogHelper.trace("ThermalExpansion Not Found.");
		}
		if (buildcraftFound) {
			LogHelper.trace("BuildCraft Found.");
			if (engineBlock != null) {
				GameRegistry.addRecipe(new ItemStack(ModBlocks.converterBlockRedstoneFlux, 1, 0), "G G", " E ", "G G", Character.valueOf('G'), Items.gold_ingot, Character.valueOf('E'), new ItemStack(engineBlock, 1, 1));
			} else if (engineBlock2 != null) {
				GameRegistry.addRecipe(new ItemStack(ModBlocks.converterBlockRedstoneFlux, 1, 0), "G G", " E ", "G G", Character.valueOf('G'), Items.gold_ingot, Character.valueOf('E'), new ItemStack(engineBlock2, 1, 1));
			} else {
				LogHelper.error("A Error has occored while trying to lookup \"engineBlock\" in GameRegistry. " // Format
						+ "This can be caused by an outdated version of PowerConverters or BuildCraft. "// Format
						+ "Please Ensure you are using the Latest version of both before submitting a bug report."// Format
						+ "Due to this the recipes for RedstoneFlux Consumer / Producer will not use BuildCraft Components.");
			}
		} else {
			LogHelper.trace("BuildCraft not Found.");
		}

		// FMLLogHelper.logException(Level.ERROR, "Found ThermalExpansion But Failed To Load Recipes, Mabey They Changed Their Item / Block Names? This is not a fatal error only recipes wont be registered.", e);
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.converterBlockRedstoneFlux, 1, 1), new ItemStack(ModBlocks.converterBlockRedstoneFlux, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.converterBlockRedstoneFlux, 1, 0), new ItemStack(ModBlocks.converterBlockRedstoneFlux, 1, 1));
	}

	public static void IndustrialCraft2Recipes() {
		if (industrialCraftFound) {
			try {
				GameRegistry.addRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 0), "G G", " T ", "G G", Character.valueOf('G'), Items.gold_ingot, Character.valueOf('T'), (Class.forName("ic2.core.Ic2Items").getField("lvTransformer").get(null)));
				GameRegistry.addRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 2), "G G", " T ", "G G", Character.valueOf('G'), Items.gold_ingot, Character.valueOf('T'), (Class.forName("ic2.core.Ic2Items").getField("mvTransformer").get(null)));
				GameRegistry.addRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 4), "G G", " T ", "G G", Character.valueOf('G'), Items.gold_ingot, Character.valueOf('T'), (Class.forName("ic2.core.Ic2Items").getField("hvTransformer").get(null)));
				GameRegistry.addRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 6), "G G", " T ", "G G", Character.valueOf('G'), Items.gold_ingot, Character.valueOf('T'), (Class.forName("ic2.core.Ic2Items").getField("mfsUnit").get(null)));
			} catch (Exception e) {
				FMLLogHelper.logException(Level.ERROR, "Found IC2 But Failed To Load Recipes, Mabey They Changed Their Item / Block Names?  This is not a fatal error only recipes wont be registered.", e);
			}
		}
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 1), new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 0), new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 3), new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 2), new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 5), new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 4));
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 4), new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 5));
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 7), new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 6));
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 6), new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 7));
	}

	public static void RailcraftRecipes() {
		try {
			if (railcraftFound) {
				GameRegistry.addRecipe(new ItemStack(ModBlocks.converterBlockSteam, 1, 0), "G G", " E ", "G G", Character.valueOf('G'), Items.gold_ingot, Character.valueOf('E'), new ItemStack((Block) (Class.forName("mods.railcraft.common.blocks.RailcraftBlocks").getMethod("getBlockMachineBeta").invoke(null)), 1, 8));
			}
			if (factorizationFound) {
				Object fzRegistry = Class.forName("factorization.shared.Core").getField("registry").get(null);
				GameRegistry.addRecipe(new ItemStack(ModBlocks.converterBlockSteam, 1, 0), "G G", " E ", "G G", Character.valueOf('G'), Items.gold_ingot, Character.valueOf('E'), (Class.forName("factorization.common.Registry").getField("steamturbine_item").get(fzRegistry)));
			}
		} catch (Exception e) {
			FMLLogHelper.logException(Level.ERROR, "Found Railcraft / Factorization But Failed To Load Recipes, Mabey They Changed Ther Item / Block Names? This is not a fatal error only recipes wont be registered.", e);
		}
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.converterBlockSteam, 1, 1), new ItemStack(ModBlocks.converterBlockSteam, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.converterBlockSteam, 1, 0), new ItemStack(ModBlocks.converterBlockSteam, 1, 1));
	}

	public static void FactorizationRecipes() {
		try {
			if (factorizationFound) {
				Object fzRegistry = Class.forName("factorization.shared.Core").getField("registry").get(null);
				GameRegistry.addRecipe(new ItemStack(ModBlocks.converterBlockFactorization, 1, 0), "I I", " B ", "I I", Character.valueOf('I'), Items.gold_ingot, Character.valueOf('B'), (Class.forName("factorization.common.Registry").getField("solarboiler_item").get(fzRegistry)));
			}
		} catch (Exception e) {
			FMLLogHelper.logException(Level.ERROR, "Found Factorization But Failed To Load Recipes, Mabey They Changed Their Item / Block Names? This is not a fatal error only recipes wont be registered.", e);
		}
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.converterBlockFactorization, 1, 1), new ItemStack(ModBlocks.converterBlockFactorization, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.converterBlockFactorization, 1, 0), new ItemStack(ModBlocks.converterBlockFactorization, 1, 1));
	}

}
