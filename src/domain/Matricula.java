package domain;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Matricula {
	private IntegerProperty id = new SimpleIntegerProperty(0);
	private Pessoa aluno;
	private Disciplina disciplina;
	private StringProperty semestre = new SimpleStringProperty("");

	public Pessoa getAluno() {
		return aluno;
	}

	public void setAluno(Pessoa aluno) {
		this.aluno = aluno;
	}

	public String getSemestre() {
		return semestre.get();
	}

	public StringProperty semestreProperty() {
		return semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre.set(semestre);
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public final IntegerProperty idProperty() {
		return this.id;
	}
	
	public final int getId() {
		return this.idProperty().get();
	}
	
	public final void setId(final int id) {
		this.idProperty().set(id);
	}
}
