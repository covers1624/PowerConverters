package powercrystals.powerconverters.power.buildcraft;

import cofh.api.energy.IEnergyContainerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.common.IChargeHandler;
import powercrystals.powerconverters.init.PowerSystems;
import powercrystals.powerconverters.power.PowerSystem;

public class ChargeHandlerThermalExpansion implements IChargeHandler {
	@Override
	public PowerSystem getPowerSystem() {
		return PowerSystems.powerSystemBuildCraft;
	}

	@Override
	public boolean canHandle(ItemStack stack) {
		return stack != null && stack.getItem() instanceof IEnergyContainerItem;
	}

	@Override
	public double charge(ItemStack stack, double energyInput) {
		double mj = energyInput / getPowerSystem().getInternalEnergyPerOutput();
		mj -= ((IEnergyContainerItem) stack.getItem()).receiveEnergy(stack, MathHelper.floor_double(mj / 10), false) * 10;
		return mj * getPowerSystem().getInternalEnergyPerOutput();
	}

	@Override
	public double discharge(ItemStack stack, double energyRequest) {
		double mj = energyRequest / getPowerSystem().getInternalEnergyPerInput();
		mj = ((IEnergyContainerItem) stack.getItem()).extractEnergy(stack, MathHelper.floor_double(mj / 10), false) * 10;
		return mj * getPowerSystem().getInternalEnergyPerInput();
	}
}
