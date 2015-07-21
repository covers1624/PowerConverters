package powercrystals.powerconverters.gui;

import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import powercrystals.powerconverters.common.IChargeHandler;
import powercrystals.powerconverters.common.TileEntityCharger;

public class ChargerInputSlot extends Slot {

	public ChargerInputSlot(IInventory inventory, int slot, int xPos, int yPos) {
		super(inventory, slot, xPos, yPos);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		List<IChargeHandler> handlers = TileEntityCharger.getChargeHandlers();
		for (IChargeHandler handler : handlers) {
			if (handler.canHandle(stack)) {
				return true;
			}
		}
		return false;
	}

}
