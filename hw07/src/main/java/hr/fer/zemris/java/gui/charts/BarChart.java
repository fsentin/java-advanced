package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Model of a bar chart.
 * @author Fani
 *
 */
public class BarChart {
	private List<XYValue> values;
	private String xDesc;
	private String yDesc;
	private int yMin;
	private int yMax;
	private int gap;
	
	/**
	 * Creates a new bar chart model
	 * @param values
	 * @param xDesc
	 * @param yDesc
	 * @param yMin
	 * @param yMax
	 * @param gap
	 */
	public BarChart(List<XYValue> values, String xDesc, String yDesc, int yMin, int yMax, int gap) {
		
		if(yMin < 0) 
			throw new IllegalArgumentException();
		if(!(yMin < yMax))
			throw new IllegalArgumentException();
		for (XYValue value : values) {
			if(value.getY() < yMin) {
				throw new IllegalArgumentException();
			}
		}
		
		this.values = values;
		this.xDesc = xDesc;
		this.yDesc = yDesc;
		this.yMin = yMin;
		this.yMax = yMax;
		this.gap = gap;
	}


	public List<XYValue> getValues() {
		return values;
	}


	public String getxDesc() {
		return xDesc;
	}


	public String getyDesc() {
		return yDesc;
	}


	public int getyMin() {
		return yMin;
	}


	public int getyMax() {
		return yMax;
	}


	public int getGap() {
		return gap;
	}


	@Override
	public String toString() {
		return "BarChart [values=" + values + ", xDesc=" + xDesc + ", yDesc=" + yDesc + ", yMin=" + yMin + ", yMax="
				+ yMax + ", gap=" + gap + "]";
	}
	
}
