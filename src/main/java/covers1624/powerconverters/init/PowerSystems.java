package covers1624.powerconverters.init;

import covers1624.powerconverters.api.registry.PowerSystemRegistry;
import covers1624.powerconverters.api.registry.PowerSystemRegistry.PowerSystem;
import covers1624.powerconverters.api.registry.UniversalChargerRegistry;
import covers1624.powerconverters.charge.ChargeHandlerIndustrialCraft;
import covers1624.powerconverters.charge.ChargeHandlerRedstoneFlux;
import covers1624.powerconverters.handler.ConfigurationHandler;
import covers1624.powerconverters.util.RFHelper;

public class PowerSystems {

	public static PowerSystem powerSystemIndustrialCraft;
	public static PowerSystem powerSystemSteam;
	public static PowerSystem powerSystemFactorization;
	public static PowerSystem powerSystemRedstoneFlux;
	public static PowerSystem powerSystemPneumaticCraft;

	public static void init() {
		powerSystemIndustrialCraft = new PowerSystem("IndustrialCraft", "IC2", 1800, new String[] { "LV", "MV", "HV", "EV" }, new int[] { 32, 128, 512, 2048 }, "EU/t");
		powerSystemSteam = new PowerSystem("Steam", "STEAM", 875, "mB/t");
		powerSystemFactorization = new PowerSystem("Factorization", "FZ", 175, "CG/t");
		powerSystemRedstoneFlux = new PowerSystem("RedstoneFlux", "RF", 1000, "RF/t");
		powerSystemPneumaticCraft = new PowerSystem("PneumaticCraft", "PSI", 100, "PSI");

		powerSystemIndustrialCraft.setConsumerState(ConfigurationHandler.dissableIC2Consumer).setProducerState(ConfigurationHandler.dissableIC2Producer);
		powerSystemSteam.setConsumerState(ConfigurationHandler.dissableSteamConsumer).setProducerState(ConfigurationHandler.dissableSteamProducer);
		powerSystemFactorization.setConsumerState(ConfigurationHandler.dissableFactorizationConsumer).setProducerState(ConfigurationHandler.dissableFactorizationProducer);
		powerSystemRedstoneFlux.setConsumerState(ConfigurationHandler.dissableRFConsumer).setProducerState(ConfigurationHandler.dissableRFProducer);
		// powerSystemPneumaticCraft.setConsumerState(ConfigurationHandler.dissableIC2Consumer).setProducerState(ConfigurationHandler.dissableIC2Producer);

		PowerSystemRegistry.registerPowerSystem(powerSystemIndustrialCraft);
		PowerSystemRegistry.registerPowerSystem(powerSystemSteam);
		PowerSystemRegistry.registerPowerSystem(powerSystemFactorization);
		PowerSystemRegistry.registerPowerSystem(powerSystemRedstoneFlux);
	}

	public static void initChargeHandlers() {
		if (RFHelper.iEnergyContainerItemExists) {
			UniversalChargerRegistry.registerChargeHandler(new ChargeHandlerRedstoneFlux());
		}
		if (Recipes.industrialCraftFound) {
			UniversalChargerRegistry.registerChargeHandler(new ChargeHandlerIndustrialCraft());
		}
	}
}
