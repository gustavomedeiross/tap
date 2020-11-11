package domain;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Disciplina {
	
	private IntegerProperty id = new SimpleIntegerProperty(0);
	private StringProperty nome = new SimpleStringProperty("");
	private IntegerProperty cargaHoraria = new SimpleIntegerProperty(0);
	private BooleanProperty ativo = new SimpleBooleanProperty(false);
	
	@Override
	public String toString() {
		
		return getNome();
	}
	
	public final StringProperty nomeProperty() {
		return this.nome;
	}
	
	public final String getNome() {
		return this.nomeProperty().get();
	}
	
	public final void setNome(final String nome) {
		this.nomeProperty().set(nome);
	}
	
	public final IntegerProperty cargaHorariaProperty() {
		return this.cargaHoraria;
	}
	
	public final int getCargaHoraria() {
		return this.cargaHorariaProperty().get();
	}
	
	public final void setCargaHoraria(final int cargaHoraria) {
		this.cargaHorariaProperty().set(cargaHoraria);
	}

	public final BooleanProperty ativoProperty() {
		return this.ativo;
	}
	

	public final boolean isAtivo() {
		return this.ativoProperty().get();
	}
	

	public final void setAtivo(final boolean ativo) {
		this.ativoProperty().set(ativo);
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
