package powercrystals.powerconverters.init;

import org.apache.logging.log4j.Level;

import powercrystals.powerconverters.common.TileEntityCharger;
import powercrystals.powerconverters.power.ic2.ChargeHandlerIndustrialCraft;
import powercrystals.powerconverters.power.redstoneflux.ChargeHandlerRedstoneFlux;
import powercrystals.powerconverters.util.FMLLogHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class Recipes {

	public static void initDefaults() {
		ModRecipes();
		// BuildCraftRecipes();
		RedstoneFluxRecipes();
		IndustrialCraft2Recipes();
		RailcraftRecipes();
		FactorizationRecipes();
	}

	public static void ModRecipes() {
		GameRegistry.addRecipe(new ItemStack(ModBlocks.converterBlockCommon, 1, 0), "GRG", "LDL", "GRG", Character.valueOf('G'), Items.gold_ingot, Character.valueOf('R'), Items.redstone, Character.valueOf('L'), Blocks.glass, Character.valueOf('D'), Items.diamond);

		GameRegistry.addRecipe(new ItemStack(ModBlocks.converterBlockCommon, 1, 2), "GRG", "ICI", "GRG", Character.valueOf('G'), Items.gold_ingot, Character.valueOf('R'), Items.redstone, Character.valueOf('I'), Items.iron_ingot, Character.valueOf('C'), Blocks.chest);
	}

	/*
	 * public static void BuildCraftRecipes(){ try{ GameRegistry.addRecipe(new
	 * ItemStack(ModBlocks.converterBlockBuildCraft, 1, 0), "G G", " E ", "G G",
	 * Character.valueOf('G'), Items.gold_ingot, Character.valueOf('E'), new
	 * ItemStack
	 * ((Block)(Class.forName("buildcraft.BuildCraftEnergy").getField("engineBlock"
	 * ).get(null)), 1, 1)); }catch(Exception e){
	 * FMLLogHelper.logException(Level.FATAL,
	 * "Found Buildcraft But Failed To Load Recipes, Mabey They Changed Their Item / Block Names?"
	 * , e); } GameRegistry.addShapelessRecipe(new
	 * ItemStack(ModBlocks.converterBlockBuildCraft, 1, 1), new
	 * ItemStack(ModBlocks.converterBlockBuildCraft, 1, 0));
	 * GameRegistry.addShapelessRecipe(new
	 * ItemStack(ModBlocks.converterBlockBuildCraft, 1, 0), new
	 * ItemStack(ModBlocks.converterBlockBuildCraft, 1, 1));
	 * 
	 * }
	 */

	public static void RedstoneFluxRecipes() {
		try {
			if (Loader.isModLoaded("ThermalExpansion")) {
				GameRegistry.addRecipe(new ItemStack(ModBlocks.converterBlockRedstoneFlux, 1, 0), "G G", " E ", "G G", Character.valueOf('G'), Items.gold_ingot, Character.valueOf('E'), new ItemStack((Block) (Class.forName("thermalexpansion.block.TEBlocks").getField("blockDynamo").get(null)), 1, 1));
			}
			GameRegistry.addRecipe(new ItemStack(ModBlocks.converterBlockRedstoneFlux, 1, 0), "G G", " E ", "G G", Character.valueOf('G'), Items.gold_ingot, Character.valueOf('E'), new ItemStack((Block) (Class.forName("buildcraft.BuildCraftEnergy").getField("engineBlock").get(null)), 1, 1));
		} catch (Exception e) {
			GameRegistry.addRecipe(new ItemStack(ModBlocks.converterBlockRedstoneFlux, 1, 0), "G G", " E ", "G G", Character.valueOf('G'), Items.gold_ingot, Character.valueOf('E'), new ItemStack(Items.iron_ingot));
			FMLLogHelper.logException(Level.FATAL, "Found ThermalExpansion But Failed To Load Recipes, Mabey They Changed Their Item / Block Names?", e);
		}
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.converterBlockRedstoneFlux, 1, 1), new ItemStack(ModBlocks.converterBlockRedstoneFlux, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.converterBlockRedstoneFlux, 1, 0), new ItemStack(ModBlocks.converterBlockRedstoneFlux, 1, 1));
	}

	public static void IndustrialCraft2Recipes() {
		try {
			GameRegistry.addRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 0), "G G", " T ", "G G", Character.valueOf('G'), Items.gold_ingot, Character.valueOf('T'), (ItemStack) (Class.forName("ic2.core.Ic2Items").getField("lvTransformer").get(null)));
			GameRegistry.addRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 2), "G G", " T ", "G G", Character.valueOf('G'), Items.gold_ingot, Character.valueOf('T'), (ItemStack) (Class.forName("ic2.core.Ic2Items").getField("mvTransformer").get(null)));
			GameRegistry.addRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 4), "G G", " T ", "G G", Character.valueOf('G'), Items.gold_ingot, Character.valueOf('T'), (ItemStack) (Class.forName("ic2.core.Ic2Items").getField("hvTransformer").get(null)));
			GameRegistry.addRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 6), "G G", " T ", "G G", Character.valueOf('G'), Items.gold_ingot, Character.valueOf('T'), (ItemStack) (Class.forName("ic2.core.Ic2Items").getField("mfsUnit").get(null)));
		} catch (Exception e) {
			FMLLogHelper.logException(Level.FATAL, "Found IC2 But Failed To Load Recipes, Mabey They Changed Their Item / Block Names?", e);
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
			if (Loader.isModLoaded("Railcraft")) {
				GameRegistry.addRecipe(new ItemStack(ModBlocks.converterBlockSteam, 1, 0), "G G", " E ", "G G", Character.valueOf('G'), Items.gold_ingot, Character.valueOf('E'), new ItemStack((Block) (Class.forName("mods.railcraft.common.blocks.RailcraftBlocks").getMethod("getBlockMachineBeta").invoke(null)), 1, 8));
			}
			if (Loader.isModLoaded("factorization")) {
				Object fzRegistry = Class.forName("factorization.shared.Core").getField("registry").get(null);
				GameRegistry.addRecipe(new ItemStack(ModBlocks.converterBlockSteam, 1, 0), "G G", " E ", "G G", Character.valueOf('G'), Items.gold_ingot, Character.valueOf('E'), (ItemStack) (Class.forName("factorization.common.Registry").getField("steamturbine_item").get(fzRegistry)));
			}
		} catch (Exception e) {
			FMLLogHelper.logException(Level.FATAL, "Found Railcraft / Factorization But Failed To Load Recipes, Mabey They Changed Ther Item / Block Names?", e);
		}
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.converterBlockSteam, 1, 1), new ItemStack(ModBlocks.converterBlockSteam, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.converterBlockSteam, 1, 0), new ItemStack(ModBlocks.converterBlockSteam, 1, 1));
	}

	public static void FactorizationRecipes() {
		try {
			Object fzRegistry = Class.forName("factorization.shared.Core").getField("registry").get(null);
			GameRegistry.addRecipe(new ItemStack(ModBlocks.converterBlockFactorization, 1, 0), "I I", " B ", "I I", Character.valueOf('I'), Items.gold_ingot, Character.valueOf('B'), (ItemStack) (Class.forName("factorization.common.Registry").getField("solarboiler_item").get(fzRegistry)));
		} catch (Exception e) {
			FMLLogHelper.logException(Level.FATAL, "Found Factorization But Failed To Load Recipes, Mabey They Changed Their Item / Block Names?", e);
		}
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.converterBlockFactorization, 1, 1), new ItemStack(ModBlocks.converterBlockFactorization, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.converterBlockFactorization, 1, 0), new ItemStack(ModBlocks.converterBlockFactorization, 1, 1));
	}

}
