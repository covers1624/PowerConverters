package covers1624.powerconverters.util;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class IRecipeHandler {

	public static NBTTagCompound writeIRecipeToTag(IRecipe recipe) {
		NBTTagCompound mainTag = new NBTTagCompound();
		if (recipe instanceof ShapedRecipes) {
			ShapedRecipes shapedRecipes = (ShapedRecipes) recipe;
			NBTTagCompound tag = new NBTTagCompound();
			shapedRecipes.getRecipeOutput().writeToNBT(tag);
			mainTag.setTag("Output", tag);

			NBTTagList tagList = new NBTTagList();
			for (int i = 0; i < shapedRecipes.recipeItems.length; i++) {
				if (shapedRecipes.recipeItems[i] != null) {
					NBTTagCompound nbtTagCompound = new NBTTagCompound();
					nbtTagCompound.setInteger("Slot", i);
					shapedRecipes.recipeItems[i].writeToNBT(nbtTagCompound);
					tagList.appendTag(nbtTagCompound);
				}
			}

			mainTag.setTag("Input", tagList);
			mainTag.setInteger("Height", shapedRecipes.recipeHeight);
			mainTag.setInteger("Width", shapedRecipes.recipeWidth);
			mainTag.setString("Type", "Shaped");
			return mainTag;
		}

		if (recipe instanceof ShapelessRecipes) {
			ShapelessRecipes shapelessRecipes = (ShapelessRecipes) recipe;
			NBTTagCompound tag = new NBTTagCompound();
			shapelessRecipes.getRecipeOutput().writeToNBT(tag);
			mainTag.setTag("Output", tag);

			NBTTagList tagList = new NBTTagList();
			for (int i = 0; i < shapelessRecipes.recipeItems.size(); i++) {
				NBTTagCompound nbtTagCompound = new NBTTagCompound();
				ItemStack stack = (ItemStack) shapelessRecipes.recipeItems.get(i);
				stack.writeToNBT(nbtTagCompound);
				tagList.appendTag(nbtTagCompound);
			}

			mainTag.setTag("Input", tagList);
			mainTag.setString("Type", "Shapeless");
			return mainTag;
		}
		return null;

	}

	public static IRecipe readIRecipeFromTag(NBTTagCompound tagCompound) {
		String type = tagCompound.getString("Type");
		// LogHelper.info(type);
		if (type.equals("Shaped")) {
			// LogHelper.info("Detected Shaped Recipe Stored.");
			ItemStack output = ItemStack.loadItemStackFromNBT((NBTTagCompound) tagCompound.getTag("Output"));
			int height = tagCompound.getInteger("Height");
			int width = tagCompound.getInteger("Width");
			ItemStack[] recipeItems = new ItemStack[9];

			NBTTagList tagList = tagCompound.getTagList("Input", 10);
			for (int i = 0; i < tagList.tagCount(); i++) {
				NBTTagCompound nbtTagCompound = tagList.getCompoundTagAt(i);
				int slot = nbtTagCompound.getInteger("Slot");
				recipeItems[slot] = ItemStack.loadItemStackFromNBT(nbtTagCompound);
			}

			ShapedRecipes shapedRecipe = new ShapedRecipes(width, height, recipeItems, output);
			return shapedRecipe;
		}
		if (type.equals("Shapeless")) {
			// LogHelper.info("Detected Shapeless Recipe Stored.");
			ItemStack output = ItemStack.loadItemStackFromNBT((NBTTagCompound) tagCompound.getTag("Output"));
			ArrayList<ItemStack> recipeItems = new ArrayList<ItemStack>();

			NBTTagList tagList = tagCompound.getTagList("Input", 10);
			for (int i = 0; i < tagList.tagCount(); i++) {
				ItemStack itemStack = ItemStack.loadItemStackFromNBT(tagList.getCompoundTagAt(i));
				recipeItems.add(itemStack);
			}

			ShapelessRecipes shapelessRecipe = new ShapelessRecipes(output, recipeItems);
			return shapelessRecipe;
		}

		return null;
	}
}
