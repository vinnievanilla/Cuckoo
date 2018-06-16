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
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import model.Progress;
import model.SQLite;

public class cpeController implements Initializable{
	

	
    @FXML
    private BorderPane cpePane;

    @FXML
    private Button menuButton;

    @FXML
    private Button checkButton;

    @FXML
    private Button clearButton;

    @FXML
    private TextField translated;

    @FXML
    private TextField toTranslate;

    @FXML
    private TextArea correct;

    @FXML
    private TextArea resoult;

    @FXML
    private Button startButton;

    @FXML
    private ProgressBar cpeProgressBar;

    @FXML
    void clickedMenuButton(ActionEvent event) throws IOException {
       	BorderPane pane = FXMLLoader.load(getClass().getResource("/view/menuView.fxml"));
    	cpePane.getChildren().setAll(pane);
    }
 
    int randID;
    int currentBasket;
    String currentUser;
    
        
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		SQLite base = new SQLite();
		randID = 0;
		currentBasket = 1;
		currentUser = "admin";
		
		Progress pBar = new Progress(currentUser);
		cpeProgressBar.setProgress(pBar.getProgress());
		
		while (base.baseSize(currentBasket) == 0) {
			currentBasket = base.changeBasket(currentBasket);
		}
		randID = base.getRandomID(currentBasket);
		toTranslate.setText(base.getWordA(randID));
					
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {

			while (base.baseSize(currentBasket) == 0) {
				currentBasket = base.changeBasket(currentBasket);
			}

			randID = base.getRandomID(currentBasket);
			toTranslate.setText(base.getWordA(randID));	
			translated.clear();
//			correct.clear();
			resoult.clear();
			}});
		
		checkButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				
				try {
//					correct.setText(base.getWordB(getRandomID()));
					if (base.getWordB(randID).equals(translated.getText().toLowerCase().replaceAll("\\s",""))) {
						resoult.setText("Correct !");
						resoult.setStyle("-fx-background-color: #008000; -fx-text-fill: #008000;-fx-font-size: 16;");
						base.setBasket(randID, true);
						base.setTotalCorrect(currentUser);
						base.setTotalAttempt(currentUser);
						
					} else {
						resoult.setText("Not this time. Correct answer is: " + base.getWordB(randID));
						resoult.setStyle("-fx-background-color: #FF0000; -fx-text-fill: #FF0000; -fx-text-alignment: center; -fx-text-origin: baseline; -fx-font-size: 16; ");
						base.setBasket(randID, false);
						base.setTotalAttempt(currentUser);
					}
				pBar.setProgress(currentUser);
				cpeProgressBar.setProgress(pBar.getProgress());
					
				}
				catch (Exception e) {
//					System.out.println(e);
				}
				}
			
		});		
	}	

	
	
}
