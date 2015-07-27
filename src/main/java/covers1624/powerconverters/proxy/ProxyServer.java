package covers1624.powerconverters.proxy;

import net.minecraft.entity.player.EntityPlayer;

public class ProxyServer implements IPCProxy {

	@Override
	public void initRendering() {
	}

	@Override
	public EntityPlayer getClientPlayer() {
		return null;
	}
}
