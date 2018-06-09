package model;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Alert {

	public static void display(String title, String message) {

		Stage secondStage = new Stage();

		Label label = new Label();
		label.setText(message);
		Button closeButton = new Button("OK");
		closeButton.setOnAction(e -> secondStage.close());
		VBox layout = new VBox(20);
		layout.getChildren().addAll(label, closeButton);
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout);
		secondStage.setTitle(title);
		secondStage.setScene(scene);

		double centerXPosition = Main.primaryStage.getX() + Main.primaryStage.getWidth() / 2d;
		double centerYPosition = Main.primaryStage.getY() + Main.primaryStage.getHeight() / 2d;

		secondStage.setOnShowing(ev -> secondStage.hide());

		secondStage.setOnShown(ev -> {
			secondStage.setX(centerXPosition - secondStage.getWidth() / 2d);
			secondStage.setY(centerYPosition - secondStage.getHeight() / 2d);
			secondStage.show();
		});

		secondStage.showAndWait();

	}
}
