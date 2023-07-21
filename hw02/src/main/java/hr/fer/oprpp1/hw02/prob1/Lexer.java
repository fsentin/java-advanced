package hr.fer.oprpp1.hw02.prob1;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;

/**
 * Tokenizator.
 * @author Fani
 *
 */
public class Lexer {
	/**
	 * Input text.
	 */
	private char[] data; 
	
	/**
	 * Current token.
	 */
	private Token token; 
	
	/**
	 * Index of the first non processed character.
	 */
	private int currentIndex;
	
	/**
	 * The current state of the lexer.
	 */
	private LexerState state;
	
	/**
	 * Constructor takes input text to be tokenized.
	 * @param text the input text
	 */
	public Lexer(String text) {
		if(text == null)
			throw new NullPointerException();
		
		this.data = text.toCharArray();
		this.currentIndex  = 0;
		this.state = LexerState.BASIC;
	}
	
	/**
	 * Sets the state of a lexer.
	 * @param state the state in which the lexer is in after he call of this function
	 */
	public void setState(LexerState state) {
		if(state == null) throw new NullPointerException("The state can't be null!");
		this.state = state;
	}
	
	/**
	 * Generates and returns the next token
	 * @return the next token.
	 * @throws LexerException if an error occurs during tokenization
	 */
	public Token nextToken() {
		extractNextToken();
		return token;
	}
	
	/**
	 * Returns the last generated token. Can be called multiple times, no new tokens will be generated.
	 * @return the last generated token.
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Extracts the next token from the input.
	 */
	private void extractNextToken() {
		// there has already been an EOF
		if(token != null && token.getType() == TokenType.EOF)
			throw new LexerException("No tokens available.");
		
		skipBlank();
		
		// no more tokens, EOF
		if(currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return;
		}
		
		if(state == LexerState.EXTENDED) {
			extractNextTokenExtended();
			return;
		}
		
		// check for WORDS
		if(isBackslash(data[currentIndex]) || isLetter(data[currentIndex])) {
			int start = currentIndex;
			
			boolean treatTheNextAsLetter = false;
			if(isBackslash(data[currentIndex]))
				treatTheNextAsLetter = true;
			
			currentIndex++;
			
			while(currentIndex < data.length) {
				//a regular letter
				if(!treatTheNextAsLetter && isLetter(data[currentIndex])) { 
					currentIndex++;
				
				// start of an escape sequence
				} else if(!treatTheNextAsLetter && isBackslash(data[currentIndex])) { 
					treatTheNextAsLetter = true;
					currentIndex++;
					
				// end of escape sequence
				} else if(treatTheNextAsLetter && (isBackslash(data[currentIndex]) || isDigit(data[currentIndex])) ) { 
					treatTheNextAsLetter = false;
					currentIndex++;
				// an illegal escape sequence	
				} else if(treatTheNextAsLetter){
					throw new LexerException("Illegal escape sequence!");
					
				// other legal characters 
				} else {
					break;
				}
			}
			
			int end = currentIndex;
			String newWord = new String(data, start, end- start);
			
			String newProcessed = "";
			newProcessed = removeBackslashBeforeNumber(newWord);
			
			if(checkIfIllegal(newProcessed)) throw new LexerException("Illegal escape sequence!");
			
			newProcessed = removeDoubleBackslash(newProcessed);

			token = new Token(TokenType.WORD, newProcessed);
			return;
		}
		
		//check for NUMBERS
		if(isDigit(data[currentIndex])) {
			int start = currentIndex;
			currentIndex++;
			while(currentIndex < data.length && isDigit(data[currentIndex])) {
				currentIndex++;
			}
			int end = currentIndex;
			String newNumber = new String(data, start, end - start);
			
			checkIfParseable(newNumber);
			
			token = new Token(TokenType.NUMBER, Long.parseLong(newNumber));
			return;
		}
		
		// it is a SYMBOL
		token = new Token(TokenType.SYMBOL, data[currentIndex]);
		currentIndex++;
	}
	
	private void extractNextTokenExtended() {
		
		if(isHashtag(data[currentIndex])) {
			token = new Token(TokenType.SYMBOL, data[currentIndex]);
			currentIndex++;
			return;
		}
		
		int start = currentIndex;
		currentIndex++;
		while(currentIndex < data.length && !isWhitespace(data[currentIndex]) && !isHashtag(data[currentIndex])) {
			currentIndex++;
		}
		
		int end = currentIndex;
		String newWord = new String(data, start, end- start);
		token = new Token(TokenType.WORD, newWord);
		return;
	}
	
	/**
	 * Skips all blank/whitespace characters.
	 */
	private void skipBlank() {
		while(currentIndex < data.length) {
			char currChar = data[currentIndex];
			if(currChar == '\r' || currChar == '\n' || currChar == '\t'|| currChar == ' ') {
				currentIndex++;
				continue;
			}
			break;
		}
	}
	

	
	/**
	 * Checks if a character is backslash.
	 * @param c char to be checked
	 * @return <code>true</code> if a the specified character is backslash, <code>false</code> otherwise.
	 */
	private boolean isBackslash(char c) {
		return c == '\\';
	}
	
	/**
	 * Checks if a character is whitespace.
	 * @param c char to be checked
	 * @return <code>true</code> if a the specified character is whitespace, <code>false</code> otherwise.
	 */
	private boolean isWhitespace(char c) {
		return c == ' ';
	}
	
	/**
	 * Checks if a character is hashtag.
	 * @param c char to be checked
	 * @return <code>true</code> if a the specified character is hashtag, <code>false</code> otherwise.
	 */
	private boolean isHashtag(char c) {
		return c == '#';
	}
	
	/**
	 * Removes backslashes before numbers inside a string.
	 * @param s the string to be processed.
	 * @return a string without backslashes before numbers.
	 */
	private String removeBackslashBeforeNumber(String s) {
		String processed = "";
		for (int i = 0; i < s.length(); i++) {
			if( (i < s.length() - 1 ) && isBackslash(s.charAt(i)) && isDigit(s.charAt(i + 1))) {
				processed += s.charAt(i + 1);
				i++;
			} else {
				processed += s.charAt(i);
			}
		}
		return processed;
	}
	
	/**
	 * Removes double backslashes from a string.
	 * @param s the string to be processed
	 * @return a string without double backslashes.
	 */
	private String removeDoubleBackslash(String s) {
		String processed = "";
		for (int i = 0; i < s.length(); i++) {
			if( (i < s.length() - 1 ) && isBackslash(s.charAt(i)) && isBackslash(s.charAt(i + 1))) {
				processed += s.charAt(i + 1);
				i++;
			} else {
				processed += s.charAt(i);
			}
		}
		return processed;
	}
	
	/**
	 * Checks if there are any illegal escape sequences left.
	 * @param s the string to be checked
	 * @return <code>true</code> if it is illegal, <code>false</code> otherwise.
	 */
	private boolean checkIfIllegal(String s) {
		boolean res = false;
		if(s.length() == 1 && isBackslash(s.charAt(0)))
			return true;	
		for (int i = 0; i < s.length(); i++) {
			if((i < s.length() - 1 ) && isBackslash(s.charAt(i)) && !(isBackslash(s.charAt(i + 1)) || isDigit(s.charAt(i + 1)))) {
				res = true;
				break;
			} else if((i == s.length() - 1) && isBackslash(s.charAt(i)) && !isBackslash(s.charAt(i - 1)) ){
				res = true;
			}
		}
		return res;
	}
	
	/**
	 * Checks if a string is parsable to long.
	 * @param number a string to be tested
	 * @return <code>true</code> if the string is parseable to long.
	 * @throws LexerException if the string is not parseable to long
	 */
	private boolean checkIfParseable(String number) {
		try {
			Long.parseLong(number);
			return true;
		} catch (NumberFormatException e) {
			throw new LexerException("The number is not parseable!");
		}
	}
	
}

