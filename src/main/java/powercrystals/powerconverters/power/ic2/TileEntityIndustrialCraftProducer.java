package powercrystals.powerconverters.power.ic2;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergySource;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.helper.ConfigurationHelper;
import powercrystals.powerconverters.init.PowerSystems;
import powercrystals.powerconverters.power.TileEntityEnergyProducer;

public class TileEntityIndustrialCraftProducer extends TileEntityEnergyProducer<IEnergyAcceptor> implements IEnergySource {
	private boolean _isAddedToEnergyNet;
	private boolean _didFirstAddToNet;

	private double eu;

	private int _packetCount;

	public TileEntityIndustrialCraftProducer() {
		this(0);
	}

	public TileEntityIndustrialCraftProducer(int voltageIndex) {
		super(PowerSystems.powerSystemIndustrialCraft, voltageIndex, IEnergyAcceptor.class);
		if (voltageIndex == 0) {
			_packetCount = ConfigurationHelper.throttleIC2LVProducer.getInt();
		} else if (voltageIndex == 1) {
			_packetCount = ConfigurationHelper.throttleIC2MVProducer.getInt();
		} else if (voltageIndex == 2) {
			_packetCount = ConfigurationHelper.throttleIC2HVProducer.getInt();
		} else if (voltageIndex == 3) {
			_packetCount = ConfigurationHelper.throttleIC2EVProducer.getInt();
		}
	}

	@Override
	public void updateEntity() {

		if (!_didFirstAddToNet && !worldObj.isRemote) {
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			_didFirstAddToNet = true;
			_isAddedToEnergyNet = true;
		}
		super.updateEntity();
	}

	@Override
	public void validate() {
		super.validate();
		if (!_isAddedToEnergyNet) {
			_didFirstAddToNet = false;
		}
	}

	@Override
	public void invalidate() {
		if (_isAddedToEnergyNet) {
			if (!worldObj.isRemote) {
				MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
			}
			_isAddedToEnergyNet = false;
		}
		super.invalidate();
	}

	@Override
	public double produceEnergy(double energy) {

		double eu = energy / PowerSystems.powerSystemIndustrialCraft.getInternalEnergyPerOutput();
		double usedEu = Math.min(eu, getMaxEnergyOutput() - this.eu);
		this.eu += usedEu;
		return (eu - usedEu) * PowerSystems.powerSystemIndustrialCraft.getInternalEnergyPerOutput();
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

		eu -= MathHelper.ceiling_double_int(amount);
	}

	@Override
	public int getSourceTier() {
		return getVoltageIndex();
	}
}