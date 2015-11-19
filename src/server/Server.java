package server;

/*
 * Server - Control the actual server
 * Handles all communication between devices and the program
 */
public class Server {

	private PowerShell powerShell;
	private Config config;
	private Http http;
	
	/*
	 * Build: load configs
	 */
	public Server(){
		powerShell = new PowerShell();
		http = new Http();
		setConfig();
		
	}
	/*
	 * Start server with configs
	 */
	public void start(){
		try {
			http.lift(config.getPort(), config.getRoutes());
			System.out.println("server listening at port "+config.getPort());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("port in use");
		}
	}
	
	/*
	 * register a command coming from a request - allowed? default?
	 * @param command: String from req.body
	 */
	public static void requestCommand(String command){
		System.out.println("Command requested: "+command);
	}
	
	
	
	/****************
	 * USER CONFIGS
	 */
	/*
	 * Load configs from file
	 * Set system properties to config: IP addr and hostname
	 */
	public void setConfig(){
		config = new Config();
		config.setIp(powerShell.command("system", "ip", false));
		config.setHost(powerShell.command("system", "host", false));
	}
	/*
	 * Get userConfigs
	 */
	public String getUserConfig(){
		return config.getUserConfig();
	}
	/*
	 * Set userConfigs
	 */
	public void setUserConfig(String conf){
		config.setUserConfig(conf);
	}
	
//	/*
//	 * reload server with new configs
//	 * TODO ...
//	 */
//	public void reload(){
//		setConfig();
//	}
	


}
