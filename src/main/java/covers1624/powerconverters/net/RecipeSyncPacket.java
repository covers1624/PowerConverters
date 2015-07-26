package covers1624.powerconverters.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import covers1624.powerconverters.PowerConverters;
import covers1624.powerconverters.init.Recipes;
import covers1624.powerconverters.util.IRecipeHandler;
import covers1624.powerconverters.util.RecipeRemover;
import cpw.mods.fml.common.network.ByteBufUtils;

public class RecipeSyncPacket extends AbstractPacket {

	private NBTTagCompound tagCompound;

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
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		NBTTagCompound tag = new NBTTagCompound();
		List<IRecipe> recipes = Recipes.getCurrentRecipes();
		NBTTagList tagList = new NBTTagList();
		for (IRecipe recipe : recipes) {
			NBTTagCompound tagCompound = IRecipeHandler.writeIRecipeToTag(recipe);
			tagList.appendTag(tagCompound);
		}
		tag.setTag("Recipes", tagList);
		RecipeSyncPacket syncPacket = new RecipeSyncPacket(tag);
		PowerConverters.packetPipeline.sendTo(syncPacket, (EntityPlayerMP) player);
	}

}
