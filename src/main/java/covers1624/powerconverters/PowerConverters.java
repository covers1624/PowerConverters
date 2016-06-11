package covers1624.powerconverters;

import covers1624.lib.api.recipe.VanillaShapedRecipeNBTLogic;
import covers1624.lib.api.recipe.VanillaShapelessRecipeNBTLogic;
import covers1624.lib.discovery.RecipeNBTLogicDiscovery;
import covers1624.powerconverters.api.recipe.AbstractRecipeModule;
import covers1624.powerconverters.gui.PCCreativeTab;
import covers1624.powerconverters.handler.ConfigurationHandler;
import covers1624.powerconverters.handler.PCEventHandler;
import covers1624.powerconverters.handler.PCGUIHandler;
import covers1624.powerconverters.init.ModBlocks;
import covers1624.powerconverters.init.ModItems;
import covers1624.powerconverters.init.PowerSystems;
import covers1624.powerconverters.init.Recipes;
import covers1624.powerconverters.init.recipes.RecipeModuleDiscoverer;
import covers1624.powerconverters.manager.RecipeStateManager;
import covers1624.powerconverters.network.PacketPipeline;
import covers1624.powerconverters.network.packets.EnergyBridgeSyncPacket;
import covers1624.powerconverters.network.packets.RecipeSyncPacket;
import covers1624.powerconverters.proxy.CommonProxy;
import covers1624.powerconverters.reference.Reference;
import covers1624.powerconverters.updatechecker.UpdateManager;
import covers1624.powerconverters.util.LogHelper;
import covers1624.powerconverters.util.RFHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.event.FMLMissingMappingsEvent.MissingMapping;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.Type;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.Set;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, dependencies = "required-after:Covers1624Lib;after:BuildCraft|Core;after:factorization;after:IC2;after:Railcraft;after:ThermalExpansion", guiFactory = Reference.GUI_FACTORY)
public class PowerConverters {

    @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
    public static CommonProxy proxy;

    @Instance("Power Converters")
    public static PowerConverters instance;

    public static final PCCreativeTab creativeTab = new PCCreativeTab();

    public static int steamId = -1;

    public PowerConverters() {
        long start = System.currentTimeMillis();
        LogHelper.info("Attempting to find PowerConverters Recipe Modules...");
        RecipeModuleDiscoverer.findModules();
        LogHelper.info("Finished search in %s Ms.", (System.currentTimeMillis() - start));
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        //NBTBulshitery.init();
        //Load the static Loader.isModLoaded calls once all mods have been discovered.
        AbstractRecipeModule.init();
        RecipeModuleDiscoverer.sortRecipeModules();
        checkClassLoader();
        instance = this;

        LogHelper.info("PowerConverters PreInitialization Started.");

        LogHelper.trace("Checking For RF API...");
        RFHelper.init();

        LogHelper.trace("Initializing Configuration File");
        ConfigurationHandler.init(event.getSuggestedConfigurationFile());

        //TODO, Make betterer
        LogHelper.trace("Registering Update Manager");
        UpdateManager updateManager = new UpdateManager();
        FMLCommonHandler.instance().bus().register(updateManager);

        LogHelper.trace("Registering Event Handlers.");
        PCEventHandler eventHandler = new PCEventHandler();
        MinecraftForge.EVENT_BUS.register(eventHandler);
        FMLCommonHandler.instance().bus().register(eventHandler);
        FMLCommonHandler.instance().bus().register(RecipeStateManager.instance());

        LogHelper.trace("Initializing PowerSystems");
        PowerSystems.init();

        LogHelper.trace("Initializing ChargeHandlers");
        PowerSystems.initChargeHandlers();

        LogHelper.info("PowerConverters PreInitialization Finished.");

    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        LogHelper.info("PowerConverters Core Initialization Started.");

        LogHelper.trace("Initializing PacketPipeline");
        PacketPipeline.instance().initalise();
        PacketPipeline.instance().registerPacket(RecipeSyncPacket.class);
        PacketPipeline.instance().registerPacket(EnergyBridgeSyncPacket.class);

        //MinecraftForge.EVENT_BUS.register(GridTickHandler.energy);
        //FMLCommonHandler.instance().bus().register(GridTickHandler.energy);

        LogHelper.trace("Registering Gui Handler.");
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new PCGUIHandler());

        LogHelper.trace("Registering Blocks.");
        ModBlocks.init();

        LogHelper.trace("Registering Items.");
        ModItems.init();

        LogHelper.trace("Registering Client Rendering.");
        proxy.initRendering();
        LogHelper.trace("Registering Default Recipes.");
        RecipeNBTLogicDiscovery.registerLogic(new VanillaShapelessRecipeNBTLogic());
        RecipeNBTLogicDiscovery.registerLogic(new VanillaShapedRecipeNBTLogic());
        Recipes.init();

        if (Loader.isModLoaded("Waila")) {
            LogHelper.trace("Registering Waila Module.");
            FMLInterModComms.sendMessage("Waila", "register", "covers1624.powerconverters.waila.WailaModule.callBackRegister");
        }
        LogHelper.info("PowerConverters Core Initialization Finished.");
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        LogHelper.info("PowerConverters PostInitialization Started.");
        LogHelper.trace("PostInitializing PacketPipeline. ALL PACKETS SHOULD BE REGISTERED BY NOW!");
        PacketPipeline.instance().postInitialise();

        // Pull steam ID
        if (FluidRegistry.isFluidRegistered("steam")) {
            steamId = FluidRegistry.getFluidID("steam");
        }

        LogHelper.trace("PowerConverters PostInitialization Finished.");

        // LogHelper.info("Dumping Blackboard...");
        // for (Entry<String, Object> entry : Launch.blackboard.entrySet()) {
        // LogHelper.info(entry.getKey() + " " + entry.getValue());
        // }
    }

    // This re-maps the old id's to the new ones as our modid changed.
    @EventHandler
    public void missingMapping(FMLMissingMappingsEvent event) {
        for (MissingMapping mapping : event.getAll()) {
            if (mapping.type == GameRegistry.Type.BLOCK) {
                //TODO Remove this.
                if (mapping.name.equals("PowerConverters:tile.powerconverters.conduit")) {
                    LogHelper.warn("REMAPPING BLOCK: " + mapping.name);
                    mapping.remap(Blocks.air);
                }
                if (mapping.name.equals("PowerConverters:tile.powerconverters.rf")) {
                    LogHelper.warn("REMAPPING BLOCK: " + mapping.name);
                    mapping.remap(ModBlocks.converterBlockRedstoneFlux);
                }
                if (mapping.name.equals("PowerConverters:tile.powerconverters.ic2")) {
                    LogHelper.warn("REMAPPING BLOCK: " + mapping.name);
                    mapping.remap(ModBlocks.converterBlockIndustrialCraft);
                }
                if (mapping.name.equals("PowerConverters:tile.powerconverters.fz")) {
                    LogHelper.warn("REMAPPING BLOCK: " + mapping.name);
                    mapping.remap(ModBlocks.converterBlockFactorization);
                }
                if (mapping.name.equals("PowerConverters:tile.powerconverters.steam")) {
                    LogHelper.warn("REMAPPING BLOCK: " + mapping.name);
                    mapping.remap(ModBlocks.converterBlockSteam);
                }
                if (mapping.name.equals("PowerConverters:tile.powerconverters.common")) {
                    LogHelper.warn("REMAPPING BLOCK: " + mapping.name);
                    mapping.remap(ModBlocks.converterBlockCommon);
                }
            }
            if (mapping.type == Type.ITEM) {
                if (mapping.name.equals("PowerConverters:debugItem")) {
                    LogHelper.warn("REMAPPING ITEM: " + mapping.name);
                    mapping.remap(ModItems.debugItem);
                }
                //TODO Remove this.
                if (mapping.name.equals("PowerConverters:tile.powerconverters.conduit")) {
                    LogHelper.warn("REMAPPING ITEM: " + mapping.name);
                    mapping.remap(Item.getItemFromBlock(Blocks.air));
                }
                if (mapping.name.equals("PowerConverters:tile.powerconverters.rf")) {
                    LogHelper.warn("REMAPPING ITEM: " + mapping.name);
                    mapping.remap(Item.getItemFromBlock(ModBlocks.converterBlockRedstoneFlux));
                }
                if (mapping.name.equals("PowerConverters:tile.powerconverters.ic2")) {
                    LogHelper.warn("REMAPPING ITEM: " + mapping.name);
                    mapping.remap(Item.getItemFromBlock(ModBlocks.converterBlockIndustrialCraft));
                }
                if (mapping.name.equals("PowerConverters:tile.powerconverters.fz")) {
                    LogHelper.warn("REMAPPING ITEM: " + mapping.name);
                    mapping.remap(Item.getItemFromBlock(ModBlocks.converterBlockFactorization));
                }
                if (mapping.name.equals("PowerConverters:tile.powerconverters.steam")) {
                    LogHelper.warn("REMAPPING ITEM: " + mapping.name);
                    mapping.remap(Item.getItemFromBlock(ModBlocks.converterBlockSteam));
                }
                if (mapping.name.equals("PowerConverters:tile.powerconverters.common")) {
                    LogHelper.warn("REMAPPING ITEM: " + mapping.name);
                    mapping.remap(Item.getItemFromBlock(ModBlocks.converterBlockCommon));
                }
            }
        }
    }

    private static void checkClassLoader() {
        Set<String> transformerExceptions = ReflectionHelper.getPrivateValue(LaunchClassLoader.class, Launch.classLoader, "transformerExceptions");
        if (!transformerExceptions.contains("covers1624.powerconverters.")) {
            LogHelper.fatal("PowerConverters has detected that it has been removed from the transformerExceptions list, this could cause unknown issues. I Will provide no support for PowerConverters in this state.");
        }
    }
}
