package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.lsystems.impl.Vector2D;

/**
 * Moves the turtle in the direction in which it is turned by <code>step</code> effective displacement length and thereby
 * leaves a trace of the current color; updates its position.
 * @author Fani
 *
 */
public class DrawCommand implements Command {
	/**
	 * Effective displacement length of the turtle.
	 */
	private double step;
	
	/**
	 * Constructs a new <code>DrawCommand</code> with the specified effective displacement length.
	 * @param step effective displacement length of the turtle
	 */ 
	public DrawCommand(double step) {
		this.step = step;
	}
	
	/**
	 * Executes the functionality of this <code>DrawCommand</code>.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState turtle = ctx.getCurrentState();
		
		double x = turtle.getPosition().getX();
		double y = turtle.getPosition().getY();
		
		
		Vector2D newDirection = turtle.getDirection().scaled(step * turtle.getLength());
		turtle.setPosition(turtle.getPosition().added(newDirection));
		

		painter.drawLine(x, y, turtle.getPosition().getX(), turtle.getPosition().getY(), turtle.getColor(), 1f);
		
	}

}
