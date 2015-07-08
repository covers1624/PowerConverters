package powercrystals.powerconverters.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import powercrystals.powerconverters.common.TileEntityCharger;

public class ContainerUniversalCharger extends Container {

	private InventoryPlayer playerInventory;
	private TileEntityCharger tileCharger;

	public ContainerUniversalCharger(InventoryPlayer playerInventory, TileEntityCharger charger) {
		this.playerInventory = playerInventory;
		this.tileCharger = charger;
		bindPlayerInventory(playerInventory);
		int slot = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				int x = 8 + j * 18;
				int y = 18 + i * 18;
				addSlotToContainer(new Slot(tileCharger, slot, x, y));
				slot++;
			}
		}
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				int x = 98 + j * 18;
				int y = 18 + i * 18;
				addSlotToContainer(new Slot(tileCharger, slot, x, y));
				slot++;
			}
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_) {
		return super.transferStackInSlot(p_82846_1_, p_82846_2_);
	}

	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				int slot = j + i * 9 + 9;
				int x = 8 + j * 18;
				int y = 104 + i * 18;
				addSlotToContainer(new Slot(inventoryPlayer, slot, x, y));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 162));
		}
	}

}
