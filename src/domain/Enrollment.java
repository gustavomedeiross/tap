package domain;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Enrollment {
	private IntegerProperty id = new SimpleIntegerProperty(0);
	private Person student;
	private Subject subject;
	private StringProperty semester = new SimpleStringProperty("");

	public final IntegerProperty idProperty() {
		return this.id;
	}

	public final int getId() {
		return this.idProperty().get();
	}

	public final void setId(final int id) {
		this.idProperty().set(id);
	}

	public Person getStudent() {
		return student;
	}

	public void setStudent(Person student) {
		this.student = student;
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

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}
}
