package powercrystals.powerconverters.init;

import powercrystals.powerconverters.common.TileEntityCharger;
import powercrystals.powerconverters.power.PowerSystemRegistry;
import powercrystals.powerconverters.power.PowerSystemRegistry.PowerSystem;
import powercrystals.powerconverters.power.ic2.ChargeHandlerIndustrialCraft;
import powercrystals.powerconverters.power.redstoneflux.ChargeHandlerRedstoneFlux;
import powercrystals.powerconverters.util.RFHelper;

public class PowerSystems {

	public static PowerSystem powerSystemIndustrialCraft;
	public static PowerSystem powerSystemSteam;
	public static PowerSystem powerSystemFactorization;
	public static PowerSystem powerSystemRedstoneFlux;

	public static void init() {
		powerSystemIndustrialCraft = new PowerSystem("IndustrialCraft", "IC2", 1800, new String[] { "LV", "MV", "HV", "EV" }, new int[] { 32, 128, 512, 2048 }, "EU/t");
		powerSystemSteam = new PowerSystem("Steam", "STEAM", 875, null, null, "mB/t");
		powerSystemFactorization = new PowerSystem("Factorization", "FZ", 175, null, null, "CG/t");
		powerSystemRedstoneFlux = new PowerSystem("RedstoneFlux", "RF", 1000, null, null, "RF/t");
		PowerSystemRegistry.registerPowerSystem(powerSystemIndustrialCraft);
		PowerSystemRegistry.registerPowerSystem(powerSystemSteam);
		PowerSystemRegistry.registerPowerSystem(powerSystemFactorization);
		PowerSystemRegistry.registerPowerSystem(powerSystemRedstoneFlux);
	}

	public static void initChargeHandlers() {
		if (RFHelper.iEnergyContainerItemExists) {
			TileEntityCharger.registerChargeHandler(new ChargeHandlerRedstoneFlux());
		}
		if (Recipes.industrialCraftFound) {
			TileEntityCharger.registerChargeHandler(new ChargeHandlerIndustrialCraft());
		}
	}
}
