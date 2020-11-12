package ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AlertController {
	@FXML
	private ImageView imagem;
	
	@FXML
	public void closeWindow() {
		try {
			Stage stage = (Stage) imagem.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
