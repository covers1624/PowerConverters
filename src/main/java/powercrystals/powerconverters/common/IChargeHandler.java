package powercrystals.powerconverters.common;

import powercrystals.powerconverters.power.PowerSystem;
import net.minecraft.item.ItemStack;

public interface IChargeHandler {
	public PowerSystem getPowerSystem();

	boolean canHandle(ItemStack stack);

	double charge(ItemStack stack, double energyInput);

	double discharge(ItemStack stack, double energyRequest);
}
