package covers1624.powerconverters.util;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author modmuss50, gigabit101
 */
public class RecipeRemover {

    public static void removeAnyRecipes(List<ItemStack> removeList) {
        for (ItemStack stack : removeList) {
            removeAnyRecipe(stack);
        }
    }

    public static void removeShapedRecipes(List<ItemStack> removelist) {
        for (ItemStack stack : removelist) {
            removeShapedRecipe(stack);
        }
    }

    public static void removeAnyRecipe(ItemStack resultItem) {
        List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
        for (int i = 0; i < recipes.size(); i++) {
            IRecipe tmpRecipe = recipes.get(i);
            ItemStack recipeResult = tmpRecipe.getRecipeOutput();
            if (ItemStack.areItemStacksEqual(resultItem, recipeResult)) {
                recipes.remove(i--);
            }
        }
    }

    public static void removeShapedRecipe(ItemStack resultItem) {
        List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
        for (int i = 0; i < recipes.size(); i++) {
            IRecipe tmpRecipe = recipes.get(i);
            if (tmpRecipe instanceof ShapedRecipes) {
                ShapedRecipes recipe = (ShapedRecipes) tmpRecipe;
                ItemStack recipeResult = recipe.getRecipeOutput();

                if (ItemStack.areItemStacksEqual(resultItem, recipeResult)) {
                    recipes.remove(i++);
                }
            }
        }
    }

    public static void removeAllRecipes(IRecipe... recipes) {
        for (IRecipe recipe : recipes) {
            removeRecipe(recipe);
        }
    }

    public static void removeAllRecipes(List<IRecipe> recipes) {
        for (IRecipe recipe : recipes) {
            removeRecipe(recipe);
        }
    }

    public static void removeRecipe(IRecipe toRemove) {
        ArrayList<IRecipe> recipeList = new ArrayList<IRecipe>();
        Collections.copy(recipeList, CraftingManager.getInstance().getRecipeList());
        //Yeah i could just call the method but this is safer.
        for (IRecipe recipe : recipeList) {
            if (recipe.equals(toRemove)) {
                CraftingManager.getInstance().getRecipeList().remove(recipe);
                break;
            }
        }

    }

}