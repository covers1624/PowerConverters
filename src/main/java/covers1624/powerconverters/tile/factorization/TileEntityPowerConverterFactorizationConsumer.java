package covers1624.powerconverters.tile.factorization;

import covers1624.powerconverters.init.PowerSystems;
import covers1624.powerconverters.tile.main.TileEntityEnergyConsumer;
import net.minecraft.util.MathHelper;
import factorization.api.Charge;
import factorization.api.Coord;
import factorization.api.IChargeConductor;

public class TileEntityPowerConverterFactorizationConsumer extends TileEntityEnergyConsumer<IChargeConductor> implements IChargeConductor {
	private Charge _charge = new Charge(this);
	private double _chargeLastTick = 0;
	private static final int _maxCG = 2000;

	public TileEntityPowerConverterFactorizationConsumer() {
		super(PowerSystems.powerSystemFactorization, 0, IChargeConductor.class);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (worldObj.isRemote) {
			return;
		}

		if (this._charge.getValue() < _maxCG) {
			this._charge.update();
		}

		if (this._charge.getValue() > 0) {
			double used = _charge.tryTake(_charge.getValue());
			_chargeLastTick = MathHelper.floor_double(used);
			storeEnergy((int) (used * PowerSystems.powerSystemFactorization.getScaleAmmount()));
		} else {
			this._chargeLastTick = 0;
		}
	}

	@Override
	public double getInputRate() {
		return this._chargeLastTick;
	}

	@Override
	public Charge getCharge() {
		return this._charge;
	}

	@Override
	public String getInfo() {
		return null;
	}

	@Override
	public Coord getCoord() {
		return new Coord(this);
	}
}
