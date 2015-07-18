package powercrystals.powerconverters.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ChargerOutputSlot extends Slot {

	public ChargerOutputSlot(IInventory inventory, int slot, int xPos, int yPos) {
		super(inventory, slot, xPos, yPos);
	}

	@Override
	public boolean isItemValid(ItemStack p_75214_1_) {
		return false;
	}
}
