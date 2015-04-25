package powercrystals.powerconverters.gui.options;

public enum EnumCapeResulution {

	HIGH,
	MEDIUM,
	LOW;

	public static String configCategory = "Capes";

	private EnumCapeResulution() {

	}

	public static EnumCapeResulution parse(String string) {
		return valueOf(string);
	}
}
