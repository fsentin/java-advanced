package hr.fer.oprpp1.hw04.db;

/**
 * Models simple lexical analysis for database queries.
 * @author Fani
 *
 */
public class Lexer {
	/**
	 * Internal structure used for iteration through input and identification of tokens.
	 */
	private char[] input; 
	
	/**
	 * The last extracted token.
	 */
	private Token token; 
	
	/**
	 * The index at which we are reading input and identificating the next token.
	 */
	private int index;
	
	/**
	 * Constructs a new lexer for given string input.
	 * @param input
	 */
	public Lexer(String input) {
		if(input == null)
			throw new NullPointerException();
		
		this.input = input.toCharArray();
		this.index  = 0;
	}
	
	/**
	 * Extracts the next token and returns the new token.
	 * @return the new token.
	 */
	public Token nextToken() {
		extractNextToken();
		return token;
	}
	
	/**
	 * Fetches the last extracted token.
	 * @return the last extracted token.
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Extracts the next token from an input string.
	 */
	private void extractNextToken(){
		if(token != null && token.getType() == TokenType.EOF)
			throw new RuntimeException("No tokens available.");
		
		skipBlank();
		
		if(index >= input.length) {
			token = new Token(TokenType.EOF, null);
			return;
		}
		
		//operators
		if(input[index] == '>' || input[index] == '<' || input[index] == '=') {
			token = new Token(TokenType.OPERATOR, Character.toString(input[index]));
			index++;
			return;
		} else if(index + 1 < input.length && input[index] == '<' && input[index + 1] == '=') {
			token = new Token(TokenType.OPERATOR, "<=");
			index++;
			return;
		} else if(index + 1 < input.length && input[index] == '>' && input[index + 1] == '=') {
			token = new Token(TokenType.OPERATOR, ">=");
			index++;
			return;
		} else if(index + 1 < input.length && input[index] == '!' && input[index + 1] == '=') {
			token = new Token(TokenType.OPERATOR, "!=");
			index++;
			return;
		}
		
		//word
		if(Character.isLetter(input[index])){
			int start = index;
			index++;
			while(index < input.length && Character.isLetter(input[index])) {
				index++;
			}
			int end = index;
			String newAttribute = new String(input, start, end - start);
			
			if(newAttribute.equals("query"))
				token = new Token(TokenType.QUERY, newAttribute);
			else if(newAttribute.equalsIgnoreCase("and")) {
				token = new Token(TokenType.AND, newAttribute);
			} else if(newAttribute.equals("LIKE")) {
				token = new Token(TokenType.OPERATOR, newAttribute);
			} else if(newAttribute.equals("exit")) {
				token = new Token(TokenType.EXIT, newAttribute);
			} else 
				token = new Token(TokenType.ATTRIBUTE, newAttribute);
			
			return;
		}
		
		//string literal
		if(input[index] == '\"'){
			boolean closed = false;
			int start = index;
			index++;
			
			while(index < input.length && 
					(Character.isLetterOrDigit(input[index]) || input[index] == '\"' || input[index] == '*')) {
				if(input[index] == '\"') {
					index++;
					closed = true; // the quote is closed
					break;
				}
				index++;
			}
			
			if(!closed) throw new RuntimeException("The string hasn't been closed!");
			int end = index;
			
			var newString = new String(input, start, end - start);
			token = new Token(TokenType.STRING_LITERATOR, newString.substring(1, newString.length() - 1));
			return;
		}
		
		throw new IllegalArgumentException("Improper input! It cannot be tokenized!");
		
	}
	
	/**
	 * Skips whitespace characters ('\r', '\n', '\t', ' ').
	 */
	private void skipBlank() {
		while(index < input.length) {
			char currChar = input[index];
			if(currChar == '\r' || currChar == '\n' || currChar == '\t'|| currChar == ' ') {
				index++;
				continue;
			}
			break;
		}
	}
}
