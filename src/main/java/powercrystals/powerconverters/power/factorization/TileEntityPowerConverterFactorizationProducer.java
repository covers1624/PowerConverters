package powercrystals.powerconverters.power.factorization;

import factorization.api.Charge;
import factorization.api.Coord;
import factorization.api.IChargeConductor;

import java.util.Map.Entry;

import net.minecraftforge.common.util.ForgeDirection;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.init.PowerSystems;
import powercrystals.powerconverters.power.TileEntityEnergyProducer;

public class TileEntityPowerConverterFactorizationProducer extends TileEntityEnergyProducer<IChargeConductor> implements IChargeConductor {
	private Charge _charge = new Charge(this);
	private static final double _maxCG = 2000;

	public TileEntityPowerConverterFactorizationProducer() {
		super(PowerSystems.powerSystemFactorization, 0, IChargeConductor.class);
	}

	@Override
	public double produceEnergy(double energy) {
		double CG = energy / PowerSystems.powerSystemFactorization.getInternalEnergyPerOutput();
		for (Entry<ForgeDirection, IChargeConductor> output : this.getTiles().entrySet()) {
			IChargeConductor o = output.getValue();
			if (o != null) {
				if (o.getCharge().getValue() < _maxCG) {
					double store = Math.min(_maxCG - o.getCharge().getValue(), CG);
					o.getCharge().addValue((int)store);
					CG -= store;
					if (CG <= 0) {
						break;
					}
				}
			}
		}
		return CG * PowerSystems.powerSystemFactorization.getInternalEnergyPerOutput();
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
