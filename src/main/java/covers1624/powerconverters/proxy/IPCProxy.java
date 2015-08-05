package covers1624.powerconverters.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IPCProxy {

	public void initRendering();

	public EntityPlayer getClientPlayer();

	public World getClientWorld();

}
