package hr.fer.zemris.java.hw05.shell;

import java.util.List;
/**
 * Simple interface for shell command implementation.
 * @author Fani
 *
 */
public interface ShellCommand {
	/**
	 * Executes the functionality of this ShellCommand.
	 * @param env the environment of the shell in which the command is executed
	 * @param arguments the arguments of the command
	 * @return status of the shell after execution of this command.
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * Returns the name of this command.
	 * @return the name of this command.
	 */
	String getCommandName();
	
	/**
	 * Returns the description of this command.
	 * @return the description of this command in the form of a list of lines.
	 */
	List<String> getCommandDescription();
}
