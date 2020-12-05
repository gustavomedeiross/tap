package ui.controllers;

import java.util.ArrayList;

import dao.SubjectDAO;
import dao.EnrollmentDAO;
import dao.PersonDAO;
import domain.Subject;
import domain.Enrollment;
import domain.Person;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ui.components.PopupAlert;

public class EnrollmentController {
	@FXML
	TableView<Enrollment> tableView;

	@FXML
	TableColumn<Enrollment, String> nameTableColumn;

	@FXML
	TableColumn<Enrollment, String> semesterTableColumn;

	@FXML
	ComboBox<Person> studentComboBox;

	@FXML
	ComboBox<Subject> subjectComboBox;

	@FXML
	TextField semesterTextField;


	@FXML
	Button saveButton;

	@FXML
	Button destroyButton;


	public void initialize() {
		subjectComboBox.setItems(FXCollections.observableArrayList(SubjectDAO.all(true)));
		studentComboBox.setItems(FXCollections.observableArrayList(PersonDAO.all("A", true)));
		nameTableColumn.setCellValueFactory(cellData -> cellData.getValue().getSubject().nameProperty());
		semesterTableColumn.setCellValueFactory(cellData -> cellData.getValue().semesterProperty());
		changeStudentEvent();

		saveButton.disableProperty().bind(Bindings.isNull(studentComboBox.valueProperty())
				.or(Bindings.isNull(subjectComboBox.valueProperty())));
		destroyButton.disableProperty().bind(tableView.getSelectionModel().selectedItemProperty().isNull());
	}

	@FXML
	public void selectStudent() {
		Person aluno = studentComboBox.getSelectionModel().getSelectedItem();
		ArrayList<Enrollment> matriculas = EnrollmentDAO.findByStudent(aluno);
		tableView.setItems(FXCollections.observableArrayList(matriculas));
	}

	private void changeStudentEvent() {
		studentComboBox.valueProperty().addListener((e, o, n) -> selectStudent());
	}

	@FXML
	public void findEnrollmentsByStudentAndSemester() {
		Person student = studentComboBox.getSelectionModel().getSelectedItem();
		String semester = semesterTextField.getText();
		ArrayList<Enrollment> enrollments = EnrollmentDAO.findByStudentAndSemester(student, semester);
		tableView.setItems(FXCollections.observableArrayList(enrollments));
	}

	@FXML
	public void store() {
		Person student = studentComboBox.getSelectionModel().getSelectedItem();
		Subject subject = subjectComboBox.getSelectionModel().getSelectedItem();
		String semestre = semesterTextField.getText();

		if(student != null && subject != null && semestre != null) {
			Enrollment m = new Enrollment();
			m.setStudent(student);
			m.setSubject(subject);
			m.setSemester(semestre);
			EnrollmentDAO.create(m);
			subjectComboBox.getSelectionModel().select(-1);
			selectStudent();
		} else {
			PopupAlert.error("ERRO", "selecione um aluno e uma disciplina");
		}
	}

	@FXML
	public void destroy() {
		Enrollment enrollment =  tableView.getSelectionModel().getSelectedItem();
		if(PopupAlert.okOrCancel("exclusão", "tem certeza que deseja excluir?") == ButtonType.OK) {
			EnrollmentDAO.delete(enrollment);
			tableView.getItems().remove(enrollment);
		}
	}
}
