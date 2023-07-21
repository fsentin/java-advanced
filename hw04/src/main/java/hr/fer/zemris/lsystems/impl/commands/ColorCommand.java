package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Changes the color of the turtle.
 * @author Fani
 *
 */
public class ColorCommand implements Command {
	/**
	 * The new color of the turtle.
	 */
	private Color color;
	
	/**
	 * Creates a new color command with the given color.
	 * @param color new color of the turtle.
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}
	
	/**
	 * Executes the functionality of this command.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		var state = ctx.getCurrentState();
		ctx.popState();
		state.setColor(color);
		ctx.pushState(state);
	}

}
