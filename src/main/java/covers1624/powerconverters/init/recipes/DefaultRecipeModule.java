package covers1624.powerconverters.init.recipes;

import covers1624.powerconverters.api.recipe.AbstractRecipeModule;
import covers1624.powerconverters.api.recipe.IRecipeStateManager;
import covers1624.powerconverters.init.ModBlocks;
import covers1624.powerconverters.util.LogHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * Created by covers1624 on 1/28/2016.
 */
public class DefaultRecipeModule extends AbstractRecipeModule {

	private Block blockDynamo;
	private Block engineBlock;
	private Block blockIc2Electric;
	private Block blockFactorization;
	private Block railcraftBlock;

	@Override
	public void preLoad() {
		blockDynamo = GameRegistry.findBlock("ThermalExpansion", "Dynamo");
		engineBlock = GameRegistry.findBlock("BuildCraft|Core", "engineBlock");
		blockIc2Electric = GameRegistry.findBlock("IC2", "blockElectric");
		blockFactorization = GameRegistry.findBlock("factorization", "FzBlock");
		railcraftBlock = GameRegistry.findBlock("Railcraft", "machine.beta");
	}

	@Override
	public String getName() {
		return "Default";
	}

	@Override
	public String[] getRequiredMods() {
		return new String[0];
	}

	@Override
	public String[] getIncompatibleMods() {
		return new String[0];
	}

	@Override
	public String getTargetMod() {
		return "powerconverters3";
	}

	@Override
	public void loadRecipes(IRecipeStateManager stateManager) {
		stateManager.addRecipe(new ItemStack(ModBlocks.converterBlockCommon, 1, 0), "GRG", "LDL", "GRG", 'G', Items.gold_ingot, 'R', Items.redstone, 'L', Blocks.glass, 'D', Items.diamond);
		stateManager.addRecipe(new ItemStack(ModBlocks.converterBlockCommon, 1, 2), "GRG", "ICI", "GRG", 'G', Items.gold_ingot, 'R', Items.redstone, 'I', Items.iron_ingot, 'C', Blocks.chest);
		/**
		 * Thermal Expansion.
		 */
		if (thermalExpansionFound) {
			LogHelper.trace("ThermalExpansion Found. Adding TE recipes...");
			if (blockDynamo != null) {
				stateManager.addRecipe(new ItemStack(ModBlocks.converterBlockRedstoneFlux, 1, 0), "G G", " E ", "G G", 'G', Items.gold_ingot, 'E', new ItemStack(blockDynamo, 1, 1));
			} else {
				LogHelper.error("A Error has occurred while trying to lookup \"Dynamo\" in GameRegistry. " // Format
						+ "This can be caused by an outdated version of PowerConverters or ThermalExpansion. "// Format
						+ "Please Ensure you are using the Latest version of both before submitting a bug report."// Format
						+ "Due to this the recipes for RedstoneFlux Consumer / Producer will not use ThermalExpansion Components.");
			}
		} else {
			LogHelper.trace("ThermalExpansion Not Found. Unable to add TE recipes.");
		}

		/**
		 * BuildCraft
		 */
		if (buildCraftCoreFound) {
			LogHelper.trace("BuildCraft Found. Adding BC recipes...");
			if (engineBlock != null) {
				stateManager.addRecipe(new ItemStack(ModBlocks.converterBlockRedstoneFlux, 1, 0), "G G", " E ", "G G", 'G', Items.gold_ingot, 'E', new ItemStack(engineBlock, 1, 1));
			} else {
				LogHelper.error("A Error has occurred while trying to lookup \"engineBlock\" in GameRegistry. " // Format
						+ "This can be caused by an outdated version of PowerConverters or BuildCraft. "// Format
						+ "Please Ensure you are using the Latest version of both before submitting a bug report."// Format
						+ "Due to this the recipes for RedstoneFlux Consumer / Producer will not use BuildCraft Components.");
			}
		} else {
			LogHelper.trace("BuildCraft not Found. Unable to add TE recipes.");
		}

		/**
		 * IC2
		 */
		if (ic2Found) {
			if (blockIc2Electric != null) {
				stateManager.addRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 0), "G G", " T ", "G G", 'G', Items.gold_ingot, 'T', new ItemStack(blockIc2Electric, 1, 3));
				stateManager.addRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 2), "G G", " T ", "G G", 'G', Items.gold_ingot, 'T', new ItemStack(blockIc2Electric, 1, 4));
				stateManager.addRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 4), "G G", " T ", "G G", 'G', Items.gold_ingot, 'T', new ItemStack(blockIc2Electric, 1, 5));
				stateManager.addRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 6), "G G", " T ", "G G", 'G', Items.gold_ingot, 'T', new ItemStack(blockIc2Electric, 1, 6));
			} else {
				LogHelper.error("A Error has occurred while trying to lookup \"blockElectric\" in GameRegistry. " // Format
						+ "This can be caused by an outdated version of PowerConverters or IC2. "// Format
						+ "Please Ensure you are using the Latest version of both before submitting a bug report."// Format
						+ "Due to this the recipes for Industrial Craft Consumer / Producer will not use IC2 Components.");
			}
		}
		if (factorizationFound) {
			if (blockFactorization != null) {
				stateManager.addRecipe(new ItemStack(ModBlocks.converterBlockFactorization, 1, 0), "I I", " B ", "I I", 'I', Items.gold_ingot, 'B', new ItemStack(blockFactorization, 1, 21));
				stateManager.addRecipe(new ItemStack(ModBlocks.converterBlockSteam, 1, 0), "G G", " E ", "G G", 'G', Items.gold_ingot, 'E', new ItemStack(blockFactorization, 1, 10));
			} else {
				LogHelper.error("A Error has occurred while trying to lookup \"FzBlock\" in GameRegistry. " // Format
						+ "This can be caused by an outdated version of PowerConverters or Factorization. "// Format
						+ "Please Ensure you are using the Latest version of both before submitting a bug report."// Format
						+ "Due to this the recipes for Factorization and Steam Consumers / Producers will not use IC2 Components.");
			}
		}

		if (railCraftFound) {
			if (railcraftBlock != null) {
				stateManager.addRecipe(new ItemStack(ModBlocks.converterBlockSteam, 1, 0), "G G", " E ", "G G", 'G', Items.gold_ingot, 'E', new ItemStack(railcraftBlock, 1, 8));
			} else {
				LogHelper.error("A Error has occurred while trying to lookup \"machine.beta\" in GameRegistry. " // Format
						+ "This can be caused by an outdated version of PowerConverters or Railcraft. "// Format
						+ "Please Ensure you are using the Latest version of both before submitting a bug report."// Format
						+ "Due to this the recipes for Steam Consumer / Producer will not use Railcraft Components.");
			}
		}

		stateManager.addShapelessRecipe(new ItemStack(ModBlocks.converterBlockRedstoneFlux, 1, 1), new ItemStack(ModBlocks.converterBlockRedstoneFlux, 1, 0));
		stateManager.addShapelessRecipe(new ItemStack(ModBlocks.converterBlockRedstoneFlux, 1, 0), new ItemStack(ModBlocks.converterBlockRedstoneFlux, 1, 1));

		stateManager.addShapelessRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 1), new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 0));
		stateManager.addShapelessRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 0), new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 1));
		stateManager.addShapelessRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 3), new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 2));
		stateManager.addShapelessRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 2), new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 3));
		stateManager.addShapelessRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 5), new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 4));
		stateManager.addShapelessRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 4), new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 5));
		stateManager.addShapelessRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 7), new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 6));
		stateManager.addShapelessRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 6), new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 7));

		stateManager.addShapelessRecipe(new ItemStack(ModBlocks.converterBlockFactorization, 1, 1), new ItemStack(ModBlocks.converterBlockFactorization, 1, 0));
		stateManager.addShapelessRecipe(new ItemStack(ModBlocks.converterBlockFactorization, 1, 0), new ItemStack(ModBlocks.converterBlockFactorization, 1, 1));

		stateManager.addShapelessRecipe(new ItemStack(ModBlocks.converterBlockSteam, 1, 1), new ItemStack(ModBlocks.converterBlockSteam, 1, 0));
		stateManager.addShapelessRecipe(new ItemStack(ModBlocks.converterBlockSteam, 1, 0), new ItemStack(ModBlocks.converterBlockSteam, 1, 1));
	}
}
