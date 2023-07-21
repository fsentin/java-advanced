package hr.fer.zemris.java.hw05.shell.commands;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;
/**
 * Command which copies one file to another location.
 * Requires two arguments - source file name and destination file name.
 * @author Fani
 *
 */
public class CopyShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.isEmpty()) {
			env.writeln("The command \"" + getCommandName() + "\" needs to have 2 arguments!");
			return ShellStatus.CONTINUE;
		} 
		
		String[] args = arguments.split(" ");
		
		if(args.length > 2) {
			env.writeln("The command \"" + getCommandName() + "\" can't have more than 2 arguments!");
			return ShellStatus.CONTINUE;
		}
		
		File originalFile = Paths.get(args[0]).toFile();
		File copiedFile = Paths.get(args[1]).toFile();
		
		if(!originalFile.exists()) {
			env.writeln("The file \"" + args[0] + "\" does not exist!");
			return ShellStatus.CONTINUE;
		}
		
		if(!originalFile.isFile()) {
			env.writeln("Only files can be copied!");
			return ShellStatus.CONTINUE;
		}
		
		if(copiedFile.exists()) {
			env.write("WARNING: The destination file already exists. Do you want to overwrite it?\n(YES/NO) ");
			String response = env.readLine();
			if(response.equalsIgnoreCase("NO")) {
				env.writeln("Copying cancelled!");
				return ShellStatus.CONTINUE;
			}
		}
		
		if(copiedFile.isDirectory()) {
			copiedFile = Paths.get(args[1] + "/" + originalFile.getName()).toFile();
		}
		
		try (BufferedReader br = new BufferedReader(new FileReader(originalFile));
				BufferedWriter bw = new BufferedWriter(new FileWriter(copiedFile))) {
			String line = null;
			while((line = br.readLine()) != null) {
				bw.write(line);
				bw.newLine();
			}
			env.writeln("Sucessfully copied file \"" + originalFile + "\" to \"" + copiedFile + "\"!");
			return ShellStatus.CONTINUE;
		} catch (IOException e) {
			env.writeln("Could not copy the \"" + args[0] + "\" file!");
			return ShellStatus.CONTINUE;
		}
		
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("copies one file to another location");
		return description;
	}

}
