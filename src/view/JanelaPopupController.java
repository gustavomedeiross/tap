package view;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class JanelaPopupController {
	
	 @FXML
	    private ImageView imagem;
	
	@FXML
	public void fecharJanela() {
		try {
			Stage stage = (Stage) imagem.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}
