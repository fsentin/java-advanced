package hr.fer.zemris.java.hw05.shell;


import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw05.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.HelpCommand;
import hr.fer.zemris.java.hw05.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.SymbolCommand;
import hr.fer.zemris.java.hw05.shell.commands.TreeShellCommand;

/**
 * Implementation of the Environment interface used in MyShell.
 * @author Fani
 *
 */
public class EnvironmentImpl implements Environment {
	/**
	 * Scanner for scanning inputstream. Unfortunately not closed (garbage collector deals with it).
	 */
	Scanner sc;
	
	/**
	 * Symbol for PROMPT of this environment.
	 */
	private Character PROMPT = '>';
	
	/**
	 *  Symbol for MORELINES of this environment.
	 */
	private Character MORELINES = '\\';
	
	/**
	 *  Symbol for MULTILINE of this environment.
	 */
	private Character MULTILINE = '|';
	
	/**
	 * Supported commands of this environment.
	 */
	private SortedMap<String, ShellCommand> commands;
	
	
	/**
	 * Creates a new Environment implementation.
	 */
	public EnvironmentImpl() {
		this.sc = new Scanner(System.in);
		commands = new TreeMap<String, ShellCommand>();
		commands.put("exit", new ExitShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("help", new HelpCommand());
		commands.put("symbol", new SymbolCommand());
	}
	
	@Override
	public String readLine() throws ShellIOException {
		String line = "";
		try {
			if(sc.hasNextLine()) {
				line = sc.nextLine().trim();

				while(line.endsWith(MORELINES.toString())) {
					write(MULTILINE.toString());
					line = remove(MORELINES, line);
					line += sc.nextLine().trim();
				}
			}
		} catch (Exception e) {
			throw new ShellIOException("An error occured while reading the input.");
		}
		
		return line;
	}

	@Override
	public void write(String text) throws ShellIOException {
		try {
			System.out.print(text);
		} catch (Exception e) {
			throw new ShellIOException();
		}
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		try {
			System.out.println(text);
		} catch (Exception e) {
			throw new ShellIOException();
		}
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return commands;
	}

	@Override
	public Character getMultilineSymbol() {
		return MULTILINE;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		MULTILINE = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return PROMPT;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		PROMPT = symbol;
		
	}

	@Override
	public Character getMorelinesSymbol() {
		return MORELINES;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		MORELINES = symbol;
	}
	
	/**
	 * Removes one (first) instance of a given character inside a given string and returns the result.
	 * @param c character which is removed from the string
	 * @param str string from which the character is removed
	 * @return string with removed specified character c
	 */
	private String remove(Character c, String str) {
		int i = str.indexOf(c.toString());
		return str.substring(0, i) + str.substring(i + 1);
	}

}
