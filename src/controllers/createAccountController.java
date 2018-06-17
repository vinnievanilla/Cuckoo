package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import model.Alert;
import model.SQLite;

	public class createAccountController implements Initializable{

	    @FXML
	    private BorderPane rootPane;

	    @FXML
	    private TextField login;

	    @FXML
	    private Button createAccount;

	    @FXML
	    private TextField password;
	    
	    @FXML
	    private Label infoLabel;
	    
	    @FXML
	    private Button authButton;
	    
	    @FXML
	    private void clickedAuthButton(ActionEvent event) throws IOException {
	    	BorderPane pane = FXMLLoader.load(getClass().getResource("/view/AuthView.fxml"));
	    	rootPane.getChildren().setAll(pane);
	    };
	    
	    
	    @FXML
	    void clickedCreateAccount(ActionEvent event) throws IOException {
	    	if(login.getLength()>=5 && password.getLength()>=5) {
	    		
	    		if (SQLite.createAccount(login.getText(), password.getText()) == true) {
	    			infoLabel.setText("Registered successfully");
	    		}
	    		else {
	    			infoLabel.setText("That login exists in Cuckoo...");
	    		}
//	    		BorderPane pane = FXMLLoader.load(getClass().getResource("/view/AuthView.fxml"));
//	        	rootPane.getChildren().setAll(pane);
	    	}else {
	    		infoLabel.setText("Login and password must have at least 5 characters");
	    		login.clear();
	    		password.clear();
	    	}
	 
	    }

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			
		}

}
