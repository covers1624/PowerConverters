package powercrystals.powerconverters.util;

import net.minecraft.util.ResourceLocation;
import powercrystals.powerconverters.reference.Reference;

public enum CapeType {

	OWNER(new ResourceLocation(Reference.TEXTURE_FOLDER + "capes/Debug.png")),
	UNKNOWN(new ResourceLocation(""));

	private ResourceLocation capeLocation;

	private CapeType(ResourceLocation cape) {
		capeLocation = cape;
	}

	public ResourceLocation getCapeLocation() {
		return capeLocation;
	}

	public static CapeType parse(String toParse) {
		for (CapeType cape : CapeType.values()) {
			if (cape.toString().toLowerCase().equals(toParse.toLowerCase())) {
				return cape;
			}
		}
		LogHelper.fatal("Was unable to parse cape! " + toParse);
		return UNKNOWN;
	}

}
