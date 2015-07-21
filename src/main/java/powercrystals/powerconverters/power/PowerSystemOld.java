package powercrystals.powerconverters.power;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraftforge.common.config.Configuration;

/**
 * This is dead see {@link PowerSystemRegistry} for the new class.
 */
@Deprecated
public class PowerSystemOld {
	private static Map<Integer, PowerSystemOld> powerSystems = new HashMap<Integer, PowerSystemOld>();
	private static Integer nextPowerSystemId = 0;

	private String abbreviation;
	private String name;
	private int internalEnergyPerInput;
	private int internalEnergyPerOutput;
	private String[] voltageNames;
	private int[] voltageValues;
	private String unit;
	private int id;

	@Deprecated
	public PowerSystemOld(String name, String abbreviation, int energyPerInput, int energyPerOutput, String[] voltageNames, int[] voltageValues, String unit) {
		this.name = name;
		this.abbreviation = abbreviation;
		this.internalEnergyPerInput = energyPerInput;
		this.internalEnergyPerOutput = energyPerOutput;
		this.voltageNames = voltageNames;
		this.voltageValues = voltageValues;
		this.unit = unit;
	}

	@Deprecated
	public static void registerPowerSystem(PowerSystemOld powerSystem) {
		powerSystems.put(nextPowerSystemId, powerSystem);
		powerSystem.id = nextPowerSystemId;
		nextPowerSystemId++;
	}

	@Deprecated
	public static PowerSystemOld getPowerSystemById(int id) {
		return powerSystems.get(id);
	}

	@Deprecated
	public String getAbbreviation() {
		return abbreviation;
	}

	@Deprecated
	public String getName() {
		return name;
	}

	@Deprecated
	public static void loadConfig(Configuration c) {
		String powerRatioComment = "Not all power systems listed here are necessarily used; they may be provided so that\r\n" + "the ratios are all stored in a single place and for possible future use.";

		c.addCustomCategoryComment("PowerRatios", powerRatioComment);

		for (Entry<Integer, PowerSystemOld> p : powerSystems.entrySet()) {
			String configSection = "PowerRatios." + p.getValue().name;
			p.getValue().internalEnergyPerInput = c.get(configSection, p.getValue().name + "InternalEnergyPerEachInput", p.getValue().internalEnergyPerInput).getInt();
			p.getValue().internalEnergyPerOutput = c.get(configSection, p.getValue().name + "InternalEnergyPerEachOutput", p.getValue().internalEnergyPerOutput).getInt();
		}
	}

	@Deprecated
	public int getInternalEnergyPerInput() {
		return internalEnergyPerInput;
	}

	@Deprecated
	public int getInternalEnergyPerOutput() {
		return internalEnergyPerOutput;
	}

	@Deprecated
	public String[] getVoltageNames() {
		return voltageNames;
	}

	@Deprecated
	public int[] getVoltageValues() {
		return voltageValues;
	}

	@Deprecated
	public String getUnit() {
		return unit;
	}

	@Deprecated
	public int getId() {
		return id;
	}
}
