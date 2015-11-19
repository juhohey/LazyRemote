package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ViewBody {

	private ViewWindow viewWindow;
	public ViewBody(ViewWindow viewWindow) {
		this.viewWindow = viewWindow;
	}

	
	
	/**************************************************************************
	 * HOME VIEW
	 */
	
	public GridPane buildHome(GridPane grid) {
		//get body
		VBox configBody = buildHomeBody();
		//add body
		grid.add(configBody, 0, 1);  //col, row
						
		return grid;
	}
	
	/*
	 * Generate input & label elements
	 */
	private VBox buildHomeBody() {
		
	    VBox body = new VBox();

	    Text bodyLabel = new Text("bodyLabel");
	    body.getChildren().add(bodyLabel);

	    body.setId("home");
	    return body;
	}
	
	
	/**************************************************************************
	 * CONFIG VIEW
	 */
	
	//Config CheckBox inputs
	private CheckBox configSSL = new CheckBox(); 			//0
	private CheckBox configCommands = new CheckBox();		//1
	private CheckBox configPermission = new CheckBox();		//2
	//so static it hurts
	private CheckBox[] configs = {configSSL,configCommands,configPermission};
	private String[] configLabels = {"Use SSL","Allow custom commands","Request permission on new connection"};
	private String[] configSettings = null;
	
	//SSl Input
	private TextField configSecret = new TextField();		
	private String configSecretLabel = "AppSecret";
	
	
	/*
	 * Set CheckBox values based on configuration file
	 * @param config - contents of config file	
	 */
	public void setConfigs(String config) {
		if(config!=null) configSettings = config.split(";");
	}
	
	/*
	 * Build body
	 */
	public GridPane buildConfig(GridPane grid) {
		//get body
		VBox configBody = buildConfigBody();
		//add body
		grid.add(configBody, 0, 1);  //col, row
				
		return grid;
	}
	
	/*
	 * Generate input & label elements
	 */
	private VBox buildConfigBody() {
		
	    VBox body = new VBox();
	    
	    //Add child CheckBox elements
	    for (int i = 0; i < configs.length; i++) {
	    	//Check if user has set this as true
	    	configs[i].setSelected(getUserConfig(i));
	    	
			Text configLabel = new Text(configLabels[i]);
			body.getChildren().addAll(configLabel,configs[i]);
		}
	    //Add secret input
	    Text configLabel = new Text(configSecretLabel);
	    body.getChildren().addAll(configLabel,configSecret);
	    
	    //Add save button
	    body.getChildren().add(saveConfigButton());
	    
	    body.setId("config");
	    return body;
	}
	/*
	 * Parse a single config setting
	 * @param array identifier of config
	 */
	private boolean getUserConfig(int i) {
		if(configSettings!=null){
			String[] configSetting = configSettings[i].split(":");
			if(configSetting[1].equals("True")) return true;
		}
		return false;
	}

	/*
	 * This saves config options on click
	 */
	private Node saveConfigButton() {
		Button save = new Button();
		save.setText("save");
		
		save.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent ev) {
				//string actual
				String configStrings = "";
				//Checkboxes
				for (int i = 0; i < configs.length; i++) {
					String label = getLabel(i);
					configStrings += (label + bToString(configs[i].isSelected()) +";");
				}
				//input
				configStrings += ("secret:"+ configSecret.getText());
				// Instance REFERENCE - SAVE
				viewWindow.saveConfig(configStrings);
			}

			//asd
			private String getLabel(int i) {
				String res = "";
				if(i==0) res = "ssl:";
				if(i==1) res = "custom:";
				if(i==2) res = "permission:";
				return res;
				
			}

			private String bToString(boolean selected) {
				return (String) (selected ? "true" : "false");
			}
			
		});
		return save;
	}

	

	

}
