package covers1624.powerconverters.api.charge;

import covers1624.powerconverters.registry.PowerSystemRegistry;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by covers1624 on 12/30/2015.
 */
public interface IConduitChargeHandler {

	/**
	 * Gets the Power System for the Charge Handler.
	 *
	 * @return PowerSystem.
	 */
	PowerSystemRegistry.PowerSystem getPowerSystem();

	/**
	 * Return weather this Handler can charge the TileEntity given.
	 *
	 * @param tileEntity Item to charge.
	 * @return Can it charge.
	 */
	boolean canHandle(TileEntity tileEntity);

	/**
	 * Do actual TileEntity charging.
	 *
	 * @param tileEntity  The TileEntity to charge.
	 * @param energyInput Amount of energy available.
	 * @return Amount of energy used.
	 */
	double charge(TileEntity tileEntity, double energyInput);

	/**
	 * Do actual TileEntity Discharge.
	 *
	 * @param tileEntity    The TileEntity to discharge.
	 * @param energyRequest Amount of energy to drain.
	 * @return Amount of energy consumed from TileEntity.
	 */
	double discharge(TileEntity tileEntity, double energyRequest);

	/**
	 * Return true if the TileEntity is charged.
	 *
	 * @param tileEntity TileEntity to check.
	 * @return weather the TileEntity is charged or not.
	 */
	boolean isItemCharged(TileEntity tileEntity);

	/**
	 * @return Name of the handler.
	 */
	String name();
}
