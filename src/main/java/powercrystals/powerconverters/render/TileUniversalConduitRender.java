package powercrystals.powerconverters.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import powercrystals.powerconverters.reference.Reference;

public class TileUniversalConduitRender extends TileEntitySpecialRenderer {

	private CustomTechneModel model = new CustomTechneModel(new ResourceLocation(Reference.MODEL_FOLDER + "universalConduitModel.tcn"));

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks) {

		CustomTechneModel modelConduit = model;
		// bindTexture(new ResourceLocation(Reference.MODEL_FOLDER + "universalConduitTexture.png"));
		bindTexture(new ResourceLocation(Reference.MODEL_FOLDER + "universalConduitTextureDebug.png"));
		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
		GL11.glScalef(0.06F, 0.06F, 0.06F);
		GL11.glTranslatef(8.35F, -2F, -8.35F);
		modelConduit.renderAll();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}

}
