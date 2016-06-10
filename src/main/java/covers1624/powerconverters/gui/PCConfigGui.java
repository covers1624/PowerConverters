package covers1624.powerconverters.gui;

import covers1624.powerconverters.handler.ConfigurationHandler;
import covers1624.powerconverters.reference.Reference;
import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

public class PCConfigGui extends GuiConfig {

    public PCConfigGui(GuiScreen parent) {
        super(parent, new ConfigElement(ConfigurationHandler.configuration.getCategory(Reference.BASIC_CATEGORY)).getChildElements(), Reference.MOD_ID, false, true, GuiConfig.getAbridgedConfigPath(ConfigurationHandler.configuration.toString()));
    }

}
