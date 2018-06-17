package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import model.SQLite;


public class menuController implements Initializable{

	@FXML
    private BorderPane menuPane;
    
    @FXML
    private Button peButton;
    
    @FXML
    private Button epButton;
    
    @FXML
    private Button cpeButton;
    
    @FXML
    private Button cepButton;
    
    @FXML
    private Label infoLabel;
    
    @FXML
    private Button signOutButton;
    
    SQLite db = new SQLite();
      
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		peButton.setOnMouseClicked((Event event)->openNewWindow("/view/peView.fxml"));
		
		epButton.setOnMouseClicked((Event event)->openNewWindow("/view/epView.fxml"));
		
		cpeButton.setOnMouseClicked((Event event)->openNewWindow("/view/cpeView.fxml"));
		
		cepButton.setOnMouseClicked((Event event)->openNewWindow("/view/cepView.fxml"));
		
		
		
		infoLabel.setText("Welcome " +db.getCurrentUser() + " ");
	}
	
	@FXML
    private void clickedSignOut(Event event) throws IOException {
    	BorderPane pane = FXMLLoader.load(getClass().getResource("/view/AuthView.fxml"));
    	menuPane.getChildren().setAll(pane);
    	db.setCurrentUser(null);
    };
	
	
	
    private void openNewWindow(String pathToFxmlFile ){
    	try {
    		BorderPane pane = FXMLLoader.load(getClass().getResource(pathToFxmlFile));
    		menuPane.getChildren().setAll(pane);
		} catch(Exception e) {
			e.printStackTrace();
		}
   }
}
