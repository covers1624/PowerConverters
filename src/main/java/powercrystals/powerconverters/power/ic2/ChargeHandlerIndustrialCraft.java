package powercrystals.powerconverters.power.ic2;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.item.ItemStack;
import powercrystals.powerconverters.common.IChargeHandler;
import powercrystals.powerconverters.init.PowerSystems;
import powercrystals.powerconverters.power.PowerSystem;

public class ChargeHandlerIndustrialCraft implements IChargeHandler {
	@Override
	public PowerSystem getPowerSystem() {
		return PowerSystems.powerSystemIndustrialCraft;
	}

	@Override
	public boolean canHandle(ItemStack stack) {
		if (stack == null || !(stack.getItem() instanceof IElectricItem)) {
			return false;
		}
		return true;
	}

	@Override
	public double charge(ItemStack stack, double energyInput) {
		double eu = energyInput / PowerSystems.powerSystemIndustrialCraft.getInternalEnergyPerOutput();
		eu -= ElectricItem.manager.charge(stack, eu, ((IElectricItem) stack.getItem()).getTier(stack), false, false);
		return eu * PowerSystems.powerSystemIndustrialCraft.getInternalEnergyPerOutput();
	}

	@Override
	public double discharge(ItemStack stack, double energyRequest) {
		double eu = energyRequest / PowerSystems.powerSystemIndustrialCraft.getInternalEnergyPerInput();
		eu = ElectricItem.manager.discharge(stack, eu, ((IElectricItem) stack.getItem()).getTier(stack), false, false, false);
		return eu * PowerSystems.powerSystemIndustrialCraft.getInternalEnergyPerInput();
	}

	@Override
	public String name() {
		return "Industrial Craft";
	}

	@Override
	public boolean isItemCharged(ItemStack stack) {
		if (canHandle(stack)) {
			IElectricItem item = (IElectricItem) stack.getItem();
			if (item.getMaxCharge(stack) == ElectricItem.manager.getCharge(stack)) {
				return true;
			}
		}
		return false;
	}
}
