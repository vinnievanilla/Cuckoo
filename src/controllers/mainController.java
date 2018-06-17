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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import model.SQLite;


public class mainController implements Initializable{
 
    @FXML
    private BorderPane rootPane;
    
    @FXML
    private Button goButton;
    
    @FXML
    private Button createAccount;
    
    @FXML
    private PasswordField password;

    @FXML
    private TextField login;
    
    @FXML
    private Label welcomeLabel;
    
    @FXML
    private void clickedGoButton(Event event) throws IOException {
    	boolean a;
    	a = SQLite.checkUser(login.getText(), password.getText());
    	if(a==true) {
    		BorderPane pane = FXMLLoader.load(getClass().getResource("/view/menuView.fxml"));
        	rootPane.getChildren().setAll(pane);
    	}else if(a==false) {
    		welcomeLabel.setText("You have to sign in first!");
    	}	
    };
    
    @FXML
    private void clickedCreateAccount(Event event) throws IOException {
    	BorderPane pane = FXMLLoader.load(getClass().getResource("/view/CreateAccountView.fxml"));
    	rootPane.getChildren().setAll(pane);
    };
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

}
