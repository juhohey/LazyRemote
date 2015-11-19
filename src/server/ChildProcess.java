package server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * ChildProcess - actual PowerShell execution
 */
public class ChildProcess {

	
	/*
	 * Send command to PowerShell
	 * @param: command to be executed
	 */
	public String sendCommand(String commandArg) throws IOException{
		//form commandString
		String command = "powershell.exe " + commandArg;
			 
		String line;
		String log ="";
		Process powerShellProcess = Runtime.getRuntime().exec(command);
		powerShellProcess.getOutputStream().close();
		  
		 
		//System.out.println("Standard Output:");
		  
		BufferedReader stdout = new BufferedReader(new InputStreamReader(
				    powerShellProcess.getInputStream()));
		while ((line = stdout.readLine()) != null) {
			log+=line;
			//System.out.println(line);
		}
		stdout.close();
		//System.out.println("Standard Error:");
		BufferedReader stderr = new BufferedReader(new InputStreamReader(
		  powerShellProcess.getErrorStream()));
		while ((line = stderr.readLine()) != null) {
		 //System.out.println(line);
		}
		stderr.close();
		//System.out.println("Done");
		return log;
	}
}
