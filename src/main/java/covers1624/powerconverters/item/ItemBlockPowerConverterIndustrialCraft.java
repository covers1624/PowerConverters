package covers1624.powerconverters.item;

import covers1624.powerconverters.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBlockPowerConverterIndustrialCraft extends ItemBlock {
	public ItemBlockPowerConverterIndustrialCraft(Block block) {
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
		if (md == 0) {
			return "powerconverters.ic2.lv.consumer";
		}
		if (md == 1) {
			return "powerconverters.ic2.lv.producer";
		}
		if (md == 2) {
			return "powerconverters.ic2.mv.consumer";
		}
		if (md == 3) {
			return "powerconverters.ic2.mv.producer";
		}
		if (md == 4) {
			return "powerconverters.ic2.hv.consumer";
		}
		if (md == 5) {
			return "powerconverters.ic2.hv.producer";
		}
		if (md == 6) {
			return "powerconverters.ic2.ev.consumer";
		}
		if (md == 7) {
			return "powerconverters.ic2.ev.producer";
		}
		return "unknown";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		int md = itemstack.getItemDamage();
		if (md == 0) {
			list.add("IC2 LV Consumer, MAX Input: 32 EU/t (BatBox)");
		} else if (md == 1) {
			list.add("IC2 LV Producer, MAX Output: 32 EU/t (BatBox)");
		} else if (md == 2) {
			list.add("IC2 MV Consumer, MAX Input: 128 EU/t (CESU)");
		} else if (md == 3) {
			list.add("IC2 MV Producer, MAX Output: 128 EU/t (CESU)");
		} else if (md == 4) {
			list.add("IC2 HV Consumer, MAX Input: 512 EU/t (MFE)");
		} else if (md == 5) {
			list.add("IC2 HV Producer, MAX Output: 512 EU/t (MFE)");
		} else if (md == 6) {
			list.add("IC2 EV Consumer, MAX Input: 2048 EU/t (MFSU)");
		} else if (md == 7) {
			list.add("IC2 EV Producer, MAX Output: 2048 EU/t (MFSU)");
		} else {
			list.add(Reference.TOOLTIP_ERROR);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(Item item, CreativeTabs creativeTab, List subTypes) {
		for (int i = 0; i <= 7; i++) {
			subTypes.add(new ItemStack(item, 1, i));
		}
	}

}
