package powercrystals.powerconverters.temp;

import net.minecraftforge.common.util.ForgeDirection;

public interface IRotateableTile {
	public boolean canRotate();

	public void rotate();

	public ForgeDirection getDirectionFacing();
}
