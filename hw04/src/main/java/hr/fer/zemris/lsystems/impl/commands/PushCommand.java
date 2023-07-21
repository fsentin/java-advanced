package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Puts a copy of current state on top of stack.
 * @author Fani
 *
 */
public class PushCommand implements Command {
	
	/**
	 * Executes the functionality of this <code>PushCommand</code>.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.pushState(ctx.getCurrentState().copy());
		
	}

}
