package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;
/**
 * Represents a command which can be executed.
 * @author Fani
 *
 */
public interface Command {
	/**
	 * Executes this command.
	 * @param ctx context in which the command is executed
	 * @param painter the object used to draw if needed 
	 */
	void execute(Context ctx, Painter painter);
}
