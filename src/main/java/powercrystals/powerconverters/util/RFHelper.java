package powercrystals.powerconverters.util;

public class RFHelper {

	public static boolean iEnergyHandler = false;
	
	
	public static void init(){
		
		try {
			
			Class.forName("cofh.api.energy.IEnergyHandler");
			iEnergyHandler = true;
			LogHelper.trace("IEnergyHandler Exists!");
		} catch (ClassNotFoundException e) {
			LogHelper.trace("Failed To Find IEnergy Handler, Not Enableing RF Support.");
		}
		
		
	}
	
	public static boolean getiEnergyHandlerResults(){
		return iEnergyHandler;
	}
	
	
	
	
	
}
