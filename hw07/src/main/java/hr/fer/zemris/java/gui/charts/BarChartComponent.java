package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

/**
 * Draws a bar chart according to given data.
 * @author Fani
 *
 */
public class BarChartComponent extends JComponent {
	
	private static final long serialVersionUID = -3092155870400966674L;
	
	/**
	 * Data oof the bar chart.
	 */
	private BarChart chart;
	
	/**
	 * Maximum of x value of the bar chart.
	 */
	private int maxX;
	
	/**
	 * Constant used for gaps in drawing.
	 */
	private static int BORDER_GAP = 70;
	
	/**
	 * Creates a new BarChartComponent with given data.
	 * @param chart given chart data
	 */
	public BarChartComponent(BarChart chart) {
		this.chart = chart;
		this.maxX = calculateMaxX();	
		BORDER_GAP += Math.max(getInsets().left, getInsets().top);
	}
	
	/**
	 * Paints the chart.
	 */
	@Override
	public void paintComponent(Graphics g) {
		
		//ISHODISTE
		int x0 = BORDER_GAP; 
		int y0 = getHeight() - BORDER_GAP; 
		
		int xEnd = getWidth() - BORDER_GAP;
		int yEnd = BORDER_GAP;
		
		//g.drawLine(x0, y0, 
		//	         x0, yEnd); // y axis
	    //g.drawLine(x0, y0,
	    //	         xEnd, y0); //x axis
	    
	    int xAxisLen = getWidth() - 2 * BORDER_GAP; 
	    int yAxisLen = getHeight() - 2 * BORDER_GAP;
	    
	    int xComponentLen = xAxisLen / maxX;
	    int yComponentLen = yAxisLen / chart.getyMax();
	    
	    //net and numbers for x axis
	    Integer count = 1;
	    for (int i = x0 + xComponentLen / 2; i < xEnd; i += xComponentLen) {
			int stringWidth = g.getFontMetrics().stringWidth(count.toString());
			int middle = stringWidth / 2;
			g.drawString(count.toString(), i - middle, y0 + 15);
			count++;
		}
	    for (int i = x0; i <= xEnd; i += xComponentLen) {
	    	g.setColor(Color.GRAY);
			g.drawLine(i, y0, i, yEnd);
		}
	    
	    //net and numbers for y axis
	    count = 0;
	    for (int j = y0; j >= yEnd; j -= yComponentLen) {
	    	g.setColor(Color.GRAY);
			g.drawLine(x0, j, xEnd, j);
			int stringWidth = g.getFontMetrics().stringWidth(count.toString());
			int middle = stringWidth / 2;
			g.setColor(Color.BLACK);
			g.drawString(count.toString(), x0 - stringWidth - 10, j + middle);
			count++;
		}
	    
	    g.setColor(Color.GRAY);
	    g.fillPolygon(new int[]{x0, x0 - yComponentLen/4, x0 + yComponentLen/4}, new int[]{yEnd - yComponentLen/2, yEnd, yEnd}, 3);
	    g.fillPolygon(new int[] {xEnd + yComponentLen/2, xEnd + 1, xEnd + 1}, new int[] {y0, y0 + yComponentLen/4, y0 - yComponentLen/4}, 3);
	    
	    //draw bars
	    for(var val : chart.getValues()) {
	    	int x = val.getX();
	    	int y = val.getY();
	    	g.setColor(new Color(240, 120, 81));
	    	g.fillRect(x0 + xComponentLen*(x - 1), y0- yComponentLen*(y), xComponentLen, yComponentLen * y);
	    	g.setColor(Color.WHITE);
	    	g.draw3DRect(x0 + xComponentLen*(x - 1), y0- yComponentLen*(y), xComponentLen, yComponentLen * y, true);
	    }
	    
	    //x axis description
	    g.setColor(Color.BLACK);
	    g.drawString(chart.getxDesc(),  x0 + xAxisLen/2, y0 + BORDER_GAP/2);
	    
	    //y axis description
	    Graphics2D g2d = (Graphics2D) g;
	    AffineTransform at = AffineTransform.getQuadrantRotateInstance(3);
	    g2d.setTransform(at);
	    g2d.drawString(chart.getyDesc(),  x0 - xAxisLen, BORDER_GAP/2);
	    
	}
	/**
	 * Calculates the max x value for the given chart data.
	 * @return the max x value for the given chart data.
	 */
	public int calculateMaxX() {
		int max = 0;
		for(var val : chart.getValues()) {
			if(val.getX() > max)
				max = val.getX(); 
		}
		return max;
	}
}
