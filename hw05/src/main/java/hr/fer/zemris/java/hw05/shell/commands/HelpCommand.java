package hr.fer.zemris.java.hw05.shell.commands;

import java.util.ArrayList;

import java.util.List;
import java.util.Map.Entry;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**
 * Command which provides help information for shell commands.
 * Prints description of the specified command if given the proper name, else if no arguments specified, 
 * lists all supported commands and their descriptions.
 * 
 * @author Fani
 *
 */
public class HelpCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		//no given arguments
		if(arguments.isEmpty()) {
			for(Entry<String, ShellCommand> entry : env.commands().entrySet()){
				printCommandDesc(env, entry.getKey(), entry.getValue().getCommandDescription());
			}
			return ShellStatus.CONTINUE;
		
		//correctly given argument
		} else if(arguments.split(" ").length == 1) {
			String command = arguments.split(" ")[0];
			
			//the command exists
			if(env.commands().containsKey(command)) {
				var desc = env.commands().get(command);
				printCommandDesc(env, command, desc.getCommandDescription());
				
			//no such command 
			} else {
				env.writeln("No \"" + command + "\" command in this environment." );
			}
			return ShellStatus.CONTINUE;
			
		//too many arguments
		} else {
			env.writeln("The command \"" + getCommandName() + "\" has too many arguments!");
			return ShellStatus.CONTINUE;
		}
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("provides help information for shell commands");
		return description;
	}
	
	/**
	 * Prints the description of a single command.
	 * @param env the environment of the shell this is executed
	 * @param commandName the name of the command
	 * @param description list of strings describing command behaviour
	 */
	private void printCommandDesc(Environment env, String commandName, List<String> description) {
		env.write(String.format("%10s", commandName));
		
		for(int i = 0; i < description.size(); i++) {
			if(i ==  0)  env.writeln(String.format("\t%10s", description.get(i)));
			else env.writeln(String.format("%10s\t%10s", ".", description.get(i)));
		}
		env.writeln("");
	}

}
