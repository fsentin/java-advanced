package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Token used in lexic analysis of text.
 * @author Fani
 *
 */
public class SmartToken {
	/**
	 * Type of this token.
	 */
	SmartTokenType type;
	
	/**
	 * The value of this token.
	 */
	Object value;
	
	/**
	 * Creates new SMartToken with given type and value.
	 * @param type given type
	 * @param value given value
	 * @throws IllegalArgumentException if type is <code>null</code>
	 */
	public SmartToken(SmartTokenType type, Object value) {
		if(type == null) 
			throw new IllegalArgumentException("Token type can not be null."); 
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Returns the value of this token.
	 * @return the value of this token.
	 */
	public Object getValue() {
		return this.value;
	}
	
	/**
	 * Returns the type of this token.
	 * @return the type of this token.
	 */
	public SmartTokenType getType() {
		return this.type;
	}
	
	/**
	 * Returns string representation of this token.
	 * @return string representation of this token.
	 */
	@Override
	public String toString() {
		return "(" + type + ", " + value + ")";
	}
}
