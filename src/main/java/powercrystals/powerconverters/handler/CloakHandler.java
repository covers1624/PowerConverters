package powercrystals.powerconverters.handler;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Random;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraftforge.client.event.RenderPlayerEvent.Specials.Pre;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.util.CapeType;
import powercrystals.powerconverters.util.LogHelper;
import powercrystals.powerconverters.util.Vec3F;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class CloakHandler {
	private boolean reBuilding = false;

	public static CloakHandler INSTANCE;

	private static Random random = new Random();

	private final String server = "https://raw.githubusercontent.com/covers1624/PowerConverters/master/capes.info";
	private final String particleCloak = "https://raw.githubusercontent.com/covers1624/PowerConverters/master/Particle.info";

	// private HashMap<String, CapeType> cloaks = new HashMap<String, CapeType>();
	// private ArrayList<AbstractClientPlayer> capePlayers = new ArrayList<AbstractClientPlayer>();

	public CloakHandler() {
		INSTANCE = this;
		buildCloakURLDatabase();
		buildParticleDatabase();
	}

	@SubscribeEvent
	public void onPreRenderSpecials(Pre event) {
		if (Loader.isModLoaded("shadersmod") || Loader.isModLoaded("optifine") || reBuilding) {
			return;
		}
		if (event.entityPlayer instanceof AbstractClientPlayer) {
			AbstractClientPlayer abstractClientPlayer = (AbstractClientPlayer) event.entityPlayer;
			capePlayer(abstractClientPlayer, PowerConverterCore.getCloak(abstractClientPlayer.getCommandSenderName()));
			renderParticles(abstractClientPlayer, PowerConverterCore.getCloak(abstractClientPlayer.getCommandSenderName()));
			event.renderCape = true;
		}

	}

	public void buildCloakURLDatabase() {
		try {
			URL url = new URL(server);
			URLConnection con = url.openConnection();
			con.setConnectTimeout(10000);
			con.setReadTimeout(10000);
			InputStream io = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(io));

			int LineTracker = 1;
			String str;
			while ((str = br.readLine()) != null) {
				LogHelper.trace("[CapeDebug] " + str);
				if (!str.startsWith("--")) {
					if (str.contains(":")) {
						String nick = str.substring(0, str.indexOf(":"));
						String type = str.substring(str.indexOf(":") + 1);
						LogHelper.trace("[CapeDebug] Parsed line " + LineTracker + " as: " + nick + ", " + type);
						PowerConverterCore.addCloak(nick, CapeType.parse(type));
					} else {
						LogHelper.error("Syntax error on line " + LineTracker + ":" + str);
					}
				}
				LineTracker++;
			}
			br.close();
		} catch (Exception e) {
			LogHelper.fatal("Capes.info File was unable to be accessed!");
		}
	}

	public void buildParticleDatabase() {
		try {
			URL url = new URL(particleCloak);
			URLConnection con = url.openConnection();
			con.setConnectTimeout(10000);
			con.setReadTimeout(10000);
			InputStream io = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(io));

			int LineTracker = 1;
			String str;
			while ((str = br.readLine()) != null) {
				LogHelper.trace("[ParticleDebug] " + str);
				if (!str.startsWith("--")) {
					if (str.contains(":")) {
						String nick = str.substring(0, str.indexOf(":"));
						String types = str.substring(str.indexOf(":") + 1);
						LogHelper.trace("[ParticleDebug] Parsed line " + LineTracker + " as: " + nick + ", " + types);
						String[] ints = types.split(",");
						ArrayList<Vec3F> colors = new ArrayList<Vec3F>();
						for (int i = 0; i < ints.length; i++) {
							colors.add(Vec3F.createVec3F(EntitySheep.fleeceColorTable[Integer.parseInt(ints[i])]));
						}
						PowerConverterCore.addParticleCloak(nick, colors);
					} else {
						LogHelper.error("Syntax error on line " + LineTracker + ":" + str);
					}
				}
				LineTracker++;
			}
			br.close();
		} catch (Exception e) {
			LogHelper.fatal("Capes.info File was unable to be accessed!");
		}
	}

	public void capePlayer(AbstractClientPlayer player, CapeType cape) {
		if (cape != null && cape != CapeType.UNKNOWN) {
			player.func_152121_a(MinecraftProfileTexture.Type.CAPE, cape.getCapeLocation());
		}
	}

	public void reBuild() {
		LogHelper.trace("Refreshing Cpaes.");
		reBuilding = true;
		PowerConverterCore.clearCloaks();
		buildCloakURLDatabase();
		reBuilding = false;
		LogHelper.trace("Refresh Capes Finished.");
	}

	public void reBuildParticles() {
		LogHelper.trace("Refreshing Particle Cloak.");
		reBuilding = true;
		PowerConverterCore.clearParticleCloaks();
		buildParticleDatabase();
		reBuilding = false;
		LogHelper.trace("Refresh Particle Cloak.");
	}

	public void renderParticles(AbstractClientPlayer player, CapeType cape) {
		if (PowerConverterCore.getParticlehashMap().containsKey(player.getCommandSenderName())) {
			ArrayList<Vec3F> colors = PowerConverterCore.getPrarticleCloak(player.getCommandSenderName());
			if (cape.doParticles()) {
				for (int i = 0; i < colors.size(); i++) {
					Vec3F vec3f = colors.get(i);
					if (random.nextDouble() < 0.75F) {
						PowerConverterCore.proxy.sparkleFX(player.worldObj, player.posX + 0.3 + random.nextFloat() * 0.5, player.posY + 0.3 + random.nextFloat() * 0.5, player.posZ + 0.3 + random.nextFloat() * 0.5, vec3f.xCoord, vec3f.yCoord, vec3f.zCoord, random.nextFloat(), 5);
					}
				}
			}
		}
	}
}
