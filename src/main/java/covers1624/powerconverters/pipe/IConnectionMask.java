package covers1624.powerconverters.pipe;

/**
 * Created by covers1624 on 11/9/2015.
 */
public interface IConnectionMask {

	int getConnectionMaskSize();

	ConnectionMask getConnectionMaskAt(int i);

	ConnectionMask[] getConnectonMaksArray();

	boolean setConnectionMaskAt(int i, ConnectionMask mask);

	boolean setConnectionMaskArray(ConnectionMask[] connectionMasks);
}
