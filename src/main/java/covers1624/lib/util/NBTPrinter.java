package covers1624.lib.util;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;

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
        newLine();
        builder.append("==== START TAG COMPOUND ====");
        newLine();
        handleTagCompound(tagCompound, 0);
        newLine();
        builder.append("==== END TAG COMPOUND ====");
    }

    //Depth = number of Tabs.
    private void handleTagCompound(NBTTagCompound tagCompound, int depth) {
        handleDepth(depth);
        builder.append("CompoundTag: ");
        newLine();
        depth += 1;
        handleDepth(depth);
        Map<String, NBTBase> tagMap = ObfuscationReflectionHelper.getPrivateValue(NBTTagCompound.class, tagCompound, "tagMap");

        for (Map.Entry<String, NBTBase> entry : tagMap.entrySet()) {
            int id = entry.getValue().getId();
            switch (id) {
            case 8:
                builder.append(String.format("String: NBTKey: %s, Value: %s", entry.getKey(), ((NBTTagString) entry.getValue()).func_150285_a_()));
            }
        }

    }

    private void handleDepth(int depth) {
        for (int i = 0; i < depth; i++) {
            builder.append("    ");
        }
    }

    private void newLine() {
        builder.append("\n");
    }

    public String toString() {
        return builder.toString();
    }

}
