package covers1624.powerconverters.tile.ic2;

import covers1624.powerconverters.handler.ConfigurationHandler;
import covers1624.powerconverters.init.PowerSystems;
import covers1624.powerconverters.tile.main.TileEntityEnergyProducer;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySource;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityIndustrialCraftProducer extends TileEntityEnergyProducer<IEnergySource> implements IEnergySource {
	private boolean isAddedToEnergyNet;
	private boolean didFirstAddToNet;

	private double eu;

	public TileEntityIndustrialCraftProducer(int voltageIndex) {
		super(PowerSystems.powerSystemIndustrialCraft, voltageIndex, IEnergySource.class);
	}

	@Override
	public void updateEntity() {

		if (!didFirstAddToNet && !worldObj.isRemote) {
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			didFirstAddToNet = true;
			isAddedToEnergyNet = true;
		}
		super.updateEntity();
	}

	@Override
	public void onChunkUnload() {
		MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
	}

	@Override
	public void validate() {
		super.validate();
		if (!isAddedToEnergyNet) {
			didFirstAddToNet = false;
		}
	}

	@Override
	public void invalidate() {
		if (isAddedToEnergyNet) {
			if (!worldObj.isRemote) {
				MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
			}
			isAddedToEnergyNet = false;
		}
		super.invalidate();
	}

	@Override
	public double produceEnergy(double energy) {

		if (ConfigurationHandler.dissableIC2Producer) {
			return energy;
		}

		double eu = energy / PowerSystems.powerSystemIndustrialCraft.getScaleAmmount();
		double usedEu = Math.min(eu, getMaxEnergyOutput() - this.eu);
		this.eu += usedEu;
		return (eu - usedEu) * PowerSystems.powerSystemIndustrialCraft.getScaleAmmount();
	}

	public double getMaxEnergyOutput() {
		return getPowerSystem().getVoltageValues()[getVoltageIndex()];
	}

	@Override
	public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
		return true;
	}

	@Override
	public double getOfferedEnergy() {
		return Math.min(eu, getMaxEnergyOutput());
	}

	@Override
	public void drawEnergy(double amount) {

		eu -= amount;
	}

	@Override
	public int getSourceTier() {
		return getVoltageIndex() + 1;
	}
}