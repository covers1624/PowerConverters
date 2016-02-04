package covers1624.powerconverters.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

/**
 * Created by covers1624 on 1/31/2016.
 */
public interface IRecipeStateManager {

	/**
	 * Adds a Shapeless recipe to the state manager.
	 *
	 * @param output       Stack to give the player.
	 * @param inputObjects Standard mojang format inputs.
	 */
	void addShapelessRecipe(ItemStack output, Object... inputObjects);

	/**
	 * Adds a Shaped recipe to the state manager.
	 *
	 * @param output       Stack to give the player.
	 * @param inputObjects Standard mojang format inputs.
	 */
	void addRecipe(ItemStack output, Object... inputObjects);

	/**
	 * Adds an IRecipe to the state manager.
	 * For custom IRecipe objects to work you MUST register a
	 *
	 * @param recipe
	 */
	void addRecipe(IRecipe recipe);

}
