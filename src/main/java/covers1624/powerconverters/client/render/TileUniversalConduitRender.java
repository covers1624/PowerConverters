package covers1624.powerconverters.client.render;

import covers1624.powerconverters.reference.Reference;
import covers1624.powerconverters.tile.conduit.TileEnergyConduit;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class TileUniversalConduitRender extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks) {
		TileEnergyConduit tileEntity = (TileEnergyConduit)te;
		// Setup.
		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		//GL11.glTranslatef((float) x, (float) y + 1, (float) z + 1);
		bindTexture(new ResourceLocation(Reference.TEXTURE_FOLDER + "blocks/universalCharger.png"));
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addTranslation((float) x, (float) y, (float) z);
		// Actual render.



		// Cleanup.
		tessellator.addTranslation((float) -x, (float) -y, (float) -z);
		tessellator.draw();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}
}
