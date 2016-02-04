package covers1624.powerconverters.manager;

import com.google.common.collect.ImmutableList;
import covers1624.lib.util.RecipeNBTHelper;
import covers1624.powerconverters.api.recipe.IRecipeStateManager;
import covers1624.powerconverters.handler.ConfigurationHandler;
import covers1624.powerconverters.network.PacketPipeline;
import covers1624.powerconverters.network.packets.RecipeSyncPacket;
import covers1624.powerconverters.util.LogHelper;
import covers1624.powerconverters.util.RecipeRemover;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by covers1624 on 1/28/2016.
 * Maintains a valid recipe state between the client and server at all times.
 * Will update client recipes on server login/roll back when disconnected.
 * Will update if the server sets the recipes on runtime. xD
 */
public final class RecipeStateManager implements IRecipeStateManager {

	private ArrayList<IRecipe> tempDefaults = new ArrayList<IRecipe>();

	private ImmutableList<IRecipe> defaultRecipeList;
	private boolean hasBuilt = false;

	private boolean hasServerRecipes = false;
	private ArrayList<IRecipe> currentRecipes;

	private static RecipeStateManager instance = new RecipeStateManager();

	public static RecipeStateManager instance() {
		return instance;
	}

	public void addShapelessRecipe(ItemStack output, Object... inputObjects) {
		if (hasBuilt) {
			throw new RuntimeException("RecipeStateManager is in an invalid state to register a recipe, Modders DO NOT CALL THIS DIRECTLY USE THE API!");
		}
		ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
		for (int index = 0; index < inputObjects.length; index++) {
			Object recipeObject = inputObjects[index];
			if (recipeObject == null) {
				throw new RuntimeException("Null object at index: " + index);
			}
			if (recipeObject instanceof ItemStack) {
				inputs.add(((ItemStack) recipeObject).copy());
			} else if (recipeObject instanceof Item) {
				inputs.add(new ItemStack((Item) recipeObject));
			} else if (recipeObject instanceof Block) {
				inputs.add(new ItemStack((Block) recipeObject));
			} else {
				throw new RuntimeException(String.format("Unable to parse recipe inputs! Object at index %s is invalid! Class name of object: %s", index, recipeObject.getClass().getName()));
			}
		}
		tempDefaults.add(new ShapelessRecipes(output, inputs));
	}

	public void addRecipe(ItemStack output, Object... inputObjects) {
		if (hasBuilt) {
			throw new RuntimeException("RecipeStateManager is in an invalid state to register a recipe, Modders DO NOT CALL THIS DIRECTLY USE THE API!");
		}
		String recipeChars = "";
		int totalRecipeObjects = 0;
		int recipeWidth = 0;
		int recipeHeight = 0;

		if (inputObjects[totalRecipeObjects] instanceof String[]) {
			String[] astring = ((String[]) inputObjects[totalRecipeObjects++]);

			for (String s1 : astring) {
				++recipeHeight;
				recipeWidth = s1.length();
				recipeChars = recipeChars + s1;
			}
		} else {
			while (inputObjects[totalRecipeObjects] instanceof String) {
				String s2 = (String) inputObjects[totalRecipeObjects++];
				++recipeHeight;
				recipeWidth = s2.length();
				recipeChars = recipeChars + s2;
			}
		}

		HashMap<Character, ItemStack> charToStackMap = new HashMap<Character, ItemStack>();
		for (; totalRecipeObjects < inputObjects.length; totalRecipeObjects += 2) {
			Character character = (Character) inputObjects[totalRecipeObjects];
			ItemStack stack = null;

			if (inputObjects[totalRecipeObjects + 1] instanceof Item) {
				stack = new ItemStack((Item) inputObjects[totalRecipeObjects + 1]);
			} else if (inputObjects[totalRecipeObjects + 1] instanceof Block) {
				stack = new ItemStack((Block) inputObjects[totalRecipeObjects + 1], 1, 32767);
			} else if (inputObjects[totalRecipeObjects + 1] instanceof ItemStack) {
				stack = (ItemStack) inputObjects[totalRecipeObjects + 1];
			}

			charToStackMap.put(character, stack);
		}

		ItemStack[] finalRecipeStacks = new ItemStack[recipeWidth * recipeHeight];

		for (int index = 0; index < recipeWidth * recipeHeight; ++index) {
			char charAt = recipeChars.charAt(index);

			if (charToStackMap.containsKey(charAt)) {
				finalRecipeStacks[index] = charToStackMap.get(charAt).copy();
			} else {
				finalRecipeStacks[index] = null;
			}
		}
		tempDefaults.add(new ShapedRecipes(recipeWidth, recipeHeight, finalRecipeStacks, output));
	}

	@Override
	public void addRecipe(IRecipe recipe) {
		tempDefaults.add(recipe);
	}

	/**
	 * Builds the default recipe list, clears the temp list and allows no more recipes to be registered.
	 */
	public void buildDefaults() {
		defaultRecipeList = ImmutableList.copyOf(tempDefaults);
		tempDefaults.clear();
		hasBuilt = true;
		loadDefaults();
	}

	/**
	 * Clears the defaults and starts again from scratch.
	 * WARNING, DO NOT CALL OUTSIDE THIS CLASS AND ONLY CALL THIS ON THE SERVER!
	 * If this is called on the client the Recipe state will fuck up to an unrecoverable state!
	 */
	private void reset() {
		defaultRecipeList = null;
		hasBuilt = false;
	}

	/**
	 * Pushes locally stored recipes to GameRegistry.
	 */
	public void push() {
		for (IRecipe recipe : currentRecipes) {
			GameRegistry.addRecipe(recipe);
		}
	}

	/**
	 * ONLY TO BE CALLED FROM SYNC PACKET!
	 */
	public void readRecipes(NBTTagCompound tagCompound) {
		List<IRecipe> recipes = new ArrayList<IRecipe>();
		NBTTagList tagList = tagCompound.getTagList("Recipes", 10);
		for (int i = 0; i < tagList.tagCount(); i++) {
			IRecipe recipe = RecipeNBTHelper.readIRecipeFromTag(tagList.getCompoundTagAt(i));
			if (recipe != null) {
				recipes.add(recipe);
			}
		}
		setRecipes(recipes);
	}

	/**
	 * Sets the current recipe state.
	 * Removes all default recipes from CraftingManager and loads the new ones.
	 */
	public void setRecipes(List<IRecipe> recipes) {
		RecipeRemover.removeAllRecipes(defaultRecipeList);
		currentRecipes = new ArrayList<IRecipe>(recipes);
		hasServerRecipes = true;
		push();
	}

	/**
	 * Sets the current recipe state to default.
	 * Removes all server sent recipes and loads the defaults.
	 */
	public void loadDefaults() {
		if (hasServerRecipes) {
			RecipeRemover.removeAllRecipes(currentRecipes);
			hasServerRecipes = false;
		}
		currentRecipes = new ArrayList<IRecipe>(defaultRecipeList);
		push();
	}

	@SideOnly(Side.SERVER)//TODO Local lan recipe sync.
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onClientJoin(PlayerEvent.PlayerLoggedInEvent event) {
		LogHelper.info("Attempting to send recipe packet to player: %s", event.player.getCommandSenderName());
		if (ConfigurationHandler.sendRecipesToClient) {
			if (event.player instanceof EntityPlayerMP) {
				PacketPipeline.instance().sendTo(new RecipeSyncPacket(defaultRecipeList), (EntityPlayerMP) event.player);
				LogHelper.info("Sending recipe packet to player: %s was a success!");
			} else {
				LogHelper.bigFatal("Player is not an instance of EntityPlayerMP! Unable to send Recipe Sync Packet!");
			}
		} else {
			LogHelper.bigWarn("RECIPE SENDING IS TURNED OFF! This may cause recipe syncing errors on the client!");
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onClientDisconnected(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
		loadDefaults();
	}

}