package covers1624.lib.discovery;

import covers1624.lib.api.recipe.IRecipeNBTLogic;
import covers1624.lib.util.LogHelper;
import cpw.mods.fml.common.Loader;

import java.util.HashMap;

/**
 * Created by covers1624 on 1/31/2016.
 * TODO auto class finding.
 */
@Deprecated
public class RecipeNBTLogicDiscovery {

    public static HashMap<String, IRecipeNBTLogic> handlerClassMap = new HashMap<String, IRecipeNBTLogic>();
    public static HashMap<String, IRecipeNBTLogic> nbtNameClassMap = new HashMap<String, IRecipeNBTLogic>();

    public static void registerLogic(IRecipeNBTLogic logic) {
        if (handlerClassMap.containsKey(logic.getClassToHandle()) || nbtNameClassMap.containsKey(logic.getNBTName())) {
            LogHelper.error("Unable to register IRecipeNBTLogic class {%s}! It seems it has already been registered...", logic.getClass().getName());
        }
        handlerClassMap.put(logic.getClassToHandle(), logic);
        nbtNameClassMap.put(logic.getNBTName(), logic);
        LogHelper.trace("Registered IRecipeNBTLogic {%s}, Active mod container : %s", logic.getClass().getName(), Loader.instance().activeModContainer().toString());
    }

}
