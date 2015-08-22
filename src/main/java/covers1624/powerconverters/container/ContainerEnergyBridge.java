package covers1624.powerconverters.container;

import covers1624.powerconverters.net.PacketPipeline;
import covers1624.powerconverters.tile.main.TileEntityEnergyBridge;
import covers1624.powerconverters.util.InventoryUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public class ContainerEnergyBridge extends Container {
	// private static final int _numParams = 6;

	private TileEntityEnergyBridge energyBridge;
	private EntityPlayer player;

	public ContainerEnergyBridge(TileEntityEnergyBridge bridge, InventoryPlayer inventoryPlayer) {
		player = inventoryPlayer.player;
		energyBridge = bridge;
		// InventoryUtils.bindPlayerInventory(this, inventoryPlayer, 44, 174);
		InventoryUtils.bindPlayerInventory(this, inventoryPlayer, 8, 113);
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}

	@Override
	public void updateProgressBar(int var, int value) {
		// if (var == 1000)
		// energyBridge.setIsInputLimited(value == 0 ? false : true);
		// else if (var == 1001)
		// energyBridge.setEnergyScaled(value);
		// else {
		// int side = var / _numParams;
		// int sidevar = var % _numParams;
		//
		// if (sidevar == 0)
		// energyBridge.getDataForSide(ForgeDirection.getOrientation(side)).voltageNameIndex = value;
		// if (sidevar == 1)
		// energyBridge.getDataForSide(ForgeDirection.getOrientation(side)).isConsumer = (value != 0);
		// if (sidevar == 2)
		// energyBridge.getDataForSide(ForgeDirection.getOrientation(side)).isProducer = (value != 0);
		// if (sidevar == 3)
		// energyBridge.getDataForSide(ForgeDirection.getOrientation(side)).powerSystem = PowerSystemRegistry.getPowerSystemById(value);
		// if (sidevar == 4)
		// energyBridge.getDataForSide(ForgeDirection.getOrientation(side)).isConnected = (value != 0);
		// if (sidevar == 5)
		// energyBridge.getDataForSide(ForgeDirection.getOrientation(side)).outputRate = value;
		// }
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		// for (int side = 0; side < 6; side++) {
		// ForgeDirection d = ForgeDirection.getOrientation(side);
		// BridgeSideData data = energyBridge.getDataForSide(d);
		// for (int i = 0; i < crafters.size(); i++) {
		// ((ICrafting) crafters.get(i)).sendProgressBarUpdate(this, side * _numParams + 0, data.voltageNameIndex);
		// // ((ICrafting) crafters.get(i)).sendProgressBarUpdate(this, side * _numParams + 1, data.isConsumer ? 1 : 0);
		// ((ICrafting) crafters.get(i)).sendProgressBarUpdate(this, side * _numParams + 2, data.isProducer ? 1 : 0);
		// if (data.powerSystem != null) {
		// ((ICrafting) crafters.get(i)).sendProgressBarUpdate(this, side * _numParams + 3, data.powerSystem.getId());
		// }
		// ((ICrafting) crafters.get(i)).sendProgressBarUpdate(this, side * _numParams + 4, data.isConnected ? 1 : 0);
		// ((ICrafting) crafters.get(i)).sendProgressBarUpdate(this, side * _numParams + 5, (int) data.outputRate);
		// }
		// }

		// for (int i = 0; i < crafters.size(); i++) {
		// ((ICrafting) crafters.get(i)).sendProgressBarUpdate(this, 1000, energyBridge.isInputLimited() ? 1 : 0);
		// ((ICrafting) crafters.get(i)).sendProgressBarUpdate(this, 1001, (int) energyBridge.getEnergyScaled());
		// }
		PacketPipeline.INSTANCE.sendTo(energyBridge.getNetPacket(), (EntityPlayerMP) player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		return null;
	}
}
