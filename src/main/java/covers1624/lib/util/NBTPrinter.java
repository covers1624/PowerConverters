package covers1624.lib.util;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Map;

/**
 * Created by covers1624 on 1/31/2016.
 * TODO
 */
public class NBTPrinter {

	public StringBuilder builder = new StringBuilder();

	public NBTPrinter(NBTTagCompound tagCompound) {
		if (tagCompound == null) {
			LogHelper.bigError("NBTTagCompound was null!");
			return;
		}
		builder.append("\n");
		builder.append("==== START TAG COMPOUND ====");
		handleTagCompound(tagCompound, 0);
		builder.append("==== END TAG COMPOUND ====");
		//LogHelper.info(builder.toString());
	}

	//Depth = number of Tabs.
	private void handleTagCompound(NBTTagCompound tagCompound, int depth) {
		handleDepth(depth);
		builder.append("Tag: \n");
		Map<String, NBTBase> tagMap = ObfuscationReflectionHelper.getPrivateValue(NBTTagCompound.class, tagCompound, "tagMap");

		for (Map.Entry<String, NBTBase> entry : tagMap.entrySet()) {

		}

	}

	private void handleDepth(int depth) {
		for (int i = 0; i < depth; i++) {
			builder.append("    ");
		}
	}

	public String toString() {
		return builder.toString();
	}

}
