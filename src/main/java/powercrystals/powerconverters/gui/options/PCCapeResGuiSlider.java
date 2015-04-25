package powercrystals.powerconverters.gui.options;

import net.minecraft.client.gui.GuiButton;

public class PCCapeResGuiSlider extends GuiButton {

	private float slider;

	public PCCapeResGuiSlider(int id, int xPos, int yPos) {
		super(id, xPos, yPos, 150, 20, "");
		this.slider = 1.0F;
	}

}
