package view;

import java.util.ArrayList;

import dao.PessoaDAO;
import domain.Aluno;
import domain.Pessoa;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CadAlunosController {
	@FXML TextField txtNome;
	@FXML TextField txtEmail;
	@FXML TextField txtTelefone;
	@FXML TextField txtFiltro;
	@FXML TextField txtNascimento;
	@FXML RadioButton rdMasc;
	@FXML RadioButton rdFemi;
	@FXML CheckBox ckAtivo;
	@FXML TableView<Pessoa> tbl;
	@FXML TableColumn<Pessoa, String> colNome;
	@FXML TableColumn<Pessoa, String> colTelefone;
	@FXML TableColumn<Pessoa, String> colEmail;

	private ArrayList<Pessoa> listaAlunos;
	private Pessoa a;

	@FXML
	public void initialize() {
		colNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
		colTelefone.setCellValueFactory(cellData -> cellData.getValue().telefoneProperty());
		colEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
		mostraTodsAlunos();
	}

	@FXML
	public void gravar() {
		if(a == null) {
			a = new Aluno();
			a.setNome(txtNome.getText());
			a.setNascimento(txtNascimento.getText());
			a.setSexo(rdMasc.isSelected()?"M":"F");
			a.setEmail(txtEmail.getText());
			a.setTelefone(txtTelefone.getText());
			a.setAtivo(ckAtivo.isSelected());
			PessoaDAO.novaPessoa(a);
			a = null;
			mostraTodsAlunos();;
		}else {
			Aluno altera = new Aluno();
			altera.setId(a.getId());
			altera.setNome(txtNome.getText());
			a.setNascimento(txtNascimento.getText());
			altera.setSexo(rdMasc.isSelected()?"M":"F");
			altera.setEmail(txtEmail.getText());
			altera.setTelefone(txtTelefone.getText());
			a.setNascimento(txtNascimento.getPromptText());
			altera.setAtivo(ckAtivo.isSelected());
			PessoaDAO.alteraPessoa(altera);
			a = null;
			mostraTodsAlunos();
		}

	}


	@FXML
	public void clicouTbl() {
		a = tbl.getSelectionModel().getSelectedItem();
		if(a != null) {
			txtNome.setText(a.getNome());
			txtEmail.setText(a.getEmail());
			txtTelefone.setText(a.getTelefone());
			txtNascimento.setPromptText(a.getNascimento());
			if(a.getSexo().equals("M")) {
				rdMasc.setSelected(true);
				rdFemi.setSelected(false);
			}else {
				rdMasc.setSelected(false);
				rdFemi.setSelected(true);
			}
			ckAtivo.setSelected(a.isAtivo());

		}
	}

	private void mostraTodsAlunos() {
		listaAlunos = PessoaDAO.listaTodas("A");
		tbl.setItems(FXCollections.observableArrayList(listaAlunos));
	}

	@FXML
	public void filtrar() {
		listaAlunos = PessoaDAO.filtra("A",txtFiltro.getText());
		tbl.setItems(FXCollections.observableArrayList(listaAlunos));
	}


}
