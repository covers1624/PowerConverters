package powercrystals.powerconverters.power.redstoneflux;

import net.minecraft.item.ItemStack;
import powercrystals.powerconverters.common.IChargeHandler;
import powercrystals.powerconverters.init.PowerSystems;
import powercrystals.powerconverters.power.PowerSystemRegistry.PowerSystem;
import cofh.api.energy.IEnergyContainerItem;

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
