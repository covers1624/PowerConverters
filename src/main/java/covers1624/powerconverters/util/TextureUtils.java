package covers1624.powerconverters.util;

public class TextureUtils {

	public static String stripUnlocalizedName(String unlocalizedName) {
		if (unlocalizedName.contains("item.")) {
			return unlocalizedName.replaceFirst("item.", "");
		} else if (unlocalizedName.contains("tile.")) {
			return unlocalizedName.replaceFirst("tile.", "");
		}
		LogHelper.fatal("Unable to strip UnlocalizedName %s", unlocalizedName);
		return null;
	}

}
