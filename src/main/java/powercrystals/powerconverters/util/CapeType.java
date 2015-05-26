package powercrystals.powerconverters.util;

import net.minecraft.util.ResourceLocation;
import powercrystals.powerconverters.reference.Reference;

public enum CapeType {

	OWNER(new ResourceLocation(Reference.CAPE_FOLDER + "Debug.png")),
	FAIRY(new ResourceLocation(Reference.CAPE_FOLDER + "Fairy.png")),
	UNKNOWN(new ResourceLocation(""));

	private ResourceLocation capeLocation;

	private boolean doParticles;

	private CapeType(ResourceLocation cape) {
		capeLocation = cape;
	}

	public ResourceLocation getCapeLocation() {
		return capeLocation;
	}

	public static CapeType parse(String toParse) {
		String[] capeType = toParse.split("-");
		LogHelper.info(capeType[0] + ", " + capeType[1]);
		for (CapeType cape : CapeType.values()) {
			if (cape.toString().toLowerCase().equals(capeType[0].toLowerCase())) {
				if (capeType[1].toLowerCase().equals("true")) {
					cape.doParticles = true;
				}
				return cape;
			}
		}
		LogHelper.fatal("Was unable to parse cape! " + toParse);
		return UNKNOWN;
	}

	public boolean doParticles() {
		return doParticles;
	}

}
