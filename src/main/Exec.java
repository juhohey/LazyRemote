package main;
import server.Server;
import view.Model;
import view.ViewWindow;

public class Exec {

	public static void main(String[] args) {
		
		//Server instance
		Server server = new Server();
		//Model instance, pass in invoked server
		Model model = new Model(server);
		
		ViewWindow viewWindow = new ViewWindow();
		viewWindow.build(model);
		
		//start application
		server.start();
		viewWindow.render();
		
	}
}
