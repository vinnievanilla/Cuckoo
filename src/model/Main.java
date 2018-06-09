package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {

	public static Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent parent = (Parent) FXMLLoader.load(getClass().getResource("/view/AuthView.fxml"));
			Scene scene = new Scene(parent);
			primaryStage.setScene(scene);
			primaryStage.show();
			Main.primaryStage = primaryStage;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		//Uruchomienie aplikacji
		launch(args);
		
		//Połączenie z bazą - do testowania
		SQLite get = new SQLite(); 
		
		get.setBasket(1,100);
		get.selectAll();
		//get.connect("jdbc:sqlite:src/resources/db/main.db");
		
	}
}
