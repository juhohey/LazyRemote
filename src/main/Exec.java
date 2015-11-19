package main;
import server.Server;
import view.Model;

public class Exec {

	public static void main(String[] args) {
		//Server instance
		Server server = new Server();
		//Model instance, pass in invoked server
		Model model = new Model(server);
		server.start();
		model.renderView();
	}
}
