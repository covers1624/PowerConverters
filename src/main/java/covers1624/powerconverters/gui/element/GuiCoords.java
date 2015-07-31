package covers1624.powerconverters.gui.element;

public class GuiCoords {

	private int x;
	private int y;

	public GuiCoords(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean compare(GuiCoords to) {
		if (to.getX() == x && to.getY() == y) {
			return true;
		}
		return false;
	}

	public static boolean compare(GuiCoords to, GuiCoords from) {
		if (to.getX() == from.getX() && to.getY() == from.getX()) {
			return true;
		}
		return false;
	}
}
