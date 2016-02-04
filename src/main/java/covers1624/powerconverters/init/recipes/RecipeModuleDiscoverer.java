package covers1624.powerconverters.init.recipes;

import com.google.common.collect.ImmutableList;
import covers1624.lib.repack.codechicken.core.ClassDiscoverer;
import covers1624.lib.repack.codechicken.core.IStringMatcher;
import covers1624.powerconverters.api.recipe.AbstractRecipeModule;
import covers1624.powerconverters.util.LogHelper;
import cpw.mods.fml.common.Loader;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by covers1624 on 1/28/2016.
 * TODO ASM all loaded modules and search for any GameRegistry.addRecipe/whatever calls and throw some errors to the console.
 */
public class RecipeModuleDiscoverer {

	private static ArrayList<AbstractRecipeModule> recipeModules = new ArrayList<AbstractRecipeModule>();
	public static ImmutableList<String> loadedModules;

	public static void findModules() {
		ClassDiscoverer discoverer = new ClassDiscoverer(new IStringMatcher() {
			@Override
			public boolean matches(String test) {
				return test.contains("RecipeModule");
			}
		}, AbstractRecipeModule.class);
		discoverer.findClasses();

		for (Class clazz : discoverer.classes) {
			try {
				recipeModules.add((AbstractRecipeModule) clazz.newInstance());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Removes any recipe classes that do not have the required mods loaded.
	 */
	public static void sortRecipeModules() {
		LogHelper.info("Sorting Recipe Modules...");
		//Copy so we can edit the main list.
		ArrayList<AbstractRecipeModule> copy = new ArrayList<AbstractRecipeModule>(recipeModules);
		ArrayList<String> modulesLoaded = new ArrayList<String>();
		for (AbstractRecipeModule module : copy) {
			if (!checkModList(module)) {
				LogHelper.warn("Module {%s} will not be loaded! The mods required for it to load {%s} are not installed!", module.getName(), buildUnloadedModList(module));
				recipeModules.remove(module);
			} else if (modulesLoaded.contains(module.getName())) {
				throw new RuntimeException(String.format("Module with name %s already exists, THIS IS A CRITICAL BUG! Report this to the official PowerConverters3 GitHub! Offending Class: %s", module.getName(), module.getClass().getName()));
			} else {
				modulesLoaded.add(module.getName());
			}
		}
		loadedModules = ImmutableList.copyOf(modulesLoaded);
		LogHelper.info("PowerConverters has successfully Found %s Recipe Modules {%s}", loadedModules.size(), buildModuleList(loadedModules));
	}

	/**
	 * Checks if the mods required for the module to load exist and adds the unloaded mods to the internal list of the Module.
	 *
	 * @param recipeModule Module to check.
	 * @return If all mods are loaded.
	 */
	private static boolean checkModList(AbstractRecipeModule recipeModule) {
		LogHelper.info("Checking Mods for module: " + recipeModule.getName());
		boolean flag = true;
		if (recipeModule.getRequiredMods() != null) {
			for (String mod : recipeModule.getRequiredMods()) {
				if (!Loader.isModLoaded(mod)) {
					recipeModule.unloadedMods.add(mod);
					flag = false;
				}
			}
		}
		return flag;
	}

	private static String buildUnloadedModList(AbstractRecipeModule module) {
		StringBuilder builder = new StringBuilder();
		for (String mod : module.unloadedMods) {
			builder.append(mod);
			builder.append(", ");
		}
		String list = builder.toString();
		list = list.trim();
		if (list.endsWith(",")) {
			list = list.substring(0, list.lastIndexOf(","));
		}
		return list;
	}

	private static String buildModuleList(Collection<String> modules) {
		StringBuilder builder = new StringBuilder();
		for (String module : modules) {
			builder.append(module);
			builder.append(", ");
		}
		String list = builder.toString();
		list = list.trim();
		if (list.endsWith(",")) {
			list = list.substring(0, list.lastIndexOf(","));
		}
		return list;
	}

	public static boolean moduleExists(String module) {
		return loadedModules.contains(module);
	}

	public static AbstractRecipeModule getModule(String moduleName) {
		if (moduleExists(moduleName)) {
			for (AbstractRecipeModule module : recipeModules) {
				if (module.getName().equals(moduleName)) {
					return module;
				}
			}
			throw new RuntimeException(String.format("Module %s exists in loadedModules list but not in recipe modules list...", moduleName));
		}
		return null;
	}

}
