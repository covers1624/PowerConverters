package powercrystals.powerconverters.power.te;

import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.ItemEnergyContainer;
import net.minecraft.item.ItemStack;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.common.IChargeHandler;
import powercrystals.powerconverters.init.PowerSystems;
import powercrystals.powerconverters.power.PowerSystem;

public class ChargeHandlerThermalExpansion implements IChargeHandler {

	@Override
	public PowerSystem getPowerSystem() {
		return PowerSystems.powerSystemThermalExpansion;
	}

	@Override
	public boolean canHandle(ItemStack stack) {
		return stack != null && stack.getItem() instanceof IEnergyContainerItem;
	}

	@Override
	public double charge(ItemStack stack, double energyInput) {
		double RF = energyInput / getPowerSystem().getInternalEnergyPerOutput();
		RF -= ((ItemEnergyContainer) stack.getItem()).receiveEnergy(stack, (int) RF, false);
		return RF * getPowerSystem().getInternalEnergyPerOutput();
	}

	@Override
	public double discharge(ItemStack stack, double energyRequest) {
		ItemEnergyContainer cell = (ItemEnergyContainer) stack.getItem();
		return ((cell.extractEnergy(stack, (int) (energyRequest / getPowerSystem().getInternalEnergyPerOutput()), false)) * getPowerSystem().getInternalEnergyPerOutput());
	}

}
