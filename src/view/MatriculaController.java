package view;

import java.util.ArrayList;

import dao.DisciplinaDAO;
import dao.MatriculaDAO;
import dao.PessoaDAO;
import domain.Disciplina;
import domain.Matricula;
import domain.Pessoa;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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

//	@FXML TableView<Disciplina> tbl;
	@FXML TableView<Matricula> tbl;

	@FXML TableColumn<Matricula, String> colNome;

	@FXML TableColumn<Matricula, String> colSemestre;



	public void initialize() {
		cbDisciplinas.setItems(FXCollections.observableArrayList(DisciplinaDAO.listaTodas(true)));
		cbAlunos.setItems(FXCollections.observableArrayList(PessoaDAO.listaTodas("A", true)));
		colNome.setCellValueFactory(cellData -> cellData.getValue().getDisciplina().nomeProperty());
		colSemestre.setCellValueFactory(cellData -> cellData.getValue().semestreProperty());
		eventoChangeAluno();
	}

	@FXML
	public void incluiMatricula() {
		Pessoa a = cbAlunos.getSelectionModel().getSelectedItem();
		Disciplina d = cbDisciplinas.getSelectionModel().getSelectedItem();
		String semestre = txtSemestre.getText();

		if(a != null && d != null && semestre != null) {
			Matricula m = new Matricula();
			m.setAluno(a);
			m.setDisciplina(d);
			m.setSemestre(semestre);
			MatriculaDAO.novaMatricula(m);
			cbDisciplinas.getSelectionModel().select(-1);
			selecionaAluno();
		}else {
			Mensagens.msgErro("ERRO", "selecione um aluno e uma disciplina");
		}
	}

	@FXML
	public void excluiMatricula() {
		Pessoa a = cbAlunos.getSelectionModel().getSelectedItem();
		Disciplina d = tbl.getSelectionModel().getSelectedItem().getDisciplina();
		if(a!= null && d!=null) {
			if(Mensagens.msgOkCancel("exclus�o", "tem certeza que deseja excluir?")==ButtonType.OK) {
				MatriculaDAO.excluirMatricula(a, d);

			}

		}
	}

	@FXML 
	public void selecionaAluno() {
		Pessoa aluno = cbAlunos.getSelectionModel().getSelectedItem();
		ArrayList<Matricula> matriculas = MatriculaDAO.buscaPorAluno(aluno);
		matriculas.forEach(m -> System.out.println(m.getAluno().getNome()));
		tbl.setItems(FXCollections.observableArrayList(matriculas));
	}

	private void eventoChangeAluno() {
	    cbAlunos.valueProperty().addListener((e, o, n) -> selecionaAluno());
	}
}
