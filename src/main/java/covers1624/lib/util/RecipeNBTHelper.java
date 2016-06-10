package covers1624.lib.util;

import covers1624.lib.api.recipe.IRecipeNBTLogic;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;

import static covers1624.lib.discovery.RecipeNBTLogicDiscovery.handlerClassMap;
import static covers1624.lib.discovery.RecipeNBTLogicDiscovery.nbtNameClassMap;

public class RecipeNBTHelper {

    public static NBTTagCompound writeIRecipeToTag(IRecipe recipe) {
        NBTTagCompound mainTag = new NBTTagCompound();
        if (handlerClassMap.get(recipe.getClass().getName()) != null) {
            IRecipeNBTLogic logic = handlerClassMap.get(recipe.getClass().getName());
            logic.writeToNBT(recipe, mainTag);
            mainTag.setString("Type", logic.getNBTName());
            return mainTag;
        }
        LogHelper.error("Unable to convert recipe class %s to NBT! Did you forget a IRecipeNBTLogic class??", recipe.getClass().getName());
        return null;
    }

    public static IRecipe readIRecipeFromTag(NBTTagCompound tagCompound) {
        String type = tagCompound.getString("Type");
        if (nbtNameClassMap.get(type) != null) {
            IRecipeNBTLogic logic = nbtNameClassMap.get(type);
            return logic.readFromNBT(tagCompound);
        }
        LogHelper.error("Unable to read recipe class from NBT! This should NEVER happen, Offending Type: " + type);
        return null;
    }
}
