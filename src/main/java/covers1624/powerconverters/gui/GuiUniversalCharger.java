package covers1624.powerconverters.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import covers1624.powerconverters.container.ContainerUniversalCharger;
import covers1624.powerconverters.reference.Reference;
import covers1624.powerconverters.tile.main.TileEntityCharger;

public class GuiUniversalCharger extends GuiContainer {

	public GuiUniversalCharger(InventoryPlayer playerInventory, TileEntityCharger charger) {
		super(new ContainerUniversalCharger(playerInventory, charger));
		ySize = 186;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
		fontRendererObj.drawString("Universal Charger", 40, 6, 4210752);
		fontRendererObj.drawString("Inventory", 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(new ResourceLocation(Reference.GUI_FOLDER + "universalCharger.png"));
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}

}
