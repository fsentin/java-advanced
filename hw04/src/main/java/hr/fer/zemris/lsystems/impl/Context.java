package hr.fer.zemris.lsystems.impl;
/**
 * A stack like model of turtle states used to track its movements.
 * @author Fani
 *
 */
public class Context {
	
	ObjectStack<TurtleState> states;
	
	/**
	 * Creates a new context.
	 */
	public Context() {
		this.states = new ObjectStack<>();
	}

	/**
	 * Returns the state from the top of the stack.
	 * @return the state from the top of the stack.
	 */
	public TurtleState getCurrentState() {
		return states.peek();
	}
	
	/**
	 * Pushes a state to the top of the stack.
	 * @param state a state of turtle that is pushed to the stack.
	 */
	public void pushState(TurtleState state) {
		states.push(state);
	}
	
	/**
	 * Removes the top of the stack.
	 */
	public void popState() {
		states.pop();
	}

}
