package domain;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Pessoa {
	IntegerProperty id = new SimpleIntegerProperty(0);
	StringProperty nome = new SimpleStringProperty("");
	StringProperty email = new SimpleStringProperty("");
	StringProperty telefone = new SimpleStringProperty("");
	StringProperty nascimento = new SimpleStringProperty("");
	StringProperty sexo = new SimpleStringProperty("");
	StringProperty tipo = new SimpleStringProperty("");
	BooleanProperty ativo = new SimpleBooleanProperty(true);
	public final IntegerProperty idProperty() {
		return this.id;
	}
	@Override
	public String toString() {
		
		return getNome();
	}
	
	public final int getId() {
		return this.idProperty().get();
	}
	
	public final void setId(final int id) {
		this.idProperty().set(id);
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
	
	public final StringProperty emailProperty() {
		return this.email;
	}
	
	public final String getEmail() {
		return this.emailProperty().get();
	}
	
	public final void setEmail(final String email) {
		this.emailProperty().set(email);
	}
	
	public final StringProperty telefoneProperty() {
		return this.telefone;
	}
	
	public final String getTelefone() {
		return this.telefoneProperty().get();
	}
	
	public final void setTelefone(final String telefone) {
		this.telefoneProperty().set(telefone);
	}
	
	public final StringProperty nascimentoProperty() {
		return this.nascimento;
	}
	
	public final String getNascimento() {
		return this.nascimentoProperty().get();
	}
	
	public final void setNascimento(final String nascimento) {
		this.nascimentoProperty().set(nascimento);
	}
	
	public final StringProperty sexoProperty() {
		return this.sexo;
	}
	
	public final String getSexo() {
		return this.sexoProperty().get();
	}
	
	public final void setSexo(final String sexo) {
		this.sexoProperty().set(sexo);
	}
	
	public final StringProperty tipoProperty() {
		return this.tipo;
	}
	
	public final String getTipo() {
		return this.tipoProperty().get();
	}
	
	public final void setTipo(final String tipo) {
		this.tipoProperty().set(tipo);
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
	

}
