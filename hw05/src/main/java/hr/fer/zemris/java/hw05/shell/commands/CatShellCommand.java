package hr.fer.zemris.java.hw05.shell.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;
/**
 * Command which displays the content of a file in text format, writing it to the output stream of the shell.
 * Requires one argument - path of the displayed file.
 * @author Fani
 *
 */
public class CatShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		//no arguments
		if(arguments.isEmpty()) {
			env.writeln("The command \"" + getCommandName() + "\" needs to have at least one argument!");
			return ShellStatus.CONTINUE;
		} 
		
		String[] args = arguments.split(" ");
		
		//too many arguments
		if(args.length > 2) {
			env.writeln("The command \"" + getCommandName() + "\" accepts only 1 or 2 arguments!");
			return ShellStatus.CONTINUE;
		}
		
		Charset c;
		//default charset
		if(args.length == 1) {
			c = Charset.defaultCharset();
		//given charset	
		} else {
			if(Charset.isSupported(args[1])) {
				c = Charset.forName(args[1]);
			} else {
				env.writeln("The charset \"" + args[1] + "\" is not supported!");
				return ShellStatus.CONTINUE;
			}
		}
		
		File file = Paths.get(args[0]).toFile();
		
		if(!file.exists()) {
			env.writeln("The file \"" + args[0] + "\" does not exist!");
			return ShellStatus.CONTINUE;
		}
		
		if(file.canRead()) {
			try (BufferedReader br = new BufferedReader(new FileReader(file, c))) {
				   String line;
				   while ((line = br.readLine()) != null) {
				       env.writeln(line);
				   }
				   
			} catch (IOException e) {
				env.writeln("Could not read the file \"" + args[0] + "\"!");
				return ShellStatus.CONTINUE;
			}
			
		} else {
			env.writeln("The file \"" + args[0] + "\" is not readable!");
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("displays the content of a file in text format, writing it to the output stream of the shell");
		return description;
	}
}
