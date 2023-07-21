package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Demonstrates the bar chart component.
 * @author Fani
 *
 */
public class BarChartDemo extends JFrame {
	
	private static final long serialVersionUID = 5151434034409506855L;
	
	/**
	 * Name of the file with chart data.
	 */
	private String fileName;
	
	/**
	 * List of read values from the file data.
	 */
	private List<XYValue> values;
	
	/**
	 * Axis descriptions from the file data.
	 */
	private String xDesc, yDesc;
	
	/**
	 * Other data read from the file.
	 */
	private int yMin, yMax, gap;
	
	/**
	 * Creates a new frame demonstrating the BarChartComponent.
	 * @param values list of read values from the file data
	 * @param xDesc x axis description from the file data
	 * @param yDesc y axis description from the file data
	 * @param yMin smallest y in the bar chart
	 * @param yMax largest y in the bar chart 
	 * @param interval
	 * @param fileName name of the file with bar chart data
	 */
	public BarChartDemo(List<XYValue> values, String xDesc, String yDesc, int yMin, int yMax, int interval, String fileName) {
		this.fileName = fileName;
		this.values = values;
		this.xDesc = xDesc;
		this.yDesc = yDesc;
		this.yMin = yMin;
		this.yMax = yMax;
		
		setSize(550, 550);
		setLocation(100, 100);
		setTitle("BarChart");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
	}
	
	/**
	 * Initializes the graphic interface.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		JLabel file = new JLabel(fileName);
		
		BarChartComponent chart = new BarChartComponent(new BarChart(values, xDesc, yDesc, yMin, yMax, gap));
		
		cp.add(file, BorderLayout.PAGE_START);
		cp.add(chart, BorderLayout.CENTER);
	}

	
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Improper file name");
			System.exit(1);
		}
		
		
		//READING FILE
		File file = Paths.get(args[0]).toFile();
		List<XYValue> values = new ArrayList<>();
		String yDesc = "";
		String xDesc = "";
		
		int yMin = 0;
		int yMax = 0; 
		int gap = 0;
		
		try(Scanner sc = new Scanner(file)) {
			xDesc = sc.nextLine().trim();
			yDesc = sc.nextLine().trim();
			
			String[] stringValues = sc.nextLine().trim().split("\\s+");
			for (int i = 0; i < stringValues.length; i++) {
				String[] split = stringValues[i].split(",");
				values.add(new XYValue( Integer.parseInt(split[0]), Integer.parseInt(split[1]) ));
			}
			
			yMin = Integer.parseInt(sc.nextLine());
			yMax = Integer.parseInt(sc.nextLine());
			gap = Integer.parseInt(sc.nextLine());
			
		} catch (Exception e) {
			System.out.println("Error while parsing the file.");
			System.exit(1);
		}
		
		
		
		
		String x = xDesc;
		String y = yDesc;
		int ymin = yMin;
		int ymax = yMax;
		int inter = gap;
		
		SwingUtilities.invokeLater(() ->{
			JFrame frame = new BarChartDemo(values, x, y, ymin, ymax, inter, file.toString());
			//frame.pack();
			frame.setVisible(true);
		});

	}
}
