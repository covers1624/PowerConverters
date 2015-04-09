package powercrystals.powerconverters.handler;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;

import powercrystals.powerconverters.reference.Reference;
import powercrystals.powerconverters.util.LogHelper;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent.Specials.Pre;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Type;

public class CloakHandler {
	
	public void init() {
		buildCloakURLDatabase();
	}
	
	private final String server = "https://raw.githubusercontent.com/covers1624/Covers1624Core/master/Capes/capes.txt";
	
	private static final Graphics testRender = new BufferedImage(128, 128, 1).getGraphics();
	
	private HashMap<String, String> cloaks = new HashMap<String, String>();
	private ArrayList<AbstractClientPlayer> capePlayers = new ArrayList();
	
	@SubscribeEvent
	public void onPreRenderSpecials(Pre event){
		if (Loader.isModLoaded("shadersmod") || Loader.isModLoaded("optifine")) {
			return;
		}
		
		if (event.entityPlayer instanceof AbstractClientPlayer) {
			AbstractClientPlayer abstractClientPlayer = (AbstractClientPlayer)event.entityPlayer;
			if (!capePlayers.contains(event.entityPlayer)) {
				String cloakURL = cloaks.get(event.entityPlayer.getGameProfile().getName());
				
				if (cloakURL == null) {
					return;
				}
				capePlayers.add(abstractClientPlayer);
				new Thread(new CloakThread(abstractClientPlayer, cloakURL)).start();
				event.renderCape = true;
			}
		}
		
	}
	
	
	public void buildCloakURLDatabase(){
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
						String link = str.substring(str.indexOf(":") + 1);
						new Thread(new CloakPreload(link)).start();
						this.cloaks.put(nick, link);
					} else {
						LogHelper.error("[capes.txt] Syntax error on line " + LineTracker + ":" + str);
					}
				}
				LineTracker++;
			}
			br.close();		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private class CloakPreload implements Runnable {

		String cloakURL;

		public CloakPreload(String link) {
			this.cloakURL = link;
		}

		public void run() {

			try {
				CloakHandler.testRender.drawImage(new ImageIcon(new URL(this.cloakURL)).getImage(), 0, 0, null);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

		}

	}
	
	private class CloakThread implements Runnable {

		AbstractClientPlayer player;
		String cloakURL;

		public CloakThread(AbstractClientPlayer player, String cloak) {

			this.player = player;
			cloakURL = cloak;
		}

		public void run() {
			try {
				Image cape = new ImageIcon(new URL(this.cloakURL)).getImage();
				BufferedImage bo = new BufferedImage(cape.getWidth(null), cape.getHeight(null), 2);
				bo.getGraphics().drawImage(cape, 0, 0, null);
				player.func_152121_a(MinecraftProfileTexture.Type.CAPE, new ResourceLocation(Reference.TEXTURE_FOLDER + "capes/Temp.png"));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

	}

}
