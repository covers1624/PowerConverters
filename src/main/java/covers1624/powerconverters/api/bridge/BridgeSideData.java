package covers1624.powerconverters.api.bridge;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import covers1624.powerconverters.api.registry.PowerSystemRegistry;
import covers1624.powerconverters.api.registry.PowerSystemRegistry.PowerSystem;

public class BridgeSideData {
	public ForgeDirection side;
	public PowerSystem powerSystem;
	public boolean isConsumer;
	public boolean isProducer;
	public boolean isConnected;
	public int voltageNameIndex;
	public double outputRate;
	public ItemStack displayStack;

	public void writeToNBT(NBTTagCompound tag) {
		tag.setInteger("VoltageIndex", voltageNameIndex);
		tag.setBoolean("IsConsumer", isConsumer);
		tag.setBoolean("IsProducer", isProducer);
		if (powerSystem != null) {
			tag.setInteger("PowerSystem", powerSystem.getId());
		}
		tag.setBoolean("Connected", isConnected);
		tag.setDouble("OutputRate", outputRate);
		if (displayStack != null) {
			NBTTagCompound itemTag = new NBTTagCompound();
			displayStack.writeToNBT(itemTag);
			tag.setTag("DisplayStack", itemTag);
		}
	}

	public void loadFromNBT(NBTTagCompound tag) {
		voltageNameIndex = tag.getInteger("VoltageIndex");
		isConsumer = tag.getBoolean("IsConsumer");
		isProducer = tag.getBoolean("IsProducer");
		if (tag.hasKey("PowerSystem")) {
			powerSystem = PowerSystemRegistry.getPowerSystemById(tag.getInteger("PowerSystem"));
		}
		isConnected = tag.getBoolean("Connected");
		outputRate = tag.getDouble("OutputRate");
		if (tag.hasKey("DisplayStack")) {
			displayStack = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("DisplayStack"));
		}
	}
}
