package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
/**
 * A custom implementation of the <code>LSystemBuilder</code>. Can be configured through code or text.
 * @author Fani
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {
	
	/**
	 * Length of the unit step of the turtle.
	 */
	double unitLength;
	
	/**
	 * The initial effective step length.
	 */
	double unitLengthDegreeScaler;
	
	/**
	 * The starting point of the turtle.
	 */
	Vector2D origin;
	
	/**
	 * Angle towards x axis stored in <b>radians</b>.
	 */
	double angle;
	
	/**
	 * The axiom of l system.
	 */
	String axiom;
	
	/**
	 * Stores all actions.
	 */
	Dictionary<Character, Command> commands;
	
	/**
	 * Stores all productions.
	 */
	Dictionary<Character, String> productions;
	
	/**
	 * Creates a default LSystemBuilder implementation.
	 */
	public LSystemBuilderImpl() {
		this.unitLength = 0.1;
		this.unitLengthDegreeScaler = 1;
		this.origin = new Vector2D(0.0, 0.0);
		this.angle = 0;
		this.axiom = "";
		this.commands = new Dictionary<>();
		this.productions =  new Dictionary<>();
	}
	
	/**
	 * Builds this <code>LSystem</code> from predefined specifications of unit length, unit length scaler, starting point,
	 * axiom, angle, commands and productions.
	 */
	@Override
	public LSystem build() {
		
		return new LSystem() {
			/**
			 * Draws a fractal for this LSystem for given depth using the object for drawing.
			 *
			 * @param depth an integer depth of generating the fractal
			 * @param painter the object for drawing of type <code>Painter</code>
			 */
			@Override
			public void draw(int depth, Painter painter) {
				Context ctx = new Context();
				
				TurtleState state = new TurtleState(origin, 
													new Vector2D(Math.cos(angle), Math.sin(angle)), 
													Color.BLACK, 
													unitLength * Math.pow(unitLengthDegreeScaler, depth));
				ctx.pushState(state);
				
				String finalString = generate(depth);
				
				for (int i = 0; i < finalString.length(); i++) {
					var command = commands.get(finalString.charAt(i));
					if(command != null) 
						command.execute(ctx, painter);
				}
			}
			
			/**
			 * Applies productions on the axiom for depth times.
			 * @return the string with applied productions for depth times.
			 */
			@Override
			public String generate(int depth) {
				String current = axiom;
				while(depth > 0) {
					current = applyProductions(current);
					depth--;
				}
				return current;
			}
			
		};
	}
	
	/**
	 * Applies registered productions on each symbol of the given string if there are such.
	 * @param s the given string whose characters are checked for in the registered productions
	 * @return the string on which the registered productions have been applied.
	 */
	private String applyProductions(String s) {
		String[] array = s.split("");
		String result = "";
		
		for (int i = 0; i < array.length; i++) {
			var production = productions.get(s.charAt(i));
			if(production != null)
				array[i] = production;
			result += array[i];
		}
		return result;
	}
	
	/**
	 * Configures this LSystem implementation from given array of strings containing commands.
	 * If given wrong input format, a message is displayed on the <code>System.err</code> .
	 * @param text an array of strings, each string represents one command or an empty line
	 * @return configured LSystemBuilder from specified text.
	 */
	@Override
	public LSystemBuilder configureFromText(String[] text) {
		String problematic = ""; //used for telling the user which line caused the problem
		try {
			
			for (int i = 0; i < text.length; i++) {
				if(text[i].isEmpty())
					continue;
				
				problematic = text[i]; 
				
				String[] tokens = text[i].trim().split("\\s+");
				
				
				if(text[i].contains("origin")) { 
					setOrigin(Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2]));
					
				} else if(text[i].contains("angle")) { 
					setAngle(Double.parseDouble(tokens[1]));
					
				} else if(text[i].contains("unitLengthDegreeScaler")) {
					
					//fraction x / y
					if(text[i].contains("/")) {
						tokens = text[i].split("/");
						String[] extractHeader = tokens[0].split("\\s+");
						setUnitLengthDegreeScaler(1.0 * Double.parseDouble(extractHeader[1])/Double.parseDouble(tokens[1]));
					
					//decimal scaler
					} else {
						setUnitLengthDegreeScaler(Double.parseDouble(tokens[1]));
					}
					
				} else if(text[i].contains("unitLength")) {
					setUnitLength(Double.parseDouble(tokens[1]));
					
				} else if(text[i].contains("axiom")) {
					setAxiom(tokens[1]);
					
				} else if(text[i].contains("production")) {
					registerProduction(tokens[1].charAt(0), tokens[2]);
					
				} else if(text[i].contains("command")) { //[0]command [1]F [2]draw [3]1
					if(tokens.length == 4)
						registerCommand(tokens[1].charAt(0), tokens[2] + " " + tokens[3]);
					else
						registerCommand(tokens[1].charAt(0), tokens[2]);
				}
			}
			
		} catch (Exception e) {
			System.err.println("Improper text configuration in " + problematic + "!");
			e.printStackTrace();
		}
		
		return this;
	}
	
	/**
	 * Registers a given command if it is in the correct format.
	 */
	@Override
	public LSystemBuilder registerCommand(char symbol, String command) {
		String[] splitted = command.split("\\s+"); //[0]draw [1]1
		Command com;
		
		if(command.contains("draw")) {
			com = new DrawCommand(Double.parseDouble(splitted[1]));
			
		} else if(command.contains("skip")) {
			com = new SkipCommand(Double.parseDouble(splitted[1]));
			
		} else if(command.contains("scale")) {
			com = new ScaleCommand(Double.parseDouble(splitted[1]));
			
		} else if(command.contains("rotate")) {
			com = new RotateCommand(Double.parseDouble(splitted[1]));
			
		} else if(command.contains("push")) {
			com = new PushCommand();
			
		} else if(command.contains("pop")) {
			com = new PopCommand();
			
		} else if(command.contains("color")) {
			com = new ColorCommand(new Color(Integer.parseInt(splitted[1], 16)));
			
		} else {
			throw new IllegalArgumentException("Incorect command input");
		}
		
		commands.put(symbol, com);
		
		return this;
	}
	
	/**
	 * Registers productions.
	 */
	@Override
	public LSystemBuilder registerProduction(char symbol, String sequence) {
		productions.put(symbol, sequence);
		return this;
	}
	
	/**
	 * Sets the angle of turtle.
	 * @param angle an angle towards x axis given in <b>degrees</b>
	 */
	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = Math.toRadians(angle);
		return this;
	}
	
	/**
	 * Sets the axiom.
	 */
	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}
	
	/**
	 * Sets the origin position.
	 */
	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		this.origin = new Vector2D(x, y);
		return this;
	}
	
	/**
	 * Sets the length of a unit step.
	 */
	@Override
	public LSystemBuilder setUnitLength(double length) {
		this.unitLength = length;
		return this;
	}
	
	/**
	 * Sets the scaler.
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double scaler) {
		this.unitLengthDegreeScaler = scaler;
		return this;
	}

}
