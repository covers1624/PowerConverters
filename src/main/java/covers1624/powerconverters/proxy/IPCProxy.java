package covers1624.powerconverters.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IPCProxy {

	void initRendering();

	EntityPlayer getClientPlayer();

	World getClientWorld();
}
