package hr.fer.zemris.java.gui.calc;

import javax.swing.JButton;

/**
 * Button used for representing digits on a calculator.
 * @author Fani
 *
 */
public class DigitButton extends JButton {

	private static final long serialVersionUID = 403727163152396995L;
	
	/**
	 * Creates a new digit button from given int digit.
	 * @param i the digit which the digit button represents and displays 
	 */
	public DigitButton(int digit) {
		super(Integer.valueOf(digit).toString());
	}
	
}
