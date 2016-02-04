package covers1624.powerconverters.network.packets;

import covers1624.lib.util.RecipeNBTHelper;
import covers1624.powerconverters.handler.ConfigurationHandler;
import covers1624.powerconverters.manager.RecipeStateManager;
import covers1624.powerconverters.network.AbstractPacket;
import covers1624.powerconverters.util.LogHelper;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;

import java.util.List;

public class RecipeSyncPacket extends AbstractPacket {

	private NBTTagCompound tagCompound;

	public RecipeSyncPacket() {

	}

	public RecipeSyncPacket(List<IRecipe> recipes) {
		tagCompound = new NBTTagCompound();
		NBTTagList recipesList = new NBTTagList();
		for (IRecipe recipe : recipes) {
			NBTTagCompound recipeTag = RecipeNBTHelper.writeIRecipeToTag(recipe);
			if (recipeTag != null) {
				recipesList.appendTag(recipeTag);
			}
		}
		tagCompound.setTag("Recipes", recipesList);
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
		LogHelper.info("Received Recipe sync packet from server. Attempting to parse packet...");

		if (ConfigurationHandler.ignoreRecipesFromServer) {
			LogHelper.warn("Ignoring recipe sync packet from the server! This may cause recipe syncing issues!");
			player.addChatMessage(new ChatComponentText("[PC3] [WARN] Ignoring recipe sync packet from the server! This may cause issues with crafting! \n For recipes to work correctly you may have to set the recipe type locally."));
			return;
		}

		try {
			RecipeStateManager.instance().readRecipes(tagCompound);
			LogHelper.info("Successfully parsed Recipe sync packet from server!");
		} catch (Throwable throwable) {
			throwable.printStackTrace();
			LogHelper.error("Unable to parse recipe sync packet! Restoring defaults...");
			RecipeStateManager.instance().loadDefaults();
		}
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		throw new RuntimeException("THIS PACKET CANNOT BE SENT TO THE SERVER!");
	}

}
