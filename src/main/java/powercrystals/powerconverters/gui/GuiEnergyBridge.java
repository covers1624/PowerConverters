package powercrystals.powerconverters.gui;

import org.lwjgl.opengl.GL11;

import powercrystals.powerconverters.common.BridgeSideData;
import powercrystals.powerconverters.common.TileEntityEnergyBridge;
import powercrystals.powerconverters.reference.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;

public class GuiEnergyBridge extends GuiContainer {
	private static final int _barColor = (255) | (165 << 8) | (0 << 16) | (255 << 24);

	protected TileEntityEnergyBridge _bridge;

	public static final ResourceLocation BridgeGui = new ResourceLocation(Reference.guiFolder + "energybridge.png");

	public GuiEnergyBridge(ContainerEnergyBridge container, TileEntityEnergyBridge te) {
		super(container);
		ySize = 195;
		_bridge = te;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRendererObj.drawString("Energy Bridge", 8, 6, 4210752);

		if (_bridge.isInputLimited()) {
			fontRendererObj.drawString("INPUT LIMITED", 98, 6, -1);
		} else {
			fontRendererObj.drawString("OUTPUT LIMITED", 90, 6, -1);
		}

		for (int i = 0; i < 6; i++) {
			ForgeDirection dir = ForgeDirection.getOrientation(i);

			fontRendererObj.drawString(dir.toString(), 10, 6 + 12 * (i + 1), -1);
			BridgeSideData data = _bridge.getDataForSide(dir);

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
		drawRect(46, 97, 46 + (int)_bridge.getEnergyScaled(), 89, _barColor);
		GL11.glEnable(GL11.GL_LIGHTING);

		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float gameTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(BridgeGui);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		for (int i = 0; i < 6; i++) {
			ForgeDirection dir = ForgeDirection.getOrientation(i);
			BridgeSideData data = _bridge.getDataForSide(dir);

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

	private String getOutputRateString(BridgeSideData data) {
		if (!data.isConnected)
			return "NO LINK";
		double rate = data.outputRate;
		if (rate > 1000) {
			double rateThousand = (rate / 1000.0);
			return String.format("%.1f %s%s", rateThousand, "k", data.powerSystem.getUnit());
		}
		return rate + " " + data.powerSystem.getUnit();
	}
}
