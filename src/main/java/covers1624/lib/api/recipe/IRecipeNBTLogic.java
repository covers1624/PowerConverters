package covers1624.lib.api.recipe;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by covers1624 on 1/31/2016.
 * This interface must be implemented and registered for custom IRecipe objects to be saved and loaded from NBT.
 */
public interface IRecipeNBTLogic<T extends IRecipe> {

	/**
	 * Gets the class that the IRecipeNBTLogic can handle.
	 *
	 * @return class.getName().
	 */
	String getClassToHandle();

	/**
	 * The name to use as a header in NBT.
	 *
	 * @return the name to use.
	 */
	String getNBTName();

	/**
	 * Converts an IRecipe object to NBT.
	 *
	 * @param recipe      The recipe object to convert.
	 * @param tagCompound The tag to write to.
	 */
	void writeToNBT(T recipe, NBTTagCompound tagCompound);

	/**
	 * Reads an IRecipe object from NBT.
	 *
	 * @param tagCompound The tag to read from.
	 * @return a new IRecipe from NBT.
	 */
	T readFromNBT(NBTTagCompound tagCompound);
}
