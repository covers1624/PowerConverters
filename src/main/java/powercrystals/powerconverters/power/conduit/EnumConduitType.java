package powercrystals.powerconverters.power.conduit;

public enum EnumConduitType {
	
	BASIC(0),
	ADVANCED(1),
	ENDER(2);
	
	private int type;
	
	private EnumConduitType(int type) {
		this.type = type;
	}
	
	public int getType(){
		return this.type;
	}

}
