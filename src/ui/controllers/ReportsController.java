package ui.controllers;

import dao.GradeHistoryDAO;
import dao.PersonDAO;
import domain.GradeHistory;
import domain.Person;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class ReportsController {
    @FXML
    private TextField semesterTextField;

    @FXML
    private Button exportSemesterHTMLButton;

    @FXML
    private Button exportSemesterCSVButton;

    @FXML
    private ComboBox<Person> studentComboBox;

    @FXML
    private Button exportStudentHTMLButton;

    @FXML
    private Button exportStudentCSVButton;

    public void initialize() {
        exportSemesterHTMLButton.disableProperty().bind(semesterTextField.textProperty().isEmpty());
        exportSemesterCSVButton.disableProperty().bind(semesterTextField.textProperty().isEmpty());

        studentComboBox.setItems(FXCollections.observableArrayList(PersonDAO.all("A", true)));
        exportStudentHTMLButton.disableProperty().bind(studentComboBox.valueProperty().isNull());
        exportStudentCSVButton.disableProperty().bind(studentComboBox.valueProperty().isNull());
    }

    @FXML
    public void exportSemesterHTML() {
        String semester = semesterTextField.getText();

        String html = "<html>" +
                "<head><meta charset=utf-8 /></head>" +
                "<body>";

        ArrayList<ArrayList<GradeHistory>> studentsGradesOnSemester = GradeHistoryDAO.getStudentsGradesBySemester(semester);

        for (ArrayList<GradeHistory> studentGrades: studentsGradesOnSemester) {
            if (studentGrades.isEmpty()) continue;

            html += "<h3>Aluno:" + studentGrades.get(0).getStudent().getName() + "</h3>";

             html += "<table border='1' cellspacing='0' cellpadding='0'>" +
                    "<thead><tr><th>Disciplina</th><th>Carga Horária</th><th>Nota</th><th>Situação</th><tr><thead/>";

            for (GradeHistory gh: studentGrades) {
                String situation = gh.getGrade() >= 7 ? "Aprovado(a)" : "Reprovado(a)";
                html += "<tr>";
                html += "<td>" + gh.getSubject().getName() + "</td>";
                html += "<td>" + gh.getSubject().getWorkload() + "</td>";
                html += "<td>" + gh.getGrade() + "</td>";
                html += "<td>" + situation + "</td>";
                html += "</tr>";
            }

            html += "</table>";
        }

        html += "</body>" +
                "</html>";

        try {
            FileWriter fw = new FileWriter(new File("relatorio-semestre.html"));
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(html);
            bw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void exportSemesterCSV() {
        String semester = semesterTextField.getText();

        String csv = "";

        ArrayList<ArrayList<GradeHistory>> studentsGradesOnSemester = GradeHistoryDAO.getStudentsGradesBySemester(semester);

        for (ArrayList<GradeHistory> studentGrades: studentsGradesOnSemester) {
            if (studentGrades.isEmpty()) continue;

            csv += "Disciplina,Carga Horária,Nota,Situação";

            for (GradeHistory gh: studentGrades) {
                String situation = gh.getGrade() >= 7 ? "Aprovado(a)" : "Reprovado(a)";
                csv += gh.getSubject().getName() + "," +
                        gh.getSubject().getWorkload() + "," +
                        gh.getGrade() + "," +
                        situation;
            }
        }

        try {
            FileWriter fw = new FileWriter(new File("relatorio-semestre.csv"));
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(csv);
            bw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void exportStudentHTML() {
        Person student = studentComboBox.getSelectionModel().getSelectedItem();

        String html = "<html>" +
                "<head><meta charset=utf-8 /></head>" +
                "<body>";

        ArrayList<ArrayList<GradeHistory>> studentsGradesOnSemester = GradeHistoryDAO.getStudentGradesOnEachSemester(student);

        for (ArrayList<GradeHistory> studentGrades: studentsGradesOnSemester) {
            if (studentGrades.isEmpty()) continue;

            html += "<h3> Semestre:" + studentGrades.get(0).getSemester() + "</h3>";

            html += "<table border='1' cellspacing='0' cellpadding='0'>" +
                    "<thead><tr><th>Disciplina</th><th>Carga Horária</th><th>Nota</th><th>Situação</th><tr><thead/>";

            for (GradeHistory gh: studentGrades) {
                String situation = gh.getGrade() >= 7 ? "Aprovado(a)" : "Reprovado(a)";
                html += "<tr>";
                html += "<td>" + gh.getSubject().getName() + "</td>";
                html += "<td>" + gh.getSubject().getWorkload() + "</td>";
                html += "<td>" + gh.getGrade() + "</td>";
                html += "<td>" + situation + "</td>";
                html += "</tr>";
            }

            html += "</table>";
        }

        html += "</body>" +
                "</html>";

        try {
            FileWriter fw = new FileWriter(new File("relatorio-aluno.html"));
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(html);
            bw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void exportStudentCSV() {
        Person student = studentComboBox.getSelectionModel().getSelectedItem();

        String csv = "";

        ArrayList<ArrayList<GradeHistory>> studentsGradesOnSemester = GradeHistoryDAO.getStudentGradesOnEachSemester(student);

        for (ArrayList<GradeHistory> studentGrades: studentsGradesOnSemester) {
            if (studentGrades.isEmpty()) continue;

            csv += "Disciplina,Carga Horária,Nota,Situação";

            for (GradeHistory gh: studentGrades) {
                String situation = gh.getGrade() >= 7 ? "Aprovado(a)" : "Reprovado(a)";
                csv += gh.getSubject().getName() + "," +
                        gh.getSubject().getWorkload() + "," +
                        gh.getGrade() + "," +
                        situation;
            }
        }

        try {
            FileWriter fw = new FileWriter(new File("relatorio-aluno.csv"));
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(csv);
            bw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
