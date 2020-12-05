package ui.controllers;

import java.util.ArrayList;

import dao.EnrollmentDAO;
import dao.SubjectDAO;
import domain.Subject;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ui.components.PopupAlert;

public class SubjectController {
	@FXML
	private TextField nameTextField;

	@FXML
	private TextField workloadTextField;

	@FXML
	private CheckBox isActiveCheckBox;

	@FXML
	private TextField filterTextField;

	@FXML
	private TableView<Subject> tableView;

	@FXML
	private TableColumn<Subject, String> nameTableColumn;

	@FXML
	private TableColumn<Subject, Number> workloadTableColumn;

	@FXML
	private TableColumn<Subject, Boolean> isActiveTableColumn;

	@FXML
	private Button saveButton;

	@FXML
	private Button deleteButton;

	private ArrayList<Subject> subjects;
	private Subject selectedSubject;

	@FXML
	public void initialize() {
		nameTableColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		workloadTableColumn.setCellValueFactory(cellData -> cellData.getValue().workloadProperty());
		isActiveTableColumn.setCellValueFactory(cellData -> cellData.getValue().isActiveProperty());

		saveButton.disableProperty().bind(Bindings.isEmpty(nameTextField.textProperty())
				.or(Bindings.isEmpty(workloadTextField.textProperty())));

		deleteButton.disableProperty().bind(tableView.getSelectionModel().selectedItemProperty().isNull());
		showAllSubjects();
	}

	@FXML
	void store() {
		try {
			Integer.parseInt(workloadTextField.getText());
		} catch(NumberFormatException e) {
			PopupAlert.error("ERRO", "O campo \"Carga Horária\" deve conter apenas números");
		}

		if(selectedSubject == null) {
			Subject subject = new Subject();
			subject.setName(nameTextField.getText());
			subject.setWorkload(Integer.parseInt(workloadTextField.getText()));
			subject.setIsActive(isActiveCheckBox.isSelected());
			SubjectDAO.create(subject);
			showAllSubjects();
			clearInputs();
		} else {
			Subject subjectBeingUpdated = new Subject();
			subjectBeingUpdated.setId(selectedSubject.getId());
			subjectBeingUpdated.setName(nameTextField.getText());
			subjectBeingUpdated.setWorkload(Integer.parseInt(workloadTextField.getText()));
			subjectBeingUpdated.setIsActive(isActiveCheckBox.isSelected());
			SubjectDAO.update(subjectBeingUpdated);
			selectedSubject = null;
			showAllSubjects();
			clearInputs();
		}
	}

	@FXML
	void delete() {
		Subject subject = tableView.getSelectionModel().getSelectedItem();
		if(PopupAlert.okOrCancel("Excluir", "Tem certeza que deseja excluir?") == ButtonType.OK) {
	    	SubjectDAO.delete(subject);
			tableView.getItems().remove(subject);
		}
	}

	@FXML
	void selectSubject() {
		selectedSubject = tableView.getSelectionModel().getSelectedItem();
		if(selectedSubject != null) {
			nameTextField.setText(selectedSubject.getName());
			workloadTextField.setText(selectedSubject.getWorkload()+"");
			isActiveCheckBox.setSelected(selectedSubject.getIsActive());
		}
	}

	@FXML
	void filter() {
		subjects = SubjectDAO.filter(filterTextField.getText());
		tableView.setItems(FXCollections.observableArrayList(subjects));
	}

	private void clearInputs() {
		nameTextField.setText("");
		workloadTextField.setText("");
		isActiveCheckBox.setSelected(false);
	}

	private void showAllSubjects() {
		subjects = SubjectDAO.all();
		tableView.setItems(FXCollections.observableArrayList(subjects));
	}
}