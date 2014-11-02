package powercrystals.powerconverters.power.conduit;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockEnergyConduit extends ItemBlock {

	public ItemBlockEnergyConduit(Block block) {
		super(block);
		setHasSubtypes(true);
		setMaxDamage(0);
	}
	
	@Override
	public int getMetadata(int i){
		return i;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack){
		return "UNKNOWN";
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs creativeTab, List subTypes){
		subTypes.add(new ItemStack(item, 1, 0));
	}

}
