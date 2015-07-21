package powercrystals.powerconverters.command;

import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import powercrystals.powerconverters.handler.ConfigurationHandler;

public class StopRainCommand implements ICommand {

	@Override
	public int compareTo(Object o) {
		return 0;
	}

	@Override
	public String getCommandName() {
		return "stoprain";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "Stops all rain on the server.";
	}

	@Override
	public List getCommandAliases() {
		return null;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		boolean flag1 = false;
		String[] ops = MinecraftServer.getServer().getConfigurationManager().func_152603_m().func_152685_a();
		for(String player : ops){
			if (player.toLowerCase().contains(sender.getCommandSenderName().toLowerCase())) {
				flag1 = true;
				break;
			}
			
		}
		
		if (flag1 || MinecraftServer.getServer().isSinglePlayer()) {
			sender.addChatMessage(new ChatComponentText("Yay You are a server op, we will stop all the rain for you."));
			ConfigurationHandler.stopRain = true;
		} else {
			sender.addChatMessage(new ChatComponentText("You arent a server op, we cant do anything for you today."));
		}

	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_) {
		return true;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
		return false;
	}

}
