package hr.fer.zemris.java.gui.calc;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcValueListener;

/**
 * Button used for calculator's buttons which have an inverse function as well s the main one.
 * @author Fani
 *
 */
public class InversibleButton extends JButton {
	
	
	private static final long serialVersionUID = 4112020502407653513L;
	
	List<CalcValueListener> listeners = new LinkedList<>();
	/**
	 * The text displayed when the button is inversed (happens when the checkbox is ticked in the calculator).
	 */
	private String inversedText;
	
	/**
	 * The default text displayed.
	 */
	private String text;
	
	/**
	 * Creates a new InversibleButton with given default and invert text.
	 * @param text the text displayed when the button is inversed (happens when the checkbox is ticked in the calculator).
	 * @param inversedText the default text displayed (when the checkbox is not ticked).
	 */
	public InversibleButton(String text, String inversedText) {
		super(text);
		this.text = text;
		this.inversedText = inversedText;
	}
	
	/**
	 * Inverts the button's display text.
	 */
	public void inverse() {
		if(this.getText().equals(text)) {
			this.setText(inversedText);
		} else {
			this.setText(text);
		}
	}
	
}
