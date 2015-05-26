package powercrystals.powerconverters.net;

import net.minecraft.world.World;

public interface IPCProxy {

	void preInit();

	void load();

	void sparkleFX(World world, double x, double y, double z, float r, float g, float b, float size, int m);
}
