package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import model.SQLite;


public class uploadController implements Initializable{

	@FXML
    private BorderPane uploadPane;
          
    @FXML
    private Button uploadButton;
    
    @FXML
    private TextField csvPath;
    
    SQLite db = new SQLite();
      
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
	
	@FXML
    private void clickedUploadButton(Event event) throws IOException {
    	SQLite db = new SQLite();
    	
    	db.addNewTable((String)csvPath.getText());
    	System.out.println(csvPath.getText());
		
		BorderPane pane = FXMLLoader.load(getClass().getResource("/view/menuView.fxml"));
    	uploadPane.getChildren().setAll(pane);
    	
    	
    	
    };
	
  
 
}
