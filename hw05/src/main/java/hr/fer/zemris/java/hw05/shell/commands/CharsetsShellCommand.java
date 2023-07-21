package hr.fer.zemris.java.hw05.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

public class CharsetsShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(!arguments.isEmpty()) {
			env.writeln("The command \"" + getCommandName() + "\" shouldn't have any arguments!");
			return ShellStatus.CONTINUE;
		}
		
		SortedMap<String, Charset> map = Charset.availableCharsets();
		
		for(Entry<String, Charset> entry : map.entrySet()){
			env.writeln(String.format(entry.getKey() + "%25s", entry.getValue()));
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("lists names of supported charsets");
		return description;
	}

}
