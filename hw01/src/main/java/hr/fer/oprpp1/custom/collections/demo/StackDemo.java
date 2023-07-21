package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 * Demonstration of ObjectStack on evaluation of expression with postfix notation.
 * @author Fani
 *
 */
public class StackDemo {

	/**
	 * Demonstrates ObjectStack on evaluation of expression with postfix notation.
	 * @param args commmand line input, only args[0] can be filled with the expression using quatation marks
	 * @throws IllegalArgumentException if args does not contain only 1 string
	 */
	public static void main(String[] args) {
		
		if(args.length != 1)
			throw new IllegalArgumentException("You can send one expression to be evaluated. Use quatation marks.");
		
		String[] expression = args[0].split("\\s+");
		
		ObjectStack stack = new ObjectStack();
		
		for(String string : expression) {
			if(isNumber(string))
				stack.push(Integer.parseInt(string));
			else {
				switch(string) {
				case "+" -> stack.push((Integer)stack.pop() + (Integer)stack.pop());
				case "-" -> {
								int secondArg = (Integer) stack.pop();
								int firstArg = (Integer) stack.pop();
								stack.push(firstArg - secondArg);
							}
				case "/" -> {   
								if((Integer)stack.peek() == 0) {
									System.err.println("You can't divide by zero! Retake your math class. ");
									System.exit(1);
								}
								int secondArg = (Integer) stack.pop();
								int firstArg = (Integer) stack.pop();
								stack.push( firstArg / secondArg);
								
							}
				case "*" -> stack.push((Integer)stack.pop() * (Integer)stack.pop());
				case "%" -> {
								int secondArg = (Integer) stack.pop();
								int firstArg = (Integer) stack.pop();
								stack.push(firstArg % secondArg);
							}
				default  -> {}
				}
			}
		}
		
		if(stack.size() != 1)
			System.err.println("You did something wrong buddy. Try again.");
		else 
			System.out.print(stack.pop());
		
	}
	
	/**
	 * Determines if a string is a number (integer).
	 * @param string the evaluated string 
	 * @return <code>true</code> if the string represents a number, <code>false</code> otherwise.
	 */
	private static boolean isNumber(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
