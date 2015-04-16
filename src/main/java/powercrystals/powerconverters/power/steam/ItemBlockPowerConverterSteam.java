package powercrystals.powerconverters.power.steam;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockPowerConverterSteam extends ItemBlock {
	public ItemBlockPowerConverterSteam(Block block) {
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
			return "powerconverters.steam.consumer";
		if (md == 1)
			return "powerconverters.steam.producer";
		return "unknown";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(Item item, CreativeTabs creativeTab, List subTypes) {
		for (int i = 0; i <= 1; i++) {
			subTypes.add(new ItemStack(item, 1, i));
		}
	}
}
