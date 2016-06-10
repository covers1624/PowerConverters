package covers1624.powerconverters.tile.factorization;

import covers1624.powerconverters.handler.ConfigurationHandler;
import covers1624.powerconverters.init.PowerSystems;
import covers1624.powerconverters.tile.main.TileEntityEnergyConsumer;
import factorization.api.Charge;
import factorization.api.Coord;
import factorization.api.IChargeConductor;
import net.minecraft.util.MathHelper;

public class TileEntityPowerConverterFactorizationConsumer extends TileEntityEnergyConsumer<IChargeConductor> implements IChargeConductor {
    private Charge charge = new Charge(this);
    private double chargeLastTick = 0;
    private static final int maxCG = 2000;

    public TileEntityPowerConverterFactorizationConsumer() {
        super(PowerSystems.powerSystemFactorization, 0, IChargeConductor.class);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) {
            return;
        }

        if (ConfigurationHandler.disableFactorizationConsumer) {
            return;
        }

        if (this.charge.getValue() < maxCG) {
            this.charge.update();
        }

        if (this.charge.getValue() > 0) {
            double used = charge.tryTake(charge.getValue());
            chargeLastTick = MathHelper.floor_double(used);
            storeEnergy((int) (used * PowerSystems.powerSystemFactorization.getScaleAmmount()), false);
        } else {
            this.chargeLastTick = 0;
        }
    }

    @Override
    public double getInputRate() {
        return this.chargeLastTick;
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
