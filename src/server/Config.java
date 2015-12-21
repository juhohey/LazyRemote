package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import main.FileOperator;
/**
 * String holder
 * Save these to a file later perhaps
 */
public class Config {

	//Singleton instance
	final static Config configInstance = new Config();
	
	private FileOperator file;
	
	//Server settings 
	private String ip; 
	private String host;
	private String[] routes = {"/command", "/connection",  "/"};
	private int port = 8081;
	
	//file names 
	String value = System.getenv("APPDATA");
	private String[] files = {value+"/LazyRemote/config","devices"};
	private static String userConfig;
		
	//singleton 
	private Config(){
		
	}
	//singleton 
	public static Config getInstance(){
		return configInstance;
		
	}
	
	//Init
	public void init(){
		//file instance
		file = new FileOperator();
		
		//Read config
		try {
			userConfig = file.readFile(files[0]);
			System.out.println("Config 32 read: " + userConfig);
		} catch (IOException e) {
			userConfig = null;
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

	//setters
	public void setIp(String ip) {
		this.ip = ip;		
	}
	public void setHost(String host) {
		this.host = host;		
	}
	public void setUserConfig(String conf) {
		try {
			file.writeFile(files[0], conf);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//getters
	public String getIp() {
		return this.ip;	
	}
	public String getHost() {
		return this.host;
	}
	public String[] getRoutes() {
		return this.routes;	
	}
	public int getPort() {
		return this.port;
	}
	public String getUserConfig(){
		return userConfig;
	}
}
