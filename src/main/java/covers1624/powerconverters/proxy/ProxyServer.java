package covers1624.powerconverters.proxy;

import covers1624.powerconverters.waila.IWailaSync;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ProxyServer implements IPCProxy {

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

	@Override
	public List<String> getWailaInfo(IWailaSync sync) {
		List<String> info = new ArrayList<String>();
		sync.addWailaInfo(info);
		return info;
	}
}
