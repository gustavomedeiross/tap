package ui.controllers;

import dao.EnrollmentDAO;
import dao.GradeHistoryDAO;
import dao.PersonDAO;
import dao.SubjectDAO;
import domain.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ui.components.PopupAlert;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class GradeHistoryController {
    @FXML
    private TableView<GradeHistory> tableView;

    @FXML
    private TableColumn<GradeHistory, String> subjectTableColumn;

    @FXML
    private TableColumn<GradeHistory, Number> gradeTableColumn;

    @FXML
    private ComboBox<Person> studentComboBox;

    @FXML
    private ComboBox<Subject> subjectComboBox;

    @FXML
    private TextField semesterTextField;

    @FXML
    private TextField gradeTextField;

    @FXML
    private Button saveButton;

    @FXML
    private Button destroyButton;

    private GradeHistory selectedGradeHistory;


    public void initialize() {
        subjectComboBox.setItems(FXCollections.observableArrayList(SubjectDAO.all(true)));
        studentComboBox.setItems(FXCollections.observableArrayList(PersonDAO.all("A", true)));
        subjectTableColumn.setCellValueFactory(cellData -> cellData.getValue().getSubject().nameProperty());
        gradeTableColumn.setCellValueFactory(cellData -> cellData.getValue().gradeProperty());

        changeSubjectEvent();
        changeStudentEvent();
        selectGradeEvent();
        destroyButton.disableProperty().bind(tableView.getSelectionModel().selectedItemProperty().isNull());
    }

    private void changeSubjectEvent() {
        subjectComboBox.valueProperty().addListener(new ChangeListener<Subject>() {
            @Override
            public void changed(ObservableValue<? extends Subject> observableValue, Subject subject, Subject t1) {
                gradeTextField.setDisable(true);
                saveButton.setDisable(true);

                if (!semesterTextField.getText().equals("") &&
                        studentComboBox.getSelectionModel().getSelectedItem() != null &&
                        subjectComboBox.getSelectionModel().getSelectedItem() != null) {
                    Person p = studentComboBox.getSelectionModel().getSelectedItem();
                    Subject s = subjectComboBox.getSelectionModel().getSelectedItem();

                    if (EnrollmentDAO.studentHasEnrollment(p, s, semesterTextField.getText())) {
                        gradeTextField.setDisable(false);
                        saveButton.setDisable(false);
                    }
                }
            }
        });
    }

    private void changeStudentEvent() {
        studentComboBox.valueProperty().addListener(new ChangeListener<Person>() {
            @Override
            public void changed(ObservableValue<? extends Person> observableValue, Person person, Person t1) {
                if (!semesterTextField.getText().equals("") &&
                        studentComboBox.getSelectionModel().getSelectedItem() != null) {
                    fillTable();
                }
            }
        });
    }

    private void selectGradeEvent() {
        tableView.getSelectionModel().selectedItemProperty().addListener((e, o, n) -> selectGradeHistory());
    }


    @FXML
    void selectGradeHistory() {
        selectedGradeHistory = tableView.getSelectionModel().getSelectedItem();
        if (selectedGradeHistory != null) {
            semesterTextField.setText(selectedGradeHistory.getSemester());
            gradeTextField.setText(String.valueOf(selectedGradeHistory.getGrade()));
            subjectComboBox.getSelectionModel().select(selectedGradeHistory.getSubject());
            // JavaFX issue, maybe try inside a Platform.runLater(() -> );
            // studentComboBox.getSelectionModel().select(selectedGradeHistory.getStudent());
        }
    }
    @FXML
    private void fillTable() {
        Person student = studentComboBox.getSelectionModel().getSelectedItem();
        String semester = semesterTextField.getText();
        ArrayList<GradeHistory> gradeHistories = GradeHistoryDAO.getGradesByStudentAndSemester(student, semester);
        tableView.setItems(FXCollections.observableArrayList(gradeHistories));
    }

    @FXML
    public void save() {
        Person student = studentComboBox.getSelectionModel().getSelectedItem();
        Subject subject = subjectComboBox.getSelectionModel().getSelectedItem();
        String semester = semesterTextField.getText();
        Double grade = Double.parseDouble(gradeTextField.getText());

        if(student != null && subject != null && semester != null && grade != null) {
            if ( ! EnrollmentDAO.studentHasEnrollment(student, subject, semester)) {
                PopupAlert.error("ERRO", "Sem matrícula para os dados selecionados");
                return;
            }

            if (GradeHistoryDAO.studentHasGradeForSubjectInSemester(student, subject, semester)) {
                PopupAlert.error("ERRO", "O aluno já possui uma nota para essa matéria no semestre!");
                return;
            }

            GradeHistory gh = new GradeHistory();
            gh.setStudent(student);
            gh.setSubject(subject);
            gh.setSemester(semester);
            gh.setGrade(grade);

            if (selectedGradeHistory == null) {
                GradeHistoryDAO.create(gh);
            } else {
                selectedGradeHistory.setGrade(Double.parseDouble(gradeTextField.getText()));
                GradeHistoryDAO.updateGrade(selectedGradeHistory);
                selectedGradeHistory = null;
            }

            fillTable();
//            subjectComboBox.getSelectionModel().select(-1); // TODO remove?
        } else {
            PopupAlert.error("ERRO", "Todos os campos são obrigatórios");
        }
    }

    @FXML
    public void destroy() {
        GradeHistory gradeHistory = tableView.getSelectionModel().getSelectedItem();

        if(PopupAlert.okOrCancel("exclusão", "tem certeza que deseja excluir?") == ButtonType.OK) {
            GradeHistoryDAO.delete(gradeHistory);
            tableView.getItems().remove(gradeHistory);
        }
    }

    @FXML
    public void exportHTML() {
        if (tableView.getItems().size() > 0) {
            String html = "<html>" +
                    "<table>" +
                    "<thead><tr><th>Disciplina</th><th>Carga Horária</th><th>Nota</th><th>Situação</th><tr><thead/>";

            for (GradeHistory gh : tableView.getItems()) {

                String situation = gh.getGrade() >= 7 ? "Aprovado(a)" : "Reprovado(a)";

                html += "<tr>";
                html += "<td>" + gh.getSubject().getName() + "</td>";
                html += "<td>" + gh.getSubject().getWorkload() + "</td>";
                html += "<td>" + gh.getGrade() + "</td>";
                html += "<td>" + situation + "</td>";
                html += "</tr>";
            }

            html += "</table>" +
                    "</html>";

            try {
                FileWriter fw = new FileWriter(new File("relatorio01.html"));
                BufferedWriter bw = new BufferedWriter(fw);
                bw.append(html);
                bw.close();
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void exportCSV() {
        if (tableView.getItems().size() > 0) {
            String csv = "Disciplina,Carga Horária,Nota,Situação\n";

            for (GradeHistory gh : tableView.getItems()) {
                String situation = gh.getGrade() >= 7 ? "Aprovado(a)" : "Reprovado(a)";

                csv += gh.getSubject().getName() + "," +
                        gh.getSubject().getWorkload() + "," +
                        gh.getGrade() + "," +
                        situation + "\n";
            }

            try {
                FileWriter fw = new FileWriter(new File("relatorio01.csv"));
                BufferedWriter bw = new BufferedWriter(fw);
                bw.append(csv);
                bw.close();
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
