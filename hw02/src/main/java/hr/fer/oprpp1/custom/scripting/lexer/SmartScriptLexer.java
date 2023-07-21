package hr.fer.oprpp1.custom.scripting.lexer;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;
import static java.lang.Character.isLetterOrDigit;

/**
 * A model of a tokenizer.
 * @author Fani
 *
 */

public class SmartScriptLexer {
	/**
	 * Input text.
	 */
	private char[] data; 
	
	/**
	 * Current token.
	 */
	private SmartToken token; 
	
	/**
	 * Index of the first non processed character.
	 */
	private int currentIndex;
	
	/**
	 * The current state of the lexer.
	 */
	private SmartLexerState state;
	
	/**
	 * Creates an instance of lexer using string input. The default lexer state is TEXT.
	 * @param input
	 */
	public SmartScriptLexer(String input) {
		
		this.data = input.toCharArray();
		this.currentIndex  = 0;
		this.state = SmartLexerState.TEXT;
		
	}
	
	/**
	 * Generates and returns the next token
	 * @return the next token.
	 * @throws LexerException if an error occurs during tokenization
	 */
	public SmartToken nextToken() {
		extractNextToken();
		return token;
	}
	
	/**
	 * Returns the last generated token. Can be called multiple times, no new tokens will be generated.
	 * @return the last generated token.
	 */
	public SmartToken getToken() {
		return token;
	}
	
	/**
	 * Used to extract the next token and delegate the work to other methods of extraction depending on the state of the lexer.
	 */
	private void extractNextToken() {
		// there has already been an EOF
		if(token != null && token.getType() == SmartTokenType.EOF)
			throw new LexerException("No tokens available.");
		
		// no more tokens, EOF
		if(currentIndex >= data.length) {
			token = new SmartToken(SmartTokenType.EOF, null);
			return;
		}
		
		if(state == SmartLexerState.TAG) skipBlank();
		// check for tags
		if(currentIndex + 1 < data.length) { //currentIndex == 0 || currentIndex - 1 >= 0
			if(currentIndex == 0 && isOpenTagSimple(currentIndex)) {
				createOpenTag();
				return;
			} else if(currentIndex - 1 >= 0 && isOpenTag(currentIndex)) {
				createOpenTag();
				return;
			} else if(isCloseTag(currentIndex)) {
				createCloseTag();
				return;
			}
		}
		
		if(state == SmartLexerState.TAG) {
			extractNextTokenTag();
			return;
		} else {
			extractNextTokenText();
			return;
		}
	}
	
	
	/**
	 * Used to extract the next token if the lexer is in the TAG state.
	 */
	private void extractNextTokenTag() {
		skipBlank();
		
		//if its a numeric
		if(isDigit(data[currentIndex]) || 
				data[currentIndex] == '-' &&  // a negative numeric
				currentIndex + 1 < data.length && isDigit(data[currentIndex + 1])) {
			int start = currentIndex;
			currentIndex++;
			while(currentIndex < data.length && 
					(isDigit(data[currentIndex]) 
							|| Character.toString(data[currentIndex]).contentEquals(".") 
								&& currentIndex + 1 < data.length && isDigit(data[currentIndex + 1]))) {
				currentIndex++;
			}
			int end = currentIndex;
			String newNumber = new String(data, start, end - start);
			
			if(newNumber.contains(".")) {
				checkIfParseableToDouble(newNumber);
				token = new SmartToken(SmartTokenType.DOUBLE, Double.parseDouble(newNumber));
				return;
			} else {
				checkIfParseableToInt(newNumber);
				token = new SmartToken(SmartTokenType.INTEGER, Integer.parseInt(newNumber));
				return;
			}
		}
		
		//if its an identificator
		if(isLetter(data[currentIndex])) {
			int start = currentIndex;
			currentIndex++;
			while(currentIndex < data.length && (isLetterOrDigit(data[currentIndex]) || isUnderscore(data[currentIndex]))) {
					currentIndex++;
			}
			int end = currentIndex;
			String newIdentificator = new String(data, start, end - start);
			token = new SmartToken(SmartTokenType.IDENTIFICATOR, newIdentificator);
			return;
		}
		
		//if its a function name
		if(isAt(data[currentIndex])) {
			currentIndex++;
			int start = currentIndex;
			if(currentIndex >= data.length) {
				throw new LexerException("Invalid function name!!");
			}
			while(currentIndex < data.length && (isLetterOrDigit(data[currentIndex]) || isUnderscore(data[currentIndex]))) {
					currentIndex++;
			}
			int end = currentIndex;
			String newFunctionName = new String(data, start, end - start);
			token = new SmartToken(SmartTokenType.FUNCTION, newFunctionName);
			return;
		}
		
		//if its a string
		if(isQuote(data[currentIndex])) {
			boolean closed = false; // for checking if a quote is closed
			int start = currentIndex; 
			currentIndex++;
			while(currentIndex < data.length && !isCloseTag(currentIndex)) {
				if(isQuote(data[currentIndex]) && !isBackslash(data[currentIndex - 1])) {
					currentIndex++;
					closed = true; // the quote is closed
					break;
				} else if(isBackslash(data[currentIndex]) 
						&& currentIndex + 1 < data.length 
						&& !(isBackslash(data[currentIndex + 1]) 
							 || isQuote(data[currentIndex + 1])
							 || isCrazyEscape(data[currentIndex + 1]))
						) {
					throw new LexerException("Illegal escape sequence in string!!");
				}
				currentIndex++;
			}
			if(!closed) throw new LexerException("The string hasn't been closed!");
			int end = currentIndex;
			String newString = new String(data, start, end - start);
			newString = removeBackslashBeforeQuote(newString);
			token = new SmartToken(SmartTokenType.STRING, newString.substring(1, newString.length() - 1));
			return;
		}
		
		//if its symbol
		if(isValidSymbol(data[currentIndex])) {
			token = new SmartToken(SmartTokenType.SYMBOL, data[currentIndex]);
			currentIndex++;
			return;
		}
		
		throw new LexerException("Invalid input!!");
		
	} 
	
	
	/**
	 * Used to extract the next token if the lexer is in the TEXT state.
	 */
	private void extractNextTokenText() {
		int start = currentIndex;
		
		//take all letters until you reach end or a tag
		while(currentIndex < data.length) {
			//is it the start of a tag?
			if(currentIndex - 1 >= 0 && currentIndex + 1 < data.length) {
				if(isOpenTag(currentIndex)) {
					break;
				} 
			}
			//if the next after \ is not \ or { throw exception
			if(isBackslash(data[currentIndex]) && currentIndex + 1 < data.length 
					&& !(isBackslash(data[currentIndex + 1]) ||  data[currentIndex + 1] == '{')) {	
				throw new LexerException("Illegal escape sequence in text!!");
			}
			//if not continue :)
			currentIndex++;
		}
		
		int end = currentIndex;
		// create the text token
		String newText = new String(data, start, end- start);
		token = new SmartToken(SmartTokenType.STRING, removeBackslashBeforeBracket(removeDoubleBackslash(newText)));
		return;
	}
	
	
	
	/**
	 * Skips all blank characters.
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
	 * Creates an open tag token.
	 */
	private void createOpenTag() {
		token = new SmartToken(SmartTokenType.OPEN_TAG, "{$");
		state = SmartLexerState.TAG;
		currentIndex = currentIndex + 2;
	}
	/**
	 * Creates a close tag token.
	 */
	private void createCloseTag() {
		token = new SmartToken(SmartTokenType.CLOSE_TAG, "$}");
		state = SmartLexerState.TEXT;
		currentIndex = currentIndex + 2;
	}
	
	/**
	 * Checks if a current token is the open tag "{$". 
	 * Used for elements that aren't the first because it also eliminates the possibility of it being
	 * a part of the escape sequence.
	 * @param index the index of an element to be checked
	 * @return  <code>true</code> if it is the close tag, <code>false</code> otherwise.
	 */
	private boolean isOpenTag(int index) {
		return !(isBackslash(data[index - 1])) && // it is not escape sequence
				isOpenTagSimple(index);
	}
	
	/**
	 * Checks if a current token is the open tag "{$". Used only to check if the first element is an open tag.
	 * @param index the index of an element to be checked
	 * @return  <code>true</code> if it is the open tag, <code>false</code> otherwise.
	 */
	private boolean isOpenTagSimple(int index) {
		return 	data[index] == '{' && data[index + 1] == '$';
	}
	
	/**
	 * Checks if a current token is the close tag "$}".
	 * @param index the index of an element to be checked
	 * @return  <code>true</code> if it is the close tag, <code>false</code> otherwise.
	 */
	private boolean isCloseTag(int index) {
		return data[index] == '$' && data[index + 1] == '}';
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
	 * Removes backslashes in front of brackets.
	 * @param s the string to be processed
	 * @return a string without backslashes before brackets.
	 */
	private String removeBackslashBeforeBracket(String s) {
		String processed = "";
		for (int i = 0; i < s.length(); i++) {
			if( (i < s.length() - 1 ) && isBackslash(s.charAt(i)) && s.charAt(i + 1) == '{') {
				processed += s.charAt(i + 1);
				i++;
			} else {
				processed += s.charAt(i);
			}
		}
		return processed;
	}
	
	/**
	 * Remove backslashes in front of quotes.
	 * @param s the string to be processed
	 * @return a string without backslashes before quotes.
	 */
	private String removeBackslashBeforeQuote(String s) {
		String processed = "";
		for (int i = 0; i < s.length(); i++) {
			if( (i < s.length() - 1 ) && isBackslash(s.charAt(i)) && isQuote(s.charAt(i + 1))) {
				processed += s.charAt(i + 1);
				i++;
			} else {
				processed += s.charAt(i);
			}
		}
		return processed;
	}
	
	
	/**
	 * Returns true if the character is equal to "\".
	 * @param c the checked character
	 * @return <code>true</code> if it is equal to "\", <code>false</code> otherwise.
	 */
	private boolean isBackslash(char c) {
		return c == '\\';
	}
	
	/**
	 * Returns true if the character is equal to "_".
	 * @param c the checked character
	 * @return <code>true</code> if it is equal to "_", <code>false</code> otherwise.
	 */
	private boolean isUnderscore(char c) {
		return c == '_';
	}
	
	/**
	 * Returns true if the character is equal to "@".
	 * @param c the checked character
	 * @return <code>true</code> if it is equal to "@", <code>false</code> otherwise.
	 */
	private boolean isAt(char c) {
		return c == '@';
	}
	
	/**
	 * Returns true if the character is equal to ".
	 * @param c the checked character
	 * @return <code>true</code> if it is equal to ", <code>false</code> otherwise.
	 */
	private boolean isQuote(char c) {
		return c == '\"';
	}
	
	/**
	 * Returns true if the symbol is valid. Valid symbols are +, -, *, /, ^, = .
	 * @param c the checked character
	 * @return <code>true</code> if the character is a valid symbol, <code>false</code> otherwise.
	 */
	private boolean isValidSymbol(char c) {
		return c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == '=';
	}
	
	/**
	 * Returns true if the escape sequence is \r \n or \t
	 * @param c the checked character
	 * @return <code>true</code> if it is \r \n or \t, <code>false</code> otherwise.
	 */
	private boolean isCrazyEscape(char c) {
		return c == 'r' || c == 'n' || c == 't';
	}
	
	/**
	 * Checks if a string is parsable to integer.
	 * @param number a string to be tested
	 * @return <code>true</code> if the string is parseable to integer.
	 * @throws LexerException if the string is not parseable to integer
	 */
	private boolean checkIfParseableToInt(String number) {
		try {
			Integer.parseInt(number);
			return true;
		} catch (NumberFormatException e) {
			throw new LexerException("The number is not parseable!");
		}
	}
	
	/**
	 * Checks if a string is parsable to double.
	 * @param number a string to be tested
	 * @return <code>true</code> if the string is parseable to double.
	 * @throws LexerException if the string is not parseable to double
	 */
	private boolean checkIfParseableToDouble(String number) {
		try {
			Double.parseDouble(number);
			return true;
		} catch (NumberFormatException e) {
			throw new LexerException("The number is not parseable!");
		}
	}

}
