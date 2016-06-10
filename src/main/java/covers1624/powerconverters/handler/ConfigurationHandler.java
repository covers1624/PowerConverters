package covers1624.powerconverters.handler;

import covers1624.powerconverters.init.recipes.RecipeModuleDiscoverer;
import covers1624.powerconverters.reference.Reference;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigurationHandler {//TODO Config Category's

    public static Configuration configuration;
    private static NBTTagCompound clientCache = new NBTTagCompound();
    private static boolean hasServerConfig;//TODO Support in config gui.

    public static String recipeState;

    public static boolean logDebug;

    public static int bridgeBufferSize;

    // Features
    public static boolean doUpdateCheck;
    public static boolean sendRecipesToClient;
    public static boolean ignoreRecipesFromServer;

    // Devices
    public static boolean disableRFProducer;
    public static boolean disableRFConsumer;

    public static boolean disableIC2Producer;
    public static boolean disableIC2Consumer;

    public static boolean disableFactorizationProducer;
    public static boolean disableFactorizationConsumer;

    public static boolean disableSteamProducer;
    public static boolean disableSteamConsumer;

    public static boolean disableUniversalCharger;
    public static boolean disableUniversalUnCharger;

    public static void init(File config) {
        if (configuration == null) {
            configuration = new Configuration(config);
        }
        loadConfiguration();
    }

    public static void loadConfiguration() {
        if (!hasServerConfig) {
            bridgeBufferSize = configuration.get(Reference.BASIC_CATEGORY, "BridgeBufferSize", 160000000).getInt();
        }

        logDebug = configuration.get(Reference.BASIC_CATEGORY, "Log Debug Messages", false, "Set this to true to see all debug messages in the console.").getBoolean();

        doUpdateCheck = configuration.get(Reference.BASIC_CATEGORY, "Do Update Check", true, "Set to false and PowerConverters will not check for an update").getBoolean();
        sendRecipesToClient = configuration.get(Reference.BASIC_CATEGORY, "Send Recipes To Client", true, "Setting this to false will dissable recipe syncing with the server").getBoolean();
        ignoreRecipesFromServer = configuration.get(Reference.BASIC_CATEGORY, "Ignore Server Recipes", false, "Setting this to true will dissable the client using the server recipes.").getBoolean();
        recipeState = configuration.getString("Recipe Type", Reference.RECIPES_CATEGORY, "Default", "Different recipe types will show up here, They can also be changed in-game with a command or via the mod config menu.", RecipeModuleDiscoverer.loadedModules.toArray(new String[RecipeModuleDiscoverer.loadedModules.size()]));

        if (!hasServerConfig) {
            disableRFProducer = configuration.getBoolean("Dissable RF Producer", Reference.DEVICES_CATEGORY, false, "Dissables the RF Producer");
            disableRFConsumer = configuration.getBoolean("Dissable RF Consumer", Reference.DEVICES_CATEGORY, false, "Dissables the RF Consumer");

            disableIC2Producer = configuration.getBoolean("Dissable IC2 Producers", Reference.DEVICES_CATEGORY, false, "Dissables the IC2 Producers");
            disableIC2Consumer = configuration.getBoolean("Dissable IC2 Consumers", Reference.DEVICES_CATEGORY, false, "Dissables the IC2 Consumers");

            disableFactorizationProducer = configuration.getBoolean("Dissable Factorization Producer", Reference.DEVICES_CATEGORY, false, "Dissables the Factorization Producer");
            disableFactorizationConsumer = configuration.getBoolean("Dissable Factorization Consumer", Reference.DEVICES_CATEGORY, false, "Dissables the Factorization Consumer");

            disableSteamProducer = configuration.getBoolean("Dissable Steam Producer", Reference.DEVICES_CATEGORY, false, "Dissables the Steam Producer");
            disableSteamConsumer = configuration.getBoolean("Dissable Steam Consumer", Reference.DEVICES_CATEGORY, false, "Dissables the Steam Consumer");

            disableUniversalCharger = configuration.getBoolean("Dissable Universal Charger", Reference.DEVICES_CATEGORY, false, "Dissables the Universal Charger");
            disableUniversalUnCharger = configuration.getBoolean("Dissable Universal Un Charger", Reference.DEVICES_CATEGORY, false, "Dissables the Universal Un Charger");
        }
        // TODO
        // PowerSystemOld.loadConfig(configuration);

        configuration.save();
        pushConfig();
    }

    private static void pushConfig() {
        clientCache.setInteger("bridgeBufferSize", bridgeBufferSize);
        clientCache.setBoolean("logDebug", logDebug);

        clientCache.setBoolean("doUpdateCheck", doUpdateCheck);
        clientCache.setBoolean("sendRecipesToClient", sendRecipesToClient);
        clientCache.setBoolean("ignoreRecipesFromServer", ignoreRecipesFromServer);
        clientCache.setString("recipeState", recipeState);

        clientCache.setBoolean("disableRFProducer", disableRFProducer);
        clientCache.setBoolean("disableRFConsumer", disableRFConsumer);
        clientCache.setBoolean("disableIC2Producer", disableIC2Producer);
        clientCache.setBoolean("disableIC2Consumer", disableIC2Consumer);
        clientCache.setBoolean("disableFactorizationProducer", disableFactorizationProducer);
        clientCache.setBoolean("disableFactorizationConsumer", disableFactorizationConsumer);
        clientCache.setBoolean("disableSteamProducer", disableSteamProducer);
        clientCache.setBoolean("disableSteamConsumer", disableSteamConsumer);
        clientCache.setBoolean("disableUniversalCharger", disableUniversalCharger);
        clientCache.setBoolean("disableUniversalUnCharger", disableUniversalUnCharger);
    }

    public static void restoreConfig() {
        readFromNBT(clientCache);
        hasServerConfig = false;
    }

    public static NBTTagCompound getClientSyncTag() {
        NBTTagCompound clientPacket = new NBTTagCompound();

        clientPacket.setInteger("bridgeBufferSize", bridgeBufferSize);

        clientPacket.setBoolean("disableRFProducer", disableRFProducer);
        clientPacket.setBoolean("disableRFConsumer", disableRFConsumer);
        clientPacket.setBoolean("disableIC2Producer", disableIC2Producer);
        clientPacket.setBoolean("disableIC2Consumer", disableIC2Consumer);
        clientPacket.setBoolean("disableFactorizationProducer", disableFactorizationProducer);
        clientPacket.setBoolean("disableFactorizationConsumer", disableFactorizationConsumer);
        clientPacket.setBoolean("disableSteamProducer", disableSteamProducer);
        clientPacket.setBoolean("disableSteamConsumer", disableSteamConsumer);
        clientPacket.setBoolean("disableUniversalCharger", disableUniversalCharger);
        clientPacket.setBoolean("disableUniversalUnCharger", disableUniversalUnCharger);

        return clientPacket;
    }

    public static void setServerConfig(NBTTagCompound tagCompound) {
        readFromNBT(tagCompound);
        hasServerConfig = true;
    }

    public static void readFromNBT(NBTTagCompound tagCompound) {
        if (tagCompound.hasKey("bridgeBufferSize")) {
            bridgeBufferSize = tagCompound.getInteger("bridgeBufferSize");
        }
        if (tagCompound.hasKey("logDebug")) {
            logDebug = tagCompound.getBoolean("logDebug");
        }
        if (tagCompound.hasKey("doUpdateCheck")) {
            doUpdateCheck = tagCompound.getBoolean("doUpdateCheck");
        }
        if (tagCompound.hasKey("sendRecipesToClient")) {
            sendRecipesToClient = tagCompound.getBoolean("sendRecipesToClient");
        }
        if (tagCompound.hasKey("ignoreRecipesFromServer")) {
            ignoreRecipesFromServer = tagCompound.getBoolean("ignoreRecipesFromServer");
        }
        if (tagCompound.hasKey("recipeState")) {
            recipeState = tagCompound.getString("recipeState");
        }

        if (tagCompound.hasKey("disableRFProducer")) {
            disableRFProducer = tagCompound.getBoolean("disableRFProducer");
        }
        if (tagCompound.hasKey("disableRFConsumer")) {
            disableRFConsumer = tagCompound.getBoolean("disableRFConsumer");
        }

        if (tagCompound.hasKey("disableIC2Producer")) {
            disableIC2Producer = tagCompound.getBoolean("disableIC2Producer");
        }
        if (tagCompound.hasKey("disableIC2Consumer")) {
            disableIC2Consumer = tagCompound.getBoolean("disableIC2Consumer");
        }

        if (tagCompound.hasKey("disableFactorizationProducer")) {
            disableFactorizationProducer = tagCompound.getBoolean("disableFactorizationProducer");
        }
        if (tagCompound.hasKey("disableFactorizationConsumer")) {
            disableFactorizationConsumer = tagCompound.getBoolean("disableFactorizationConsumer");
        }

        if (tagCompound.hasKey("disableSteamProducer")) {
            disableSteamProducer = tagCompound.getBoolean("disableSteamProducer");
        }
        if (tagCompound.hasKey("disableSteamConsumer")) {
            disableSteamConsumer = tagCompound.getBoolean("disableSteamConsumer");
        }

        if (tagCompound.hasKey("disableUniversalCharger")) {
            disableUniversalCharger = tagCompound.getBoolean("disableUniversalCharger");
        }
        if (tagCompound.hasKey("disableUniversalUnCharger")) {
            disableUniversalUnCharger = tagCompound.getBoolean("disableUniversalUnCharger");
        }
    }

}
