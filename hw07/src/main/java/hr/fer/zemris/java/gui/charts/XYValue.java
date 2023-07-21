package hr.fer.zemris.java.gui.charts;
/**
 * Represents a coordinate system point.
 * @author Fani
 *
 */
public class XYValue {
	private final int x;
	private final int y;
	
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "XYValue [x=" + x + ", y=" + y + "]";
	}
	
}
