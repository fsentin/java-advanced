package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
//import hr.fer.zemris.lsystems.impl.Vector2D;
import hr.fer.zemris.lsystems.impl.Vector2D;

/**
 *  
 * Moves the turtle in the direction in which it is turned by <code>step</code> effective displacement length; updates its position.
 * @author Fani
 *
 */
public class SkipCommand implements Command {
	/**
	 * Effective displacement length of the turtle.
	 */
	private double step;
	
	/**
	 * Constructs a new <code>SkipCommand</code> command with the specified effective displacement length.
	 * @param step effective displacement length of the turtle
	 */ 
	public SkipCommand(double step) {
		this.step = step;
	}
	
	/**
	 * Executes the functionality of this <code>SkipCommand</code>command.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState turtle = ctx.getCurrentState();
		
		double newX = turtle.getDirection().getX() + turtle.getLength() * step;
		double newY = turtle.getDirection().getY() + turtle.getLength() * step;
		
		turtle.setPosition(new Vector2D(newX, newY));
		
		/*ctx.popState();
		
		//move it a unit
		var newX = turtle.getPosition().getX() + turtle.getLength() * step;
		var newY = turtle.getPosition().getY() + turtle.getLength() * step;
		
		//new position
		Vector2D newV = new Vector2D(newX, newY);
		TurtleState newTurtle = turtle.copy();
		newTurtle.setPosition(newV);
		ctx.pushState(newTurtle);*/
	}

}
