package powercrystals.powerconverters;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fluids.FluidRegistry;
import powercrystals.powerconverters.gui.PCGUIHandler;
import powercrystals.powerconverters.handler.EventsHandler;
import powercrystals.powerconverters.helper.ConfigurationHelper;
import powercrystals.powerconverters.init.ModBlocks;
import powercrystals.powerconverters.init.ModItems;
import powercrystals.powerconverters.init.PowerSystems;
import powercrystals.powerconverters.init.Recipes;
import powercrystals.powerconverters.net.IPCProxy;
import powercrystals.powerconverters.power.PowerSystem;
import powercrystals.powerconverters.reference.Config;
import powercrystals.powerconverters.reference.Reference;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, dependencies = "after:BuildCraft|Energy;after:factorization;after:IC2;after:Railcraft;after:ThermalExpansion", guiFactory = Reference.GUI_FACTORY)
public class PowerConverterCore{

	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
	public static IPCProxy proxy;

	@Instance("Power Converters")
	public static PowerConverterCore instance;

	public static int steamId;

	@EventHandler
	public void preInit(FMLPreInitializationEvent evt) {
		instance = this;
		// Init Configuration
		ConfigurationHelper.init(evt.getSuggestedConfigurationFile());

		PowerSystems.init();

	}

	@EventHandler
	public void init(FMLInitializationEvent evt) {
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new PCGUIHandler());
		FMLCommonHandler.instance().bus().register(new EventsHandler());
		ModBlocks.init();
		ModItems.init();

		if (ConfigurationHelper.altRecipes.getBoolean()) {

		} else {
			Recipes.initDefaults();
		}
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent evt) {
		if (FluidRegistry.isFluidRegistered("Steam")) {
			steamId = FluidRegistry.getFluidID("Steam");
		}
	}
}
