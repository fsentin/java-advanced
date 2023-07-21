package hr.fer.oprpp1.hw04.db;

/**
 * Models a token of a lexical analysis.
 * @author Fani
 *
 */
public class Token {
	/**
	 * The type of the token.
	 */
	private TokenType type;
	/**
	 * The value of the token.
	 */
	private String value;
	
	/**
	 * Constructs a new token with given type and value. 
	 * @param type given type enumeration
	 * @param value given object value
	 */
	public Token(TokenType type, String value) {
		if(type == null) 
			throw new IllegalArgumentException("Token type can not be null."); 
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Returns the object which is the value of this token.
	 * @return the object which is the value of this token.
	 */
	public String getValue() {
		return this.value;
	}
	
	/**
	 * Returns the type of a token.
	 * @return <code>TokenType</code> enumeration which is the type of this token.
	 */
	public TokenType getType() {
		return this.type;
	}
	
	@Override
	public String toString() {
		return  type + " " + value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Token))
			return false;
		Token other = (Token) obj;
		if (type != other.type)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
}
