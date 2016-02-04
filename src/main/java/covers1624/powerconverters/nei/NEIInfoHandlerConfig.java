package covers1624.powerconverters.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import covers1624.powerconverters.util.LogHelper;

public class NEIInfoHandlerConfig implements IConfigureNEI {

	@Override
	public void loadConfig() {
		LogHelper.info("NEI Has called us to init.");
		// API.registerRecipeHandler(new InfoHandler());
		API.registerUsageHandler(new InfoHandler());
	}

	@Override
	public String getName() {
		return "PowerConverters: Nei Integration";
	}

	@Override
	public String getVersion() {
		return "1";
	}
}
