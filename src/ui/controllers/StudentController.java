package ui.controllers;

import java.util.ArrayList;

import dao.PersonDAO;
import domain.Student;
import domain.Person;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class StudentController {
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

	@FXML
	Button saveButton;

	private ArrayList<Person> students;
	private Person student;

	@FXML
	public void initialize() {
		nameTableColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		phoneTableColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
		emailTableColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

		saveButton.disableProperty().bind(Bindings.isEmpty(nameTextField.textProperty())
				.or(Bindings.isEmpty(birthDateTextField.textProperty()))
				.or(Bindings.isEmpty(emailTextField.textProperty()))
				.or(Bindings.isEmpty(phoneTextField.textProperty())));

		showAllStudents();
	}

	@FXML
	public void store() {
		if (student == null) {
			student = new Student();
			student.setName(nameTextField.getText());
			student.setBirthDate(birthDateTextField.getText());
			student.setGender(maleRadioButton.isSelected() ? "M" : "F");
			student.setEmail(emailTextField.getText());
			student.setPhone(phoneTextField.getText());
			student.setIsActive(isActiveCheckBox.isSelected());
			PersonDAO.create(student);
			student = null;
			clearInputs();
			showAllStudents();;
		} else {
			Student studentBeingUpdated = new Student();
			studentBeingUpdated.setId(student.getId());
			studentBeingUpdated.setName(nameTextField.getText());
			student.setBirthDate(birthDateTextField.getText());
			studentBeingUpdated.setGender(maleRadioButton.isSelected()?"M":"F");
			studentBeingUpdated.setEmail(emailTextField.getText());
			studentBeingUpdated.setPhone(phoneTextField.getText());
			student.setBirthDate(birthDateTextField.getPromptText());
			studentBeingUpdated.setIsActive(isActiveCheckBox.isSelected());
			PersonDAO.update(studentBeingUpdated);
			student = null;
			clearInputs();
			showAllStudents();
		}
	}


	private void clearInputs() {
		nameTextField.setText("");
		birthDateTextField.setText("");
		maleRadioButton.setSelected(false);
		femaleRadioButton.setSelected(false);
		emailTextField.setText("");
		phoneTextField.setText("");
		isActiveCheckBox.setSelected(false);
	}

	@FXML
	public void selectUserOnTable() {
		student = tableView.getSelectionModel().getSelectedItem();
		if (student != null) {
			nameTextField.setText(student.getName());
			emailTextField.setText(student.getEmail());
			phoneTextField.setText(student.getPhone());
			birthDateTextField.setPromptText(student.getBirthDate());
			if (student.getGender().equals("M")) {
				maleRadioButton.setSelected(true);
				femaleRadioButton.setSelected(false);
			} else {
				maleRadioButton.setSelected(false);
				femaleRadioButton.setSelected(true);
			}

			isActiveCheckBox.setSelected(student.getIsActive());
		}
	}

	private void showAllStudents() {
		students = PersonDAO.all("A");
		tableView.setItems(FXCollections.observableArrayList(students));
	}

	@FXML
	public void filter() {
		students = PersonDAO.filter("A", filterTextField.getText());
		tableView.setItems(FXCollections.observableArrayList(students));
	}


}
