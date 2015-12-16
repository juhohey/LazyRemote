package view;

import server.Http.ServerStatus;
import server.Server;

/*
 * Model - holds data for the view
 * Is Singleton
 */
public class Model {

	//private ViewWindow viewWindow;
	private String userConfig;
	private static Server server;
	
	/*
	 * Need to pass a Sever instance to invoke this
	 * @param Server - an instance of a server (single!)
	 */
	public Model(Server serverArg){
		//viewWindow = new ViewWindow();
		server = serverArg;
	//	this.userConfig = server.getUserConfig();		
	}
	
	
	
	/*
	 * Invoke the view
	 */
	public void renderView(){
		//viewWindow.render(userConfig);
	}
	
	/*
	 * Save new configs
	 */
	public static void saveConfig(String config){
		System.out.println("Saving config: "+ config);
		server.setUserConfig(config);
	}



	public static ServerStatus getServerStatus() {
		return server.getStatus();
	}
	public static String getIp() {
		return server.getIp();
	}



	public static void stopServer() {
		server.stop();
	}

	public static void startServer() {
		server.startUI();
		
	}


//
//	public void passReference() {
//		viewWindow.build(this);
//		
//	}
}
