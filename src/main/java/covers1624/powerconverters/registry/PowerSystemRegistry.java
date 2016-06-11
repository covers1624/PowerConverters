package covers1624.powerconverters.registry;

import covers1624.powerconverters.util.LogHelper;
import cpw.mods.fml.common.Loader;

import java.util.HashMap;

public class PowerSystemRegistry {

    private static HashMap<Integer, PowerSystem> powerSystems = new HashMap<Integer, PowerSystem>();
    private static int nextPowerSystemId = 0;

    public static PowerSystem createNewPowerSystem(String name, String abbreviation, int scaleAmount, String[] voltageNames, int[] voltageValues, String unit) {
        return new PowerSystem(name, abbreviation, scaleAmount, voltageNames, voltageValues, unit);
    }

    public static void registerPowerSystem(PowerSystem powerSystem) {
        registerPowerSystem(powerSystem, nextPowerSystemId, true);
        nextPowerSystemId++;
    }

    public static void registerPowerSystem(PowerSystem powerSystem, int id, boolean force) {
        if (powerSystems.get(id) == null) {
            powerSystem.id = id;
            powerSystems.put(id, powerSystem);
        } else {
            LogHelper.trace("PowerSystem allready Registered %s and %s is trying to be registered. It is recommended that you let the mod decide what to do here.", powerSystems.get(id).getName(), powerSystem.getName());
            if (force) {
                LogHelper.warn("Mod %s is forcing that PowerSystem id %s equals %s THIS MAY BREAK ALL FUNCTIONALITY!", Loader.instance().activeModContainer().getModId(), String.valueOf(id), powerSystem.getName());
                powerSystems.remove(id);
                powerSystem.id = id;
                powerSystems.put(id, powerSystem);
            }
        }
    }

    public static void unregisterPowerSystem(int id) {
        if (powerSystems.containsKey(id)) {
            LogHelper.warn("Someone is trying to remove PowerSystem %s THIS MAY BREAK ALL FUNCTIONALITY!", powerSystems.get(id).getName());
            powerSystems.remove(id);
        }
    }

    public static PowerSystem getPowerSystemById(int id) {
        return powerSystems.get(id);
    }

    public static class PowerSystem {

        private String abbreviation;
        private String name;
        private int scaleAmmount;
        private String[] voltageNames;
        private int[] voltageValues;
        private String unit;
        private int id;
        private boolean consumerDisabled = false;
        private boolean producerDisabled = false;

        public PowerSystem(String name, String abbreviation, int scaleAmmount, String unit) {
            this(name, abbreviation, scaleAmmount, null, null, unit);
        }

        public PowerSystem(String name, String abbreviation, int scaleAmmount, String unit, boolean consumerDisabled, boolean producerDisabled) {
            this(name, abbreviation, scaleAmmount, null, null, unit, consumerDisabled, producerDisabled);
        }

        public PowerSystem(String name, String abbreviation, int scaleAmmount, String[] voltageNames, int[] voltageValues, String unit) {
            this.name = name;
            this.abbreviation = abbreviation;
            this.scaleAmmount = scaleAmmount;
            this.voltageNames = voltageNames;
            this.voltageValues = voltageValues;
            this.unit = unit;
        }

        public PowerSystem(String name, String abbreviation, int scaleAmmount, String[] voltageNames, int[] voltageValues, String unit, boolean consumerDisabled, boolean producerDisabled) {
            this.name = name;
            this.abbreviation = abbreviation;
            this.scaleAmmount = scaleAmmount;
            this.voltageNames = voltageNames;
            this.voltageValues = voltageValues;
            this.unit = unit;
            this.consumerDisabled = consumerDisabled;
            this.producerDisabled = producerDisabled;
        }

        public String getName() {
            return this.name;
        }

        public String getAbbreviation() {
            return this.abbreviation;
        }

        public String[] getVoltageNames() {
            return this.voltageNames;
        }

        public int[] getVoltageValues() {
            return this.voltageValues;
        }

        public int getScaleAmmount() {
            return scaleAmmount;
        }

        public String getUnit() {
            return this.unit;
        }

        public int getId() {
            return id;
        }

        public boolean consumerDisabled() {
            return consumerDisabled;
        }

        public boolean producerDisabled() {
            return producerDisabled;
        }

        public PowerSystem setProducerState(boolean state) {
            producerDisabled = state;
            return this;
        }

        public PowerSystem setConsumerState(boolean state) {
            consumerDisabled = state;
            return this;
        }

    }

}
