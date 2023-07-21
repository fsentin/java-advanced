package hr.fer.zemris.java.hw05.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellIOException;
import hr.fer.zemris.java.hw05.shell.ShellStatus;
/**
 * Command which displays a list of files and subdirectories in a directory.
 * Requires one argument - path of the directory.
 * @author Fani
 *
 */
public class LsShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		//no arguments
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String[] files = file.list();
		for (int i = 0; i < files.length; i++) {
			Path subPath = Paths.get(arguments, files[i]);
			File subFile = subPath.toFile();
			
			if(subFile.isDirectory()) {
				env.write("d");
			} else {
				env.write("-");
			}
			
			if(subFile.canRead()) {
				env.write("r");
			} else {
				env.write("-");
			}
			
			if(subFile.canWrite()) {
				env.write("w");
			} else {
				env.write("-");
			}
			
			if(subFile.canExecute()) {
				env.write("x");
			} else {
				env.write("-");
			}
			
			env.write(String.format("%10d ", subFile.length()));
			
			try {
				BasicFileAttributeView faView = Files.getFileAttributeView(subPath, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
				BasicFileAttributes attributes = faView.readAttributes();
				FileTime fileTime = attributes.creationTime();
				String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
				env.write(formattedDateTime);
			} catch (ShellIOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			env.writeln(" " + subFile.getName());
			
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("displays a list of files and subdirectories in a directory");
		return description;
	}

}
