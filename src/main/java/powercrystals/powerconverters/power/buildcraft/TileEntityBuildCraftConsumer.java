package powercrystals.powerconverters.power.buildcraft;

import java.util.ArrayList;

import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.init.PowerSystems;
import powercrystals.powerconverters.power.TileEntityEnergyConsumer;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import buildcraft.api.power.PowerHandler.Type;
@Deprecated
public class TileEntityBuildCraftConsumer extends TileEntityEnergyConsumer<IPowerReceptor> implements IPowerReceptor {
	private PowerHandler _powerHandler;
	private double _mjLastTick = 0;
	private long _lastTickInjected;

	public TileEntityBuildCraftConsumer() {
		super(PowerSystems.powerSystemBuildCraft, 0, IPowerReceptor.class);
		_powerHandler = new PowerHandler(this, Type.MACHINE);
		_powerHandler.configure(2, 100, 1, 1000);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (worldObj.getWorldTime() - _lastTickInjected > 1) {
			_lastTickInjected = worldObj.getWorldTime();
			_mjLastTick = 0;
		}
		if (_powerHandler.getEnergyStored() > 0) {
			double energy = _powerHandler.useEnergy(0, powerRequest(), true);
			if (_lastTickInjected != worldObj.getWorldTime()) {
				_lastTickInjected = worldObj.getWorldTime();
				_mjLastTick = 0;
			}

			_mjLastTick += MathHelper.floor_double(energy);
			storeEnergy((energy * PowerSystems.powerSystemBuildCraft.getInternalEnergyPerInput()));
		}
	}

	public double powerRequest() {
		return getTotalEnergyDemand() / PowerSystems.powerSystemBuildCraft.getInternalEnergyPerInput();
	}

	@Override
	public double getInputRate() {
		return _mjLastTick;
	}

	@Override
	public PowerReceiver getPowerReceiver(ForgeDirection side) {

		return _powerHandler.getPowerReceiver();
	}

	@Override
	public void doWork(PowerHandler workProvider) {

	}

	@Override
	public World getWorld() {

		return worldObj;
	}
}
