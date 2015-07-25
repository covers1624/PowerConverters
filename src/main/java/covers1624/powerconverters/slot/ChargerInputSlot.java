package covers1624.powerconverters.slot;

import java.util.List;

import covers1624.powerconverters.api.charge.IChargeHandler;
import covers1624.powerconverters.tile.main.TileEntityCharger;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

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
