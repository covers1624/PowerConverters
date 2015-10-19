package covers1624.powerconverters.handler;

import covers1624.powerconverters.container.ContainerEnergyBridge;
import covers1624.powerconverters.container.ContainerUniversalCharger;
import covers1624.powerconverters.gui.GuiEnergyBridge;
import covers1624.powerconverters.gui.GuiUniversalCharger;
import covers1624.powerconverters.tile.main.TileEntityCharger;
import covers1624.powerconverters.tile.main.TileEntityEnergyBridge;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PCGUIHandler implements IGuiHandler {
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (ID == 1) {
			return new ContainerUniversalCharger(player.inventory, (TileEntityCharger) te);
		}
		if (te != null && te instanceof TileEntityEnergyBridge) {
			return new ContainerEnergyBridge((TileEntityEnergyBridge) te, player.inventory);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (ID == 1) {
			return new GuiUniversalCharger(player.inventory, (TileEntityCharger) te);
		}
		if (te != null && te instanceof TileEntityEnergyBridge) {
			return new GuiEnergyBridge(new ContainerEnergyBridge((TileEntityEnergyBridge) te, player.inventory), (TileEntityEnergyBridge) te);
		}
		return null;
	}
}
