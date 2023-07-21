package hr.fer.zemris.java.hw05.shell;

import java.util.SortedMap;
/**
 * Simple interface for shell environment implementation.
 * @author Fani
 *
 */
public interface Environment {
	
	/**
	 * Reads one line from the system input stream and returns the string representation.
	 * @return string representing one line of input from the user.
	 * @throws ShellIOException if an error during reading occurs
	 */
	String readLine() throws ShellIOException;
	
	/**
	 * Writes the given string on the system output stream.
	 * @param text the given string which is printed out
	 * @throws ShellIOException if an error during writing occurs
	 */
	void write(String text) throws ShellIOException;
	
	/**
	 * Writes the given string on the system output stream and appends a new line character.
	 * @param text the given string which is printed out
	 * @throws ShellIOException if an error during reading occurs
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * Returns the supported commands of this environment.
	 * @return supported commands, specifically: a sorted map consisting of entries whose keys are command names and values are ShellCommands.
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * Returns the symbol for multiline command input of this environment.
	 * @return the symbol for multiline command input of this environment.
	 */
	Character getMultilineSymbol();
	
	/**
	 * Sets the symbol for multiline command input of this environment.
	 * @param symbol new multiline symbol
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * Returns the symbol for command input prompt of this environment.
	 * @return the symbol for command input prompt of this environment.
	 */
	Character getPromptSymbol();
	
	/**
	 * Sets the symbol for command input prompt of this environment.
	 * @param symbol new prompt symbol
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * Returns the symbol for prompt of a multiple line (also called morelines) command input of this environment.
	 * @return the symbol for prompt of a multiple line command input of this environment.
	 */
	Character getMorelinesSymbol();
	
	/**
	 * Sets the symbol for prompt of a multiple line (also called morelines) command input of this environment.
	 * @param symbol new morelines symbol
	 */
	void setMorelinesSymbol(Character symbol);
}
