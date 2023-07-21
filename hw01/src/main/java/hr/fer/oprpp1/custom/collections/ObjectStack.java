package hr.fer.oprpp1.custom.collections;
/**
 * An implementation of stack data structure.
 * @author Fani
 *
 */
public class ObjectStack {
	
	/**
	 * Internal array that stores objects pushed to stack.
	 */
	private ArrayIndexedCollection internal = new ArrayIndexedCollection();
	
	/**
	 * Checks if the stack contains no objects.
	 * 
	 * @return <code>true</code> if the stack contains no objects, <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return internal.isEmpty();
	}
	
	/**
	 * Determines how many objects are stored on stack at the moment.
	 * 
	 * @return number of currently stored objects on the stack.
	 */
	public int size() {
		return internal.size();
	}
	
	/**
	 * Pushes given value on the stack. <code>null</code> is not allowed to be placed on stack.
	 * <p>Average Complexity: O(1)</p>
	 * 
	 * @param value object put on top of the stack
	 * @throws NullPointerException if <code>null</code> is attempted to be placed on stack
	 */
	public void push(Object value) {
		internal.add(value);
	}
	
	/**
	 * Removes last value pushed on stack from stack and returns it.
	 * <p>Average Complexity: O(1)</p>
	 * 
	 * @return object that is removed from the top of the stack.
	 * @throws EmptyStackException if stack is empty when method is called
	 */
	public Object pop() {
		if(internal.isEmpty())
			throw new EmptyStackException("You tried to pop an object from the stack but the stack is empty!");
		
		int index = internal.size() - 1;
		Object returnObject = internal.get(index);
		
		internal.remove(index); //remove function is of complexity O(1) when it removes the last element
		return returnObject;
	}
	
	/**
	 * Returns last element placed on stack but does not delete it from stack.
	 * 
	 * @return object stored at the top of the stack.
	 * @throws EmptyStackException if stack is empty when method is called
	 */
	public Object peek() {
		if(internal.isEmpty())
			throw new EmptyStackException("You tried to peek an object from the stack but the stack is empty!");
		
		return internal.get(internal.size() - 1);
	}
		
	/**
	 * Removes all elements from stack.
	 */
	public void clear() {
		internal.clear();
	}

}
