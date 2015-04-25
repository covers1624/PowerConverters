package powercrystals.powerconverters.gui.options;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import powercrystals.powerconverters.handler.ConfigurationHandler;

public class PCOptionsGuiButton extends GuiButton {

	public PCOptionsGuiButton(int id, int xPos, int yPos, String displayString) {
		super(id, xPos, yPos, 150, 20, displayString);
	}

	@Override
	public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_) {
		super.drawButton(p_146112_1_, p_146112_2_, p_146112_3_);
	}

	@Override
	public boolean mousePressed(Minecraft minecraft, int p_146116_2_, int p_146116_3_) {
		if (super.mousePressed(minecraft, p_146116_2_, p_146116_3_)) {
			minecraft.gameSettings.saveOptions();
			minecraft.displayGuiScreen(new PCOptionsGui(minecraft.currentScreen, ConfigurationHandler.INSTANCE));
			return true;
		}
		return false;
	}

}
