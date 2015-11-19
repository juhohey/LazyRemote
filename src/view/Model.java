package view;

import server.Server;

/*
 * Model - holds data for the view
 * Is Singleton
 */
public class Model {

	private ViewWindow viewWindow;
	private String userConfig;
	private Server server;
	
	/*
	 * Need to pass a Sever instance to invoke this
	 * @param Server - an instance of a server (single!)
	 */
	public Model(Server serverArg){
		viewWindow = new ViewWindow();
		viewWindow.build(this);
		server = serverArg;
		this.userConfig = server.getUserConfig();
	}
	
	
	
	/*
	 * Invoke the view
	 */
	public void renderView(){
		viewWindow.render(userConfig);
	}
	
	/*
	 * Save new configs
	 */
	public void saveConfig(String config){
		System.out.println("Saving config: "+ config);
		server.setUserConfig(config);
	}
}
