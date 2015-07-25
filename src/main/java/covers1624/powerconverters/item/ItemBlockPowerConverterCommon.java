package covers1624.powerconverters.item;

import java.util.List;

import covers1624.powerconverters.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockPowerConverterCommon extends ItemBlock {
	public ItemBlockPowerConverterCommon(Block block) {
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
			return "powerconverters.common.bridge";
		if (md == 2)
			return "powerconverters.common.charger";
		return "unknown";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		int md = itemstack.getItemDamage();
		if (md == 0)
			list.add("Common Block in the Power Converter MultiBlock");
		if (md == 2)
			list.add("A universal Charging Block (WIP)");
		if (md != 0 && md != 2)
			list.add(Reference.TOOLTIP_ERROR);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(Item item, CreativeTabs creativeTab, List subTypes) {
		subTypes.add(new ItemStack(item, 1, 0));
		subTypes.add(new ItemStack(item, 1, 2));
	}
}
