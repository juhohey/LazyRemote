package server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Exchanger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.JSONObject;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * RequestHandler - Handles a single request
 * @param: String to 
 * TODO add token...?
 */
public class RequestHandler implements HttpHandler {
	
	private String res;
	
	private String commandString = "name=\"command\"";
	private String tokenString = "name=\"token\"";
	
	private JsonFact jsonFact = new JsonFact();
	
	public RequestHandler(String res){
		this.res = res;
	}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		//System.out.println(exchange.getRequestMethod());
		int statusCode = 200;
		
		String resString = "Hello Server";
		
		// Command route
		if(res.equals("/command")){
			InputStream in = exchange.getRequestBody();
			String qry;
			
			try {
			    ByteArrayOutputStream out = new ByteArrayOutputStream();
			    byte buf[] = new byte[4096];
			    for (int n = in.read(buf); n > 0; n = in.read(buf)) {
			        out.write(buf, 0, n);
			    }
			    qry = new String(out.toByteArray(), "UTF8");
			} finally {
			    in.close();
			}
			
			// parse the query
			//System.out.println(qry);
			JSONObject data = new JSONObject(qry);
			JSONObject obj = data.getJSONObject("data");
			//System.out.println(obj);
			handleReq(obj);
			//statusCode = logger(qry);
			resString = "token";
		}
		
		// Connect route
		else if(res.equals("/connection")){
			statusCode = 200;
			String token = Server.getToken("s");
			String[] tokenKeyVal = {"token:"+token,"device:"+Server.getHostString()};
		
			resString =  jsonFact.getObject(tokenKeyVal);
			System.out.println("RequestHandler 36 Got token "+resString );
		}
		
		//Else just ok
		else{
			statusCode = 200;
		}
		
		//Response & close
		Headers headers = exchange.getResponseHeaders();
		headers.add("Content-Type", "application/json");
		
		exchange.sendResponseHeaders(statusCode, resString.length());

		OutputStream os = exchange.getResponseBody();
		os.write(resString.getBytes());
	    os.close();
	}

	private void handleReq(JSONObject obj) {
		System.out.println(obj.getString("token"));
		if(Server.validateToken(obj.getString("token"))){
			Server.requestCommand(obj.getString("type"),obj.getString("command"));
		}
		
	}

	private int logger(String body) {
		
		//Vars for this
		//request accepted == token present & valid
		boolean isToken = false;
		
		String[] commands = null;
		//System.out.println(body);
		String[] arr = body.split("------");
		//String[] line = arr[2].split("\n");
		
		//read individual params
		for (int i = 0; i < arr.length; i++) {
			String[] line = arr[i].split("\n");
			
			//0: source
			//1: whitespace
			//2: content & param
			//3: data
			for (String s : line) {
				if(s.length()>1){
								
					//TOKEN
					if(!isToken) if(matchToken(s, line[3]) ) isToken = true;
					
					//COMMAND
					Pattern pattern = Pattern.compile(commandString);
					Matcher matcher = pattern.matcher(s);
					//System.out.println(matcher);
					if(matcher.find()) {
						//Split to comnmand type : actual 
						commands = line[3].split(":");
			            //replace whitespace asd java 
						commands[1] = commands[1].replace("\\s", "").replace("\r", "").replace("\n", "");
			           
			        }
				}
			} 
		}
		System.out.println("isToken"+isToken);
		if (isToken){
			 Server.requestCommand(commands[0],commands[1]);
			 return 200;
		}
		else return 403;
		
		
		
	}//logger
	
	
	private boolean matchToken(String s, String line) {
		
		Pattern patternToken = Pattern.compile(tokenString);
		Matcher matcherToken = patternToken.matcher(s);
		if(matcherToken.find()) {
			 //replace whitespace
			System.out.println("RequestHandler read"+s+" "+line);
			String tokenS = line.replaceAll("\\r|\\n", "").replaceAll("\\s", "");;
			return Server.validateToken(tokenS);
			
		}
		return false;
	}

}
