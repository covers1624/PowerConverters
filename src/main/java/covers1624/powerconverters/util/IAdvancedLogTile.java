package covers1624.powerconverters.util;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.util.ForgeDirection;

public interface IAdvancedLogTile {

	/**
	 * Implement this and extend BlockPowerConverter and bam when you right click on the block with the debug tool, it will spue out all listed in the stored info.
	 * 
	 * @param info
	 *            , The array to put all info, each entry is a new Line in chat.
	 * @param side
	 *            ,
	 * @param player
	 * @param debug
	 */
	public void getTileInfo(List<IChatComponent> info, ForgeDirection side, EntityPlayer player, boolean debug);

}
