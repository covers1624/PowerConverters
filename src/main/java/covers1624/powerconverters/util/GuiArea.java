package covers1624.powerconverters.util;

public class GuiArea {

	public int xTop;
	public int yTop;

	public int xBottom;
	public int yBottom;

	public GuiArea(int xTop, int yTop, int xBottom, int yBottom) {
		this.xTop = xTop;
		this.yTop = yTop;

		this.xBottom = xBottom;
		this.yBottom = yBottom;
	}

	public void scaleValues(int xTopScale, int yTopScale, int xBottomScale, int yBottomScale, boolean type) {
		if (type) {
			xTop += xTopScale;
			yTop += yTopScale;
			xBottom += xBottomScale;
			yBottom += yBottomScale;
			return;
		}
		xTop -= xTopScale;
		yTop -= yTopScale;
		xBottom -= xBottomScale;
		yBottom -= yBottomScale;
	}

	public boolean isMouseInArea(int mouseX, int mouseY) {
		if (xTop <= mouseX && xBottom >= mouseX) {
			if (yTop <= mouseY && yBottom >= mouseY) {
				return true;
			}
		}

		return false;
	}

	public boolean isMouseInArea(int mouseX, int mouseY, int xScale, int yScale) {
		scaleValues(xScale, yScale, xScale, yScale, true);
		boolean awnser = isMouseInArea(mouseX, mouseY);
		scaleValues(xScale, yScale, xScale, yScale, false);
		return awnser;
	}
}
