package covers1624.powerconverters.gui;

import covers1624.powerconverters.api.bridge.BridgeSideData;
import covers1624.powerconverters.container.ContainerEnergyBridge;
import covers1624.powerconverters.reference.Reference;
import covers1624.powerconverters.tile.main.TileEntityEnergyBridge;
import covers1624.powerconverters.util.GuiArea;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

public class GuiEnergyBridge extends GuiContainer {

	private TileEntityEnergyBridge energyBridge;
	// private ContainerEnergyBridge containerEnergyBridge;

	private GuiArea[] guiAreas = new GuiArea[7];

	public GuiEnergyBridge(ContainerEnergyBridge container, TileEntityEnergyBridge te) {
		super(container);
		// xSize = 248;
		// ySize = 255;

		ySize = 195;
		energyBridge = te;
		// containerEnergyBridge = container;
		// initGuiAreas();
	}

	// Not the most efficient but it works
	private void initGuiAreas() {
		guiAreas[0] = new GuiArea(8, 17, 122, 47, ForgeDirection.UP);
		guiAreas[1] = new GuiArea(8, 51, 122, 81, ForgeDirection.NORTH);
		guiAreas[2] = new GuiArea(8, 85, 122, 115, ForgeDirection.EAST);

		guiAreas[3] = new GuiArea(126, 17, 240, 47, ForgeDirection.DOWN);
		guiAreas[4] = new GuiArea(126, 51, 240, 81, ForgeDirection.SOUTH);
		guiAreas[5] = new GuiArea(126, 85, 240, 115, ForgeDirection.WEST);

		guiAreas[6] = new GuiArea(44, 119, 204, 163, ForgeDirection.UNKNOWN);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		// fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 44, ySize - 91, 4210752);
		drawOldGuiContainerForegroundLayer(mouseX, mouseY);
		// BridgeSideData data[] = energyBridge.getClientData();
		// GL11.glPushMatrix();
		// GL11.glScalef(.9F, .9F, .9F);
		// for (GuiArea guiArea : guiAreas) {
		// String dir = GuiUtils.dirToAbbreviation(guiArea.direction);
		// fontRendererObj.drawString(dir.equals("NULL") ? "Bridge" : dir, guiArea.xTop + 2, guiArea.yTop + 2, 4210752);
		// }
		// GL11.glPopMatrix();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float gameTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(new ResourceLocation(Reference.GUI_FOLDER + "energyBridge.png"));
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		// drawSlotLighting(mouseX, mouseY, x, y);
		drawOldGuiContainerBackgroundLayer(gameTicks, mouseX, mouseY, x, y);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int clickedButton) {
		super.mouseClicked(mouseX, mouseY, clickedButton);

	}

	private void drawSlotLighting(int mouseX, int mouseY, int x, int y) {
		GL11.glPushMatrix();

		for (GuiArea guiArea : guiAreas) {
			if (guiArea.isMouseInArea(mouseX, mouseY, x, y)) {
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				GL11.glColorMask(true, true, true, false);
				drawGradientRect(guiArea.xTop + x, guiArea.yTop + y, guiArea.xBottom + x, guiArea.yBottom + y, -2130706433, -2130706433);
				GL11.glColorMask(true, true, true, true);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
			}
		}

		GL11.glPopMatrix();
	}

	private String getOutputRateString(BridgeSideData data) {
		if (!data.isConnected) {
			return "NO LINK";
		}
		double rate = data.outputRate;
		if (rate > 1000) {
			double rateThousand = (rate / 1000.0);
			return String.format("%.1f %s%s", rateThousand, "k", data.powerSystem.getUnit());
		}
		return rate + " " + data.powerSystem.getUnit();
	}

	private void drawOldGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRendererObj.drawString("Energy Bridge", 8, 6, 4210752);

		if (energyBridge.isInputLimited()) {
			fontRendererObj.drawString("INPUT LIMITED", 98, 6, -1);
		} else {
			fontRendererObj.drawString("OUTPUT LIMITED", 90, 6, -1);
		}

		for (int i = 0; i < 6; i++) {
			ForgeDirection dir = ForgeDirection.getOrientation(i);

			fontRendererObj.drawString(dir.toString(), 10, 6 + 12 * (i + 1), -1);
			BridgeSideData data = energyBridge.getDataForSide(dir);

			if ((data.isConsumer || data.isProducer) && data.powerSystem != null) {
				String name = data.powerSystem.getAbbreviation();
				if (data.powerSystem.getVoltageNames() != null) {
					name += " " + data.powerSystem.getVoltageNames()[data.voltageNameIndex];
				}
				fontRendererObj.drawString(name, 49, 6 + 12 * (i + 1), -1);
				fontRendererObj.drawString(data.isConsumer ? "IN" : "OUT", 92, 6 + 12 * (i + 1), -1);
				fontRendererObj.drawString(getOutputRateString(data), 119, 6 + 12 * (i + 1), -1);
			} else {
				fontRendererObj.drawString("<NONE>", 49, 6 + 12 * (i + 1), -1);
			}
		}

		fontRendererObj.drawString("% CHG", 10, 90, -1);

		GL11.glDisable(GL11.GL_LIGHTING);
		drawRect(46, 97, 46 + (int) energyBridge.getEnergyScaled(), 89, (255) | (165 << 8) | (0 << 16) | (255 << 24));
		GL11.glEnable(GL11.GL_LIGHTING);

		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}

	private void drawOldGuiContainerBackgroundLayer(float gameTicks, int mouseX, int mouseY, int x, int y) {
		for (int i = 0; i < 6; i++) {
			ForgeDirection dir = ForgeDirection.getOrientation(i);
			BridgeSideData data = energyBridge.getDataForSide(dir);

			if ((data.isConsumer || data.isProducer) && data.powerSystem != null) {
				if (!data.isConnected) {
					drawTexturedModalRect(x + 7, y + 15 + 12 * i, 0, 208, 162, 12);
				} else if (data.outputRate == 0) {
					drawTexturedModalRect(x + 7, y + 15 + 12 * i, 0, 234, 162, 12);
				} else {
					drawTexturedModalRect(x + 7, y + 15 + 12 * i, 0, 195, 162, 12);
				}
			} else {
				drawTexturedModalRect(x + 7, y + 15 + 12 * i, 0, 221, 162, 12);
			}
		}
	}
}
