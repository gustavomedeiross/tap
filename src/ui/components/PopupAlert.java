package ui.components;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

public class PopupAlert {
	private static Alert alert(String header, String message, AlertType type){
		  Alert alert = new Alert(type);
		  alert.setHeaderText(header);
		  alert.setContentText(message);
		  alert.initStyle(StageStyle.UNDECORATED);
		  alert.getDialogPane().setStyle("-fx-border-color: black; -fx-border-width: 3;");
		  return alert;
	}

	public static void error(String header, String message) {
		alert(header, message, AlertType.ERROR).showAndWait();
	}

	public static void warning(String header, String message){
		alert(header, message, AlertType.WARNING).showAndWait();
	}

	public static void info(String header, String message) {
		alert(header, message, AlertType.INFORMATION).showAndWait();
	}
	public static void confirmation(String header, String message) {
		alert(header, message, AlertType.CONFIRMATION).showAndWait();
	}
	public static ButtonType okOrCancel(String header, String message) {
		Optional<ButtonType> r = alert(header, message, AlertType.CONFIRMATION).showAndWait();
		return r.get();
	}
}
