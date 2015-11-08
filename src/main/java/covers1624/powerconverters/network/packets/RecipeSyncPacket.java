package covers1624.powerconverters.network.packets;

import covers1624.powerconverters.handler.ConfigurationHandler;
import covers1624.powerconverters.init.Recipes;
import covers1624.powerconverters.nei.NEIInfoHandlerConfig;
import covers1624.powerconverters.network.AbstractPacket;
import covers1624.powerconverters.util.IRecipeHandler;
import covers1624.powerconverters.util.LogHelper;
import covers1624.powerconverters.util.RecipeRemover;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.List;

public class RecipeSyncPacket extends AbstractPacket {

	private NBTTagCompound tagCompound;

	public RecipeSyncPacket() {
		// TODO Auto-generated constructor stub
	}

	public RecipeSyncPacket(NBTTagCompound tag) {
		tagCompound = tag;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		ByteBufUtils.writeTag(buffer, tagCompound);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		tagCompound = ByteBufUtils.readTag(buffer);
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		// LogHelper.info("packet arrived");
		if (ConfigurationHandler.ignoreRecipesFromServer) {
			LogHelper.trace("Ignoring recipe packet from server.");
			return;
		}
		NBTTagList tagList = tagCompound.getTagList("Recipes", 10);
		List<IRecipe> recipes = new ArrayList<IRecipe>();
		for (int i = 0; i < tagList.tagCount(); i++) {
			IRecipe recipe = IRecipeHandler.readIRecipeFromTag(tagList.getCompoundTagAt(i));
			if (recipe != null) {
				recipes.add(recipe);
			}
		}
		List<ItemStack> currentOutputs = new ArrayList<ItemStack>();
		for (IRecipe recipe : Recipes.getCurrentRecipes()) {
			ItemStack stack = recipe.getRecipeOutput();
			currentOutputs.add(stack);
		}
		RecipeRemover.removeAnyRecipes(currentOutputs);
		for (IRecipe recipe : recipes) {
			CraftingManager.getInstance().getRecipeList().add(recipe);
		}
		if (Loader.isModLoaded("NotEnoughItems")) {
			NEIInfoHandlerConfig.addRecipesToNEI();
		}
	}

	@Override
	public void handleServerSide(EntityPlayer player) {

	}

}
