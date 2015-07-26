package covers1624.powerconverters.api.bridge;

import net.minecraftforge.common.util.ForgeDirection;
import covers1624.powerconverters.api.registry.PowerSystemRegistry.PowerSystem;

public class BridgeSideData {
	public ForgeDirection side;
	public PowerSystem powerSystem;
	public boolean isConsumer;
	public boolean isProducer;
	public boolean isConnected;
	public int voltageNameIndex;
	public double outputRate;
}
