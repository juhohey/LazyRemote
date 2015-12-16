package server;

import java.util.HashMap;

public class JsonFact {

	public JsonFact(){
		
	}
	
	/**
	 * 
	 * @param obj String array with key:value - pairs
	 * @return String in JSON form
	 */
	public String getObject(String[] obj){
		
		String json = "{";
		for (int i = 0; i < obj.length; i++) {
			//split the value to key:value
			String[] keyValue = obj[i].split(":");
			keyValue[1] =  keyValue[1].replace("\\s", "").replace("\r", "").replace("\n", "");
			
			//append to our json object
			json += ('"'+keyValue[0]+'"'+ ":" + '"'+keyValue[1]+ '"');

			if(i+1 <obj.length) json += ",";
		}
		json += "}";
		
		return json;
	}
}
