package covers1624.powerconverters.gui.element;

import net.minecraft.util.StatCollector;

public abstract class GuiElement {

	protected GuiCoords elementLocation;
	protected String elementName;
	// Localized Tooltip
	protected String elementTooltip;

	/**
	 * 
	 * @param location
	 *            Where to render the Element
	 * @param name
	 *            Internal name of the Element
	 * @param unlocTooltip
	 *            Translates the tooltip using lang file.
	 */
	public GuiElement(GuiCoords location, String name, String unlocTooltip) {
		elementLocation = location;
		elementName = name;
		elementTooltip = StatCollector.translateToLocal(unlocTooltip);
	}

	public abstract void drawElementForegroundLayer(int mouseX, int mouseY);

	public abstract void drawElementBackgroundLayer(int mouseX, int mouseY);
}
