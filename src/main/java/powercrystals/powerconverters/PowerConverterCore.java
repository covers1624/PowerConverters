package powercrystals.powerconverters;

import net.minecraft.command.ICommand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fluids.FluidRegistry;
import powercrystals.powerconverters.command.EnumCommands;
import powercrystals.powerconverters.gui.PCGUIHandler;
import powercrystals.powerconverters.handler.ConfigurationHandler;
import powercrystals.powerconverters.handler.EventsHandler;
import powercrystals.powerconverters.handler.RainHandler;
import powercrystals.powerconverters.init.ModBlocks;
import powercrystals.powerconverters.init.ModItems;
import powercrystals.powerconverters.init.PowerSystems;
import powercrystals.powerconverters.init.Recipes;
import powercrystals.powerconverters.init.WorldGenerators;
import powercrystals.powerconverters.net.IPCProxy;
import powercrystals.powerconverters.power.PowerSystem;
import powercrystals.powerconverters.reference.Config;
import powercrystals.powerconverters.reference.Reference;
import powercrystals.powerconverters.util.LogHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, dependencies = "after:BuildCraft|Energy;after:factorization;after:IC2;after:Railcraft;after:ThermalExpansion", guiFactory = Reference.GUI_FACTORY)
public class PowerConverterCore {

	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
	public static IPCProxy proxy;

	@Instance("Power Converters")
	public static PowerConverterCore instance;

	public static int steamId;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		instance = this;
		LogHelper.info("Power Converters PreInitialization Started.");
		
		//First thing we do so we can catch fluid register Events.
		LogHelper.trace("Registering Event Handlers.");
		FMLCommonHandler.instance().bus().register(new EventsHandler());
		
		LogHelper.trace("Initalizing Configuration File");
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());
		
		LogHelper.trace("Initalizing PowerSystems");
		PowerSystems.init();
		
		LogHelper.trace("Initalizing ChargeHandlers");
		PowerSystems.initChargeHandlers();
		
		LogHelper.info("Power Converters PreInitialization Finished.");
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		LogHelper.info("Power Converters Core Initialization Started.");

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
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		if (FluidRegistry.isFluidRegistered("Steam")) {
			steamId = FluidRegistry.getFluidID("Steam");
		}
	}
	@EventHandler
	public void serverStarting(FMLServerStartingEvent event){
		LogHelper.trace("Server Starting Started");
		
		LogHelper.trace("Registering Rain Handler");
		FMLCommonHandler.instance().bus().register(new RainHandler() );
		
		LogHelper.trace("Registering Commands");
		for(EnumCommands command : EnumCommands.values()){
			LogHelper.trace("Registering Command: " + command.getCommand().getCommandName());
			event.registerServerCommand(command.getCommand());
		}
		LogHelper.trace("Server Starting Finished");
	}
}
