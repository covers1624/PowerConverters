package covers1624.powerconverters.client.render;

import covers1624.powerconverters.tile.conduit.TileEnergyConduit;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class TileUniversalConduitRender extends TileEntitySpecialRenderer {

	public void renderTileEntityAt(TileEnergyConduit tileEntity, double x, double y, double z, float partialTicks) {
		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glTranslatef((float) x, (float) y + 1, (float) z + 1);
		Tessellator tessellator = Tessellator.instance;
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks) {
		renderTileEntityAt((TileEnergyConduit) tileEntity, x, y, z, partialTicks);
	}
}
