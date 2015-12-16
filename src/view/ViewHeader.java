package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ViewHeader {
	private ViewWindow viewWindow;
	
	public ViewHeader(ViewWindow v) {
		viewWindow = v;
	}
	
	/*
	 * Add header to the view
	 */
	public GridPane build(GridPane grid) {
		
		GridPane headerContainer = new GridPane();
		
		//Logo
		HBox logo = getLogoBox();
		
		//Navigation
		HBox nav = addHBox();
		
		//add styles
		nav.getStyleClass().add("header");
		logo.getStyleClass().add("header");
		logo.getStyleClass().add("logo");
		
		//header: layout
		ColumnConstraints headerCol1 = new ColumnConstraints();
		headerCol1.setPercentWidth(30);
	    ColumnConstraints headerCol2 = new ColumnConstraints();
	    headerCol2.setPercentWidth(70);
	    headerContainer.getColumnConstraints().addAll(headerCol1, headerCol2); // each get 50% of width
	    
	    
	    //Add Elements
	    headerContainer.add(logo, 0, 0); 	//col, row, colspan, rowspan
	    headerContainer.add(nav, 1, 0); 
	    HBox shadow = new HBox();
	    shadow.getStyleClass().add("shadow");
	    headerContainer.add(shadow, 0, 1, 2, 1); 
	    
	    ColumnConstraints columnHeader = new ColumnConstraints();
	    columnHeader.setPercentWidth(100);
	    
	    //add styles
	    headerContainer.getStyleClass().add("header-container");
	    
	    grid.getColumnConstraints().addAll(columnHeader); // each get 50% of width
	    
	    grid.add(headerContainer, 0, 0);
	  
	    
	   
	    //headerContainer.setPrefWidth(Double.MAX_VALUE); // Default width and height
	    
	   // grid.add(headerContainer, 0, 0);
		//grid.set("top");
			
		return grid;
	}
	//A
	private HBox getLogoBox() {
		HBox hbox = new HBox();
		Image logo = new Image("lazy-logo.png");
		ImageView imgView = new ImageView(logo);
		hbox.getChildren().add(imgView);
		hbox.getStyleClass().add("logo-img");
		return hbox;
	}
	
	private HBox addHBox() {
		HBox hbox = new HBox();
		//label buttons
		Button btnHome = new Button("Host");
		Button btnConfig = new Button("Settings");
		Button[] buttons = {btnHome,btnConfig};
		
		//set EventListeners
		for (int i = 0; i < buttons.length; i++) {
			final boolean goHome = i == 0 ? true : false;
			//buttons[i].view
			
			//add styles
			buttons[i].getStyleClass().add("button");
			
			buttons[i].setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent navEvent) {
					Scene scene;
					if(goHome) scene = viewWindow.getHome();
					else scene = viewWindow.getConfig();
					Stage primaryStage  = viewWindow.getStage();
					primaryStage.setScene(scene);
					
				}
			});
			hbox.getChildren().add(buttons[i]);
		}
		
		
		hbox.setAlignment(Pos.CENTER);
		//hbox.setId("top");
		//hbox.setPadding(new Insets(20, 20, 0, 20));
		return hbox;
	}
	
}
