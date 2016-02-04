package covers1624.powerconverters.tile.factorization;

import covers1624.powerconverters.handler.ConfigurationHandler;
import covers1624.powerconverters.init.PowerSystems;
import covers1624.powerconverters.tile.main.TileEntityEnergyProducer;
import factorization.api.Charge;
import factorization.api.Coord;
import factorization.api.IChargeConductor;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Map.Entry;

public class TileEntityPowerConverterFactorizationProducer extends TileEntityEnergyProducer<IChargeConductor> implements IChargeConductor {
	private Charge charge = new Charge(this);
	private static final double maxCG = 2000;

	public TileEntityPowerConverterFactorizationProducer() {
		super(PowerSystems.powerSystemFactorization, 0, IChargeConductor.class);
	}

	@Override
	public double produceEnergy(double energy) {
		if (ConfigurationHandler.dissableFactorizationProducer) {
			return energy;
		}
		double CG = energy / PowerSystems.powerSystemFactorization.getScaleAmmount();
		for (Entry<ForgeDirection, IChargeConductor> output : this.getTiles().entrySet()) {
			IChargeConductor chargeCond = output.getValue();
			if (chargeCond != null) {
				if (chargeCond.getCharge().getValue() < maxCG) {
					double store = Math.min(maxCG - chargeCond.getCharge().getValue(), CG);
					chargeCond.getCharge().addValue((int) store);
					CG -= store;
					if (CG <= 0) {
						break;
					}
				}
			}
		}
		return CG * PowerSystems.powerSystemFactorization.getScaleAmmount();
	}

	@Override
	public Charge getCharge() {
		return this.charge;
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
