package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
		
		//Logo
		HBox logo = getLogoBox();
		
		//Navigation
		HBox nav = addHBox();
		
		//header: layout
		ColumnConstraints headerCol1 = new ColumnConstraints();
		headerCol1.setPercentWidth(50);
	    ColumnConstraints headerCol2 = new ColumnConstraints();
	    headerCol2.setPercentWidth(50);
	    grid.getColumnConstraints().addAll(headerCol1, headerCol2); // each get 50% of width
	    
	    //Add Elements
	    grid.add(logo, 0, 0); 	//col, row, colspan, rowspan
		grid.add(nav, 1, 0); 
		
		return grid;
	}
	//A
	private HBox getLogoBox() {
		HBox hbox = new HBox();
		Text logo = new Text("logo");
		hbox.getChildren().add(logo);
		return hbox;
	}
	
	private HBox addHBox() {
		HBox hbox = new HBox();
		//label buttons
		Button btnHome = new Button("Home");
		Button btnConfig = new Button("Settings");
		Button[] buttons = {btnHome,btnConfig};
		
		//set EventListeners
		for (int i = 0; i < buttons.length; i++) {
			final boolean goHome = i == 0 ? true : false;
			//buttons[i].view
			
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
		hbox.setId("top");
		//hbox.setPadding(new Insets(20, 20, 0, 20));
		return hbox;
	}
	
}
