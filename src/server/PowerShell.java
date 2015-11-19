package server;

import java.io.IOException;

/*
 * PowerShell - Interface for the server to execute commands
 */
public class PowerShell {
	
	//command lists
	private DefaultCommands defaultC;
	//ps execution child process
	private ChildProcess child;
	
	public PowerShell(){
		child = new ChildProcess();
		defaultC = new DefaultCommands();
	}
	
	/*
	 * Execute command - if custom the first param is the command
	 * @param commandType type of command, volume|power
	 * @param commandActual, actual command|"down","off" etc.
	 * @param custom: custom command or default command
	 * @return stdOut or just a dummy
	 */
	public String command(String commandType, String commandActual, boolean custom){
		//Is this a custom command? else get from default values
		String commandSingle = custom ? commandType : defaultC.commands.get(commandType).get(commandActual);
		String stdout;
		try {
			stdout = child.sendCommand(commandSingle);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			stdout ="stderr";
		}
		return stdout;
		
	}
	
	
}
