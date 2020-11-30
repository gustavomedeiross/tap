package domain;

import javafx.beans.property.*;

public class GradeHistory {
    private IntegerProperty id = new SimpleIntegerProperty(0);
    private Person student = new Student();
    private Subject subject = new Subject();
    private StringProperty semester = new SimpleStringProperty();
    private DoubleProperty grade = new SimpleDoubleProperty(0);

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public Person getStudent() {
        return student;
    }

    public void setStudent(Person student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getSemester() {
        return semester.get();
    }

    public StringProperty semesterProperty() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester.set(semester);
    }

    public double getGrade() {
        return grade.get();
    }

    public DoubleProperty gradeProperty() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade.set(grade);
    }
}

