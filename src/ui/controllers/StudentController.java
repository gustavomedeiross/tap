package ui.controllers;

import java.util.ArrayList;

import dao.PersonDAO;
import domain.Student;
import domain.Person;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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

	private ArrayList<Person> students;
	private Person student;

	@FXML
	public void initialize() {
		nameTableColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		phoneTableColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
		emailTableColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
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
			showAllStudents();
		}
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
