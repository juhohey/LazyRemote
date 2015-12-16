package view;


import server.Config;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Renders the body element in the view
 * TODO: refactor the userConfig -thing, now it's checked like a million times
 */
public class ViewBody {

	private ViewWindow viewWindow;
	
	
	private Text statusValue;
	
	public ViewBody(ViewWindow viewWindow2) {
		this.viewWindow = viewWindow2;
	}

	/**************************************************************************
	 * HOME VIEW
	 */
	
	public GridPane buildHome(GridPane grid) {
		//get body
		VBox configBody = buildHomeBody();
		//configBody.setMinWidth(Double.MAX_VALUE);
		//configBody.setAlignment(Pos.CENTER);
		//add body
		grid.add(configBody, 0, 1);  //col, row
		
		return grid;
	}
	
	/*
	 * Generate input & label elements
	 */
	private VBox buildHomeBody() {
		
	    VBox body = new VBox();
	    	    
	    //Server host name
	    VBox hostVbox = new VBox();
	    String hostName = configClass.getHost();
	    Text host = new Text(hostName);
	    hostVbox.getChildren().addAll(host);
	    //Add css
	    host.getStyleClass().add("host-text");
	    hostVbox.getStyleClass().add("host");
	    
	    
	    //Server status
	    HBox status = generateStatus();
	    
	    //Server online/offline toggle
	    HBox startStop = generateStartStop();
	    
	    
	    //server ip
	    String ip = Model.getIp();
	    Text ipLabel = new Text(ip);
	    //Add css
	    host.getStyleClass().add("ip");
	    
	    //add children
	    body.getChildren().addAll(hostVbox,status,ipLabel,startStop);
	    
	    //add css
	    body.getStyleClass().add("body");
	    
	    
	    
	    return body;
	}
	
	/**
	 * Generate buttons for start/stop action
	 * @return HBox container w/ 2 buttons
	 */
	
	private HBox generateStartStop() {
	//	serverStatus.set("asd");
		HBox st = new HBox (); //container
		
		//Stop
		Button stopBtn = new Button("stop");
		stopBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent stopA) {
				System.out.println("stop server...");
				
				Model.stopServer();
				setServerStatusMessage();
			}
			
		});
		
		//START
		Button startBtn = new Button("start");
		startBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent startA) {
				System.out.println("starting server...");
				
				Model.startServer();
				setServerStatusMessage();
			}

			
			
		});
		//Add styles
		stopBtn.getStyleClass().addAll("button","button-clear");
		startBtn.getStyleClass().addAll("button","button-clear");
		st.getStyleClass().addAll("start-stop");
		
		st.getChildren().addAll(stopBtn, startBtn);
		
		return st;
	}
	
	private void setServerStatusMessage() {
		String val = Model.getServerStatus().toString();
		statusValue.setText(val);
		 //Assign class
	    switch (val) {
		case "ONLINE":
			statusValue.getStyleClass().removeAll("green-text","red-text");
			statusValue.getStyleClass().add("green-text");
			break;
		case "OFFLINE":
			statusValue.getStyleClass().removeAll("green-text","red-text");
			statusValue.getStyleClass().add("red-text");
			break;
		default:
			break;
		}
		
	}
	
	private HBox generateStatus() {
		
			HBox status = new HBox (); //container
			
			//Bind value			
		    statusValue = new Text(); // value
		    setServerStatusMessage(); 
		   
		    Text statusLabel = new Text("status: "); //label
		    status.getChildren().addAll(statusLabel,statusValue); // add elements
		    
		    //Add css
		    status.getStyleClass().add("status");
		    return status;
	}


	/**************************************************************************
	 * CONFIG VIEW
	 */
	Config configClass =Config.getInstance();
	
	//Config CheckBox inputs
	private CheckBox configSSL = new CheckBox(); 			//0
	private CheckBox configCommands = new CheckBox();		//1
	private CheckBox configPermission = new CheckBox();		//2
	
	//so static it hurts
	private CheckBox[] configs = {configSSL,configCommands,configPermission};
	private String[] configLabels = {"Use SSL","Allow custom commands","Request permission on new connection"};
	private String[] configSettings;
	
	//SSl Input
	private TextField configSecret = new TextField();		
	private String configSecretLabel = "AppSecret";
	
	
	/*
	 * Set CheckBox values based on configuration file
	 * @param config - contents of config file	
	 */
	public void getConfigs( ) {
		String s = configClass.getUserConfig();
		if(s!=null) configSettings = s.split(";");
	}
	public void setConfigs(String s) {
		configSettings = s.split(";");
	}
	/*

	/** 
	 * Build body
	 */
	public GridPane buildConfig(GridPane grid) {
		//get body
		VBox configBody = buildConfigBody();
		//add body
		grid.add(configBody, 0, 1);  //col, row
				
		return grid;
	}
	
	/**
	 * Generate input & label elements
	 */
	private VBox buildConfigBody() {
		VBox container = new VBox();
	    VBox body = new VBox();
	    
	   // System.out.println("viewbody 94 configSettings: "+configSettings);
	    if(configSettings==null) getConfigs();
	    //Add child CheckBox elements
	    for (int i = 0; i < configs.length; i++) {
	    	//Check if user has set this as true
	    	configs[i].setSelected(getUserConfig(i));
	    	configs[i].getStyleClass().add("cb");
			Text configLabel = new Text(configLabels[i]);
			//add styles
			configLabel.getStyleClass().add("config-label");
			//add elements
			body.getChildren().addAll(configLabel,configs[i]);
		}
	    //Add secret input
	    Text configLabel = new Text(configSecretLabel);
	    //Split the string since it's secret:asd
	    if(configSettings!=null) configSecret = checkSecret(configSettings[3].split(":"),configSecret);
	    body.getChildren().addAll(configLabel,configSecret);
	    
	    //Add save button
	    body.getChildren().add(saveConfigButton());
	    
	    body.setId("config");
	    
	    //add css
	    container.getChildren().addAll(body);
	    container.getStyleClass().add("body");
	    //container.setMinHeight(Double.MAX_VALUE);
	    
	    body.setMaxWidth(Double.MIN_VALUE);
	    
	    return container;
	}
	private TextField checkSecret(String[] secrets, TextField field) {
		if (secrets[1]!=null) field.setText(secrets[1]);
		return field;
	}



	/*
	 * Parse a single config setting
	 * @param array identifier of config
	 */
	private boolean getUserConfig(int i) {
		
		if(configSettings!=null){
			String[] configSetting = configSettings[i].split(":");
			if(configSetting[1].equals("true")) return true;
		}
		return false;
	}

	/*
	 * This saves config options on click
	 */
	private Node saveConfigButton() {
		
		//element
		Button save = new Button();
		save.setText("save & start server");
		//add css
		save.getStyleClass().add("button-primary");
		
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
				ViewWindow.saveConfig(configStrings);
				setConfigs(configStrings);
				
				
				//Goto home view
				Scene scene = viewWindow.getHome();
				Stage primaryStage  = viewWindow.getStage();
				primaryStage.setScene(scene);
				
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
