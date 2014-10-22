package powercrystals.powerconverters.init;

import cpw.mods.fml.common.registry.GameRegistry;
import powercrystals.powerconverters.item.DebugItem;
import net.minecraft.item.Item;

public class ModItems {
	
	public static Item debugItem;
	
	public static void init(){
		debugItem = new DebugItem();
		
		
		
		
		GameRegistry.registerItem(debugItem, "debugItem");
		
	}
	

}
