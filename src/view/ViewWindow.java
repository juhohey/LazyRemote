package view;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ViewWindow extends Application {
	
	//private static Model model;
	
	private final ViewHeader header = new ViewHeader(this);
	private ViewBody body;
	
	private Stage primaryStage;
	private Scene configScene;
	private Scene homeScene;

	/**CSS**/
	private File css = new File("layout.css");
	private String cssFile = "file:///" + css.getAbsolutePath().replace("\\", "/");
	
	public void build(Model model){
		//ViewWindow.model = model;
		//System.out.println("ref of model saved" + this.model);
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
		
		//add style
		//configScene.getStylesheets().add("layout.css");
		
		//configScene.getStylesheets().clear();
		//configScene.getStylesheets().add();
		
		//homeScene.getStylesheets().clear();
		//homeScene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
		
		//com.sun.javafx.css.StyleManager.getInstance().reloadStylesheets(configScene);
		primaryStage.setScene(homeScene);
		//primaryStage.setResizable(false);
		//primaryStage.centerOnScreen(); 
		primaryStage.setTitle("LazyRemote");
		primaryStage.show();
	}
	@Override
	public void stop(){
	    System.out.println("Stage is closing");
	    Model.stopServer();
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
		body = new ViewBody(this);
		grid = body.buildHome(grid);
		
		grid.setId("root");
		//build scene
		Scene scene = new Scene(grid, 640, 420);
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
		body = new ViewBody(this);
		grid = body.buildConfig(grid);
		
		//build scene
		Scene scene = new Scene(grid, 640, 420);
		
		//add style
		scene.getStylesheets().add("layout.css");
		
		return scene;
		
	}

	/*
	 * Render function
	 */
	public void render(){
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
	/*
	 * Save config from user input
	 * @param string with values:separated;
	 */
	public static void saveConfig(String configStrings) {
		//System.out.println(this.model);
		try{
			Model.saveConfig(configStrings);
		}
		catch(NullPointerException wat){
			wat.printStackTrace();
			System.out.println(wat.getMessage());
		}
		
	}


	
}
