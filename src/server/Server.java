package server;

import java.util.HashMap;

/*
 * Server - Control the actual server
 * Handles all communication between devices and the program
 */
public class Server extends Http {

	private static PowerShell powerShell;
	private Config config;
	private static Token token;
	
	private String userConfig;
	private static HashMap<String, String> settings = new HashMap<String, String>();
	
	/**
	 * Build: load configs
	 */
	public Server(){
		powerShell = new PowerShell();
		token = Token.getInstance();
		setConfig();
	}
	
	/**
	 * Start server with configs
	 */
	public void start(){
		if(!serverStatusMessage.equals(ServerStatus.NO_SECRET)){
			try {
				super.lift(settings, config.getRoutes());
				System.out.println("Server 25 listening at "+config.getIp()+":"+config.getPort());
			} catch (Exception e) {
				System.out.println("Server 25 start: port in use");
				e.printStackTrace();
				serverStatusMessage = ServerStatus.PORT_IN_USE;
			}
		}
	}
	
	/**
	 * register a command coming from a request - allowed? default?
	 * @param command: String from req.body
	 */
	public static void requestCommand(String command, String commandActual){
		System.out.println("Command requested: "+command+commandActual);
		powerShell.command(command, commandActual, false);
	}
	
	/**
	 * Get server status
	 */
	public ServerStatus getStatus() {
		return serverStatusMessage;
	}
	//server ip from config
	public String getIp() {
		return config.getIp();
	}
	
	/**
	 * Generate settings for server
	 */
	private void generateSettings() {
		if(userConfig!=null){
			String[] settingsUser = userConfig.split(";");
			//User made settings saved in file
			for (String settingsUserSingle : settingsUser) {
				String[] settingsUserSingleKV = settingsUserSingle.split(":");
				settings.put(settingsUserSingleKV[0], settingsUserSingleKV[1]); // [0] = Key [1] = value
			}
			if(settings.get("secret")==null){
				serverStatusMessage= ServerStatus.NO_SECRET;
			}
			else serverStatusMessage= ServerStatus.OFFLINE;
//			else{
//				String s = settings.get("secret");
//				settings.put("token", token.generate(s));
//				
//				//test
//				token.read(settings.get("token"));
//			}
		}//userConfig != null
		else serverStatusMessage= ServerStatus.NO_SECRET;
		//settings from PS
		settings.put("ip",config.getIp());
		settings.put("host",config.getHost());
		settings.put("port", String.valueOf(config.getPort()));

	}

	/****************
	 * USER CONFIGS
	 */
	
	/*
	 * Load configs from file
	 * Set system properties to config: IP addr and hostname
	 */
	public void setConfig(){
		//instance & read file -> get settings
		if(config==null) config = Config.getInstance();
		config.init();
		refreshUserConfig();
		//read & set values from powershell
		config.setIp(powerShell.command("system", "ip", false));
		config.setHost(powerShell.command("system", "host", false));
		generateSettings();
	}
	
	private void refreshUserConfig() {
		userConfig = config.getUserConfig();
		
	}
	
	/**
	 * Get userConfigs
	 */
	public String getUserConfig(){
		return config.getUserConfig();
	}
	
	/**
	 * Set userConfigs
	 */
	public void setUserConfig(String conf){
		//Save to file
		config.setUserConfig(conf);
		//Refresh own instance 
		refresh();
		
	}
	
	/**
	 * Refresh & restart the server
	 * 1. get new user config
	 * 2. save it for the server
	 * 3. lift
	 */
	private void refresh(){
		super.stop();
		setConfig();
		start(); 
	}
	public void stop() {
		//System.out.println("server stopped");
		if(serverStatusMessage.equals(ServerStatus.ONLINE)){
			serverStatusMessage = ServerStatus.OFFLINE;
			//System.out.println("server stop!"+serverStatusMessage);
			super.stop();
		}
	}
	public void startUI() {
	
		if(serverStatusMessage.equals(ServerStatus.OFFLINE)){
			serverStatusMessage = ServerStatus.ONLINE;
			//System.out.println("server START!"+serverStatusMessage);
			this.start();
		}
	}
	
	/**
	 *  Tokens
	*/
	
	/** 
	 * Get token
	 * @param s string to use to generate tokens 
	 * @return token 
	 */
	public static String requestToken(String s) {
		String sa =  token.generate(settings.get("secret"), s);
		return sa;
	}
	
	public static boolean validateToken(String s){
		return token.read(s,settings.get("secret"));
	}
	
	/**
	 * Get token 
	 * @param s
	 * @return
	 */
	public static String getToken(String s){
		return requestToken(s);
	}
	
	public static String getHostString(){
		return settings.get("host");
	}
	
}
