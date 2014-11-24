package powercrystals.powerconverters.init;

import powercrystals.powerconverters.common.TileEntityCharger;
import powercrystals.powerconverters.power.PowerSystem;
import powercrystals.powerconverters.power.ic2.ChargeHandlerIndustrialCraft;
import powercrystals.powerconverters.power.te.ChargeHandlerThermalExpansion;

public class PowerSystems {
	
	@Deprecated
	public static PowerSystem powerSystemBuildCraft;
	public static PowerSystem powerSystemIndustrialCraft;
	public static PowerSystem powerSystemSteam;
	public static PowerSystem powerSystemFactorization;
	public static PowerSystem powerSystemThermalExpansion;

	public static void init() {
		powerSystemBuildCraft = new PowerSystem("BuildCraft", "BC", 4375, 4375, null, null, "MJ/t");
		powerSystemIndustrialCraft = new PowerSystem("IndustrialCraft", "IC2", 1800, 1800, new String[] { "LV", "MV", "HV", "EV" }, new int[] { 32, 128, 512, 2048 }, "EU/t");
		powerSystemSteam = new PowerSystem("Steam", "STEAM", 875, 875, null, null, "mB/t");
		powerSystemFactorization = new PowerSystem("Factorization", "FZ", 175, 175, null, null, "CG/t");
		powerSystemThermalExpansion = new PowerSystem("ThermalExpansion", "TE", 1000, 1000, null, null, "RF/t");
		PowerSystem.registerPowerSystem(powerSystemBuildCraft);
		PowerSystem.registerPowerSystem(powerSystemIndustrialCraft);
		PowerSystem.registerPowerSystem(powerSystemSteam);
		PowerSystem.registerPowerSystem(powerSystemFactorization);
		PowerSystem.registerPowerSystem(powerSystemThermalExpansion);
	}
	
	public static void initChargeHandlers(){
		TileEntityCharger.registerChargeHandler(new ChargeHandlerThermalExpansion());
		TileEntityCharger.registerChargeHandler(new ChargeHandlerIndustrialCraft());
	}

}
