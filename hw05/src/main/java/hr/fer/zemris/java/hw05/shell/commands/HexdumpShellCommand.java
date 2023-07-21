package hr.fer.zemris.java.hw05.shell.commands;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;
/**
 * Command which Ddisplays the contents of files in hexadecimal and ASCII format.
 * Requires one argument - path of the displayed file.
 * @author Fani
 *
 */
public class HexdumpShellCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.isEmpty()) {
			env.writeln("The command \"" + getCommandName() + "\" needs to have 1 argument!");
			return ShellStatus.CONTINUE;
		} 
		
		if(arguments.split(" ").length > 1) {
			env.writeln("The command \"" + getCommandName() + "\" can't have more than 1 arguments!");
			return ShellStatus.CONTINUE;
		}
		
		File file = Paths.get(arguments).toFile();
		
		try(FileInputStream fin = new FileInputStream(file)){
			
			byte[] buffer = new byte[16];
			int numOfBytes = 0;
			int preamble = 0;
			while((numOfBytes = fin.read(buffer)) != -1) {
				env.write(String.format("%08X: ", preamble));
				for (int i = 0; i < buffer.length; i++) {
					if(i == 8) env.write("|  ");
					if(i < numOfBytes) {
						env.write(String.format("%02X ", buffer[i]));
					} else {
						env.write("   ");
					}
				}
				env.write("|  ");
				for (int i = 0; i < numOfBytes; i++) {
					char c = (char) (buffer[i] & 0xFF);
					c = ((int)c >= 32 && (int) c <= 127) ? c : '.';
					env.write(Character.toString(c));
				}
				env.writeln("");
				preamble += numOfBytes;
			}
			
			
		} catch (Exception e) {
			env.writeln("Failed hexdump!");
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("displays the contents of files in hexadecimal and ASCII format");
		description.add("requires one argument - path of the displayed file");
		return description;
	}

}
