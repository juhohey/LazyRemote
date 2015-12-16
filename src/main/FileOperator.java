package main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*
 * File operations, write/read
 * See config for usage
 */
public class FileOperator {

	public String readFile(String fileName) throws IOException, FileNotFoundException{
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		StringBuilder builder = new StringBuilder();
		String line = reader.readLine();
	    while (line != null) {
	    	builder.append(line);
	    	builder.append(System.lineSeparator());
	    	line =reader.readLine();
	    }
	    reader.close();
	    return builder.toString();
		   
	}
	

	public void writeFile(String fileName, String data) throws IOException, FileNotFoundException{
		File dir = new File("lazyfiles");
		dir.mkdirs();
		BufferedWriter bf = new BufferedWriter(new FileWriter(fileName));
		bf.write(data);
		bf.close();
	}
}
