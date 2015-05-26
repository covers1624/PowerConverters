package powercrystals.powerconverters.gui.options;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.resources.I18n;
import powercrystals.powerconverters.handler.ConfigurationHandler;

public class PCOptionsGui extends GuiScreen implements GuiYesNoCallback {

	private final GuiScreen parentScreen;
	private final ConfigurationHandler config;

	public PCOptionsGui(GuiScreen parent, ConfigurationHandler configurationHandler) {
		parentScreen = parent;
		config = configurationHandler;
	}

	/**
	 * Add Our buttons and sliders.
	 */
	@Override
	public void initGui() {
		buttonList.clear();
		this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, I18n.format("gui.done", new Object[0])));
		this.buttonList.add(new PCCapeReloadGuiButton(100, this.width / 2 - 155, this.height / 6 + 144 - 6, "Refresh Capes"));
		this.buttonList.add(new PCParticleGuiButton(101, this.width / 2 + 5, this.height / 6 + 144 - 6, "Refresh Particles"));
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		int id = button.id;
		if (button.enabled) {
			if (id == 200) {
				this.mc.displayGuiScreen(parentScreen);
			}
		}
	}

	@Override
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRendererObj, "Power Converters Options", this.width / 2, 15, 16777215);
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
	}

	@Override
	public void onGuiClosed() {
		ConfigurationHandler.INSTANCE.loadConfiguration();
	}
}
