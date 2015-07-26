package covers1624.powerconverters.api.charge;

import net.minecraft.item.ItemStack;
import covers1624.powerconverters.api.registry.PowerSystemRegistry.PowerSystem;

public interface IChargeHandler {
	public PowerSystem getPowerSystem();

	boolean canHandle(ItemStack stack);

	double charge(ItemStack stack, double energyInput);

	double discharge(ItemStack stack, double energyRequest);

	boolean isItemCharged(ItemStack stack);

	String name();
}
