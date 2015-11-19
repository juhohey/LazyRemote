package server;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/*
 * RequestHandler - Handles a single request
 * @param: String to 
 * TODO add token...?
 */
public class RequestHandler implements HttpHandler {
	
	private String res;
	
	public RequestHandler(String res){
		this.res = res;
	}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		//System.out.println(exchange.getRequestMethod());
		Server.requestCommand(exchange.getRequestMethod());
		exchange.sendResponseHeaders(200, res.length());
		OutputStream os = exchange.getResponseBody();
	    os.write(res.getBytes());
	    os.close();
	}
	

}
