package covers1624.powerconverters.init;

import covers1624.powerconverters.item.DebugItem;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class ModItems {

	public static Item debugItem;

	public static void init() {
		debugItem = new DebugItem();

		GameRegistry.registerItem(debugItem, "debugItem");

	}

}
