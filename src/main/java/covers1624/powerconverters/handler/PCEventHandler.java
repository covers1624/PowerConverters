package covers1624.powerconverters.handler;

import covers1624.powerconverters.PowerConverters;
import covers1624.powerconverters.init.Recipes;
import covers1624.powerconverters.network.PacketPipeline;
import covers1624.powerconverters.network.packets.RecipeSyncPacket;
import covers1624.powerconverters.reference.Reference;
import covers1624.powerconverters.util.IRecipeHandler;
import covers1624.powerconverters.util.LogHelper;
import covers1624.powerconverters.util.RecipeRemover;
import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fluids.FluidRegistry.FluidRegisterEvent;

import java.util.ArrayList;
import java.util.List;

public class PCEventHandler {

	@SubscribeEvent
	public void onFluidRegisterEvent(FluidRegisterEvent event) {
		LogHelper.info(event.fluidName);
		if (event.fluidName.equals("Steam")) {
			PowerConverters.steamId = event.fluidID;
		} else if (event.fluidName.equals("steam") && PowerConverters.steamId == -1) {
			PowerConverters.steamId = event.fluidID;
		}
	}

	@SubscribeEvent
	public void onConfigChanged(OnConfigChangedEvent event) {
		if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
			ConfigurationHandler.INSTANCE.loadConfiguration();
		}
	}

	@SideOnly(Side.SERVER)
	// @SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onClientJoin(PlayerLoggedInEvent event) {
		if (!ConfigurationHandler.sendRecipesToClient) {
			LogHelper.trace("Recipe Sending is turned off.");
			return;
		}
		NBTTagCompound tag = new NBTTagCompound();
		List<IRecipe> recipes = Recipes.getCurrentRecipes();
		NBTTagList tagList = new NBTTagList();
		for (IRecipe recipe : recipes) {
			NBTTagCompound tagCompound = IRecipeHandler.writeIRecipeToTag(recipe);
			tagList.appendTag(tagCompound);
		}
		tag.setTag("Recipes", tagList);
		RecipeSyncPacket syncPacket = new RecipeSyncPacket(tag);
		PacketPipeline.instance().sendTo(syncPacket, (EntityPlayerMP) event.player);
	}

	@SideOnly(Side.CLIENT)
	// @SubscribeEvent
	public void onClientDisconnect(ClientDisconnectionFromServerEvent event) {
		List<ItemStack> currentOutputs = new ArrayList<ItemStack>();
		for (IRecipe recipe : Recipes.getCurrentRecipes()) {
			ItemStack stack = recipe.getRecipeOutput();
			currentOutputs.add(stack);
		}
		RecipeRemover.removeAnyRecipes(currentOutputs);
		for (IRecipe recipe : Recipes.getDefaultRecipes()) {
			CraftingManager.getInstance().getRecipeList().add(recipe);
		}
	}
}
