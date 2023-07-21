package hr.fer.zemris.java.gui.calc.model.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Implementation of a calculator model
 * @author Fani
 *
 */
public class CalcModelImpl implements CalcModel {
	/**
	 * Tells if the calculator is in the editable state.
	 */
	private boolean editable;
	
	/**
	 * Tells if the current value in the calculator is positive.
	 */
	private boolean positive;
	
	/**
	 * Represents the input digits of the user.
	 */
	private String inputDigits;
	
	
	/**
	 * The value currently stored in the calculator.
	 */
	private double value;
	
	/**
	 * String representation of the value currently stored in the calculator.
	 */
	private String valueAsString;
	
	
	/**
	 * Tells if an active operand has been set.
	 */
	private boolean operandSet;
	
	/**
	 * The value of the active operand.
	 */
	private double activeOperand;
	
	/**
	 * Tells if a value has been frozen.
	 */
	private boolean frozen;
	
	/**
	 * The value which has been frozen.
	 */
	public String frozenValue;
	
	/**
	 * Represents the currently scheduled binary operation.
	 */
	private DoubleBinaryOperator operator;
	
	/**
	 * Represents the listeners of this model.
	 */
	List<CalcValueListener> listeners;
	
	/**
	 * Creates a new calculator model.
	 */
	public CalcModelImpl() {
		clearAll();
		this.listeners = new ArrayList<>();
	}
	
	/**
	* Registration of observers to be notified when a
	* value stored in the calculator is changed.
	*
	* @param l observer; must not be <code> null </code>
	* @throws NullPointerException if passed <code> l </code> is <code> null </code>
	*/
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(l);	
	}
	
	/**
	* Unsubscribes observers from the list of observers notified when the value
	* stored in the calculator changes.
	*
	* @param l observer; must not be <code> null </code>
	*
	* @throws NullPointerException if passed <code> l </code> is <code> null </code>
	*/
	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(l);
	}

	@Override
	public double getValue() {
		double val = (positive ? value : -value);
		return val;
	}
	
	/**
	* Writes a decimal value in the calculator. The value may
	* be also infinite or NaN. After entering the calculator
	* becomes uneditable.
	*
	* @param value value to enter
	*/
	@Override
	public void setValue(double value) {
		this.value = Math.abs(value);
		this.positive = value >= 0;
		this.valueAsString = Double.valueOf(Math.abs(value)).toString();
		this.editable = false;
		
		for (CalcValueListener l : listeners) {
			l.valueChanged(this);
		}
	}
	
	/**
	* Returns information about whether the calculator is editable (in other words,
	* whether the user can call methods {@link #swapSign ()},
	* {@link #insertDecimalPoint ()} and {@link #insertDigit (int)}).
	* @return <code> true </code> if the model is editable, <code> false </code> otherwise.
	*/
	@Override
	public boolean isEditable() {
		return editable;
	}
	
	/**
	* Resets the current value to the unentered one and returns the calculator to
	* editable condition.
	*/
	@Override
	public void clear() {
		editable = true;
		value = 0;
		valueAsString = "";
		inputDigits = "";
		positive = true;
		
		if(listeners != null) {
			for (CalcValueListener l : listeners) {
				l.valueChanged(this);
			}
		}
	}
	
	/**
	* Performs everything that {@link #clear ()} does, and additionally removes the active
	* operand and scheduled operation.
	*/
	@Override
	public void clearAll() {
		clear();
		this.operandSet = false;
		this.operator = null;
		
		this.frozen = false;
		this.frozenValue = "";
		
		/*if(listeners != null) {
			for (CalcValueListener l : listeners) {
				l.valueChanged(this);
			}
		}*/
	}
	
	/**
	* Changes the sign of the entered number.
	*
	* @throws CalculatorInputException if the calculator is not editable
	*/
	@Override
	public void swapSign() throws CalculatorInputException {
		if(!editable) {
			throw new CalculatorInputException();
		}
		positive = !positive;
		
		for (CalcValueListener l : listeners) {
			l.valueChanged(this);
		}
	}
	
	/**
	* Adds a decimal point to the end of the current number.
	*
	* @throws CalculatorInputException if no digit of the number has been entered yet,
	* if the number already contains a decimal point or if the calculator is not editable
	*/
	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(!editable || valueAsString.isEmpty() || valueAsString.contains(".")) {
			throw new CalculatorInputException();
		}
		inputDigits += ".";
	}
	
	/**
	* Add the sent digit to the number currently being entered at the end.
	* If the current number is "0", adding another zero is silently
	* ignored.
	*
	* @param digit digit to add
	* @throws CalculatorInputException if adding a digit would make the number too large for the final display in the {@link Double} type, or if the calculator is not editable.
	* @throws IllegalArgumentException if <code> digit & lt; 0 </code> or <code> digit & gt; 9 </code>
	*/
	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(!editable) {
			throw new CalculatorInputException();
		} else if(digit < 0 || digit > 9) {
			throw new IllegalArgumentException();
		}
		
		String parse = inputDigits + Integer.valueOf(digit).toString();
		
		double newValue;
		try {
			newValue = Double.parseDouble(parse);
			
		} catch(Exception e) {
			throw new CalculatorInputException();
		}
		
		if(Double.valueOf(newValue).equals(Double.POSITIVE_INFINITY) || 
		   Double.valueOf(newValue).equals(Double.NEGATIVE_INFINITY)) {
			throw new CalculatorInputException("The number has become too large!");
		}
		
		inputDigits = parse;
		value = newValue;
		
		if(!parse.contains(".")) {
			valueAsString = Integer.valueOf((int) Math.abs(newValue)).toString();
		} else {
			valueAsString = Double.valueOf(Math.abs(newValue)).toString();
		}
		
		frozen = false;
		if(newValue < 0) positive = false;
		
		for (CalcValueListener l : listeners) {
			l.valueChanged(this);
		}
	}
	
	/**
	* Check that the active operand is entered.
	*
	* @return <code> true </code> if the active operand is entered, <code> false </code> otherwise.
	*/
	@Override
	public boolean isActiveOperandSet() {
		return operandSet;
	}
	
	/**
	* Retrieves the active operand.
	*
	* @return the active operand.
	*
	* @throws IllegalStateException if the active operand is not set
	*/
	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(!operandSet) {
			throw new IllegalStateException();
		}
		return activeOperand;
	}
	
	/**
	* The method sets the active operand to the given value.
	* If the calculator already has an active operand set, submitted
	* value overwrites it.
	*
	* @param activeOperand value to be stored as active operand
	*/
	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		this.operandSet = true;
	}
	
	/**
	* Removes the storred active operand.
	*/
	@Override
	public void clearActiveOperand() {
		operandSet = false;
	}
	
	/**
	* Retrieves the scheduled operation.
	*
	* @return scheduled operation, or <code> null </code> if no operation is scheduled.
	*/
	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return operator;
	}
	
	/**
	* Sets up the scheduled operation. If operatuon is already scheduled, this call ovewrites it.
	*
	* @param op scheduled operation to be set; can be <code> null </code>
	*/
	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.operator = op;
	}
	
	/**
	* Returns the text to be displayed on the calculator screen.
	* 
	* @return text to display on the calculator screen.
	*/
	@Override
	public String toString() {
		if(hasFrozenValue()) {
			return Double.valueOf(frozenValue).toString();
		}
		if(valueAsString.isEmpty()) {
			return (positive ? "" : "-") + "0";
		} 
		
		return (positive ? "" : "-") + valueAsString;  
	}

	@Override
	public void freezeValue(String value) {
		frozen = true;
		this.frozenValue = value;
		clear();
	}

	@Override
	public boolean hasFrozenValue() {
		return frozen;
	}
	
}