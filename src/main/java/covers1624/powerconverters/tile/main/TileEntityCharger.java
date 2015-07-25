package covers1624.powerconverters.tile.main;

import java.util.ArrayList;
import java.util.List;

import covers1624.powerconverters.api.charge.IChargeHandler;
import covers1624.powerconverters.init.PowerSystems;
import covers1624.powerconverters.util.BlockPosition;
import covers1624.powerconverters.util.IAdvancedLogTile;
import covers1624.powerconverters.util.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCharger extends TileEntityEnergyProducer<IInventory> implements IAdvancedLogTile, ISidedInventory {
	private static List<IChargeHandler> chargeHandlers = new ArrayList<IChargeHandler>();
	// Array of all IInventory devices touching the block, indexed with ForgeDirection.
	private TileEntity[] sideCache = new TileEntity[6];
	private ItemStack[] slots;

	public static void registerChargeHandler(IChargeHandler handler) {
		LogHelper.trace(String.format("Registered Charge Handler %s.", handler.name()));
		chargeHandlers.add(handler);
	}

	public TileEntityCharger() {
		super(PowerSystems.powerSystemRedstoneFlux, 0, IInventory.class);
		slots = new ItemStack[32];
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		// Update our cache on every tick.
		searchForTiles();
		// Make sure that the only thing in the left slots is non charged items.
		validateSlots();
	}

	@Override
	public double produceEnergy(double energy) {
		if (energy == 0) {
			return 0;
		}
		double energyRemaining = energy;
		// Iterate over the main part of the inventory.
		for (int i = 0; i < 16; i++) {
			ItemStack stack = slots[i];
			if (stack == null) {
				continue;
			}
			for (IChargeHandler handler : chargeHandlers) {
				if (handler.canHandle(stack)) {
					energyRemaining = handler.charge(stack, energyRemaining);
					if (energyRemaining == 0) {
						return 0;
					}
				}
			}
		}
		energyRemaining = powerTiles(energyRemaining);
		return energyRemaining;
	}

	private void searchForTiles() {
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			TileEntity tileEntity = BlockPosition.getAdjacentTileEntity(this, dir);
			// Add new Tiles to our cache.
			if (tileEntity != null && tileEntity instanceof IInventory) {
				sideCache[dir.ordinal()] = tileEntity;
			}

			// Validate our cache.
			if (tileEntity == null) {
				sideCache[dir.ordinal()] = null;
			}
		}
	}

	// Validates that the input slots don't have charged items in it. I.E. Moves all charged items to the Right hand inventory.
	private void validateSlots() {
		for (int i = 0; i < 16; i++) {
			if (slots[i] != null) {
				for (IChargeHandler handler : chargeHandlers) {
					if (handler.canHandle(slots[i])) {
						if (handler.isItemCharged(slots[i])) {
							for (int j = 16; j < 32; j++) {
								if (slots[j] == null) {
									slots[j] = slots[i];
									slots[i] = null;
									break;
								}
							}
						}
					}
				}
			}
		}
	}

	// Watch this Explode... Or not....
	public double powerTiles(double energyRemaining) {
		for (TileEntity tileEntity : sideCache) {
			if (tileEntity != null) {
				IInventory iInventory = (IInventory) tileEntity;
				for (int i = 0; i < iInventory.getSizeInventory(); i++) {
					ItemStack itemStack = iInventory.getStackInSlot(i);
					if (itemStack != null) {
						for (IChargeHandler handler : chargeHandlers) {
							if (handler.canHandle(itemStack)) {
								energyRemaining = handler.charge(itemStack, energyRemaining);
								if (energyRemaining == 0) {
									return 0;
								}
							}
						}
					}
				}
			}
		}
		return energyRemaining;
	}

	@Override
	public void getTileInfo(List<IChatComponent> info, ForgeDirection side, EntityPlayer player, boolean debug) {
		info.add(text("-SideCache-"));
		for (int i = 0; i < sideCache.length; i++) {
			String data = sideCache[i] != null ? sideCache[i].getClass().getName() : "Null";
			info.add(text(String.format("Side: %s, Data: %s", ForgeDirection.VALID_DIRECTIONS[i], data)));
		}
	}

	private ChatComponentText text(String string) {
		return new ChatComponentText(string);
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < slots.length; i++) {
			if (slots[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setInteger("Slot", i);
				slots[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		tagCompound.setTag("Items", nbttaglist);
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		NBTTagList nbttaglist = tagCompound.getTagList("Items", 10);
		slots = new ItemStack[getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound1.getInteger("Slot");

			if (j >= 0 && j < slots.length) {
				slots[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
	}

	@Override
	public int getSizeInventory() {
		return slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return slots[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int ammount) {
		if (slots[slot] != null) {
			if (slots[slot].stackSize <= ammount) {
				ItemStack itemStack = slots[slot];
				slots[slot] = null;
				return itemStack;
			}
			ItemStack itemStack = slots[slot].splitStack(ammount);
			if (slots[slot].stackSize == 0) {
				slots[slot] = null;
			}
			return itemStack;
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		return slots[slot];
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		slots[slot] = stack;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
			stack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return "Universal Charger";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return true;
	}

	@Override
	public void openInventory() {

	}

	@Override
	public void closeInventory() {

	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (slot < 16) {
			for (IChargeHandler chargeHandler : chargeHandlers) {
				if (chargeHandler.canHandle(stack)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return slot > 16;
	}

	public static List<IChargeHandler> getChargeHandlers() {
		return chargeHandlers;
	}
}
