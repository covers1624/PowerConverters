package powercrystals.powerconverters.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import powercrystals.powerconverters.handler.ConfigurationHandler;
import powercrystals.powerconverters.reference.Reference;
import cpw.mods.fml.client.config.GuiConfig;

public class PCConfigGui extends GuiConfig {

	public PCConfigGui(GuiScreen parent) {
		super(parent, new ConfigElement(ConfigurationHandler.INSTANCE.configuration.getCategory(Reference.BASIC_CATEGORY)).getChildElements(), Reference.MOD_ID, false, true, GuiConfig.getAbridgedConfigPath(ConfigurationHandler.INSTANCE.configuration.toString()));
	}

}
