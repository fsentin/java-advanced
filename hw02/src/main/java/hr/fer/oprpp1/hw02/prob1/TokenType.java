package hr.fer.oprpp1.hw02.prob1;

/**
 * Enumeration of token types.
 * @author Fani
 *
 */
public enum TokenType {
	/**The last token in a tokenization, generated after the lexer is done with the input. If tried to tokenize more after a there has 
	 * already occurred one EOF, LexerException is thrown.**/
	EOF,
	/**One or more characters for which  <code>Character.isLetter</code> returns true. **/
	WORD, 
	/**One or more digits that can be represented as a <code>long</code> type of data.**/
	NUMBER,
	/**Each symbol from the input text when '\r', '\n', '\t', ' ' are removed from the input.**/
	SYMBOL
}
