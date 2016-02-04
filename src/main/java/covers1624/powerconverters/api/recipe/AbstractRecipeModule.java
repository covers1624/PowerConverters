package covers1624.powerconverters.api.recipe;

import cpw.mods.fml.common.Loader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by covers1624 on 1/28/2016.
 * Implementing this interface on a class provided the class has "RecipeModule" at the end of the classes name and
 * The mods required are available, It will be automatically added as a viable recipe type to load.
 * If a recipe type was selected and the mods required are no longer available it will load the Defaults.
 * This class may change as features are added, just a FYI.
 * It is recommended that you use GameRegistry.findItem / GameRegistry.findBlock for items and blocks that do not belong to you,
 * This makes PowerConverters a soft dependency, witch i highly recommend.
 */
public abstract class AbstractRecipeModule {

	//Easy access Loader.isModLoaded calls.
	public static boolean buildCraftCoreFound;
	public static boolean buildCraftEnergyFound;
	public static boolean buildCraftFactoryFound;
	public static boolean buildCraftRoboticsFound;
	public static boolean buildCraftSiliconFound;
	public static boolean buildCraftTransportFound;
	public static boolean buildCraftBuildersFound;
	public static boolean ic2Found;
	public static boolean ic2ClassicFound;
	public static boolean ic2ExcrementalFound;
	public static boolean thermalExpansionFound;
	public static boolean thermalFoundationFound;
	public static boolean factorizationFound;
	public static boolean railCraftFound;

	public static void init() {
		buildCraftCoreFound = Loader.isModLoaded("BuildCraft|Core");
		buildCraftEnergyFound = Loader.isModLoaded("BuildCraft|Energy");
		buildCraftFactoryFound = Loader.isModLoaded("BuildCraft|Factory");
		buildCraftRoboticsFound = Loader.isModLoaded("BuildCraft|Robotics");
		buildCraftSiliconFound = Loader.isModLoaded("BuildCraft|Silicon");
		buildCraftTransportFound = Loader.isModLoaded("BuildCraft|Transport");
		buildCraftBuildersFound = Loader.isModLoaded("BuildCraft|Builders");
		ic2Found = Loader.isModLoaded("IC2");
		ic2ClassicFound = Loader.isModLoaded("IC2-Classic-Spmod");
		ic2ExcrementalFound = !ic2ClassicFound;
		thermalExpansionFound = Loader.isModLoaded("ThermalExpansion");
		thermalFoundationFound = Loader.isModLoaded("ThermalFoundation");
		factorizationFound = Loader.isModLoaded("factorization");
		railCraftFound = Loader.isModLoaded("Railcraft");
	}

	/**
	 * Contains a list of all mods not loaded for this module.
	 * Please don't edit this. It should only be used for logging purposes only.
	 */
	public List<String> unloadedMods = new ArrayList<String>();

	/**
	 * Contains a list of incompatible mods for this module that are loaded.
	 */
	public List<String> incompatableMods = new ArrayList<String>();

	/**
	 * Use this to load any blocks or items from GameRegistry.find/whatever.
	 */
	public abstract void preLoad();

	/**
	 * Gets the unique name for this recipe handler.
	 *
	 * @return The name for the Recipe Handler.
	 */
	public abstract String getName();

	/**
	 * Gets the mods required mods for this recipe handler to load.
	 * The list MUST be mod id's.
	 *
	 * @return List of mods required.
	 */
	public abstract String[] getRequiredMods();

	/**
	 * Gets a list of mods that MUST not be loaded for this module to work.
	 * The list MUST be mod id's.
	 *
	 * @return List of incompatible mods.
	 */
	public abstract String[] getIncompatibleMods();

	/**
	 * Gets the mod that will handle this recipe module.
	 *
	 * @return Modid of the mod to target.
	 * DO NOT USE NOT IMPLEMENTED, this is here as a placeholder for the eventual move to covers1624 lib.
	 */
	@Deprecated
	public abstract String getTargetMod();

	/**
	 * Loads the actual recipes, Use the recipe calls in stateManager!!!
	 */
	public abstract void loadRecipes(IRecipeStateManager stateManager);
}
