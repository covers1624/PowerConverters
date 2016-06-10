package covers1624.powerconverters.test;

import covers1624.powerconverters.util.LogHelper;
import covers1624.lib.util.NBTPrinter;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;

/**
 * Created by covers1624 on 2/5/2016.
 */
public class NBTBulshitery {

	public static void main(String[] args){
		init();
	}

	public static void init() {
		NBTTagCompound tagCompound = new NBTTagCompound();
		tagCompound.setString("String", "String Test");
		NBTTagCompound innerTag = new NBTTagCompound();
		NBTTagList tagList = new NBTTagList();
		NBTTagCompound tagElement1 = new NBTTagCompound();
		tagElement1.setString("TagElement 1", "Inner Tag List Element 1");
		NBTTagCompound tagElement2 = new NBTTagCompound();
		tagElement2.setString("TagElement2", "Inner Tag List Element 2");
		tagList.appendTag(tagElement1);
		tagList.appendTag(tagElement2);
		innerTag.setTag("Inner List", tagList);
		LogHelper.info(new NBTPrinter(tagCompound).toString());
	}

}
