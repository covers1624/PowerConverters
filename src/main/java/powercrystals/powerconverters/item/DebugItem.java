package powercrystals.powerconverters.item;

import java.util.ArrayList;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import powercrystals.powerconverters.common.TileEntityEnergyBridge;
import powercrystals.powerconverters.power.TileEntityBridgeComponent;
import powercrystals.powerconverters.reference.Reference;
import powercrystals.powerconverters.util.IAdvancedLogTile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class DebugItem extends Item {

	public DebugItem() {
		super();
		setUnlocalizedName("pcdebugitem");
		setMaxStackSize(1);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister ir) {
		itemIcon = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName());
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitx, float hity, float hitz) {
		TileEntity te = world.getTileEntity(x, y, z);
		TileEntityEnergyBridge teb = null;
		if (te instanceof TileEntityEnergyBridge || te instanceof TileEntityBridgeComponent<?>) {
			if (te instanceof TileEntityBridgeComponent<?>) {
				teb = ((TileEntityBridgeComponent<?>) te).getFirstBridge();
			} else
				teb = (TileEntityEnergyBridge) te;
			double energyamount = 100 * (teb.getEnergyStored() / teb.getEnergyStoredMax());
			String energy = String.valueOf((int) energyamount);
			if (world.isRemote) {
				player.addChatMessage(new ChatComponentText("-Client-"));
			} else {
				player.addChatMessage(new ChatComponentText("-Server-"));
			}
			player.addChatMessage(new ChatComponentText("Energy Bridge Is " + energy + "% Full"));
			return true;
		} else if (te instanceof IAdvancedLogTile) {
			ArrayList<IChatComponent> info = new ArrayList<IChatComponent>();
			if (world.isRemote) {
				info.add(new ChatComponentText("-Client-"));
			} else {
				info.add(new ChatComponentText("-Server-"));
			}
			((IAdvancedLogTile) te).getTileInfo(info, ForgeDirection.VALID_DIRECTIONS[side], player, player.isSneaking());
			for (int i = 0; i < info.size(); i++) {
				player.addChatMessage(info.get(i));
			}
			return true;
		} else if (world.getBlock(x, y, z) != null) {
			player.addChatMessage(new ChatComponentText(String.valueOf(world.getBlockMetadata(x, y, z))));
		}
		return false;

	}

}
