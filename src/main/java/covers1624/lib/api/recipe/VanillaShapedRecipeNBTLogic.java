package covers1624.lib.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/**
 * Created by covers1624 on 1/31/2016.
 */
public class VanillaShapedRecipeNBTLogic implements IRecipeNBTLogic<ShapedRecipes> {

	@Override
	public String getClassToHandle() {
		return ShapedRecipes.class.getName();
	}

	@Override
	public String getNBTName() {
		return "VanillaShaped";
	}

	@Override
	public void writeToNBT(ShapedRecipes recipe, NBTTagCompound tagCompound) {
		NBTTagCompound tag = new NBTTagCompound();
		recipe.getRecipeOutput().writeToNBT(tag);
		tagCompound.setTag("Output", tag);

		NBTTagList tagList = new NBTTagList();
		for (int i = 0; i < recipe.recipeItems.length; i++) {
			if (recipe.recipeItems[i] != null) {
				NBTTagCompound nbtTagCompound = new NBTTagCompound();
				nbtTagCompound.setInteger("Slot", i);
				recipe.recipeItems[i].writeToNBT(nbtTagCompound);
				tagList.appendTag(nbtTagCompound);
			}
		}

		tagCompound.setTag("Input", tagList);
		tagCompound.setInteger("Height", recipe.recipeHeight);
		tagCompound.setInteger("Width", recipe.recipeWidth);
	}

	@Override
	public ShapedRecipes readFromNBT(NBTTagCompound tagCompound) {
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

		return new ShapedRecipes(width, height, recipeItems, output);
	}
}
