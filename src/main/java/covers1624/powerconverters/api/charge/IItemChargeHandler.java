package covers1624.powerconverters.api.charge;

import covers1624.powerconverters.registry.PowerSystemRegistry.PowerSystem;
import net.minecraft.item.ItemStack;

/**
 * Used for item charging with the Universal Charger.
 */
public interface IItemChargeHandler {

	/**
	 * Gets the Power System for the Charge Handler.
	 *
	 * @return PowerSystem.
	 */
	PowerSystem getPowerSystem();

	/**
	 * Return weather this Handler can charge the item given.
	 *
	 * @param stack Item to charge.
	 * @return Can it charge.
	 */
	boolean canHandle(ItemStack stack);

	/**
	 * Do actual item charging.
	 *
	 * @param stack       The Item to charge.
	 * @param energyInput Amount of energy available.
	 * @return Amount of energy used.
	 */
	double charge(ItemStack stack, double energyInput);

	/**
	 * Do actual item Discharge.
	 *
	 * @param stack         The Item to discharge.
	 * @param energyRequest Amount of energy to drain.
	 * @return Amount of energy consumed from item.
	 */
	double discharge(ItemStack stack, double energyRequest);

	/**
	 * Return true if the item is charged.
	 *
	 * @param stack Stack to check.
	 * @return weather the stack is charged or not.
	 */
	boolean isItemCharged(ItemStack stack);

	/**
	 * @return Name of the handler.
	 */
	String name();
}
