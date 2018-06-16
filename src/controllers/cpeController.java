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
    private TextArea translated;

    @FXML
    private TextArea toTranslate;

    @FXML
    private TextArea correct;

    @FXML
    private TextArea resoult;

    @FXML
    private Button startButton;

    @FXML
    private ProgressBar progressBar;

    @FXML
    void clickedMenuButton(ActionEvent event) throws IOException {
       	BorderPane pane = FXMLLoader.load(getClass().getResource("/view/menuView.fxml"));
    	cpePane.getChildren().setAll(pane);
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
		setRandomID(rand.nextInt(base.baseSize())+1);
		toTranslate.setText("asdasdasda");
		System.out.println(toTranslate.getText());
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {	
				    setRandomID(rand.nextInt(base.baseSize()));
					toTranslate.setText(base.getWordA(getRandomID()));
					translated.clear();
//					correct.clear();
					resoult.clear();
			}
		});
		
		checkButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				try {
//					correct.setText(base.getWordB(getRandomID()));
					if (base.getWordB(getRandomID()).equals(translated.getText().toLowerCase().replaceAll("\\s",""))) {
						resoult.setText("Correct !");
						resoult.setStyle("-fx-background-color: #008000; -fx-text-fill: #008000;-fx-font-size: 16;");
					} else {
						resoult.setText("Not this time. Correct answer is: " + base.getWordB(getRandomID()));
						resoult.setStyle("-fx-background-color: #FF0000; -fx-text-fill: #FF0000; -fx-text-alignment: center; -fx-text-origin: baseline; -fx-font-size: 16; ");
				}
				}
				catch (Exception e) {
//					System.out.println(e);
				}
				}
			
		});		
	}	

}
