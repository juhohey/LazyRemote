package server;

import java.util.HashMap;
/*
 * Default commands
 * Holds the string values of default commands
 * TODO: put commands into file
 */
public class DefaultCommands {
	//command lists
	public HashMap<String, HashMap<String, String>> commands = new HashMap<String, HashMap<String, String>>();
	
	public DefaultCommands(){		
		//Need 3 instances...?
		HashMap<String, String> volume = new HashMap<String, String>();
		HashMap<String, String> power = new HashMap<String, String>();
		HashMap<String, String> system = new HashMap<String, String>();
		//volume
		volume.put("off", "$obj = new-object -com wscript.shell;$obj.SendKeys([char]173)");
		volume.put("down", "$obj = new-object -com wscript.shell;$obj.SendKeys([char]174)");
		volume.put("up", "$obj = new-object -com wscript.shell;$obj.SendKeys([char]175)");
		commands.put("volume", volume);

		//power
		power.put("off", "Stop-Computer");
		commands.put("power", power);
		
		//system
		system.put("ip","gwmi Win32_NetworkAdapterConfiguration |Where { $_.IPAddress } | Select -Expand IPAddress | Where { $_ -like '192.168.*' } | select -First 1");
		system.put("host","hostname");
		
		commands.put("system", system);
		//System.out.println(commands.ge);
		
	}
}
