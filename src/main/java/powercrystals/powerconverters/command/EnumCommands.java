package powercrystals.powerconverters.command;

import net.minecraft.command.ICommand;

public enum EnumCommands {
	
	STOPRAIN(new StopRainCommand()),
	ENABLERAIN(new EnableRainCommand());

	
	private ICommand command;
	
	private EnumCommands(ICommand command) {
		this.command = command;
	}
	
	public ICommand getCommand(){
		return this.command;
	}
	
}
