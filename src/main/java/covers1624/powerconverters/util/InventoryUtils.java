package covers1624.powerconverters.util;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class InventoryUtils {

	public static void bindPlayerInventory(Container container, InventoryPlayer inventoryPlayer, int xStart, int yStart) {
		// Main inventory
		for (int i = 0; i < 3; i++) {// Rows, 3 Rows
			for (int j = 0; j < 9; j++) {// Columns, 9 Slots per row
				int slot = j + i * 9 + 9;
				int x = xStart + j * 18;
				int y = yStart + i * 18;
				addSlotToContainer(container, new Slot(inventoryPlayer, slot, x, y));
			}
		}

		// Hotbar
		for (int i = 0; i < 9; i++) {
			addSlotToContainer(container, new Slot(inventoryPlayer, i, xStart + i * 18, yStart + 58));
		}
	}

	public static Slot addSlotToContainer(Container container, Slot slot) {
		slot.slotNumber = container.inventorySlots.size();
		container.inventorySlots.add(slot);
		container.inventoryItemStacks.add(null);
		return slot;
	}
}
