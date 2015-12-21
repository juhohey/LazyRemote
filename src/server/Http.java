package server;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

import com.sun.net.httpserver.HttpServer;


public class Http {

	private HttpServer httpServer;
	public enum ServerStatus{
		ONLINE, OFFLINE, NO_SECRET, PORT_IN_USE
	}
	
	ServerStatus serverStatusMessage = ServerStatus.OFFLINE;

	/**
	 * Create server
	 * @param: port - port to listen to
	 */
	public void create(int port, String ip) throws Exception{
				
			httpServer = HttpServer.create(new InetSocketAddress(ip,port), 0);
			httpServer.setExecutor(null); // creates a default executor
			httpServer.start();
			serverStatusMessage = ServerStatus.ONLINE;
	}
	
	/**
	 * Register routes for server
	 * @param: Routes, list of routes to register
	 * 		0 / = help
	 * 		1 /connect = get token
	 * 		2 /command = send command
	 */
	private void registerRoutes(String[] routes){ 
		for (int i = 0; i < routes.length; i++) {
			//System.out.println(routes[i]);
			RequestHandler handle = new RequestHandler(routes[i]);
			httpServer.createContext(routes[i], handle);
		}
	}
	
	/**
	 * Lift the actual server
	 * @param: Settings hashmap
	 * @param: Routes, list of routes to register
	 */
	public void lift(HashMap<String, String> settings, String[] routes) throws Exception{
		if(serverStatusMessage == ServerStatus.ONLINE) stop();
		this.create(Integer.parseInt(settings.get("port")),settings.get("ip"));
		this.registerRoutes(routes);
	}
	public void stop() {
		if(httpServer!=null) httpServer.stop(1);
		
	}
}


/*
*DUMP
*/
////System.out.println(InetAddress.getByName(hostName));
////socketAddress =  new InetSocketAddress(InetAddress.getByName(hostName), 8080);
////httpServer = HttpServer.create(socketAddress, 1);
//ServerSocket serverSocket = new ServerSocket(8080);
//Socket clientSocket = serverSocket.accept();
//clientSocket.
////  serverSocket.
//PrintWriter out =
//new PrintWriter(clientSocket.getOutputStream(), true);
//BufferedReader in = new BufferedReader(
//new InputStreamReader(clientSocket.getInputStream()));
//
//while (out != null) {
//System.out.println(out);
//}
//while (in != null) {
//   System.out.println(in);
//}
