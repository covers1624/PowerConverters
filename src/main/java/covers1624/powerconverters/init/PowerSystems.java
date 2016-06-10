package covers1624.powerconverters.init;

import covers1624.powerconverters.api.recipe.AbstractRecipeModule;
import covers1624.powerconverters.charge.ItemChargeHandlerIndustrialCraft;
import covers1624.powerconverters.charge.ItemChargeHandlerRedstoneFlux;
import covers1624.powerconverters.handler.ConfigurationHandler;
import covers1624.powerconverters.registry.PowerSystemRegistry;
import covers1624.powerconverters.registry.PowerSystemRegistry.PowerSystem;
import covers1624.powerconverters.registry.UniversalChargerRegistry;
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

        powerSystemIndustrialCraft.setConsumerState(ConfigurationHandler.disableIC2Consumer).setProducerState(ConfigurationHandler.disableIC2Producer);
        powerSystemSteam.setConsumerState(ConfigurationHandler.disableSteamConsumer).setProducerState(ConfigurationHandler.disableSteamProducer);
        powerSystemFactorization.setConsumerState(ConfigurationHandler.disableFactorizationConsumer).setProducerState(ConfigurationHandler.disableFactorizationProducer);
        powerSystemRedstoneFlux.setConsumerState(ConfigurationHandler.disableRFConsumer).setProducerState(ConfigurationHandler.disableRFProducer);
        // powerSystemPneumaticCraft.setConsumerState(ConfigurationHandler.disableIC2Consumer).setProducerState(ConfigurationHandler.disableIC2Producer);

        PowerSystemRegistry.registerPowerSystem(powerSystemIndustrialCraft);
        PowerSystemRegistry.registerPowerSystem(powerSystemSteam);
        PowerSystemRegistry.registerPowerSystem(powerSystemFactorization);
        PowerSystemRegistry.registerPowerSystem(powerSystemRedstoneFlux);
    }

    public static void initChargeHandlers() {
        if (RFHelper.iEnergyContainerItemExists) {
            UniversalChargerRegistry.registerChargeHandler(new ItemChargeHandlerRedstoneFlux());
        }
        if (AbstractRecipeModule.ic2Found) {
            UniversalChargerRegistry.registerChargeHandler(new ItemChargeHandlerIndustrialCraft());
        }
    }
}
