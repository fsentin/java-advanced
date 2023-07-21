package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Removes one state from the stack.
 * @author Fani
 *
 */
public class PopCommand implements Command {

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
	}
	
}
