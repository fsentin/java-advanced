package hr.fer.zemris.java.hw05.shell.commands;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;
/**
 * Command which displays the structure of the directory system.
 * Requires one argument - path of the parent directory from which the listing starts.
 * @author Fani
 *
 */
public class TreeShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.isEmpty()) {
			env.writeln("The command \"" + getCommandName() + "\" needs to have one argument!");
			return ShellStatus.CONTINUE;
		} 
		
		String[] args = arguments.split(" ");
		
		if(args.length > 1) {
			env.writeln("The command \"" + getCommandName() + "\" accepts only 1 argument!");
			return ShellStatus.CONTINUE;
		}
		
		File file = Paths.get(arguments).toFile();
		
		if(!file.exists()) {
			env.writeln("The file \"" + args[0] + "\" does not exist!");
			return ShellStatus.CONTINUE;
		}
		
		if(!file.isDirectory()) {
			env.writeln("The file \"" + arguments + "\" is not a directory!");
			return ShellStatus.CONTINUE;
		}
		
		printDirTree(env, file, 0);
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("displays the structure of the directory system");
		return description;
	}
	
	/**
	 * Prints the structure of a directory system recursively.
	 * @param env the environment of the shell in which the command is executed
	 * @param file the file inside the directory structure
	 * @param level the depth of the file in comparison with the furthermost parent directory of this tree
	 */
	private static void printDirTree(Environment env, File file, int level) {
		env.writeln(String.format("%s%s", "  ".repeat(level), file.getName()));
		if(file.isDirectory()) {
			File[] children = file.listFiles();
			if(children == null) 
				return;
			for(File d : children) {
				printDirTree(env, d, ++level);
				level--;
			}
		}
	}

}
