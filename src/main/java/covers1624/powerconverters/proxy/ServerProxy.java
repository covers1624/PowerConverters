package covers1624.powerconverters.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ServerProxy implements IPCProxy {

	@Override
	public void initRendering() {
	}

	@Override
	public EntityPlayer getClientPlayer() {
		return null;
	}

	@Override
	public World getClientWorld() {
		// TODO Auto-generated method stub
		return null;
	}
}
