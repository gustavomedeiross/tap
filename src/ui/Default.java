package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Default extends Application {
	 
		@Override
		public void start(Stage primaryStage) {
			try {
				BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("pages/Main.fxml"));
				Scene scene = new Scene(root,400,400);
				scene.getStylesheets().add(getClass().getResource("pages/application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.setOnCloseRequest(e -> System.exit(0));
				primaryStage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
}
