package server;

import java.io.FileNotFoundException;
import java.io.IOException;

import main.FileOperator;
/*
 * String holder
 * Save these to a file later perhaps
 */
public class Config {

	private FileOperator file;
	private String ip; 
	private String host;
	private String[] routes = { "/connect",  "/"};
	private int port = 8001;
	
	private String[] files = {"config","devices"};
	private String userConfig;
	
	
	public Config(){
		//Init
		file = new FileOperator();
		try {
			userConfig = file.readFile(files[0]);
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
