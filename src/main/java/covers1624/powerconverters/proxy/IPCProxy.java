package covers1624.powerconverters.proxy;

import covers1624.powerconverters.waila.IWailaSync;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.List;

public interface IPCProxy {

	void initRendering();

	EntityPlayer getClientPlayer();

	World getClientWorld();
}
