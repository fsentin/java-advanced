package hr.fer.zemris.java.hw05.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;
/**
 * Command which displays or changes the symbols of this environment.
 * Requires one or two arguments:
 * 	If given one argument - displays the wanted symbol for this environment.
 *  If given two arguments - replaces the wanted symbol to the newly given one for this environment.
 * @author Fani
 *
 */
public class SymbolCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.isEmpty()) {
			env.writeln("Improper " + getCommandName() + " command.");
			return ShellStatus.CONTINUE;
		}
		
		String[] args = arguments.split(" ");

		if(args.length > 2) {
			env.writeln("The command \"" + getCommandName() + "\" has too many arguments!");
			return ShellStatus.CONTINUE;
		}
			
		Character sym = null;
		switch(args[0]) {
		case "PROMPT": sym = env.getPromptSymbol();
			break;
		case "MORELINES": sym = env.getMorelinesSymbol();
			break;
		case "MULTILINE": sym = env.getMultilineSymbol();
			break;
		default: break;
		}
			
		if(sym == null) {
			env.writeln("There is no such symbol in this environment!");
			return ShellStatus.CONTINUE;
		}
			
		if(args.length == 1) {
			env.writeln(String.format("Symbol for %s is '%c'", args[0], sym));
			return ShellStatus.CONTINUE;
		}
		
		Character newSym = args[1].charAt(0);
		switch(args[0]) {
		case "PROMPT": env.setPromptSymbol(newSym);
			break;
		case "MORELINES": env.setMorelinesSymbol(newSym);
			break;
		case "MULTILINE": env.setMultilineSymbol(newSym);
			break;
		default: break;
		}
		
		env.writeln(String.format("Symbol for %s changed from '%c' to '%c'", args[0], sym, newSym));
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("displays or changes the symbols of this environment");
		return description;
	}

}
