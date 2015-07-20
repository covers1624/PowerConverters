package powercrystals.powerconverters.power;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraftforge.common.config.Configuration;

public class PowerSystem {
	private static Map<Integer, PowerSystem> powerSystems = new HashMap<Integer, PowerSystem>();
	private static Integer nextPowerSystemId = 0;

	private String abbreviation;
	private String name;
	private int internalEnergyPerInput;
	private int internalEnergyPerOutput;
	private String[] voltageNames;
	private int[] voltageValues;
	private String unit;
	private int id;

	public PowerSystem(String name, String abbreviation, int energyPerInput, int energyPerOutput, String[] voltageNames, int[] voltageValues, String unit) {
		this.name = name;
		this.abbreviation = abbreviation;
		this.internalEnergyPerInput = energyPerInput;
		this.internalEnergyPerOutput = energyPerOutput;
		this.voltageNames = voltageNames;
		this.voltageValues = voltageValues;
		this.unit = unit;
	}

	public static void registerPowerSystem(PowerSystem powerSystem) {
		powerSystems.put(nextPowerSystemId, powerSystem);
		powerSystem.id = nextPowerSystemId;
		nextPowerSystemId++;
	}

	public static PowerSystem getPowerSystemById(int id) {
		return powerSystems.get(id);
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public String getName() {
		return name;
	}

	public static void loadConfig(Configuration c) {
		String powerRatioComment = "Not all power systems listed here are necessarily used; they may be provided so that\r\n" + "the ratios are all stored in a single place and for possible future use.";

		c.addCustomCategoryComment("PowerRatios", powerRatioComment);

		for (Entry<Integer, PowerSystem> p : powerSystems.entrySet()) {
			String configSection = "PowerRatios." + p.getValue().name;
			p.getValue().internalEnergyPerInput = c.get(configSection, p.getValue().name + "InternalEnergyPerEachInput", p.getValue().internalEnergyPerInput).getInt();
			p.getValue().internalEnergyPerOutput = c.get(configSection, p.getValue().name + "InternalEnergyPerEachOutput", p.getValue().internalEnergyPerOutput).getInt();
		}
	}

	public int getInternalEnergyPerInput() {
		return internalEnergyPerInput;
	}

	public int getInternalEnergyPerOutput() {
		return internalEnergyPerOutput;
	}

	public String[] getVoltageNames() {
		return voltageNames;
	}

	public int[] getVoltageValues() {
		return voltageValues;
	}

	public String getUnit() {
		return unit;
	}

	public int getId() {
		return id;
	}
}
