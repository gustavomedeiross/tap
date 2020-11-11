package domain;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Matricula {
	private IntegerProperty id = new SimpleIntegerProperty(0);
	private Pessoa aluno;
	private Disciplina disciplina;
	
	public Pessoa getAluno() {
		return aluno;
	}

	public void setAluno(Pessoa professor) {
		this.aluno = professor;
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
