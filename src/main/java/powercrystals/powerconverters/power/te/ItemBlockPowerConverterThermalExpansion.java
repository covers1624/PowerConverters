package powercrystals.powerconverters.power.te;

import java.util.List;

import powercrystals.powerconverters.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockPowerConverterThermalExpansion extends ItemBlock {

	public ItemBlockPowerConverterThermalExpansion(Block block) {
		super(block);
		setHasSubtypes(true);
		setMaxDamage(0);
	}

	@Override
	public int getMetadata(int i) {
		return i;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		int md = itemstack.getItemDamage();
		if (md == 0)
			return "powerconverters.te.consumer";
		if (md == 1)
			return "powerconverters.te.producer";
		return "unknown";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		int md = itemstack.getItemDamage();
		if (md == 0)
			list.add("Consumes ThermalExpansion RF");
		else if (md == 1)
			list.add("Produces ThermalExpansion RF");
		else
			list.add(Reference.TOOLTIP_ERROR);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(Item item, CreativeTabs creativeTab, List subTypes) {
		for (int i = 0; i <= 1; i++) {
			subTypes.add(new ItemStack(item, 1, i));
		}
	}
}
