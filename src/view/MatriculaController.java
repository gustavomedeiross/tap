package view;

import java.util.ArrayList;

import dao.DisciplinaDAO;
import dao.MatriculaDAO;
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
import javafx.scene.control.TextField;
import util.Mensagens;

public class MatriculaController {
	@FXML ComboBox<Pessoa> cbAlunos;

	@FXML ComboBox<Disciplina> cbDisciplinas;

	@FXML TextField txtSemestre;

	@FXML TableView<Disciplina> tbl;

	@FXML TableColumn<Disciplina, String> colNome;

	@FXML TableColumn<Disciplina, Number> colCarga;



	public void initialize() {

		cbDisciplinas.setItems(FXCollections.observableArrayList(DisciplinaDAO.listaTodas(true)));
		cbAlunos.setItems(FXCollections.observableArrayList(PessoaDAO.listaTodas("A", true)));
		colNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
		colCarga.setCellValueFactory(cellData -> cellData.getValue().cargaHorariaProperty());
		eventoChangeAluno();
	}

	@FXML
	public void incluiMatricula() {
		Pessoa a = cbAlunos.getSelectionModel().getSelectedItem();
		Disciplina d = cbDisciplinas.getSelectionModel().getSelectedItem();

		if(a!= null && d!=null) {
			MatriculaDAO.novaMatricula(a, d);
			cbDisciplinas.getSelectionModel().select(-1);
			selecionaAluno();
		}else {
			Mensagens.msgErro("ERRO", "selecione um aluno e uma disciplina");
		}
	}

	@FXML
	public void excluiMatricula() {
		Pessoa a = cbAlunos.getSelectionModel().getSelectedItem();
		Disciplina d = tbl.getSelectionModel().getSelectedItem();
		if(a!= null && d!=null) {
			if(Mensagens.msgOkCancel("exclusão", "tem certeza que deseja excluir?")==ButtonType.OK) {
				MatriculaDAO.excluirMatricula(a, d);

			}

		}
	}

	@FXML 
	public void selecionaAluno() {

		Pessoa aluno = cbAlunos.getSelectionModel().getSelectedItem();
		ArrayList<Disciplina> disciplinas = MatriculaDAO.buscaPorAluno(aluno);
		tbl.setItems(FXCollections.observableArrayList(disciplinas));
	}

	private void eventoChangeAluno() {
		cbAlunos.valueProperty().addListener(new ChangeListener<Pessoa>() {

			@Override
			public void changed(ObservableValue<? extends Pessoa> arg0, Pessoa arg1, Pessoa arg2) {
				selecionaAluno();

			}
		});
	}


}
