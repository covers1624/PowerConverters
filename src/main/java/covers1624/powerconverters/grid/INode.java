package covers1624.powerconverters.grid;

public interface INode {
	public boolean isNotValid();

	public void firstTick(GridTickHandler grid);

	public void updateInternalTypes(GridTickHandler grid);
}
