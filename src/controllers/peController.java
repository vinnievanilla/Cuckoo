package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import model.SQLite;

public class peController implements Initializable {


	@FXML
    private BorderPane pePane;
    
    @FXML
    private Button nextButton;
    
    @FXML
    private Button turnButton;
    
    @FXML
    private Button menuButton;
    
    @FXML
    private TextField flashcard;
    
    int randID;
    
    public int getRandomID() {
    	return randID;
    }
    
    public void setRandomID(int randomID) {
    	randID = randomID;
    }
    
    @FXML
    private void clickedMenuButton(ActionEvent event) throws IOException {
    	BorderPane pane = FXMLLoader.load(getClass().getResource("/view/menuView.fxml"));
    	pePane.getChildren().setAll(pane);
    };
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		SQLite base = new SQLite();
		Random rand = new Random();
		setRandomID(rand.nextInt(base.baseSize()));
		flashcard.setText(base.getWordA(getRandomID()));
		
	
		
		turnButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {	
				flashcard.clear();
				flashcard.setText(base.getWordB(getRandomID()));
				System.out.println(flashcard.getText());
			}
		});
		
		nextButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {	
				flashcard.clear();
				setRandomID(rand.nextInt(base.baseSize()));
				flashcard.setText(base.getWordA(getRandomID()));    
			}
		});

		
	}
	
 

}

