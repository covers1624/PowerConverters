package powercrystals.powerconverters.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import powercrystals.powerconverters.util.LogHelper;

public class PCGuiButton extends GuiButton {

	public PCGuiButton(int id, int xPos, int yPos, int width, int height, String displayString) {
		super(id, xPos, yPos, width, height, displayString);
	}

	@Override
	public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_) {
		super.drawButton(p_146112_1_, p_146112_2_, p_146112_3_);
	}

	@Override
	public boolean mousePressed(Minecraft p_146116_1_, int p_146116_2_, int p_146116_3_) {
		if (super.mousePressed(p_146116_1_, p_146116_2_, p_146116_3_)) {
			LogHelper.info("Button Pressed");
			GuiOptions options = (GuiOptions) Minecraft.getMinecraft().currentScreen;
			for (Object suspect : options.buttonList) {
				GuiButton button = (GuiButton) suspect;
				LogHelper.info("Button Name: " + button.displayString + " XPosition: " + button.xPosition + " YPosition " + button.xPosition);
			}

			return true;
		}
		return false;
	}

}
