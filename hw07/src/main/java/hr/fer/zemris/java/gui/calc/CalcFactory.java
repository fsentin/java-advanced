package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Factory class which creates new Swing components for Calculator set up. Decreases the amount of code in the Calculator class.
 * @author Fani
 *
 */
public class CalcFactory {
	
	/**
	 * Creates a main label which displays input and calculations.
	 * @return JLabel label for the calculator display.
	 */
	public static final JLabel display() {
		JLabel display = new JLabel("0");
		
		display.setBackground(Color.YELLOW);
		display.setOpaque(true);
		display.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		display.setFont(display.getFont().deriveFont(30f));
		display.setHorizontalAlignment(JTextField.RIGHT);
		
		return display;
	}
	
	/**
	 * Creates a new inversable button.
	 * @param text the given default text which is displayed on the button
	 * @param inversedText the given text which is diplayed when the inverse functions are enabled in the calculator
	 * @param l ActionListener which describes behaviour that happens once the button has been clicked
	 * @return a new instance of InversableButton.
	 */
	public static final InversibleButton inversableButton(String text, String inversedText, ActionListener l) {
		InversibleButton button = new InversibleButton(text, inversedText);
		button.addActionListener(l);
		return button;
	}
	
	/**
	 * Creates a new digit button.
	 * @param i the digit which the digit button represents and displays
	 * @param l ActionListener which describes behaviour that happens once the button has been clicked
	 * @return a new instance of DigitButton.
	 */
	public static final DigitButton digitButton(int i, ActionListener l) {
		DigitButton button = new DigitButton(i);
		button.setFont(button.getFont().deriveFont(30f));
		button.addActionListener(l);
		return button;
	}
	
	/**
	 * Creates a new regular button. 
	 * @param text the given default text which is displayed on the button
	 * @param l ActionListener which describes behaviour that happens once the button has been clicked
	 * @return  a new instance of JButton.
	 */
	public static final JButton regularButton(String text, ActionListener l) {
		JButton button = new JButton(text);
		button.addActionListener(l);
		return button;
	}
	
}


