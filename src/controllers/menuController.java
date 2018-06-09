package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;


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
    
      
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		peButton.setOnMouseClicked((Event event)->openNewWindow("/view/peView.fxml"));
		
		epButton.setOnMouseClicked((Event event)->openNewWindow("/view/epView.fxml"));
		
		cpeButton.setOnMouseClicked((Event event)->openNewWindow("/view/cpeView.fxml"));
		
		cepButton.setOnMouseClicked((Event event)->openNewWindow("/view/cepView.fxml"));
		
	}
	
	
    private void openNewWindow(String pathToFxmlFile ){
    	try {
    		BorderPane pane = FXMLLoader.load(getClass().getResource(pathToFxmlFile));
    		menuPane.getChildren().setAll(pane);
		} catch(Exception e) {
			e.printStackTrace();
		}
   }
}
