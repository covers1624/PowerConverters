package covers1624.powerconverters.client.util;

import java.util.Formatter;
import java.util.Locale;

public class Vector3 {
	public double x;
	public double y;
	public double z;

	public Vector3() {
	}

	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3(Vector3 var1) {
		this.x = var1.x;
		this.y = var1.y;
		this.z = var1.z;
	}

	public Vector3 copy() {
		return new Vector3(this);
	}

	public void set(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void set(Vector3 vector3) {
		this.x = vector3.x;
		this.y = vector3.y;
		this.z = vector3.z;
	}

	public double dotProduct(Vector3 vector3) {
		return vector3.x * this.x + vector3.y * this.y + vector3.z * this.z;
	}

	public double dotProduct(double x, double y, double z) {
		return x * this.x + y * this.y + z * this.z;
	}

	public void crossProduct(Vector3 vector3) {
		double var2 = this.y * vector3.z - this.z * vector3.y;
		double var4 = this.z * vector3.x - this.x * vector3.z;
		double var6 = this.x * vector3.y - this.y * vector3.x;
		this.x = var2;
		this.y = var4;
		this.z = var6;
	}

	public void add(double var1, double var3, double var5) {
		this.x += var1;
		this.y += var3;
		this.z += var5;
	}

	public void add(Vector3 var1) {
		this.x += var1.x;
		this.y += var1.y;
		this.z += var1.z;
	}

	public void subtract(Vector3 var1) {
		this.x -= var1.x;
		this.y -= var1.y;
		this.z -= var1.z;
	}

	public void multiply(double var1) {
		this.x *= var1;
		this.y *= var1;
		this.z *= var1;
	}

	public double mag() {
		return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
	}

	public double magSquared() {
		return this.x * this.x + this.y * this.y + this.z * this.z;
	}

	public void normalize() {
		double var1 = this.mag();

		if (var1 != 0.0D) {
			this.multiply(1.0D / var1);
		}
	}

	@SuppressWarnings("resource")
	@Override
	public String toString() {
		StringBuilder var1 = new StringBuilder();
		Formatter var2 = new Formatter(var1, Locale.US);
		var2.format("Vector:\n");
		var2.format("  < %f %f %f >\n", this.x, this.y, this.z);
		return var1.toString();
	}
}
