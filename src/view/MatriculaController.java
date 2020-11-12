package view;

import java.util.ArrayList;

import dao.SubjectDAO;
import dao.EnrollmentDAO;
import dao.PersonDAO;
import domain.Disciplina;
import domain.Matricula;
import domain.Pessoa;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import util.Mensagens;

public class MatriculaController {
	@FXML ComboBox<Pessoa> cbAlunos;

	@FXML ComboBox<Disciplina> cbDisciplinas;

	@FXML TextField txtSemestre;

//	@FXML TableView<Disciplina> tbl;
	@FXML TableView<Matricula> tbl;

	@FXML TableColumn<Matricula, String> colNome;

	@FXML TableColumn<Matricula, String> colSemestre;

	@FXML Button btnExcluir;



	public void initialize() {
		cbDisciplinas.setItems(FXCollections.observableArrayList(SubjectDAO.all(true)));
		cbAlunos.setItems(FXCollections.observableArrayList(PersonDAO.all("A", true)));
		colNome.setCellValueFactory(cellData -> cellData.getValue().getDisciplina().nomeProperty());
		colSemestre.setCellValueFactory(cellData -> cellData.getValue().semestreProperty());
		eventoChangeAluno();
		btnExcluir.disableProperty().bind(tbl.getSelectionModel().selectedItemProperty().isNull());
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
			EnrollmentDAO.create(m);
			cbDisciplinas.getSelectionModel().select(-1);
			selecionaAluno();
		}else {
			Mensagens.msgErro("ERRO", "selecione um aluno e uma disciplina");
		}
	}

	@FXML
	public void excluiMatricula() {
		Matricula m =  tbl.getSelectionModel().getSelectedItem();

		if(Mensagens.msgOkCancel("exclus�o", "tem certeza que deseja excluir?")==ButtonType.OK) {
			EnrollmentDAO.delete(m);
			tbl.getItems().remove(m);
		}
	}

	@FXML
	public void buscaMateriasPorAlunoESemestre() {
		Pessoa aluno = cbAlunos.getSelectionModel().getSelectedItem();
		String semestre = txtSemestre.getText();
		ArrayList<Matricula> matriculas = EnrollmentDAO.findByStudentAndSemester(aluno, semestre);
		tbl.setItems(FXCollections.observableArrayList(matriculas));
	}

	@FXML 
	public void selecionaAluno() {
		Pessoa aluno = cbAlunos.getSelectionModel().getSelectedItem();
		ArrayList<Matricula> matriculas = EnrollmentDAO.findByStudent(aluno);
		tbl.setItems(FXCollections.observableArrayList(matriculas));
	}

	private void eventoChangeAluno() {
	    cbAlunos.valueProperty().addListener((e, o, n) -> selecionaAluno());
	}
}
