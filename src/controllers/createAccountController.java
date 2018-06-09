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
	    void clickedCreateAccount(ActionEvent event) throws IOException {
	    	if(login.getLength()>5 && password.getLength()>5) {
	    		SQLite.createAccount(login.getText(), password.getText());
	    		BorderPane pane = FXMLLoader.load(getClass().getResource("/view/AuthView.fxml"));
	        	rootPane.getChildren().setAll(pane);
	    	}else {
	    		Alert.display("Informacja", "Login i Password musz¹ mieæ d³ugoœæ przynajmniej 5 znaków !");
	    		login.clear();
	    		password.clear();
	    	}
	 
	    }

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			
		}

}
