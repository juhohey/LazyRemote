package server;

import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;


public class Http {

	private HttpServer httpServer;
	private boolean serverRunning;
	public Http(){
		
	}

	/*
	 * Create server
	 * @param: port - port to listen to
	 */
	public void create(int port) throws Exception{
			httpServer = HttpServer.create(new InetSocketAddress(port), 0);
			httpServer.setExecutor(null); // creates a default executor
			httpServer.start();
			serverRunning = true;
	}
	/*
	 * Register routes for server
	 * @param: Routes, list of routes to register
	 */
	private void registerRoutes(String[] routes){
		for (String route : routes) {
			RequestHandler handle = new RequestHandler(route);
			httpServer.createContext(route, handle);
		}
	}
	/*
	 * Lift the actual server
	 * @param: port, port to listen to
	 * @param: Routes, list of routes to register
	 */
	public void lift(int port, String[] routes) throws Exception{
		if(serverRunning) httpServer.stop(port);
		this.create(port);
		this.registerRoutes(routes);
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
