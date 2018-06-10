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
import javafx.scene.layout.BorderPane;
import model.SQLite;

public class cepController implements Initializable{
	
    @FXML
    private BorderPane cepPane;

    @FXML
    private Button menuButton;

    @FXML
    private Button checkButton;

    @FXML
    private Button clearButton;

    @FXML
    private TextArea polishWord;

    @FXML
    private TextArea englishWord;

    @FXML
    private TextArea translate;

    @FXML
    private TextArea resoult;

    @FXML
    private Button startButton;

    @FXML
    private ProgressBar progressBar;

    @FXML
    void clickedMenuButton(ActionEvent event) throws IOException {
       	BorderPane pane = FXMLLoader.load(getClass().getResource("/view/menuView.fxml"));
    	cepPane.getChildren().setAll(pane);
    }
 
    int randID;
    
    public int getRandomID() {
    	return randID;
    }
    
    public void setRandomID(int randomID) {
    	randID = randomID;
    }
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		SQLite base = new SQLite();
		Random rand = new Random();
				
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {	
				    setRandomID(rand.nextInt(base.baseSize()));
					englishWord.setText(base.getWordB(getRandomID()));				
			}
		});
		
		checkButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				translate.setText(base.getWordA(getRandomID()));
				if (translate.getText().equals(polishWord.getText().toLowerCase().replaceAll("\\s",""))) {
					resoult.setText("CORRECT !");
					resoult.setStyle("-fx-background-color: #008000; -fx-text-fill: #008000;-fx-font-size: 30;");
				} else {
					resoult.setText("Oops, it's not right :(");
					resoult.setStyle("-fx-background-color: #FF0000; -fx-text-fill: #FF0000;-fx-font-size: 16;");
				}
			}
		});
		
		clearButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				englishWord.clear();
				polishWord.clear();
				translate.clear();
				resoult.clear();
				setRandomID(0);
			}
		});		
	}	
}
