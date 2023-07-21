package hr.fer.oprpp1.hw02.prob1;

public class Token {
	/**
	 * The type of a token.
	 */
	TokenType type;
	/**
	 * The value of a token.
	 */
	Object value;
	
	/**
	 * Constructs a new token with specified type and value.
	 * @param type the TokenType enumeration constant of the new token
	 * @param value the value object of the new token
	 */
	public Token(TokenType type, Object value) {
		if(type == null) 
			throw new IllegalArgumentException("Token type can not be null."); 
		
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Returns the value object of the token.
	 * @return the value object of the token.
	 */
	public Object getValue() {
		return this.value;
	}
	
	/**
	 * Returns the type of the token.
	 * @return TokenType enumeration constant which represents the type of the token.
	 */
	public TokenType getType() {
		return this.type;
	}
	
	/**
	 * Returns the string representation of a token.
	 * @return a string representation of a token.
	 */
	@Override
	public String toString() {
		return "(" + type + ", " + value + ")";
	}
	
}
