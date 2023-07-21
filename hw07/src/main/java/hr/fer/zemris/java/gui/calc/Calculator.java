package hr.fer.zemris.java.gui.calc;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.impl.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.operators.Operators;
import hr.fer.zemris.java.gui.layouts.CalcLayout;

/**
 * Model of a primitive calculator.
 * @author Fani
 *
 */
public class Calculator extends JFrame {
	
	private static final long serialVersionUID = -5148492242213114522L;
	
	/**
	 * The model of the calculator. 
	 */
	private CalcModel model;
	
	/**
	 * Internal stack for pushing and poping calculator values.
	 */
	private Stack<Double> stack;
	
	/**
	 * Creates a calculator with starting configuration.
	 */
	public Calculator() {
		setLocation(100, 100);
		setTitle("My Calculator v1.0");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		model = new CalcModelImpl();
		stack = new Stack<>();
		
		initGUI();
	}
	
	/**
	 * Initializes the graphic interface.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(5));
		
		//LABEL
		JLabel display = CalcFactory.display();
			model.addCalcValueListener((model) -> {
			display.setText(model.toString());
		});
		
		//CHECKBOX
		JCheckBox inv = new JCheckBox("Inv");
		
		//List of buttons which are inversible - the function and displayed 
		//text are changed once the checkbox is ticked
		List<InversibleButton> inversables = new LinkedList<>();
		
		// DIGIT BUTTONS
		DigitButton[] btns = new DigitButton[10];
		for (int i = 0; i < btns.length; i++) {
			btns[i] = CalcFactory.digitButton(i, new DigitAction(i));
		}
		
		//OPERATORS
		
		//unary
		JButton mulinv = CalcFactory.regularButton("1/x", new UnaryOperatorAction(Operators.INVERSE, null, inv));

		InversibleButton log = CalcFactory.inversableButton("log", "10^x", new UnaryOperatorAction(Operators.LOG, Operators.TENXP, inv));
		InversibleButton ln = CalcFactory.inversableButton("ln", "e^x", new UnaryOperatorAction(Operators.LN, Operators.EXP, inv));
		InversibleButton sin = CalcFactory.inversableButton("sin", "arcsin", new UnaryOperatorAction(Operators.SIN, Operators.ASIN, inv));
		InversibleButton cos = CalcFactory.inversableButton("cos", "arccos", new UnaryOperatorAction(Operators.COS, Operators.ACOS, inv));
		InversibleButton tan = CalcFactory.inversableButton("tan", "arctan", new UnaryOperatorAction(Operators.TAN, Operators.ATAN, inv));
		InversibleButton ctg = CalcFactory.inversableButton("ctg", "arcctg", new UnaryOperatorAction(Operators.CTG, Operators.ACTG, inv));
		
		inversables.add(log);
		inversables.add(ln);
		inversables.add(sin);
		inversables.add(cos);
		inversables.add(tan);
		inversables.add(ctg);
	
		inv.addActionListener((e) ->{
			for (InversibleButton i : inversables) {
				i.inverse();
			}
		});
		
		//binary
		JButton plus = CalcFactory.regularButton("+", new BinaryOperatorAction(Operators.PLUS, null, inv));
		JButton minus = CalcFactory.regularButton("-", new BinaryOperatorAction(Operators.MINUS, null, inv));
		JButton mul = CalcFactory.regularButton("*", new BinaryOperatorAction(Operators.MUL, null, inv));
		JButton div = CalcFactory.regularButton("/", new BinaryOperatorAction(Operators.DIV, null, inv));
		JButton powOfN = CalcFactory.regularButton("x^n", new BinaryOperatorAction(Operators.X_POW_N, Operators.NTH_ROOT_OF_X, inv));
		
		//CONTROLS
		JButton negate = CalcFactory.regularButton("+/-", (e) -> {
			model.swapSign();
			display.setText(model.toString());
		});
		
		JButton clr = CalcFactory.regularButton("clr", (e) -> {
			model.clear();
			display.setText(model.toString());
		});
		
		JButton reset = CalcFactory.regularButton("reset", (e) -> {
			model.clearAll();
			display.setText(model.toString());
		});
		
		JButton decimal = CalcFactory.regularButton(".", (e) -> {
			model.insertDecimalPoint();
			display.setText(model.toString() + ".");
		});
		
		JButton push = CalcFactory.regularButton("push", (e) -> stack.push(model.getValue()));
		
		JButton pop = CalcFactory.regularButton("pop",(e) -> {
			if(stack.isEmpty()) {
				System.out.println("No elements on stack!");
			} else {
				model.setValue(stack.pop());
				display.setText(model.toString());
			}
		});
		
		JButton calculate = CalcFactory.regularButton("=", (e) -> {
			if(model.isActiveOperandSet()) {
				double op2 = model.getValue();
				double op1 = model.getActiveOperand();
				model.setValue(model.getPendingBinaryOperation().applyAsDouble(op1, op2));
				System.out.println(model.getPendingBinaryOperation().applyAsDouble(op1, op2));
				//display.setText(model.toString());
				System.out.println(model.getActiveOperand());
			}
			
			model.freezeValue(model.toString());
			model.clearAll();
		});
		
		
		cp.add(display, "1,1");																							  cp.add(calculate, "1,6"); cp.add(clr, "1,7");
		cp.add(mulinv, "2,1");	cp.add(sin, "2,2");	cp.add(btns[7],"2,3"); 	cp.add(btns[8],"2,4");	cp.add(btns[9],"2,5");	 cp.add(div, "2,6");	cp.add(reset, "2,7");
		cp.add(log, "3,1"); 	cp.add(cos, "3,2");	cp.add(btns[4], "3,3");	cp.add(btns[5], "3,4");	cp.add(btns[6], "3,5");	 cp.add(mul, "3,6");	cp.add(push, "3,7");
		cp.add(ln, "4,1"); 		cp.add(tan, "4,2");	cp.add(btns[1], "4,3");	cp.add(btns[2], "4,4");	cp.add(btns[3], "4,5");	 cp.add(minus, "4,6");	cp.add(pop, "4,7");
		cp.add(powOfN, "5,1");	cp.add(ctg, "5,2");	cp.add(btns[0], "5,3");	cp.add(negate, "5,4");	cp.add(decimal, "5,5");	 cp.add(plus,"5,6");	cp.add(inv, "5,7");
		
	}
	
	/**
	 * Starting source of this program; creates and starts the calculator. 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() ->{
			JFrame frame = new Calculator();
			frame.pack();
			frame.setVisible(true);
		});
	}
	
	

	/**
	 * ActionListener for actions in the calculator caused by unary operations.
	 * @author Fani
	 *
	 */
	public class UnaryOperatorAction implements ActionListener {
		/**
		 * Checkbox indicating if an inversed function should be used for calculation.
		 */
		private JCheckBox inv;
		
		/**
		 * Function executed if the ActionEvent is caused and the checkbox is not ticked.
		 */
		private DoubleUnaryOperator operator;
		
		/**
		 * Function executed if the ActionEvent is caused while the checkbox is ticked.
		 */
		private DoubleUnaryOperator inversedOperator;
		
		/**
		 * Creates a new ActionListener for actions in the calculator caused by unary operations.
		 * @param operator function executed if the ActionEvent is caused and the checkbox is not ticked
		 * @param inversedOperator function executed if the ActionEvent is caused while the checkbox is ticked
		 * @param label display label which is updated upon an ActionEvent
		 * @param inv checkbox indicating if an inversed function should be used for calculation
		 */
		public UnaryOperatorAction(DoubleUnaryOperator operator, DoubleUnaryOperator inversedOperator, JCheckBox inv) {
			this.operator = operator;
			this.inversedOperator = inversedOperator;
			this.inv = inv;
		}
		
		/**
		 * Sets the internally saved value to the newly calculated value using operator function and displays it on the display label. 
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			model.setValue((inv.isSelected() && inversedOperator != null ? inversedOperator : operator)
							.applyAsDouble(model.getValue()
						   ));
		}
	}
	
	/**
	 * ActionListener for actions in the calculator caused by binary operations.
	 * @author Fani
	 *
	 */
	public class BinaryOperatorAction implements ActionListener {
		/**
		 * Checkbox indicating if an inversed function should be used for calculation.
		 */
		private JCheckBox inv;
		
		/**
		 * Function executed if the ActionEvent is caused and the checkbox is not ticked.
		 */
		private DoubleBinaryOperator operator;
		
		/**
		 * Function executed if the ActionEvent is caused while the checkbox is ticked.
		 */
		private DoubleBinaryOperator inversedOperator;
		
		/**
		 * Creates a new ActionListener for actions in the calculator caused by binary operations.
		 * @param operator function executed if the ActionEvent is caused and the checkbox is not ticked
		 * @param inversedOperator function executed if the ActionEvent is caused while the checkbox is ticked
		 * @param label display label which is updated upon an ActionEvent
		 * @param inv checkbox indicating if an inversed function should be used for calculation
		 */
		public BinaryOperatorAction(DoubleBinaryOperator operator, DoubleBinaryOperator inversedOperator, 
				 JCheckBox inv) {
			this.operator = operator;
			this.inversedOperator = inversedOperator;
			this.inv = inv;
		}
		
		/**
		 * Sets the internally saved value to the newly calculated value using operator function and displays it on the display label. 
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if(model.isActiveOperandSet()) {
				double op2 = model.getValue();
				double op1 = model.getActiveOperand();
				model.setValue(model.getPendingBinaryOperation().applyAsDouble(op1, op2));
			}
			model.setActiveOperand(model.getValue());
			model.setPendingBinaryOperation((inv.isSelected() && inversedOperator != null ? inversedOperator : operator));
			model.freezeValue(model.toString());
		}
	}
	
	/**
	 * ActionListener for actions in the calculator caused by digit input.
	 * @author Fani
	 *
	 */
	public class DigitAction implements ActionListener {
		/**
		 * The digit representing the button which was clicked causing the ActionEvent.
		 */
		private int digit;
		
		/**
		 * Creates a new ActionListener for actions in the calculator caused by 
		 * @param i the digit representing the button which was clicked causing the ActionEvent
		 * @param label display label which is updated upon an ActionEvent
		 */
		public DigitAction(int i) {
			this.digit = i;
		}
		
		/**
		 * Inserts a new digit into currently displayed number.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			model.insertDigit(digit);
		}
	}	
}