package covers1624.powerconverters.api.registry;

import covers1624.powerconverters.api.charge.IChargeHandler;
import covers1624.powerconverters.util.LogHelper;
import cpw.mods.fml.common.Loader;

import java.util.ArrayList;
import java.util.List;

public class UniversalChargerRegistry {

	private static List<IChargeHandler> chargeHandlers = new ArrayList<IChargeHandler>();

	public static void registerChargeHandler(IChargeHandler chargeHandler) {
		LogHelper.trace("Registering ChargeHandler for mod %s with the name of %s.", Loader.instance().activeModContainer().getModId(), chargeHandler.name());
		if (chargeHandlers.contains(chargeHandler)) {
			LogHelper.error("Failed to register ChargeHandler %s it is already registered.");
		} else {
			chargeHandlers.add(chargeHandler);
		}
	}

	public static List<IChargeHandler> getChargeHandlers() {
		return chargeHandlers;
	}

}
