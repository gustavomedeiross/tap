package view;

import java.util.ArrayList;

import dao.AlocacaoDAO;
import dao.DisciplinaDAO;
import dao.PessoaDAO;
import domain.Disciplina;
import domain.Pessoa;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import util.Mensagens;


public class AlocacaoController {

	@FXML ComboBox<Pessoa> cbProfessores;

	@FXML ComboBox<Disciplina> cbDisciplinas;

	@FXML TableView<Disciplina> tbl;

	@FXML TableColumn<Disciplina, String> colNome;

	@FXML TableColumn<Disciplina, Number> colCarga;

	public void initialize() {

		cbDisciplinas.setItems(FXCollections.observableArrayList(DisciplinaDAO.listaTodas(true)));
		cbProfessores.setItems(FXCollections.observableArrayList(PessoaDAO.listaTodas("P", true)));
		colNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
		colCarga.setCellValueFactory(cellData -> cellData.getValue().cargaHorariaProperty());
		eventoChangeProfessor();

	}

	@FXML
	public void excluiAlocacao() {
		Pessoa p = cbProfessores.getSelectionModel().getSelectedItem();
		Disciplina d = tbl.getSelectionModel().getSelectedItem();
		if(p!= null && d!=null) {
			if(Mensagens.msgOkCancel("exclusão", "tem certeza que deseja excluir?")==ButtonType.OK) {
				AlocacaoDAO.excluirAlocacao(p, d);
				selecionaProfessor();
			}

		}
	}

	@FXML
	public void incluiDisciplina() {
		Pessoa p = cbProfessores.getSelectionModel().getSelectedItem();
		Disciplina d = cbDisciplinas.getSelectionModel().getSelectedItem();
		if(p!= null && d!=null) {
			AlocacaoDAO.novaAlocacao(p, d);
			selecionaProfessor();
			cbDisciplinas.getSelectionModel().select(-1);
		}else {
			Mensagens.msgErro("ERRO", "selecione um professor e uma disciplina");
		}
	}


	@FXML 
	public void selecionaProfessor() {

		Pessoa professor = cbProfessores.getSelectionModel().getSelectedItem();
		ArrayList<Disciplina> disciplinas = AlocacaoDAO.buscaPorProfessor(professor);
		tbl.setItems(FXCollections.observableArrayList(disciplinas));
	}

	private void eventoChangeProfessor() {
		cbProfessores.valueProperty().addListener(new ChangeListener<Pessoa>() {

			@Override
			public void changed(ObservableValue<? extends Pessoa> arg0, Pessoa arg1, Pessoa arg2) {
				selecionaProfessor();

			}
		});
	}

}
