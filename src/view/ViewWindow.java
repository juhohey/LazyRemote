package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ViewWindow extends Application {
	
	private Model model;
	
	private final ViewHeader header = new ViewHeader(this);
	private final ViewBody body = new ViewBody(this);
	
	private Stage primaryStage;
	private Scene configScene;
	private Scene homeScene;

	public void build(Model model){
		this.model = model;
		System.out.println("ref of model saved" + this.model);
		//body = new ViewBody();
	}


	@Override
	public void start(Stage stageArg) throws Exception {
		primaryStage = stageArg;
		//setPrimaryStage(stageArg);
			
		 //build config
		configScene = buildConfig();

		//build home
		homeScene = buildHome();
		
		primaryStage.setScene(configScene);
		primaryStage.show();
	}
	
	
	
	/*
	 * Build the home view
	 */
	private Scene buildHome() {
		// Layout init
		GridPane grid = new GridPane();	
		
		//Add header
		
		grid = header.build(grid);
		
		//Add body
		grid = body.buildHome(grid);
		
		//build scene
		Scene scene = new Scene(grid, 300, 250);
		//add style
		scene.getStylesheets().add("layout.css");
		
		return scene;
	}

	/*
	 * Build the config view
	 */
	private Scene buildConfig() {
		// Layout init
		GridPane grid = new GridPane();	
		
		//Add header
		grid = header.build(grid);
		
		//Add body
		grid = body.buildConfig(grid);
		
		//build scene
		Scene scene = new Scene(grid, 300, 250);
		//add style
		scene.getStylesheets().add("layout.css");
		
		return scene;
		
	}

	/*
	 * Render function
	 */
	public void render(String config){
		body.setConfigs(config);
		launch(); //DEFAULT JAVAFX METHOD
	}
	
	/*
	 * Setters
	 */
	public Stage getStage() {
		return primaryStage;
	}
	public Scene getConfig(){
		return buildConfig();
	}
	public Scene getHome(){
		return buildHome();
	}
	public void saveConfig(String configStrings) {
		System.out.println(this.model);
		try{
			this.model.saveConfig("configStrings");
		}
		catch(NullPointerException wat){
			wat.printStackTrace();
			System.out.println(wat.getMessage());
		}
		
	}
//	public void switchScene(boolean showHome){
//	if(showHome){
//		
//		//getPrimaryStage().setScene(homeScene);
//	}
//	else if(showHome){
//		
//		//getPrimaryStage().setScene(configScene);
//	}
//	 //getPrimaryStage().show();
//	System.out.println("Showing scene"+configScene );
//	
//}



	
}
