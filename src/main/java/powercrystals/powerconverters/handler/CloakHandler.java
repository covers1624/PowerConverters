package powercrystals.powerconverters.handler;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent.Specials.Pre;
import powercrystals.powerconverters.util.CapeType;
import powercrystals.powerconverters.util.LogHelper;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class CloakHandler {
	private boolean reBuilding = false;

	private final String server = "https://raw.githubusercontent.com/covers1624/PowerConverters/master/capes.info";

	private HashMap<String, CapeType> cloaks = new HashMap<String, CapeType>();
	private ArrayList<AbstractClientPlayer> capePlayers = new ArrayList<AbstractClientPlayer>();

	public CloakHandler() {
		buildCloakURLDatabase();
	}

	@SubscribeEvent
	public void onPreRenderSpecials(Pre event) {
		if (Loader.isModLoaded("shadersmod") || Loader.isModLoaded("optifine") || reBuilding) {
			return;
		}
		if (event.entityPlayer instanceof AbstractClientPlayer) {
			AbstractClientPlayer abstractClientPlayer = (AbstractClientPlayer) event.entityPlayer;
			capePlayer(abstractClientPlayer, cloaks.get(abstractClientPlayer.getGameProfile().getName()));
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
				if (!str.startsWith("--")) {
					if (str.contains(":")) {
						String nick = str.substring(0, str.indexOf(":"));
						String type = str.substring(str.indexOf(":") + 1);
						this.cloaks.put(nick, CapeType.parse(type));
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
		player.func_152121_a(MinecraftProfileTexture.Type.CAPE, cape.getCapeLocation());
	}

	public void reBuild() {
		cloaks.clear();
		buildCloakURLDatabase();
	}
}
