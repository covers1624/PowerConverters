package covers1624.powerconverters.charge;

import net.minecraft.item.ItemStack;
import cofh.api.energy.IEnergyContainerItem;
import covers1624.powerconverters.api.charge.IChargeHandler;
import covers1624.powerconverters.api.registry.PowerSystemRegistry.PowerSystem;
import covers1624.powerconverters.init.PowerSystems;

public class ChargeHandlerRedstoneFlux implements IChargeHandler {

	@Override
	public PowerSystem getPowerSystem() {
		return PowerSystems.powerSystemRedstoneFlux;
	}

	@Override
	public boolean canHandle(ItemStack stack) {
		return stack != null && stack.getItem() instanceof IEnergyContainerItem;
	}

	@Override
	public double charge(ItemStack stack, double energyInput) {
		double RF = energyInput / getPowerSystem().getScaleAmmount();
		RF -= ((IEnergyContainerItem) stack.getItem()).receiveEnergy(stack, (int) RF, false);
		return RF * getPowerSystem().getScaleAmmount();
	}

	@Override
	public double discharge(ItemStack stack, double energyRequest) {
		IEnergyContainerItem cell = (IEnergyContainerItem) stack.getItem();
		return ((cell.extractEnergy(stack, (int) (energyRequest / getPowerSystem().getScaleAmmount()), false)) * getPowerSystem().getScaleAmmount());
	}

	@Override
	public String name() {
		return "Redstone Flux";
	}

	@Override
	public boolean isItemCharged(ItemStack stack) {
		if (canHandle(stack)) {
			IEnergyContainerItem item = (IEnergyContainerItem) stack.getItem();
			if (item.getEnergyStored(stack) == item.getMaxEnergyStored(stack)) {
				return true;
			}
		}
		return false;
	}

}
