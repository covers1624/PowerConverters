package covers1624.powerconverters.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import covers1624.powerconverters.updatechecker.ModUpdate;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
@SideOnly(Side.CLIENT)
public class GuiModUpdateNotification extends Gui {
	private static final ResourceLocation field_146261_a = new ResourceLocation("textures/gui/achievement/achievement_background.png");
	private Minecraft mc;
	private int width;
	private int height;
	private ModUpdate update;
	private String modUpdate;
	private String modVersion;
	private long timeOnScreen;
	private RenderItem renderItem;
	private boolean someBoolean;

	public GuiModUpdateNotification(Minecraft minecraft, ModUpdate update) {
		this.mc = minecraft;
		this.renderItem = new RenderItem();
		this.update = update;
		this.modUpdate = update.getModName() + " Has an update";
		this.modVersion = update.getModVersion();
		this.timeOnScreen = Minecraft.getSystemTime();
		this.someBoolean = false;
	}


	private void preRender() {
		GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		this.width = this.mc.displayWidth;
		this.height = this.mc.displayHeight;
		ScaledResolution scaledresolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
		this.width = scaledresolution.getScaledWidth();
		this.height = scaledresolution.getScaledHeight();
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0D, (double) this.width, (double) this.height, 0.0D, 1000.0D, 3000.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
	}

	public void update() {
		if (this.timeOnScreen != 0L && this.mc.thePlayer != null) {
			double d0 = (double) (Minecraft.getSystemTime() - this.timeOnScreen) / 3000.0D;

			if (!this.someBoolean) {
				if (d0 < 0.0D || d0 > 1.0D) {
					this.timeOnScreen = 0L;
					return;
				}
			} else if (d0 > 0.5D) {
				d0 = 0.5D;
			}

			this.preRender();
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glDepthMask(false);
			double d1 = d0 * 2.0D;

			if (d1 > 1.0D) {
				d1 = 2.0D - d1;
			}

			d1 *= 4.0D;
			d1 = 1.0D - d1;

			if (d1 < 0.0D) {
				d1 = 0.0D;
			}

			d1 *= d1;
			d1 *= d1;
			int i = this.width - 160;
			int j = 0 - (int) (d1 * 36.0D);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			this.mc.getTextureManager().bindTexture(field_146261_a);
			GL11.glDisable(GL11.GL_LIGHTING);
			this.drawTexturedModalRect(i, j, 96, 202, 160, 32);

			this.mc.fontRenderer.drawString(this.modUpdate, i + 30, j + 7, -256);
			this.mc.fontRenderer.drawString(this.modVersion, i + 30, j + 18, -1);

			RenderHelper.enableGUIStandardItemLighting();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glEnable(GL11.GL_COLOR_MATERIAL);
			GL11.glEnable(GL11.GL_LIGHTING);
			this.renderItem.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), this.update.getUpdateIcon(), i + 8, j + 8);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDepthMask(true);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
		}
	}

}
