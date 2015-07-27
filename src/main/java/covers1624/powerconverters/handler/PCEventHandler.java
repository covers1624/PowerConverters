package covers1624.powerconverters.handler;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry.FluidRegisterEvent;
import covers1624.powerconverters.PowerConverters;
import covers1624.powerconverters.init.Recipes;
import covers1624.powerconverters.net.PacketPipeline;
import covers1624.powerconverters.net.RecipeSyncPacket;
import covers1624.powerconverters.reference.Reference;
import covers1624.powerconverters.util.LogHelper;
import covers1624.powerconverters.util.RecipeRemover;
import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PCEventHandler {

	public static int ticksInGame = 0;

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

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onClientJoin(ClientConnectedToServerEvent event) {
		// Send blank packet to server because it is the easiest way in my opinion.
		if (event.isLocal) {
			LogHelper.trace("Detected Singleplayer server not sending request for recipes.");
			return;
		}
		RecipeSyncPacket syncPacket = new RecipeSyncPacket(new NBTTagCompound());
		PacketPipeline.INSTANCE.sendToServer(syncPacket);
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
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
