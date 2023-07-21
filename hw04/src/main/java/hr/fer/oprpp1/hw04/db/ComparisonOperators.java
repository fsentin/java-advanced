package hr.fer.oprpp1.hw04.db;

/**
 * A class of final static functional <code>IComparsionOperator</code> interfaces.
 * <p>Includes:</p>
 * <p>LESS</p>
 * <p>LESS_OR_EQUALS</p>
 * <p>GREATER</p>
 * <p>GREATER_OR_EQUALS</p>
 * <p>EQUALS</p>
 * <p>NOT_EQUALS</p>
 * <p>LIKE</p>
 * 
 * @author Fani
 *
 */
public class ComparisonOperators {
	
	/**
	 * An <code>IComparsionOperator</code> functional interface.
	 * Using a function <code>satisfies</code>, determines if one string is less than the other lexicographically.
	 */
	public static final IComparsionOperator LESS = (x, y) -> x.compareTo(y) < 0 ? true : false;
	
	
	/**
	 * An <code>IComparsionOperator</code> functional interface.
	 * Using a function <code>satisfies</code>, determines if one string is less than or equal to the other lexicographically.
	 */
	public static final IComparsionOperator LESS_OR_EQUALS = (x, y) -> x.compareTo(y) <= 0 ? true : false;
	
	/**
	 * An <code>IComparsionOperator</code> functional interface.
	 * Using a function <code>satisfies</code>, determines if one string is greater than the other lexicographically.
	 */
	public static final IComparsionOperator GREATER = (x, y) -> x.compareTo(y) > 0 ? true : false;
	
	/**
	 * An <code>IComparsionOperator</code> functional interface.
	 * Using a function <code>satisfies</code>, determines if one string is greater than or equal to the other lexicographically.
	 */
	public static final IComparsionOperator GREATER_OR_EQUALS = (x, y) -> x.compareTo(y) >= 0 ? true : false;
	
	/**
	 * An <code>IComparsionOperator</code> functional interface.
	 * Using a function <code>satisfies</code>, determines if one string is equal to the other lexicographically.
	 */
	public static final IComparsionOperator EQUALS = (x, y) -> x.compareTo(y) == 0 ? true : false;
	
	/**
	 * An <code>IComparsionOperator</code> functional interface.
	 * Using a function <code>satisfies</code>, determines if one string is not equal to the other lexicographically.
	 */
	public static final IComparsionOperator NOT_EQUALS = (x, y) -> x.compareTo(y) != 0 ? true : false;
	
	/**
	 * An <code>IComparsionOperator</code> functional interface.
	 * Using a function <code>satisfies</code>, determines if one string is like the other. Only one wild card is allowed.
	 */
	public static final IComparsionOperator LIKE = new IComparsionOperator() {
		@Override
		public boolean satisfied(String value1, String value2) { 
			String[] regex = value2.split("");
			
			int occurance = 0;
			
			for (int i = 0; i < regex.length; i++) {
				if(regex[i].equals("*"))
					occurance++;
			}
			
			if(occurance > 1)
				throw new IllegalArgumentException("Wildcard can be used only once");
			/*
			 * SELF-IMPLEMENTED
			String str = value2.replace("*", "");
			
			
			String[] string = value1.split("");
			int i  = 0;
			int j = 0;
			
			if(regex[0].equals("*")) {
				j++;
				//find index of first
				while(!string[i].equals(regex[j])) {
					i++;
				}
				//if once found they don't match, false
				while(j < regex.length && i < string.length) {
					if(!regex[j].equals(string[i]))
						return false;
					i++; j++;
				}
				return true;
				
			} else if(regex[regex.length - 1].equals("*")) {
				while(regex[i].equals(string[i])) 
					i++;
				
				//if they match until the *
				if(regex[i].equals("*"))
					return true;
				else 
					return false;
		
			} else {
				
				while(regex[i].equals(string[i])) 
					i++;
				
				if(!regex[i].equals("*"))
					return false;
				j = i + 1;
				while(j < regex.length && i < string.length) {
					if(!regex[j].equals(string[i]))
						return false;
					i++; j++;
				}
				if(j <= str.length())
					return false;
				*/
				return value1.matches(value2.replace("*", ".*"));
				
			}
		};
}
