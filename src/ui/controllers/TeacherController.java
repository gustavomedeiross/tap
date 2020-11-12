package ui.controllers;

import java.util.ArrayList;
import dao.PersonDAO;
import domain.Person;
import domain.Teacher;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TeacherController {
	@FXML
	TextField nameTextField;

	@FXML
	TextField emailTextField;

	@FXML
	TextField phoneTextField;

	@FXML
	TextField filterTextField;

	@FXML
	TextField birthDateTextField;

	@FXML
	RadioButton maleRadioButton;

	@FXML
	RadioButton femaleRadioButton;

	@FXML
	CheckBox isActiveCheckBox;

	@FXML
	TableView<Person> tableView;

	@FXML
	TableColumn<Person, String> nameTableColumn;

	@FXML
	TableColumn<Person, String> phoneTableColumn;

	@FXML
	TableColumn<Person, String> emailTableColumn;

	private ArrayList<Person> teachers;

	private Person selectedTeacher;

	@FXML
	public void initialize() {
		nameTableColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		phoneTableColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
		emailTableColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
		showAllTeachers();
	}

	@FXML
	public void store() {
		if (selectedTeacher == null) {
			selectedTeacher = new Teacher();
			selectedTeacher.setName(nameTextField.getText());
			selectedTeacher.setBirthDate(birthDateTextField.getText());
			selectedTeacher.setGender(maleRadioButton.isSelected()?"M":"F");
			selectedTeacher.setEmail(emailTextField.getText());
			selectedTeacher.setPhone(phoneTextField.getText());
			selectedTeacher.setIsActive(isActiveCheckBox.isSelected());
			PersonDAO.create(selectedTeacher);
			selectedTeacher = null;
			showAllTeachers();;
		} else {
			Teacher teacherBeingUpdated = new Teacher();
			teacherBeingUpdated.setId(selectedTeacher.getId());
			teacherBeingUpdated.setName(nameTextField.getText());
			selectedTeacher.setBirthDate(birthDateTextField.getText());
			teacherBeingUpdated.setGender(maleRadioButton.isSelected()?"M":"F");
			teacherBeingUpdated.setEmail(emailTextField.getText());
			teacherBeingUpdated.setPhone(phoneTextField.getText());
			selectedTeacher.setBirthDate(birthDateTextField.getPromptText());
			teacherBeingUpdated.setIsActive(isActiveCheckBox.isSelected());
			PersonDAO.update(teacherBeingUpdated);
			selectedTeacher = null;
			showAllTeachers();
		}
	}

	@FXML
	public void selectTeacher() {
		selectedTeacher = tableView.getSelectionModel().getSelectedItem();
		if (selectedTeacher != null) {
			nameTextField.setText(selectedTeacher.getName());
			emailTextField.setText(selectedTeacher.getEmail());
			phoneTextField.setText(selectedTeacher.getPhone());
			birthDateTextField.setPromptText(selectedTeacher.getBirthDate());
			if (selectedTeacher.getGender().equals("M")) {
				maleRadioButton.setSelected(true);
				femaleRadioButton.setSelected(false);
			} else {
				maleRadioButton.setSelected(false);
				femaleRadioButton.setSelected(true);
			}
			isActiveCheckBox.setSelected(selectedTeacher.getIsActive());
		}
	}

	private void showAllTeachers() {
		teachers = PersonDAO.all("P");
		tableView.setItems(FXCollections.observableArrayList(teachers));
	}

	@FXML
	public void filter() {
		teachers = PersonDAO.filter("P", filterTextField.getText());
		tableView.setItems(FXCollections.observableArrayList(teachers));
	}
}
    