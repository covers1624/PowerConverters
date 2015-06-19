package powercrystals.powerconverters;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import powercrystals.powerconverters.gui.PCGUIHandler;
import powercrystals.powerconverters.handler.ConfigurationHandler;
import powercrystals.powerconverters.handler.PCEventHandler;
import powercrystals.powerconverters.init.ModBlocks;
import powercrystals.powerconverters.init.ModItems;
import powercrystals.powerconverters.init.PowerSystems;
import powercrystals.powerconverters.init.Recipes;
import powercrystals.powerconverters.init.WorldGenerators;
import powercrystals.powerconverters.net.IPCProxy;
import powercrystals.powerconverters.reference.Reference;
import powercrystals.powerconverters.updatechecker.UpdateManager;
import powercrystals.powerconverters.util.CapeType;
import powercrystals.powerconverters.util.LogHelper;
import powercrystals.powerconverters.util.RFHelper;
import powercrystals.powerconverters.util.Vec3F;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, dependencies = "after:BuildCraft|Energy;after:factorization;after:IC2;after:Railcraft;after:ThermalExpansion", guiFactory = Reference.GUI_FACTORY)
public class PowerConverterCore {

	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
	public static IPCProxy proxy;

	@Instance("Power Converters")
	public static PowerConverterCore instance;

	private static ConfigurationHandler configHandler;

	private static HashMap<String, CapeType> cloaks = new HashMap<String, CapeType>();
	private static HashMap<String, ArrayList<Vec3F>> particleColors = new HashMap<String, ArrayList<Vec3F>>();

	public static int steamId = -1;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		instance = this;
		proxy.preInit();

		LogHelper.info("Power Converters PreInitialization Started.");

		LogHelper.trace("Initalizing Configuration File");
		configHandler = new ConfigurationHandler(event.getSuggestedConfigurationFile());

		LogHelper.trace("Registering Update Manager");
		UpdateManager updateManager = new UpdateManager();
		FMLCommonHandler.instance().bus().register(updateManager);

		LogHelper.trace("Registering Event Handlers.");
		PCEventHandler eventHandler = new PCEventHandler();
		MinecraftForge.EVENT_BUS.register(eventHandler);
		FMLCommonHandler.instance().bus().register(eventHandler);

		LogHelper.trace("Initalizing PowerSystems");
		PowerSystems.init();

		// LogHelper.trace("Initalizing ChargeHandlers");
		// PowerSystems.initChargeHandlers();

		LogHelper.info("Power Converters PreInitialization Finished.");
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		LogHelper.info("Power Converters Core Initialization Started.");

		// MinecraftForge.EVENT_BUS.register(GridTickHandler.energy);
		// FMLCommonHandler.instance().bus().register(GridTickHandler.energy);
		LogHelper.trace("Checking For RF API...");
		RFHelper.init();

		LogHelper.trace("Registering Gui Handler.");
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new PCGUIHandler());

		LogHelper.trace("Registering Blocks.");
		ModBlocks.init();

		LogHelper.trace("Registering Items.");
		ModItems.init();

		LogHelper.trace("Registering World Generators.");
		WorldGenerators.init();

		if (ConfigurationHandler.altRecipes) {

		} else {
			LogHelper.trace("Registering Default Recipes.");
			Recipes.initDefaults();
		}

		if (Loader.isModLoaded("Waila")) {
			FMLInterModComms.sendMessage("Waila", "register", "powercrystals.powerconverters.waila.WailaProvider.callBackRegister");
		}

		if (event.getSide() == Side.CLIENT) {
			// MinecraftForge.EVENT_BUS.register(new CloakHandler());
			// ShaderHelper.initShaders();
		}
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		if (FluidRegistry.isFluidRegistered("steam")) {
			steamId = FluidRegistry.getFluidID("steam");
		}
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		LogHelper.trace("Server Starting Started");

		// 0--LogHelper.trace("Registering Rain Handler");
		// MinecraftForge.EVENT_BUS.register(new RainHandler());

		// LogHelper.trace("Registering Commands");
		// for (EnumCommands command : EnumCommands.values()) {
		// LogHelper.trace("Registering Command: " + command.getCommand().getCommandName());
		// event.registerServerCommand(command.getCommand());
		// }
		LogHelper.trace("Server Starting Finished");
	}

	public static void addCloak(String name, CapeType type) {
		cloaks.put(name, type);
	}

	public static CapeType getCloak(String name) {
		return cloaks.get(name);
	}

	public static void clearCloaks() {
		cloaks.clear();
	}

	public static void addParticleCloak(String name, ArrayList<Vec3F> colors) {
		particleColors.put(name, colors);
	}

	public static ArrayList<Vec3F> getPrarticleCloak(String name) {
		return particleColors.get(name);
	}

	public static void clearParticleCloaks() {
		particleColors.clear();
	}

	public static HashMap<String, ArrayList<Vec3F>> getParticlehashMap() {
		return particleColors;
	}

}
