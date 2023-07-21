package hr.fer.zemris.java.hw05.shell;

/**
 * A simple shell implementation.
 * @author Fani
 *
 */
public class MyShell {
	/**
	 * Current status of the shell.
	 */
	private static ShellStatus status = ShellStatus.CONTINUE;
	
	/**
	 * Runs the shell and all its functionalities.
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to MyShell v 1.0");
		
		Environment env = new EnvironmentImpl();
		
		do {
			try {
				env.write(env.getPromptSymbol().toString() + " ");
				String l = env.readLine();
				String commandName = l.trim().split(" ")[0];
				String arguments = l.substring(l.indexOf(" ") + 1);
				
				if(commandName.equals(arguments) && !commandName.equals("help")) 
					arguments = "";
				
				ShellCommand command = env.commands().get(commandName);
				if(command ==  null) { 
					env.writeln("This command is not supported!");
					continue;
				}
				status = command.executeCommand(env, arguments);
				
			} catch (ShellIOException e) {
				System.exit(1);
			} 
			
		} while(status != ShellStatus.TERMINATE);
		
	}
	
}
