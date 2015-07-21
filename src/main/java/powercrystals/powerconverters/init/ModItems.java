package powercrystals.powerconverters.init;

import net.minecraft.item.Item;
import powercrystals.powerconverters.item.DebugItem;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {
	
	public static Item debugItem;
	
	public static void init(){
		debugItem = new DebugItem();
		
		
		
		
		GameRegistry.registerItem(debugItem, "debugItem");
		
	}
	

}
