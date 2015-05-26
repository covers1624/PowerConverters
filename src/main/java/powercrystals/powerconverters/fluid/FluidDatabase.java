package powercrystals.powerconverters.fluid;

import net.minecraftforge.fluids.FluidStack;

public class FluidDatabase {
	public static String supportedFluids[] = { "steam", "Steam"/* , "ic2superheatedsteam" */};

	public static boolean isFluidSupported(FluidStack fluid) {
		for (int i = 0; i < supportedFluids.length; i++) {
			if (supportedFluids[i].equals(fluid.getFluidID())) {
				return true;
			}
		}
		return false;
	}
}
