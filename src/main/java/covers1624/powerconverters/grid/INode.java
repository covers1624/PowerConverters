package covers1624.powerconverters.grid;

public interface INode {
	public boolean isNotValid();

	public void firstTick(IGridController grid);

	public void updateInternalTypes(IGridController grid);
}
