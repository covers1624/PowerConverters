package powercrystals.powerconverters.gui;

import powercrystals.powerconverters.handler.ConfigurationHandler;
import powercrystals.powerconverters.reference.Reference;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.config.GuiConfig;

public class PCConfigGui extends GuiConfig{

	public PCConfigGui(GuiScreen parent) {
		super(parent, new ConfigElement(ConfigurationHandler.configuration.getCategory(Reference.BASIC_CATEGORY)).getChildElements(), "Power Converters", false, true, GuiConfig.getAbridgedConfigPath(ConfigurationHandler.configuration.toString()));
	}
	
}
