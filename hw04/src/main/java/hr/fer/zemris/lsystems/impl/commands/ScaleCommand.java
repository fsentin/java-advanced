package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Updates the effective offset length by multiplying it by a given factor.
 * @author Fani
 *
 */
public class ScaleCommand implements Command {
	
	/**
	 * Factor by which the effective offset is scaled.
	 */
	private double factor;
	
	/**
	 * Constructs a new <code>ScaleCommand</code> with the specified factor of scaling.
	 * @param factor factor of scaling
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}
	
	/**
	 * Executes the functionality of this <code>ScaleCommand</code>.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		var state = ctx.getCurrentState();
		ctx.popState();
		double newUnitLen = state.getLength() * factor;
		state.setLength(newUnitLen);
		ctx.pushState(state);
	}

}
