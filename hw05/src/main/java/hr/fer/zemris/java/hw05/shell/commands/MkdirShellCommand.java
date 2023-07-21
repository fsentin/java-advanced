package hr.fer.zemris.java.hw05.shell.commands;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;
/**
 * Command which creates a new directory.
 * Requires one argument - name of the directory.
 * @author Fani
 *
 */
public class MkdirShellCommand implements ShellCommand {

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
		
		if(file.exists()) {
			env.writeln("The directory \"" + args[0] + "\" already exists!");
			return ShellStatus.CONTINUE;
		}
		
		boolean created = file.mkdir();
		env.writeln(String.format("Directory %s %s", file.getName(), created ? "succesfuly created." : "could not be created."));
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("creates a directory");
		return description;
	}

}
