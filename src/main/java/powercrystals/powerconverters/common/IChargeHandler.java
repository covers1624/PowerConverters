package powercrystals.powerconverters.common;

import net.minecraft.item.ItemStack;
import powercrystals.powerconverters.power.PowerSystemRegistry.PowerSystem;

public interface IChargeHandler {
	public PowerSystem getPowerSystem();

	boolean canHandle(ItemStack stack);

	double charge(ItemStack stack, double energyInput);

	double discharge(ItemStack stack, double energyRequest);

	boolean isItemCharged(ItemStack stack);

	String name();
}
