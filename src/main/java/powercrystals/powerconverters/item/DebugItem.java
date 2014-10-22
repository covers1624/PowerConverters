package powercrystals.powerconverters.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import powercrystals.powerconverters.common.TileEntityEnergyBridge;
import powercrystals.powerconverters.power.TileEntityBridgeComponent;
import powercrystals.powerconverters.reference.Reference;
import scala.reflect.internal.Trees.If;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class DebugItem extends Item {

	public DebugItem() {
		super();
		setUnlocalizedName("pcdebugitem");
		setMaxStackSize(1);
	}
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister ir){
		itemIcon = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName());
	}
	
	

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitx, float hity, float hitz) {
		if (world.isRemote) {
			return false;
		}
		TileEntity te = world.getTileEntity(x, y, z);
		TileEntityEnergyBridge teb = null;
		if (te instanceof TileEntityEnergyBridge || te instanceof TileEntityBridgeComponent<?>) {
			if (te instanceof TileEntityBridgeComponent<?>) {
				teb = ((TileEntityBridgeComponent<?>) te).getFirstBridge();
			} else
				teb = (TileEntityEnergyBridge) te;
			double energyamount = 100 * (teb.getEnergyStored() / teb.getEnergyStoredMax());
			String energy = String.valueOf((int) energyamount);
			player.addChatMessage(new ChatComponentText("Energy Bridge Is " + energy + "% Full"));
			return true;
		} else
			return false;
	}

}
