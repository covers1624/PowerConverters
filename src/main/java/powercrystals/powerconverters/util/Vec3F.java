package powercrystals.powerconverters.util;

public class Vec3F {
	public float xCoord;

	public float yCoord;

	public float zCoord;

	public static Vec3F createVec3F(float x, float y, float z) {
		return new Vec3F(x, y, z);
	}

	public static Vec3F createVec3F(float[] floats) {
		return new Vec3F(floats[0], floats[1], floats[2]);
	}

	protected Vec3F(float x, float y, float z) {
		this.xCoord = x;
		this.yCoord = y;
		this.zCoord = z;
	}
}
