package powercrystals.powerconverters.net;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import powercrystals.powerconverters.fx.FXSparkle;

public class ProxyClient implements IPCProxy {
	@Override
	public void load() {
	}

	@Override
	public void preInit() {
		// EnumCapeResulution.init();
	}

	@Override
	public void sparkleFX(World world, double x, double y, double z, float r, float g, float b, float size, int m) {
		FXSparkle sparkle = new FXSparkle(world, x, y, z, size, r, g, b, m);
		sparkle.fake = false;
		sparkle.noClip = true;
		Minecraft.getMinecraft().effectRenderer.addEffect(sparkle);

	}
}
