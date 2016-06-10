package covers1624.lib.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;

/**
 * Created by covers1624 on 1/31/2016.
 */
public class VanillaShapelessRecipeNBTLogic implements IRecipeNBTLogic<ShapelessRecipes> {

    @Override
    public String getClassToHandle() {
        return ShapelessRecipes.class.getName();
    }

    @Override
    public String getNBTName() {
        return "VanillaShapeless";
    }

    @Override
    public void writeToNBT(ShapelessRecipes recipe, NBTTagCompound tagCompound) {
        NBTTagCompound tag = new NBTTagCompound();
        recipe.getRecipeOutput().writeToNBT(tag);
        tagCompound.setTag("Output", tag);

        NBTTagList tagList = new NBTTagList();
        for (int i = 0; i < recipe.recipeItems.size(); i++) {
            NBTTagCompound nbtTagCompound = new NBTTagCompound();
            ItemStack stack = (ItemStack) recipe.recipeItems.get(i);
            stack.writeToNBT(nbtTagCompound);
            tagList.appendTag(nbtTagCompound);
        }

        tagCompound.setTag("Input", tagList);
    }

    @Override
    public ShapelessRecipes readFromNBT(NBTTagCompound tagCompound) {
        ItemStack output = ItemStack.loadItemStackFromNBT((NBTTagCompound) tagCompound.getTag("Output"));
        ArrayList<ItemStack> recipeItems = new ArrayList<ItemStack>();

        NBTTagList tagList = tagCompound.getTagList("Input", 10);
        for (int i = 0; i < tagList.tagCount(); i++) {
            ItemStack itemStack = ItemStack.loadItemStackFromNBT(tagList.getCompoundTagAt(i));
            recipeItems.add(itemStack);
        }

        return new ShapelessRecipes(output, recipeItems);
    }
}
