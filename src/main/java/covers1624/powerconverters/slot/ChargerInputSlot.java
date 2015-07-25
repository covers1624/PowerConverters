package covers1624.powerconverters.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import covers1624.powerconverters.api.charge.IChargeHandler;
import covers1624.powerconverters.api.registry.UniversalChargerRegistry;

public class ChargerInputSlot extends Slot {

	public ChargerInputSlot(IInventory inventory, int slot, int xPos, int yPos) {
		super(inventory, slot, xPos, yPos);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		for (IChargeHandler handler : UniversalChargerRegistry.getChargeHandlers()) {
			if (handler.canHandle(stack)) {
				return true;
			}
		}
		return false;
	}

}
