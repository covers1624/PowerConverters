package covers1624.powerconverters.init;

import covers1624.powerconverters.util.FMLLogHelper;
import covers1624.powerconverters.util.LogHelper;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Recipes {
	// The fall back recipes when the client leaves the server.
	private static List<IRecipe> defaultRecipes = new ArrayList<IRecipe>();
	private static List<IRecipe> curentRecipes = new ArrayList<IRecipe>();

	public static boolean buildcraftFound = Loader.isModLoaded("BuildCraft|Energy");
	public static boolean industrialCraftFound = Loader.isModLoaded("IC2");
	public static boolean thermalExpansionFound = Loader.isModLoaded("ThermalExpansion");
	public static boolean factorizationFound = Loader.isModLoaded("factorization");
	public static boolean railcraftFound = Loader.isModLoaded("Railcraft");

	public static void initDefaults() {
		Defaults.mainRecipes();
		Defaults.redstoneFluxRecipes();
		Defaults.industrialCraft2Recipes();
		Defaults.railcraftRecipes();
		Defaults.factorizationRecipes();
	}

	public static List<IRecipe> getCurrentRecipes() {
		return curentRecipes;
	}

	public static List<IRecipe> getDefaultRecipes() {
		return defaultRecipes;
	}

	public static void setDefaultRecipes(List<IRecipe> recipes) {
		defaultRecipes = recipes;
	}

	private static void addRecipe(ItemStack output, Object... params) {
		String s = "";
		int i = 0;
		int j = 0;
		int k = 0;

		if (params[i] instanceof String[]) {
			String[] astring = ((String[]) params[i++]);

			for (int l = 0; l < astring.length; ++l) {
				String s1 = astring[l];
				++k;
				j = s1.length();
				s = s + s1;
			}
		} else {
			while (params[i] instanceof String) {
				String s2 = (String) params[i++];
				++k;
				j = s2.length();
				s = s + s2;
			}
		}

		HashMap<Character, ItemStack> hashmap;

		for (hashmap = new HashMap<Character, ItemStack>(); i < params.length; i += 2) {
			Character character = (Character) params[i];
			ItemStack itemstack1 = null;

			if (params[i + 1] instanceof Item) {
				itemstack1 = new ItemStack((Item) params[i + 1]);
			} else if (params[i + 1] instanceof Block) {
				itemstack1 = new ItemStack((Block) params[i + 1], 1, 32767);
			} else if (params[i + 1] instanceof ItemStack) {
				itemstack1 = (ItemStack) params[i + 1];
			}

			hashmap.put(character, itemstack1);
		}

		ItemStack[] aitemstack = new ItemStack[j * k];

		for (int i1 = 0; i1 < j * k; ++i1) {
			char c0 = s.charAt(i1);

			if (hashmap.containsKey(Character.valueOf(c0))) {
				aitemstack[i1] = hashmap.get(Character.valueOf(c0)).copy();
			} else {
				aitemstack[i1] = null;
			}
		}

		ShapedRecipes shapedrecipes = new ShapedRecipes(j, k, aitemstack, output);
		curentRecipes.add(shapedrecipes);
		CraftingManager.getInstance().getRecipeList().add(shapedrecipes);
	}

	private static void addShapelessRecipe(ItemStack output, Object... params) {
		ArrayList<ItemStack> arraylist = new ArrayList<ItemStack>();
		Object[] aobject = params;
		int i = params.length;

		for (int j = 0; j < i; ++j) {
			Object object1 = aobject[j];

			if (object1 instanceof ItemStack) {
				arraylist.add(((ItemStack) object1).copy());
			} else if (object1 instanceof Item) {
				arraylist.add(new ItemStack((Item) object1));
			} else {
				if (!(object1 instanceof Block)) {
					throw new RuntimeException("Invalid shapeless recipy!");
				}

				arraylist.add(new ItemStack((Block) object1));
			}
		}

		curentRecipes.add(new ShapelessRecipes(output, arraylist));
		CraftingManager.getInstance().getRecipeList().add(new ShapelessRecipes(output, arraylist));
	}

	public static class Defaults {
		public static void mainRecipes() {
			addRecipe(new ItemStack(ModBlocks.converterBlockCommon, 1, 0), "GRG", "LDL", "GRG", Character.valueOf('G'), Items.gold_ingot, Character.valueOf('R'), Items.redstone, Character.valueOf('L'), Blocks.glass, Character.valueOf('D'), Items.diamond);

			addRecipe(new ItemStack(ModBlocks.converterBlockCommon, 1, 2), "GRG", "ICI", "GRG", Character.valueOf('G'), Items.gold_ingot, Character.valueOf('R'), Items.redstone, Character.valueOf('I'), Items.iron_ingot, Character.valueOf('C'), Blocks.chest);
		}

		public static void redstoneFluxRecipes() {
			Block blockDynamo = GameRegistry.findBlock("ThermalExpansion", "Dynamo");
			Block engineBlock = GameRegistry.findBlock("BuildCraft|Core", "engineBlock");
			Block engineBlock2 = GameRegistry.findBlock("BuildCraft|Energy", "engineBlock");
			// try {
			if (thermalExpansionFound) {
				LogHelper.trace("ThermalExpansion Found.");
				if (blockDynamo != null) {
					addRecipe(new ItemStack(ModBlocks.converterBlockRedstoneFlux, 1, 0), "G G", " E ", "G G", Character.valueOf('G'), Items.gold_ingot, Character.valueOf('E'), new ItemStack(blockDynamo, 1, 1));
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
					addRecipe(new ItemStack(ModBlocks.converterBlockRedstoneFlux, 1, 0), "G G", " E ", "G G", Character.valueOf('G'), Items.gold_ingot, Character.valueOf('E'), new ItemStack(engineBlock, 1, 1));
				} else if (engineBlock2 != null) {
					addRecipe(new ItemStack(ModBlocks.converterBlockRedstoneFlux, 1, 0), "G G", " E ", "G G", Character.valueOf('G'), Items.gold_ingot, Character.valueOf('E'), new ItemStack(engineBlock2, 1, 1));
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
			addShapelessRecipe(new ItemStack(ModBlocks.converterBlockRedstoneFlux, 1, 1), new ItemStack(ModBlocks.converterBlockRedstoneFlux, 1, 0));
			addShapelessRecipe(new ItemStack(ModBlocks.converterBlockRedstoneFlux, 1, 0), new ItemStack(ModBlocks.converterBlockRedstoneFlux, 1, 1));
		}

		public static void industrialCraft2Recipes() {
			if (industrialCraftFound) {
				try {
					addRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 0), "G G", " T ", "G G", Character.valueOf('G'), Items.gold_ingot, Character.valueOf('T'), (Class.forName("ic2.core.Ic2Items").getField("lvTransformer").get(null)));
					addRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 2), "G G", " T ", "G G", Character.valueOf('G'), Items.gold_ingot, Character.valueOf('T'), (Class.forName("ic2.core.Ic2Items").getField("mvTransformer").get(null)));
					addRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 4), "G G", " T ", "G G", Character.valueOf('G'), Items.gold_ingot, Character.valueOf('T'), (Class.forName("ic2.core.Ic2Items").getField("hvTransformer").get(null)));
					addRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 6), "G G", " T ", "G G", Character.valueOf('G'), Items.gold_ingot, Character.valueOf('T'), (Class.forName("ic2.core.Ic2Items").getField("mfsUnit").get(null)));
				} catch (Exception e) {
					FMLLogHelper.logException(Level.ERROR, "Found IC2 But Failed To Load Recipes, Mabey They Changed Their Item / Block Names?  This is not a fatal error only recipes wont be registered.", e);
				}
			}
			addShapelessRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 1), new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 0));
			addShapelessRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 0), new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 1));
			addShapelessRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 3), new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 2));
			addShapelessRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 2), new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 3));
			addShapelessRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 5), new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 4));
			addShapelessRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 4), new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 5));
			addShapelessRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 7), new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 6));
			addShapelessRecipe(new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 6), new ItemStack(ModBlocks.converterBlockIndustrialCraft, 1, 7));
		}

		public static void railcraftRecipes() {
			try {
				if (railcraftFound) {
					addRecipe(new ItemStack(ModBlocks.converterBlockSteam, 1, 0), "G G", " E ", "G G", Character.valueOf('G'), Items.gold_ingot, Character.valueOf('E'), new ItemStack((Block) (Class.forName("mods.railcraft.common.blocks.RailcraftBlocks").getMethod("getBlockMachineBeta").invoke(null)), 1, 8));
				}
				if (factorizationFound) {
					Object fzRegistry = Class.forName("factorization.shared.Core").getField("registry").get(null);
					addRecipe(new ItemStack(ModBlocks.converterBlockSteam, 1, 0), "G G", " E ", "G G", Character.valueOf('G'), Items.gold_ingot, Character.valueOf('E'), (Class.forName("factorization.common.Registry").getField("steamturbine_item").get(fzRegistry)));
				}
			} catch (Exception e) {
				FMLLogHelper.logException(Level.ERROR, "Found Railcraft / Factorization But Failed To Load Recipes, Mabey They Changed Ther Item / Block Names? This is not a fatal error only recipes wont be registered.", e);
			}
			addShapelessRecipe(new ItemStack(ModBlocks.converterBlockSteam, 1, 1), new ItemStack(ModBlocks.converterBlockSteam, 1, 0));
			addShapelessRecipe(new ItemStack(ModBlocks.converterBlockSteam, 1, 0), new ItemStack(ModBlocks.converterBlockSteam, 1, 1));
		}

		public static void factorizationRecipes() {
			try {
				if (factorizationFound) {
					Object fzRegistry = Class.forName("factorization.shared.Core").getField("registry").get(null);
					addRecipe(new ItemStack(ModBlocks.converterBlockFactorization, 1, 0), "I I", " B ", "I I", Character.valueOf('I'), Items.gold_ingot, Character.valueOf('B'), (Class.forName("factorization.common.Registry").getField("solarboiler_item").get(fzRegistry)));
				}
			} catch (Exception e) {
				FMLLogHelper.logException(Level.ERROR, "Found Factorization But Failed To Load Recipes, Mabey They Changed Their Item / Block Names? This is not a fatal error only recipes wont be registered.", e);
			}
			addShapelessRecipe(new ItemStack(ModBlocks.converterBlockFactorization, 1, 1), new ItemStack(ModBlocks.converterBlockFactorization, 1, 0));
			addShapelessRecipe(new ItemStack(ModBlocks.converterBlockFactorization, 1, 0), new ItemStack(ModBlocks.converterBlockFactorization, 1, 1));
		}

	}

}
