package hr.fer.oprpp1.custom.scripting.lexer;

public enum SmartTokenType {
	/**Marks the beginning of a tag.**/
	OPEN_TAG,
	/**Marks the ending of a tag.**/
	CLOSE_TAG,
	/**Marks a variable or keyword.**/
	IDENTIFICATOR,
	STRING,
	INTEGER,
	DOUBLE,
	FUNCTION,
	SYMBOL,
	EOF
}
