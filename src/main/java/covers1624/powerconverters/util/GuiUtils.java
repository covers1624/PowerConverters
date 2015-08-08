package covers1624.powerconverters.util;

import net.minecraftforge.common.util.ForgeDirection;

public class GuiUtils {

	public static String dirToAbbreviation(ForgeDirection dir) {
		return dirToAbbreviation(dir.ordinal());
	}

	public static String dirToAbbreviation(int dir) {
		switch (dir) {
		case 0:
			return "D";
		case 1:
			return "U";
		case 2:
			return "N";
		case 3:
			return "S";
		case 4:
			return "W";
		case 5:
			return "E";
		default:
			return "NULL";
		}
	}

}
