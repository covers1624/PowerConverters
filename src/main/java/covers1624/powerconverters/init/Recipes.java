package covers1624.powerconverters.init;

import covers1624.powerconverters.api.recipe.AbstractRecipeModule;
import covers1624.powerconverters.handler.ConfigurationHandler;
import covers1624.powerconverters.init.recipes.DefaultRecipeModule;
import covers1624.powerconverters.init.recipes.RecipeModuleDiscoverer;
import covers1624.powerconverters.manager.RecipeStateManager;
import covers1624.powerconverters.util.LogHelper;

public class Recipes {

    public static void init() {
        String desiredState = ConfigurationHandler.recipeState;
        AbstractRecipeModule module = RecipeModuleDiscoverer.getModule(desiredState);
        if (module == null) {
            LogHelper.fatal("Unable to find recipe state %s! Using default recipes...");
            module = new DefaultRecipeModule();
        }

        module.preLoad();
        module.loadRecipes(RecipeStateManager.instance());
        RecipeStateManager.instance().buildDefaults();
    }

}
