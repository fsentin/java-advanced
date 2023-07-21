package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.Vector2D;

/**
 * Rotates the direction in which the turtle looks for a given angle in degrees.
 * @author Fani
 *
 */
public class RotateCommand implements Command {
	/**
	 * Angle of rotation in degrees.
	 */
	private double angle;
	
	/**
	 * Constructs a new <code>RotateCommand</code> with the rotation angle.
	 * @param angle
	 */
	public RotateCommand(double angle) {
		this.angle = Math.toRadians(angle);
	}
	
	/**
	 * Executes the functionality of this <code>RotateCommand</code>.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		var state = ctx.getCurrentState();
		
		Vector2D dir = state.getDirection();
		state.setDirection(dir.rotated(angle));
	}

}
