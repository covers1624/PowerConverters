package powercrystals.powerconverters.fx;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.profiler.Profiler;

import org.lwjgl.opengl.GL11;

/**
 * 
 * @author Vazkii
 */
public class ParticleRenderDispatcher {
	public static int sparkleFxCount = 0;
	public static int fakeSparkleFxCount = 0;

	public static void dispatch() {
		Tessellator tessellator = Tessellator.instance;

		boolean isLightingEnabled = GL11.glGetBoolean(GL11.GL_LIGHTING);
		Profiler profiler = Minecraft.getMinecraft().mcProfiler;

		GL11.glDepthMask(false);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);
		if (isLightingEnabled)
			GL11.glDisable(GL11.GL_LIGHTING);

		profiler.startSection("sparkle");
		FXSparkle.dispatchQueuedRenders(tessellator);
		profiler.endSection();

		if (isLightingEnabled)
			GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDepthMask(true);
	}
}
