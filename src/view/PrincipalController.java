package view;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class PrincipalController {
	 @FXML
	 private Label lblData;

	 
	 public void initialize() {
		 DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		 
		 Date data = new Date();
		 lblData.setText("Data Atual: " +df.format(data));
	 }
	 
	 	@FXML
	    private TabPane tabPane;

	    @FXML
	    void abreCadDisciplinas() {
	    	abreTab("Disciplinas", "Disciplina.fxml");
	    }

	    @FXML
	    void abreCadProfessores() {
	    	abreTab("Professores", "TelaProfessores.fxml");
	    }

	    @FXML
	    void abreAlocacao() {
	    	abreTab("Alocação", "Alocacao.fxml");
	    }
	    @FXML
	    void abreCadAlunos() {
	    	abreTab("Alunos", "TelaAlunos.fxml");
	    }

	    @FXML
	    void abreTela4() {
	    	abreTab("matrícula", "TelaMatricula.fxml");
	    }
	    
	    
	    private Tab tabAberta(String titulo) {
			for (Tab tb : tabPane.getTabs()) {
				if(!(tb.getText()==null) && tb.getText().equals(titulo)) 
					return tb;
			}
			return null;
		}

	    
	    
	    private void selecionaTab(Tab tab) {
			tabPane.getSelectionModel().select(tab);
		}
	 
	    private void abreTab(String titulo, String path) {
			try{
				Tab tab = tabAberta(titulo);
				if (tab==null) {
					tab = new Tab(titulo);
					tab.setClosable(true);
					tabPane.getTabs().add(tab);
					FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
					Parent root = loader.load();
					tab.setContent((Node) root);
				}
				selecionaTab(tab);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	 
	 
	 
}
