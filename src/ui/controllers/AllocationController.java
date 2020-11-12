package ui.controllers;

import java.util.ArrayList;

import dao.AllocationDAO;
import dao.SubjectDAO;
import dao.PersonDAO;
import domain.Allocation;
import domain.Subject;
import domain.Person;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ui.components.PopupAlert;


public class AllocationController {
	@FXML
	TableView<Subject> tableView;

	@FXML
	TableColumn<Subject, String> subjectTableColumn;

	@FXML
	TableColumn<Subject, Number> workloadTableColumn;

	@FXML
	ComboBox<Person> teacherComboBox;

	@FXML
	ComboBox<Subject> subjectComboBox;

	public void initialize() {
		subjectComboBox.setItems(FXCollections.observableArrayList(SubjectDAO.all(true)));
		teacherComboBox.setItems(FXCollections.observableArrayList(PersonDAO.all("P", true)));
		subjectTableColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		workloadTableColumn.setCellValueFactory(cellData -> cellData.getValue().workloadProperty());
		changeTeacherEvent();
	}

	@FXML
	public void selectTeacher() {
		Person teacher = teacherComboBox.getSelectionModel().getSelectedItem();
		ArrayList<Subject> subjects = AllocationDAO.findByTeacher(teacher);
		tableView.setItems(FXCollections.observableArrayList(subjects));
	}

	private void changeTeacherEvent() {
		teacherComboBox.valueProperty().addListener(new ChangeListener<Person>() {
			@Override
			public void changed(ObservableValue<? extends Person> arg0, Person arg1, Person arg2) {
				selectTeacher();
			}
		});
	}

	@FXML
	public void store() {
		Person teacher = teacherComboBox.getSelectionModel().getSelectedItem();
		Subject subject = subjectComboBox.getSelectionModel().getSelectedItem();
		if(teacher!= null && subject!=null) {
			Allocation allocation = new Allocation();
			allocation.setTeacher(teacher);
			allocation.setSubject(subject);
			AllocationDAO.create(allocation);
			selectTeacher();
			subjectComboBox.getSelectionModel().select(-1);
		} else {
			PopupAlert.error("ERRO", "selecione um professor e uma disciplina");
		}
	}

	@FXML
	public void destroy() {
		Person teacher = teacherComboBox.getSelectionModel().getSelectedItem();
		Subject subject = tableView.getSelectionModel().getSelectedItem();
		if(teacher!= null && subject!=null) {
			if(PopupAlert.okOrCancel("exclusão", "tem certeza que deseja excluir?")==ButtonType.OK) {
				Allocation allocation = new Allocation();
				allocation.setTeacher(teacher);
				allocation.setSubject(subject);
				AllocationDAO.delete(allocation);
				selectTeacher();
			}
		}
	}
}
