package covers1624.powerconverters;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import covers1624.powerconverters.grid.GridTickHandler;
import covers1624.powerconverters.handler.ConfigurationHandler;
import covers1624.powerconverters.handler.PCEventHandler;
import covers1624.powerconverters.handler.PCGUIHandler;
import covers1624.powerconverters.init.ModBlocks;
import covers1624.powerconverters.init.ModItems;
import covers1624.powerconverters.init.PowerSystems;
import covers1624.powerconverters.init.Recipes;
import covers1624.powerconverters.init.WorldGenerators;
import covers1624.powerconverters.net.EnergyBridgeSyncPacket;
import covers1624.powerconverters.net.PacketPipeline;
import covers1624.powerconverters.net.RecipeSyncPacket;
import covers1624.powerconverters.proxy.IPCProxy;
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
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
import cpw.mods.fml.common.event.FMLMissingMappingsEvent.MissingMapping;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.Type;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, dependencies = "after:BuildCraft|Energy;after:factorization;after:IC2;after:Railcraft;after:ThermalExpansion", guiFactory = Reference.GUI_FACTORY)
public class PowerConverters {

	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
	public static IPCProxy proxy;

	@Instance("Power Converters")
	public static PowerConverters instance;

	private static ConfigurationHandler configHandler;

	public static int steamId = -1;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		instance = this;

		LogHelper.info("PowerConverters PreInitialization Started.");

		LogHelper.trace("Checking For RF API...");
		RFHelper.init();

		LogHelper.trace("Initializing Configuration File");
		configHandler = new ConfigurationHandler(event.getSuggestedConfigurationFile());

		LogHelper.trace("Registering Update Manager");
		UpdateManager updateManager = new UpdateManager();
		FMLCommonHandler.instance().bus().register(updateManager);

		LogHelper.trace("Registering Event Handlers.");
		PCEventHandler eventHandler = new PCEventHandler();
		MinecraftForge.EVENT_BUS.register(eventHandler);
		FMLCommonHandler.instance().bus().register(eventHandler);

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
		PacketPipeline.INSTANCE.initalise();
		PacketPipeline.INSTANCE.registerPacket(RecipeSyncPacket.class);
		PacketPipeline.INSTANCE.registerPacket(EnergyBridgeSyncPacket.class);

		MinecraftForge.EVENT_BUS.register(GridTickHandler.energy);
		FMLCommonHandler.instance().bus().register(GridTickHandler.energy);

		LogHelper.trace("Registering Gui Handler.");
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new PCGUIHandler());

		LogHelper.trace("Registering Blocks.");
		ModBlocks.init();

		LogHelper.trace("Registering Items.");
		ModItems.init();

		LogHelper.trace("Registering World Generators.");
		WorldGenerators.init();

		LogHelper.trace("Registering Client Rendering.");
		proxy.initRendering();

		if (ConfigurationHandler.useTechRebornRecipes) {

		} else if (ConfigurationHandler.useThermalExpansionRecipes) {

		} else {
			LogHelper.trace("Registering Default Recipes.");
			Recipes.initDefaults();
		}
		Recipes.setDefaultRecipes(Recipes.getCurrentRecipes());

		if (Loader.isModLoaded("Waila")) {
			FMLInterModComms.sendMessage("Waila", "register", "covers1624.powerconverters.waila.WailaModule.callBackRegister");
		}
		LogHelper.info("PowerConverters Core Initialization Finished.");
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		LogHelper.info("PowerConverters PostInitialization Started.");

		LogHelper.trace("PostInitializing PacketPipeline. ALL PACKETS SHOULD BE REGISTERED BY NOW!");
		PacketPipeline.INSTANCE.postInitialise();

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
				if (mapping.name.equals("PowerConverters:tile.powerconverters.conduit")) {
					LogHelper.warn("REMAPING BLOCK: " + mapping.name);
					mapping.remap(ModBlocks.conduitBlock);
				}
				if (mapping.name.equals("PowerConverters:tile.powerconverters.rf")) {
					LogHelper.warn("REMAPING BLOCK: " + mapping.name);
					mapping.remap(ModBlocks.converterBlockRedstoneFlux);
				}
				if (mapping.name.equals("PowerConverters:tile.powerconverters.ic2")) {
					LogHelper.warn("REMAPING BLOCK: " + mapping.name);
					mapping.remap(ModBlocks.converterBlockIndustrialCraft);
				}
				if (mapping.name.equals("PowerConverters:tile.powerconverters.fz")) {
					LogHelper.warn("REMAPING BLOCK: " + mapping.name);
					mapping.remap(ModBlocks.converterBlockFactorization);
				}
				if (mapping.name.equals("PowerConverters:tile.powerconverters.steam")) {
					LogHelper.warn("REMAPING BLOCK: " + mapping.name);
					mapping.remap(ModBlocks.converterBlockSteam);
				}
				if (mapping.name.equals("PowerConverters:tile.powerconverters.common")) {
					LogHelper.warn("REMAPING BLOCK: " + mapping.name);
					mapping.remap(ModBlocks.converterBlockCommon);
				}
			}
			if (mapping.type == Type.ITEM) {
				if (mapping.name.equals("PowerConverters:debugItem")) {
					LogHelper.warn("REMAPING ITEM: " + mapping.name);
					mapping.remap(ModItems.debugItem);
				}
				if (mapping.name.equals("PowerConverters:tile.powerconverters.conduit")) {
					LogHelper.warn("REMAPING ITEM: " + mapping.name);
					mapping.remap(Item.getItemFromBlock(ModBlocks.conduitBlock));
				}
				if (mapping.name.equals("PowerConverters:tile.powerconverters.rf")) {
					LogHelper.warn("REMAPING ITEM: " + mapping.name);
					mapping.remap(Item.getItemFromBlock(ModBlocks.converterBlockRedstoneFlux));
				}
				if (mapping.name.equals("PowerConverters:tile.powerconverters.ic2")) {
					LogHelper.warn("REMAPING ITEM: " + mapping.name);
					mapping.remap(Item.getItemFromBlock(ModBlocks.converterBlockIndustrialCraft));
				}
				if (mapping.name.equals("PowerConverters:tile.powerconverters.fz")) {
					LogHelper.warn("REMAPING ITEM: " + mapping.name);
					mapping.remap(Item.getItemFromBlock(ModBlocks.converterBlockFactorization));
				}
				if (mapping.name.equals("PowerConverters:tile.powerconverters.steam")) {
					LogHelper.warn("REMAPING ITEM: " + mapping.name);
					mapping.remap(Item.getItemFromBlock(ModBlocks.converterBlockSteam));
				}
				if (mapping.name.equals("PowerConverters:tile.powerconverters.common")) {
					LogHelper.warn("REMAPING ITEM: " + mapping.name);
					mapping.remap(Item.getItemFromBlock(ModBlocks.converterBlockCommon));
				}
			}
		}
	}
}
